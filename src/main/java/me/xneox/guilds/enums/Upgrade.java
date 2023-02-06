package me.xneox.guilds.enums;

import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.ConfigManager;
import org.jetbrains.annotations.NotNull;

public enum Upgrade {
  // holy fucking shit what the fuck did I just create
  SLOTS(
      ConfigManager.config().upgrades().slots().title(),
      ConfigManager.config().upgrades().slots().maxValue(),
      ConfigManager.config().upgrades().slots().exponent(),
      ConfigManager.config().upgrades().slots().increase()),

  CHUNKS(
      ConfigManager.config().upgrades().chunks().title(),
      ConfigManager.config().upgrades().chunks().maxValue(),
      ConfigManager.config().upgrades().chunks().exponent(),
      ConfigManager.config().upgrades().chunks().increase()),

  STORAGE(
      ConfigManager.config().upgrades().storage().title(),
      ConfigManager.config().upgrades().storage().maxValue(),
      ConfigManager.config().upgrades().storage().exponent(),
      ConfigManager.config().upgrades().storage().increase());

  private final String title;
  private final int maxValue;     // Maximum value of the upgrade.
  private final int exponent;     // How much will be divided from upgrade cost.
  private final int increase;   // How much the upgrade level will increase.

  Upgrade(@NotNull String title, int maxValue, int exponent, int increase) {
    this.title = title;
    this.maxValue = maxValue;
    this.exponent = exponent;
    this.increase = increase;
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
    return this.increase;
  }

  /**
   * Performs the current upgrade on the specified guild.
   */
  public void performUpgrade(@NotNull Guild guild) {
    switch (this) {
      case SLOTS -> guild.maxSlots(guild.maxSlots() + this.increase);
      case CHUNKS -> guild.maxChunks(guild.maxChunks() + this.increase);
      case STORAGE -> guild.maxStorage(guild.maxStorage() + this.increase);
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
    return currentValue(guild) * ConfigManager.config().upgrades().costValueMultiplier() / this.exponent;
  }
}
