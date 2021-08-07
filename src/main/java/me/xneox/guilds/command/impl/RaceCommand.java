package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.HookUtils;
import org.bukkit.entity.Player;

public class RaceCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        HookUtils.INSTANCE.inventoryManager().open("races", player);
    }
}
