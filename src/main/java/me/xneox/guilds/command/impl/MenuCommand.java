package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.gui.HelpProfileGui;
import me.xneox.guilds.manager.GuildManager;
import org.bukkit.entity.Player;

public class MenuCommand implements SubCommand {
  @Override
  public void handle(GuildManager manager, Player player, String[] args) {
    HelpProfileGui.INVENTORY.open(player);
  }
}
