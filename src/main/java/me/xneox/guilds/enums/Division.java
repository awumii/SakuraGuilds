package me.xneox.guilds.enums;

import me.xneox.guilds.util.text.ChatUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Division is a way of grouping and ranking guilds with similar points range.
 */
public enum Division {
  CHAMPION(2000, ChatUtils.ALIZARIN_RED + "Czempion"),
  ADAMANT(1700, "&2Adamantyt"),
  RUBY(1200, "&cRubin"),
  CRYSTAL(1000, "&bKryształ"),
  GOLDEN(800, "&6Złoto"),
  STEEL(600, "&fStal"),
  BRONZE(0, ChatUtils.BRONZE + "Brąz");

  private final int minPoints;
  private final String name;

  Division(int minPoints, @NotNull String name) {
    this.minPoints = minPoints;
    this.name = name;
  }

  @NotNull
  public String getName() {
    return name;
  }

  public int getMinPoints() {
    return minPoints;
  }
}
