package me.xneox.guilds.enums;

import me.xneox.guilds.manager.ConfigManager;
import org.jetbrains.annotations.NotNull;

/**
 * Division is a way of grouping and ranking guilds with similar points range.
 */
public enum Division {
  CHAMPION(2000, ConfigManager.messages().divisions().champion()),
  ADAMANT(1700, ConfigManager.messages().divisions().adamant()),
  RUBY(1200, ConfigManager.messages().divisions().ruby()),
  CRYSTAL(1000, ConfigManager.messages().divisions().crystal()),
  GOLDEN(800, ConfigManager.messages().divisions().golden()),
  STEEL(600, ConfigManager.messages().divisions().steel()),
  BRONZE(0, ConfigManager.messages().divisions().bronze());

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
