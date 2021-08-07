package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.AdminOnly;
import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.element.User;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import me.xneox.guilds.util.HookUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@AdminOnly
public class SetTrophiesCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        if (args.length < 3) {
            ChatUtils.sendMessage(player, "&cPodaj nick gracza.");
            return;
        }

        User user = HookUtils.INSTANCE.userManager().getUser(Bukkit.getPlayerUniqueId(args[1]));
        user.trophies(Integer.parseInt(args[2]));
        ChatUtils.sendMessage(player, "&7Ustawiono pucharki gracza na " + args[2]);
    }
}
