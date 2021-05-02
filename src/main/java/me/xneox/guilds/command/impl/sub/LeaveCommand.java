package me.xneox.guilds.command.impl.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.type.Rank;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.entity.Player;

public class LeaveCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        Guild guild = manager.getGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        if (guild.getPlayerRank(player) == Rank.LEADER) {
            ChatUtils.sendMessage(player, "&cJesteś liderem! Awansuj kogoś, lub rozwiąż gildię.");
            return;
        }

        ChatUtils.broadcast("&e" + player.getName() + " &7opuszcza gildię &6" + guild.getName());
        guild.getMembers().remove(player.getName());
    }
}
