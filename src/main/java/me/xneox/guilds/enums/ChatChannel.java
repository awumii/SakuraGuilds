package me.xneox.guilds.enums;

import me.xneox.guilds.manager.ConfigManager;
import org.jetbrains.annotations.NotNull;

/**
 * Players can switch chat channels to chat privately to their guild or allies.
 */
public enum ChatChannel {
  GLOBAL(ConfigManager.messages().chat().channelNameGlobal()),
  GUILD(ConfigManager.messages().chat().channelNameGuild()),
  ALLY(ConfigManager.messages().chat().channelNameAlly());

  private final String name;

  ChatChannel(@NotNull String name) {
    this.name = name;
  }

  @NotNull
  public String getName() {
    return name;
  }
}
