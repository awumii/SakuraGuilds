package me.xneox.guilds.enums;

import static me.xneox.guilds.enums.Permission.ALLIES;
import static me.xneox.guilds.enums.Permission.BUILD;
import static me.xneox.guilds.enums.Permission.CLAIM;
import static me.xneox.guilds.enums.Permission.SET_HOME;
import static me.xneox.guilds.enums.Permission.UPGRADES;

import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.EnumSet;
import me.xneox.guilds.util.text.ChatUtils;
import org.jetbrains.annotations.NotNull;

// todo replace with object
@Deprecated
public enum Rank {
  LEADER(5, "Lider", ChatUtils.CRIMSON + "★★★★", Permission.values()), // all permissions.
  GENERAL(4, "Generał", "&d★★★", Permission.values()),
  OFFICER(3, "Oficer", "&b★★", BUILD, SET_HOME, CLAIM, UPGRADES, ALLIES),
  CORPORAL(2, "Kapral", "&6★", BUILD, SET_HOME, CLAIM),
  NEWBIE(1, "Rekrut", ChatUtils.BRONZE + "♧", BUILD);

  private final int weight;
  private final String name;
  private final String icon;
  private final EnumSet<Permission> defaultPermissions;

  Rank(int weight, @NotNull String name, @NotNull String icon, @NotNull Permission... defaultPermissions) {
    this.weight = weight;
    this.name = name;
    this.icon = icon;
    this.defaultPermissions = Sets.newEnumSet(Arrays.asList(defaultPermissions), Permission.class);
  }

  public boolean isHigher(@NotNull Rank compareTo) {
    return this.weight > compareTo.weight();
  }

  @NotNull
  public String title() {
    return icon + " " + name;
  }

  public int weight() {
    return weight;
  }

  @NotNull
  public String icon() {
    return icon;
  }

  @NotNull
  public EnumSet<Permission> defaultPermissions() {
    return this.defaultPermissions;
  }
}
