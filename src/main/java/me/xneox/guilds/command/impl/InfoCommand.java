package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.entity.Player;

public class InfoCommand implements SubCommand {

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

        ChatUtils.sendRaw(player, "&3&m--&8&m------------------------------------------&3&m--");
        ChatUtils.sendRaw(player, "&6&lInformacje o gildii:");
        ChatUtils.sendRaw(player, "&eNazwa: &6" + guild.name());
        ChatUtils.sendRaw(player, "&eLider: &6" + guild.leader().nickname());
        ChatUtils.sendRaw(player, "&eLimit chunków: &6" + guild.maxChunks());
        ChatUtils.sendRaw(player, "&eLimit członków: &6" + guild.maxSlots());
        ChatUtils.sendRaw(player, "");
        ChatUtils.sendRaw(player, "&eCzłonkowie: &7");
        guild.members().forEach(member -> ChatUtils.sendRaw(player, " &8- " + member.displayName()));
        ChatUtils.sendRaw(player, "");
        ChatUtils.sendRaw(player, "&eZajęte Chunki: &7" + guild.claims().size());
        ChatUtils.sendRaw(player, "&3&m--&8&m------------------------------------------&3&m--");
    }
}
