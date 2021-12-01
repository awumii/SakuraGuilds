package me.xneox.guilds.element;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.enums.Division;
import me.xneox.guilds.enums.Rank;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Guild implements Comparable<Guild> {
  // PERMANENT DATA
  private final String name;
  private final List<Member> members;
  private final List<Chunk> chunks;
  private final Location nexusLocation;
  private final long creation;

  // CHANGEABLE DATA
  private final List<Guild> allies;

  private Location home;
  private Inventory storage;

  private long shield;
  private int health;
  private int maxHealth;
  private int money;
  private int maxSlots;
  private int maxChunks;
  private int maxStorage;
  private boolean deleteConfirm;

  // TEMPORARY DATA
  private final List<UUID> playerInvitations = new ArrayList<>();
  private final List<Guild> allyInvitations = new ArrayList<>();

  public Guild(
      @NotNull String name,
      @NotNull List<Member> members,
      @NotNull Location nexusLocation,
      @NotNull List<Guild> allies,
      @NotNull Location home,
      @NotNull List<Chunk> chunks,
      @NotNull ItemStack[] storageContent,
      long creation,
      long shield,
      int health,
      int maxHealth,
      int money,
      int maxSlots,
      int maxChunks,
      int maxStorage) {

    this.name = name;
    this.members = members;
    this.nexusLocation = nexusLocation;
    this.creation = creation;
    this.allies = allies;
    this.home = home;
    this.chunks = chunks;
    this.shield = shield;
    this.health = health;
    this.maxHealth = maxHealth;
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

  @NotNull
  public String creationDate() {
    var sdf = new SimpleDateFormat("dd/MM/yyy HH:mm");
    return sdf.format(new Date(this.creation));
  }

  /**
   * Recreates the storage Inventory object,
   * and copies the previous contents to the newly created inventory.
   */
  public void updateStorage() {
    var contentCopy = this.storage.getContents();
    this.storage = Bukkit.createInventory(null, this.maxStorage);
    this.storage.setContents(contentCopy);
  }

  public boolean isNexusChunk(@Nullable Chunk chunk) {
    return this.nexusLocation.getChunk().equals(chunk);
  }

  /**
   * Returns the leader of this guild.
   * Never returns null.
   * TODO: while this practically can't ever be null, the logic below specifies that it will return null if there is no member will the leader rank.
   */
  public Member leader() {
    return this.members.stream()
        .filter(member -> member.rank() == Rank.LEADER)
        .findFirst()
        .orElse(null);
  }

  /**
   * Returns the member object for the specified player.
   */
  public Member member(@NotNull Player player) {
    return this.member(player.getName());
  }

  /**
   * Finds a Member by their nickname.
   * Returns null if no member with the specified nickname is in the guild.
   */
  public Member member(@NotNull String name) {
    for (Member member : this.members) {
      if (name.equals(member.nickname())) {
        return member;
      }
    }
    return null;
  }

  /**
   * @return Sum of all member's trophies, divided by the member count.
   */
  public int trophies() {
    int total = 0;
    for (Member member : this.members) {
      var user = SakuraGuildsPlugin.get().userManager().user(member.uuid());
      int trophies = user.trophies();
      total += trophies;
    }

    return total / this.members.size();
  }

  /**
   * @return the division of this guild, based on the trophy amount.
   */
  @NotNull
  public Division division() {
    for (Division value : Division.values()) {
      if (trophies() >= value.getMinPoints()) {
        return value;
      }
    }
    return Division.BRONZE;
  }

  /**
   * @return Whenever this guild owns the chunk at the specified location.
   */
  public boolean inside(@NotNull Location location) {
    for (Chunk chunk : this.chunks) {
      if (location.getChunk().equals(chunk)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Changes the rank of the specified member.
   */
  public void changeRank(@NotNull Member member, Rank rank) {
    // Handle a situation where a leader is changed.
    if (rank == Rank.LEADER) {
      var leader = this.leader();
      leader.rank(Rank.GENERAL);
    }

    member.rank(rank);
  }

  /**
   * @return list of currently online players in this guild.
   */
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

  public boolean isMember(@NotNull String player) {
    return this.member(player) != null;
  }

  // -----------------------------------------
  // GETTERS AND SETTERS
  // -----------------------------------------

  @NotNull
  public String name() {
    return this.name;
  }

  public long creationLong() {
    return this.creation;
  }

  @NotNull
  public Location homeLocation() {
    return this.home;
  }

  public void homeLocation(@NotNull Location home) {
    this.home = home;
  }

  @NotNull
  public List<Member> members() {
    return this.members;
  }

  @NotNull
  public List<Chunk> claims() {
    return this.chunks;
  }

  @NotNull
  public List<UUID> playerInvitations() {
    return this.playerInvitations;
  }

  @NotNull
  public List<Guild> allyInvitations() {
    return this.allyInvitations;
  }

  @NotNull
  public List<Guild> allies() {
    return this.allies;
  }

  public boolean deleteConfirmation() {
    return this.deleteConfirm;
  }

  public void deleteConfirmation(boolean deleteConfirm) {
    this.deleteConfirm = deleteConfirm;
  }

  public long shieldDuration() {
    return this.shield;
  }

  public void shieldDuration(Duration duration) {
    this.shield = System.currentTimeMillis() + duration.toMillis();
  }

  @NotNull
  public Location nexusLocation() {
    return this.nexusLocation;
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
    return this.health;
  }

  public void health(int health) {
    this.health = health;
  }

  public int maxHealth() {
    return this.maxHealth;
  }

  public void maxHealth(int maxHealth) {
    this.maxHealth = maxHealth;
  }

  @NotNull
  public Inventory storage() {
    return this.storage;
  }

  @Override
  public int compareTo(@NotNull Guild guild) {
    return Integer.compare(guild.trophies(), this.trophies());
  }
}
