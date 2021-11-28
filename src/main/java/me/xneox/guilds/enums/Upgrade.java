package me.xneox.guilds.enums;

import me.xneox.guilds.element.Guild;
import org.jetbrains.annotations.NotNull;

public enum Upgrade {
  SLOTS("Zwiększenie Slotów", 20, 6, 4),
  CHUNKS("Więcej Terenu", 64, 2, 6),
  STORAGE("Zwiększony Magazyn", 54, 6, 9);

  private final String title;
  private final int maxValue;     // Maximum value of the upgrade.
  private final int exponent;     // How much will be divided from upgrade cost.
  private final int multiplier;   // How much the upgrade level will increase.

  Upgrade(@NotNull String title, int maxValue, int exponent, int multiplier) {
    this.title = title;
    this.maxValue = maxValue;
    this.exponent = exponent;
    this.multiplier = multiplier;
  }

  @NotNull
  public String title() {
    return this.title;
  }

  /**
   * @return Maximum value of the upgrade.
   */
  public int maxValue() {
    return this.maxValue;
  }

  /**
   * @return How much the upgrade level will increase.
   */
  public int multiplier() {
    return this.multiplier;
  }

  /**
   * Performs the current upgrade on the specified guild.
   */
  public void performUpgrade(@NotNull Guild guild) {
    switch (this) {
      case SLOTS -> guild.maxSlots(guild.maxSlots() + this.multiplier);
      case CHUNKS -> guild.maxChunks(guild.maxChunks() + this.multiplier);
      case STORAGE -> guild.maxStorage(guild.maxStorage() + this.multiplier);
    }
  }

  /**
   * @return Value of the current upgrade of the specified guild.
   */
  public int currentValue(@NotNull Guild guild) {
    return switch (this) {
      case SLOTS -> guild.maxSlots();
      case CHUNKS -> guild.maxChunks();
      case STORAGE -> guild.maxStorage();
    };
  }

  /**
   * @return Cost of the next update for the specified guild.
   */
  public int cost(@NotNull Guild guild) {
    return currentValue(guild) * 200 / this.exponent;
  }
}
