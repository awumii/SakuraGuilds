package me.xneox.guilds.command.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.File;

public class DeleteCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        Guild guild = manager.getGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        if (!guild.getLeader().equals(player.getName())) {
            ChatUtils.sendMessage(player, "&cMusisz być liderem gildii.");
            return;
        }

        if (!guild.isDeleteConfirm()) {
            guild.setDeleteConfirm(true);
            ChatUtils.sendMessage(player, "&7Użyj komendy ponownie aby potwierdzić usunięcie gildii. &cTA AKCJA JEST NIEODWRACALNA!");
            return;
        }

        ChatUtils.broadcast("&e" + player.getName() + " &7rozwiązuje gildię &c" + guild.getName());
        manager.deleteGuild(guild);
    }
}
