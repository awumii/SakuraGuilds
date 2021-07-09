package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.AdminOnly;
import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.HookUtils;
import org.bukkit.entity.Player;

@AdminOnly
public class SetLeaderboardCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        HookUtils.INSTANCE.dataManager().leaderboardLocation(player.getLocation());
        ChatUtils.sendMessage(player, "&7Ustawiono lokalizację topki.");
    }
}
