package me.xneox.guilds.command.sub;

import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.gui.UpgradesGui;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UpgradeCommand implements SubCommand {
  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
    var config = ConfigManager.messages().commands();

    var guild = manager.playerGuild(player.getName());
    if (guild == null) {
      ChatUtils.sendMessage(player, config.noGuild());
      return;
    }

    UpgradesGui.INVENTORY.open(player);
  }
}
