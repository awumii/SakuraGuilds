package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.gui.LeaderboardsGui;
import me.xneox.guilds.manager.GuildManager;
import org.bukkit.entity.Player;

public class TopCommand implements SubCommand {

  @Override
  public void handle(GuildManager manager, Player player, String[] args) {
    LeaderboardsGui.INVENTORY.open(player);
  }
}
