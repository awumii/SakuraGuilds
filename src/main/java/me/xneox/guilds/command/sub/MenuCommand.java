package me.xneox.guilds.command.sub;

import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.gui.HelpProfileGui;
import me.xneox.guilds.manager.GuildManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MenuCommand implements SubCommand {
  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
    HelpProfileGui.INVENTORY.open(player);
  }
}
