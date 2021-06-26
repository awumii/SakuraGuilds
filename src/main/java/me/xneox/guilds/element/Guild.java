package me.xneox.guilds.element;

import me.xneox.guilds.type.Division;
import me.xneox.guilds.type.Permission;
import me.xneox.guilds.type.Rank;
import me.xneox.guilds.util.ChunkUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
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
    private final List<Member> members;
    private final List<String> chunks;
    private final Location nexusLocation;
    private final long creation;

    // CHANGEABLE DATA
    private final List<String> allies;
    private Location home;
    private Inventory storage;

    private boolean isPublic;
    private long shield;

    private int health;
    private int trophies;
    private int kills;
    private int deaths;

    private int money;
    private int maxSlots;
    private int maxChunks;
    private int maxStorage;

    // TEMPORARY DATA
    private final List<String> invitations = new ArrayList<>();
    private boolean deleteConfirm;

    private Guild warEnemy;

    public Guild(String name, List<Member> members, Location nexusLocation, long creation, List<String> allies, Location home,
                 List<String> chunks, long shield, int health, int trophies, int kills, int deaths, int money, boolean isPublic,
                 int maxSlots, int maxChunks, int maxStorage, ItemStack[] storageContent) {

        this.name = name;
        this.members = members;
        this.nexusLocation = nexusLocation;
        this.creation = creation;
        this.allies = allies;
        this.home = home;
        this.chunks = chunks;
        this.shield = shield;
        this.health = health;
        this.trophies = trophies;
        this.kills = kills;
        this.deaths = deaths;
        this.maxSlots = maxSlots;
        this.maxChunks = maxChunks;
        this.maxStorage = maxStorage;
        this.money = money;
        this.isPublic = isPublic;
        this.storage = Bukkit.createInventory(null, this.maxStorage);
        this.storage.setContents(storageContent);
    }

    // -----------------------------------------
    // CONTROLLERS
    // -----------------------------------------

    public String getCreationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm");
        return sdf.format(new Date(this.creation));
    }

    public void updateStorage() {
        ItemStack[] contentCopy = this.storage.getContents();
        this.storage = Bukkit.createInventory(null, this.maxStorage);
        this.storage.setContents(contentCopy);
    }

    public boolean isClaimed(Chunk chunk) {
        return this.chunks.contains(ChunkUtils.toString(chunk));
    }

    public boolean isNexusChunk(Chunk chunk) {
        return this.nexusLocation.getChunk().equals(chunk);
    }

    public String getDisplayName(Player player) {
        return getDisplayName(player.getName());
    }

    public String getDisplayName(String player) {
        return getPlayerRank(player).getIcon() + " " + player;
    }

    @Deprecated
    public Rank getPlayerRank(String player) {
        return this.members.stream()
                .filter(member -> member.nickname().equals(player))
                .map(Member::rank)
                .findFirst()
                .orElse(null);
    }

    public Rank getPlayerRank(Player player) {
        return this.getPlayerRank(player.getName());
    }

    public boolean isLeader(Player player) {
        return this.isLeader(player.getName());
    }

    public boolean isLeader(String player) {
        return this.getLeader().nickname().equals(player);
    }

    public Member getLeader() {
        return members.stream()
                .filter(member -> member.rank() == Rank.LEADER)
                .findFirst()
                .orElse(null);
    }

    public Member member(String name) {
        return members.stream()
                .filter(member -> member.nickname().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Member member(Player player) {
        return this.member(player.getName());
    }

    public boolean isHigher(String compareFrom, String compareTo) {
        return getPlayerRank(compareFrom).isHigher(getPlayerRank(compareTo));
    }

    public void addTrophies(int amount) {
        this.setTrophies(this.trophies + amount);
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
        if (this.trophies < 0) {
            this.trophies = 0;
        }
    }

    public Division getDivision() {
        return Arrays.stream(Division.values())
                .filter(value -> this.trophies >= value.getMinPoints())
                .findFirst()
                .orElse(Division.BRONZE);
    }

    public boolean inside(Location location) {
        return location.getWorld().getName().equals("world")
                && this.chunks.stream().anyMatch(chunk -> ChunkUtils.isEqual(location.getChunk(), chunk));
    }

    public void changeRank(String player, Rank rank) {
        Member member = this.member(player);
        member.rank(rank);

        // Handle a situation where a leader is changed.
        if (rank == Rank.LEADER) {
            Member leader = this.getLeader();
            leader.rank(Rank.GENERAL);
        }
    }

    public void changePermission(String player, Permission permission, boolean state) {
        Member member = this.member(player);
        if (state) {
            member.permissions().add(permission);
        } else {
            member.permissions().remove(permission);
        }
    }

    public List<Player> getOnlineMembers() {
        return this.members.stream()
                .map(Member::nickname)
                .map(Bukkit::getPlayerExact)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public boolean isShieldActive() {
        return shield >= System.currentTimeMillis();
    }

    public boolean isMember(String player) {
        return this.member(player) != null;
    }

    // -----------------------------------------
    // GETTERS AND SETTERS
    // -----------------------------------------

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

    @Deprecated
    public List<Member> getMembers() {
        return members;
    }

    public List<String> getChunks() {
        return chunks;
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

    public void addKill() {
        this.kills++;
    }

    public void addDeath() {
        this.deaths++;
    }

    public int maxSlots() {
        return this.maxSlots;
    }

    public void maxSlots(int maxSlots) {
        this.maxSlots = maxSlots;
    }

    public int maxChunks() {
        return this.maxChunks;
    }

    public void maxChunks(int maxChunks) {
        this.maxChunks = maxChunks;
    }

    public int maxStorage() {
        return this.maxStorage;
    }

    public void maxStorage(int maxStorage) {
        this.maxStorage = maxStorage;
    }

    public int money() {
        return this.money;
    }

    public void money(int money) {
        this.money = money;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Guild guild = (Guild) o;

        return name.equals(guild.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
