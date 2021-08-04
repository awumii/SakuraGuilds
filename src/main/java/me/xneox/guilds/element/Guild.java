package me.xneox.guilds.element;

import me.xneox.guilds.type.Division;
import me.xneox.guilds.type.Rank;
import me.xneox.guilds.util.ChunkUtils;
import me.xneox.guilds.util.HookUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class Guild implements Comparable<Guild> {
    // PERMANENT DATA
    private final String name;
    private final List<Member> members;
    private final List<String> chunks;
    private final Location nexusLocation;
    private final long creation;

    // CHANGEABLE DATA
    private final List<String> allies;
    private final List<String> invitations = new ArrayList<>();

    private Location home;
    private Inventory storage;

    private long shield;
    private int health;
    private int kills;
    private int deaths;
    private int money;
    private int maxSlots;
    private int maxChunks;
    private int maxStorage;
    private boolean deleteConfirm;

    public Guild(String name, List<Member> members, Location nexusLocation, long creation, List<String> allies, Location home,
                 List<String> chunks, long shield, int health, int kills, int deaths, int money,
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
        this.kills = kills;
        this.deaths = deaths;
        this.maxSlots = maxSlots;
        this.maxChunks = maxChunks;
        this.maxStorage = maxStorage;
        this.money = money;
        this.storage = Bukkit.createInventory(null, this.maxStorage);
        this.storage.setContents(storageContent);
    }

    // -----------------------------------------
    // CONTROLLERS
    // -----------------------------------------

    public String creationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm");
        return sdf.format(new Date(this.creation));
    }

    public void updateStorage() {
        ItemStack[] contentCopy = this.storage.getContents();
        this.storage = Bukkit.createInventory(null, this.maxStorage);
        this.storage.setContents(contentCopy);
    }

    public boolean isClaimed(Chunk chunk) {
        return this.chunks.contains(ChunkUtils.deserialize(chunk));
    }

    public boolean isNexusChunk(Chunk chunk) {
        return this.nexusLocation.getChunk().equals(chunk);
    }

    public Member leader() {
        return members.stream()
                .filter(member -> member.rank() == Rank.LEADER)
                .findFirst()
                .orElse(null);
    }

    public Member member(Player player) {
        return this.member(player.getName());
    }

    public Member member(String name) {
        return members.stream()
                .filter(member -> member.nickname().equals(name))
                .findFirst()
                .orElse(null);
    }

    public int trophies() {
        int total = this.members.stream()
                .map(member -> HookUtils.INSTANCE.userManager().getUser(member.uuid()))
                .mapToInt(User::trophies)
                .sum();

        return total / this.members.size();
    }

    public Division division() {
        return Arrays.stream(Division.values())
                .filter(value -> trophies() >= value.getMinPoints())
                .findFirst()
                .orElse(Division.BRONZE);
    }

    public boolean inside(Location location) {
        return location.getWorld().getName().equals("world")
                && this.chunks.stream().anyMatch(chunk -> ChunkUtils.isEqual(location.getChunk(), chunk));
    }

    public void changeRank(String player, Rank rank) {
        // Handle a situation where a leader is changed.
        if (rank == Rank.LEADER) {
            Member leader = this.leader();
            leader.rank(Rank.GENERAL);
        }

        Member member = this.member(player);
        member.rank(rank);
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

    public String name() {
        return name;
    }

    public long creationLong() {
        return creation;
    }

    public Location homeLocation() {
        return home;
    }

    public void homeLocation(Location home) {
        this.home = home;
    }

    public List<Member> members() {
        return members;
    }

    public List<String> claims() {
        return chunks;
    }

    public List<String> invitations() {
        return invitations;
    }

    public List<String> allies() {
        return allies;
    }

    public boolean deleteConfirmation() {
        return deleteConfirm;
    }

    public void deleteConfirmation(boolean deleteConfirm) {
        this.deleteConfirm = deleteConfirm;
    }

    public long shieldDuration() {
        return shield;
    }

    public void shieldDuration(Duration duration) {
        this.shield = System.currentTimeMillis() + duration.toMillis();
    }

    public Location nexusLocation() {
        return nexusLocation;
    }

    public int deaths() {
        return deaths;
    }

    public int kills() {
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
        this.updateStorage();
    }

    public int money() {
        return this.money;
    }

    public void money(int money) {
        this.money = money;
    }

    public int health() {
        return health;
    }

    public void health(int health) {
        this.health = health;
    }

    public Inventory storage() {
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

    @Override
    public int compareTo(@NotNull Guild guild) {
        return Integer.compare(guild.trophies(), this.trophies());
    }
}
