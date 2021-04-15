package me.xneox.guilds.command.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.type.Permission;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ChunkUtils;
import me.xneox.guilds.util.LocationUtils;
import org.bukkit.entity.Player;

public class SetHomeCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        Guild guild = manager.getGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        if (!guild.getPlayerRank(player).hasPermission(Permission.SET_HOME)) {
            ChatUtils.sendMessage(player, "&cTwoja pozycja jest zbyt niska.");
            return;
        }

        if (!guild.getChunks().contains(ChunkUtils.toString(player.getChunk()))) {
            ChatUtils.sendMessage(player, "&cBazę można ustawić tylko na zajętym terenie.");
            return;
        }

        guild.setHome(player.getLocation());
        guild.log(player.getName() + " ustawia bazę na " + LocationUtils.toSimpleString(player.getLocation()));
        ChatUtils.guildAlert(guild, guild.getDisplayName(player) + " &7ustawił bazę na: &6" + LocationUtils.toSimpleString(player.getLocation()));
    }
}
