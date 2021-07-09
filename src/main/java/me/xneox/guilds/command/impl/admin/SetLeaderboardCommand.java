package me.xneox.guilds.command.impl.admin;

import me.xneox.guilds.command.annotations.AdminOnly;
import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.HookUtils;
import org.bukkit.entity.Player;

@AdminOnly
public class SetLeaderboardCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        HookUtils.INSTANCE.arenaManager().leaderboardLocation(player.getLocation());
        ChatUtils.sendMessage(player, "&7Ustawiono lokalizację topki.");
    }
}
