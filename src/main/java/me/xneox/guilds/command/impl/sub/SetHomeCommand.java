package me.xneox.guilds.command.impl.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.type.Permission;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.entity.Player;

public class SetHomeCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        Guild guild = manager.playerGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        if (!guild.member(player.getName()).hasPermission(Permission.SET_HOME)) {
            ChatUtils.sendMessage(player, "&cTwoja pozycja jest zbyt niska.");
            return;
        }

        if (!guild.inside(player.getLocation())) {
            ChatUtils.sendMessage(player, "&cBazę można ustawić tylko na zajętym terenie.");
            return;
        }

        guild.homeLocation(player.getLocation());
        ChatUtils.guildAlert(guild, guild.member(player).displayName() + " &7zmienił lokalizację bazy.");
    }
}
