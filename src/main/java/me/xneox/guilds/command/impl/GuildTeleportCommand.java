package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.AdminOnly;
import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import me.xneox.guilds.util.HookUtils;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@AdminOnly
public class GuildTeleportCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        if (args.length < 2) {
            ChatUtils.sendMessage(player, "&cPodaj nazwę gildii.");
            return;
        }

        Guild guild = manager.get(args[1]);
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie odnaleziono takiej gildii.");
            return;
        }

        player.teleportAsync(guild.nexusLocation());
    }

    @Override
    public List<String> suggest(String[] args) {
        return HookUtils.INSTANCE.guildManager().guildMap().values()
                .stream()
                .map(Guild::name)
                .collect(Collectors.toList());
    }
}
