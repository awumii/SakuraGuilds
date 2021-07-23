package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.AdminOnly;
import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.entity.Player;

@AdminOnly
public class SetTrophiesCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        if (args.length < 3) {
            ChatUtils.sendMessage(player, "&cPodaj nazwę gildii.");
            return;
        }

        Guild guild = manager.get(args[1]);
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie odnaleziono takiej gildii.");
            return;
        }

        int trophies = Integer.parseInt(args[2]);
        guild.trophies(trophies);
        ChatUtils.sendMessage(player, "&7Ustawiono pucharki na " + trophies);
    }
}
