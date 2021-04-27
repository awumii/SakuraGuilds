package me.xneox.guilds.command.admin;

import me.xneox.guilds.command.AdminCommand;
import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ServiceUtils;
import org.bukkit.entity.Player;

@AdminCommand
public class SetLeaderboardCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        ServiceUtils.INSTANCE.getArenaManager().setLeaderboard(player.getLocation());
        ChatUtils.sendMessage(player, "&7Ustawiono lokalizację topki.");
    }
}
