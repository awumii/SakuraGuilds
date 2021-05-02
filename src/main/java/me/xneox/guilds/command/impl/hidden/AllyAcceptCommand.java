package me.xneox.guilds.command.impl.hidden;

import me.xneox.guilds.command.annotations.Hidden;
import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.type.Permission;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.entity.Player;

@Hidden
public class AllyAcceptCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        Guild guild = manager.getGuild(player);
        Guild other = manager.getGuildExact(args[2]);

        if (guild.getAllies().contains(other.getName())) {
            ChatUtils.sendMessage(player, "&cSojusz już został zawarty!");
            return;
        }

        if (!guild.getPlayerRank(player).hasPermission(Permission.ALLIES)) {
            ChatUtils.sendMessage(player, "&cTwoja ranga w gildii jest zbyt niska!");
            return;
        }

        if (args[1].equals("IJAD98jdksldM")) {
            ChatUtils.broadcast("&7Gildie &6" + guild.getName() + " &7oraz &6" + other.getName() + " &7zawarły &aSOJUSZ!");
            guild.getAllies().add(other.getName());
            other.getAllies().add(guild.getName());
        } else if (args[1].equals("dh98jadOAKD")) {
            ChatUtils.guildAlert(guild, guild.getDisplayName(player) + " &7odrzucił zaproszenie sojuszu od &6" + other.getName());
            ChatUtils.guildAlert(other, guild.getDisplayName(player) + " &7z gildii &6" + guild.getName() + " &7odrzucił wasze zaproszenie do sojuszu.");
        }
    }
}
