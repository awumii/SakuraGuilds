package me.xneox.guilds.command.admin;

import me.xneox.guilds.command.AdminOnly;
import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.entity.Player;

@AdminOnly
public class GuildTeleportCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        if (args.length < 2) {
            ChatUtils.sendMessage(player, "&cPodaj nazwę gildii.");
            return;
        }

        Guild guild = manager.getGuildExact(args[1]);
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie odnaleziono takiej gildii.");
            return;
        }

        player.teleport(guild.getNexusLocation());
    }
}
