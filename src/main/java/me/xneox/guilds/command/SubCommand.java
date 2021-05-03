package me.xneox.guilds.command;

import me.xneox.guilds.manager.GuildManager;
import org.bukkit.entity.Player;

@FunctionalInterface
public interface SubCommand {
    void handle(GuildManager manager, Player player, String[] args);
}
