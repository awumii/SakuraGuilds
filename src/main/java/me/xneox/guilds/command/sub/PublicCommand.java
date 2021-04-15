package me.xneox.guilds.command.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.type.Rank;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.entity.Player;

public class PublicCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        Guild guild = manager.getGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        if (guild.getPlayerRank(player) != Rank.LEADER) {
            ChatUtils.sendMessage(player, "&cTwoja pozycja jest zbyt niska.");
            return;
        }

        if (guild.isPublic()) {
            ChatUtils.broadcast("&7Gildia &e" + guild.getName() + " &7stała się &cPRYWATNA.");
            guild.setPublic(false);
        } else {
            ChatUtils.broadcast("&7Gildia &e" + guild.getName() + " &7stała się &aPUBLICZNA! &8[&6/g join&8]");
            guild.setPublic(true);
        }
    }
}
