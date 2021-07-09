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
        Guild guild = manager.playerGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        if (player.getName().equals(guild.leader().nickname())) {
            ChatUtils.sendMessage(player, "&cJesteś liderem! Awansuj kogoś, lub rozwiąż gildię.");
            return;
        }

        ChatUtils.broadcast("&e" + player.getName() + " &7opuszcza gildię &6" + guild.name());
        guild.members().remove(guild.member(player.getName()));
    }
}
