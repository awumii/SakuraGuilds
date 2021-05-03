package me.xneox.guilds.element;

import me.xneox.guilds.type.BuildingType;
import me.xneox.guilds.type.Division;
import me.xneox.guilds.type.Rank;
import me.xneox.guilds.util.ChunkUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
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
    private final List<String> allies;
    private final List<Building> buildings;
    private Location home;
    private Inventory storage;

    private boolean isPublic;
    private long shield;

    private int health;
    private int money;
    private int trophies;
    private int kills;
    private int deaths;

    // TEMPORARY DATA
    private final List<String> invitations = new ArrayList<>();
    private boolean deleteConfirm;

    private Guild warEnemy;

    public Guild(String name, Map<String, Rank> members, Location nexusLocation, long creation, List<String> allies, Location home,
                 List<String> chunks, long shield, int health, int money, int trophies, int kills,
                 int deaths, boolean isPublic, ItemStack[] storageContent, List<Building> buildings) {

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
        this.money = money;
        this.kills = kills;
        this.deaths = deaths;
        this.isPublic = isPublic;
        this.buildings = buildings;
        this.storage = Bukkit.createInventory(null, this.getStorageRows());
        this.storage.setContents(storageContent);
    }

    // -----------------------------------------
    // CONTROLLERS
    // -----------------------------------------

    @Nullable
    public Building getBuildingOfType(BuildingType type) {
        return this.buildings.stream()
                .filter(building -> building.getType() == type)
                .findAny()
                .orElse(null);
    }

    public String getCreationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm");
        return sdf.format(new Date(this.creation));
    }

    public void updateStorage() {
        ItemStack[] contentCopy = this.storage.getContents();
        this.storage = Bukkit.createInventory(null, this.getStorageRows());
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
        if (rank == Rank.LEADER) {
            String leader = this.getLeader();
            this.members.put(leader, Rank.GENERAL);
        }
        this.members.put(player, rank);
    }

    public List<Player> getOnlineMembers() {
        return this.members.keySet().stream()
                .map(Bukkit::getPlayerExact)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public int getMaxMembers() {
        Building townhall = this.getBuildingOfType(BuildingType.TOWNHALL);
        return townhall != null ? (townhall.getLevel() + 1) * 4 : 4;
    }

    public int getMaxChunks() {
        Building barrack = this.getBuildingOfType(BuildingType.BARRACK);
        return barrack != null ? (barrack.getLevel() + 1) * 4 : 4;
    }

    public int getStorageRows() {
        Building storage = this.getBuildingOfType(BuildingType.STORAGE);
        return storage != null ? (storage.getLevel() + 1) * 9 : 9;
    }

    public boolean isShieldActive() {
        return shield >= System.currentTimeMillis();
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

    public Map<String, Rank> getMembers() {
        return members;
    }

    public List<String> getChunks() {
        return chunks;
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

    public List<Building> getBuildings() {
        return buildings;
    }
}
