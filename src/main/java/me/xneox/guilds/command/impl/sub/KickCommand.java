package me.xneox.guilds.command.impl.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.type.Permission;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.entity.Player;

public class KickCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        if (args.length < 2) {
            ChatUtils.sendMessage(player, "&cPodaj nick gracza.");
            return;
        }

        Guild guild = manager.getGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        if (guild.isMember(args[1])) {
            if (!guild.isHigher(player.getName(), args[1]) || !guild.member(player.getName()).hasPermission(Permission.KICK)) {
                ChatUtils.sendMessage(player, "&cTwoja ranga w gildii jest zbyt niska.");
                return;
            }

            if (player.getName().equals(args[1])) {
                ChatUtils.sendMessage(player, "&cNie możesz wyrzucić siebie.");
                return;
            }

            guild.getMembers().remove(guild.member(args[1]));
            ChatUtils.broadcast(guild.getDisplayName(player) + " &7wyrzuca &e" + args[1] + " &7z gildii &6" + guild.getName());
        }
    }
}
