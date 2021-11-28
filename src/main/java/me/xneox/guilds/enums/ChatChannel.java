package me.xneox.guilds.enums;

import org.jetbrains.annotations.NotNull;

/**
 * Players can switch chat channels to chat privately to their guild or allies.
 */
public enum ChatChannel {
  GLOBAL("Globalny"),
  GUILD("&aGildyjny"),
  ALLY("&6Sojuszniczy");

  private final String name;

  ChatChannel(@NotNull String name) {
    this.name = name;
  }

  @NotNull
  public String getName() {
    return name;
  }
}
