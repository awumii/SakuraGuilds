package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.gui.RacesGui;
import me.xneox.guilds.manager.GuildManager;
import org.bukkit.entity.Player;

public class RaceCommand implements SubCommand {
  @Override
  public void handle(GuildManager manager, Player player, String[] args) {
    RacesGui.INVENTORY.open(player);
  }
}
