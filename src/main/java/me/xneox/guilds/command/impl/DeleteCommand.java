package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.entity.Player;

public class DeleteCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        Guild guild = manager.playerGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        if (!player.getName().equals(guild.leader().nickname())) {
            ChatUtils.sendMessage(player, "&cMusisz być liderem gildii.");
            return;
        }

        if (!guild.deleteConfirmation()) {
            guild.deleteConfirmation(true);
            ChatUtils.sendMessage(player, "&7Użyj komendy ponownie aby potwierdzić usunięcie gildii. &cTA AKCJA JEST NIEODWRACALNA!");
            return;
        }

        ChatUtils.broadcast("&e" + player.getName() + " &7rozwiązuje gildię &c" + guild.name());
        manager.delete(guild);
    }
}
