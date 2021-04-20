package me.xneox.guilds.element;

import com.google.common.collect.EvictingQueue;
import me.xneox.guilds.type.Division;
import me.xneox.guilds.type.Rank;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ChunkUtils;
import me.xneox.guilds.util.gui.InventorySize;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class Guild {
    // PERMANENT DATA
    private final String name;
    private final Map<String, Rank> members;
    private final List<String> chunks;
    private final Location nexusLocation;
    private final long creation;

    // CHANGEABLE DATA
    @SuppressWarnings("UnstableApiUsage")
    private final Queue<LogEntry> logQueue = EvictingQueue.create(6);
    private final List<String> allies;
    private final Inventory storage;

    private boolean isPublic;
    private long shield;
    private int health;
    private int money;
    private Location home;
    private int maxMembers;
    private int maxChunks;
    private int trophies;
    private int kills;
    private int deaths;

    // TEMPORARY DATA
    private final List<String> invitations = new ArrayList<>();
    private boolean deleteConfirm;

    private Guild warEnemy;

    public Guild(String name, Map<String, Rank> members, Location nexusLocation, long creation, List<String> allies, Location home,
                 List<String> chunks, long shield, int health, int money, int maxMembers, int maxChunks, int trophies, int kills,
                 int deaths, boolean isPublic, ItemStack[] storageContent) {

        this.name = name;
        this.members = members;
        this.nexusLocation = nexusLocation;
        this.creation = creation;
        this.allies = allies;
        this.home = home;
        this.chunks = chunks;
        this.shield = shield;
        this.health = health;
        this.maxMembers = maxMembers;
        this.maxChunks = maxChunks;
        this.trophies = trophies;
        this.money = money;
        this.kills = kills;
        this.deaths = deaths;
        this.isPublic = isPublic;
        this.storage = Bukkit.createInventory(null, InventorySize.BIGGEST.getSize());
        this.storage.setContents(storageContent);
    }

    // CONTROLLERS //

    public void log(String info) {
        this.logQueue.add(new LogEntry(System.currentTimeMillis(), info));
        ChatUtils.forGuildMembers(this, player -> ChatUtils.sendBossBar(player, BarColor.GREEN, "&a" + info));
    }

    public String getCreationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm");
        return sdf.format(new Date(this.creation));
    }

    public boolean isNexusChunk(Chunk chunk) {
        return this.nexusLocation.getChunk().equals(chunk);
    }

    public int getUpgradeCost(int number) {
        return number * 2000;
    }

    public String getDisplayName(Player player) {
        return getDisplayName(player.getName());
    }

    public String getDisplayName(String player) {
        return getPlayerRank(player).getIcon() + " " + player;
    }

    @Deprecated
    public Rank getPlayerRank(String player) {
        return members.get(player);
    }

    public Rank getPlayerRank(Player player) {
        return this.getPlayerRank(player.getName());
    }

    public String getLeader() {
        return members.keySet().stream().filter(member -> members.get(member) == Rank.LEADER).findFirst().orElse(null);
    }

    public boolean isHigher(String compareFrom, String compareTo) {
        return getPlayerRank(compareFrom).isHigher(getPlayerRank(compareTo));
    }

    public void addTrophies(int amount) {
        this.setTrophies(this.trophies + amount);
    }

    public void removeTrophies(int amount) {
        this.setTrophies(this.trophies - amount);
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
        if (this.trophies < 0) {
            this.trophies = 0;
        }
    }

    public Division getDivision() {
        for (Division value : Division.values()) {
            if (this.trophies >= value.getMinPoints()) {
                return value;
            }
        }
        return Division.BRONZE;
    }

    public boolean inside(Location location) {
        return location.getWorld().getName().equals("world") && this.chunks.stream().anyMatch(chunk -> ChunkUtils.isEqual(location.getChunk(), chunk));
    }

    public void changeRank(String player, Rank rank) {
        if (rank == Rank.LEADER) {
            String leader = this.getLeader();
            this.members.remove(leader);
            this.members.put(leader, Rank.GENERAL);
        }

        this.members.remove(player);
        this.members.put(player, rank);
    }

    public List<Player> getOnlineMembers() {
        return this.members.keySet().stream().map(Bukkit::getPlayerExact).filter(Objects::nonNull).collect(Collectors.toList());
    }

    // GETTERS AND SETTERS


    public Queue<LogEntry> getLogQueue() {
        return logQueue;
    }

    public String getName() {
        return name;
    }

    public long getCreationLong() {
        return creation;
    }

    public Location getHome() {
        return home;
    }

    public void setHome(Location home) {
        this.home = home;
    }

    public Map<String, Rank> getMembers() {
        return members;
    }

    public List<String> getChunks() {
        return chunks;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public int getMaxChunks() {
        return maxChunks;
    }

    public void setMaxChunks(int maxChunks) {
        this.maxChunks = maxChunks;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<String> getInvitations() {
        return invitations;
    }

    public List<String> getAllies() {
        return allies;
    }

    public boolean isDeleteConfirm() {
        return deleteConfirm;
    }

    public void setDeleteConfirm(boolean deleteConfirm) {
        this.deleteConfirm = deleteConfirm;
    }

    public int getTrophies() {
        return trophies;
    }

    public boolean isShieldActive() {
        return shield >= System.currentTimeMillis();
    }

    public long getShield() {
        return shield;
    }

    public void setShield(Duration duration) {
        this.shield = System.currentTimeMillis() + duration.toMillis();
    }

    public Location getNexusLocation() {
        return nexusLocation;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getKills() {
        return kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Guild getWarEnemy() {
        return warEnemy;
    }

    public void setWarEnemy(Guild warEnemy) {
        this.warEnemy = warEnemy;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Inventory getStorage() {
        return storage;
    }
}
