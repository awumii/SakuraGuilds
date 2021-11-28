package me.xneox.guilds.element;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import me.xneox.guilds.enums.Permission;
import me.xneox.guilds.enums.Rank;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a member of a {@link Guild}.
 */
public class Member {
  private final UUID uuid;

  private EnumSet<Permission> permissions;
  private Rank rank;
  private long joinDate;

  private Member(@NotNull UUID uuid, @NotNull Rank rank, long joinDate, @NotNull EnumSet<Permission> permissions) {
    this.uuid = uuid;
    this.rank = rank;
    this.joinDate = joinDate;
    this.permissions = permissions;
  }

  @NotNull
  public static Member create(@NotNull UUID uuid, @NotNull Rank rank, long joinDate) {
    return new Member(uuid, rank, joinDate, rank.defaultPermissions());
  }

  /**
   * Creates an Member object from a string in format
   * UUID;RANK;JOIN_DATE;PERMISSION;ANOTHER_PERMISSION...
   * @see Member#toString()
   */
  @NotNull
  public static Member serialize(@NotNull String string) {
    String[] split = string.split(";");

    return new Member(
        UUID.fromString(split[0]),
        Rank.valueOf(split[1]),
        Long.parseLong(split[2]),
        IntStream.range(3, split.length)
            .mapToObj(i -> Permission.valueOf(split[i]))
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(Permission.class))));
  }

  @NotNull
  public UUID uuid() {
    return this.uuid;
  }

  @Nullable
  public String nickname() {
    return Bukkit.getOfflinePlayer(this.uuid).getName();
  }

  @NotNull
  public String joinDate() {
    var sdf = new SimpleDateFormat("dd/MM/yyy HH:mm");
    return sdf.format(new Date(this.joinDate));
  }

  public long joinLong() {
    return this.joinDate;
  }

  public void joinDate(long joinDate) {
    this.joinDate = joinDate;
  }

  @NotNull
  public Rank rank() {
    return this.rank;
  }

  public void rank(@NotNull Rank rank) {
    this.rank = rank;
    this.permissions = rank.defaultPermissions();
  }

  public boolean hasPermission(Permission permission) {
    return this.permissions.contains(permission);
  }

  @NotNull
  public Set<Permission> permissions() {
    return this.permissions;
  }

  @NotNull
  public String displayName() {
    return this.rank.icon() + " " + nickname();
  }

  @Override
  public String toString() {
    return this.uuid
        + ";"
        + this.rank.name()
        + ";"
        + this.joinDate
        + ";"
        + this.permissions.stream().map(Enum::name).collect(Collectors.joining(";"));
  }
}
