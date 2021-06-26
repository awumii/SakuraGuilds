package me.xneox.guilds.command.impl.sub;

import me.xneox.guilds.command.SubCommand;
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

        Guild guild = manager.getGuildExact(args[1]);
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie odnaleziono takiej gildii.");
            return;
        }

        ChatUtils.sendRaw(player, "&3&m--&8&m------------------------------------------&3&m--");
        ChatUtils.sendRaw(player, "&6&lInformacje o gildii:");
        ChatUtils.sendRaw(player, "&eNazwa: &6" + guild.getName());
        ChatUtils.sendRaw(player, "&eLider: &6" + guild.getLeader().nickname());
        ChatUtils.sendRaw(player, "&eLimit chunków: &6" + guild.maxChunks());
        ChatUtils.sendRaw(player, "&eLimit członków: &6" + guild.maxSlots());
        ChatUtils.sendRaw(player, "");
        ChatUtils.sendRaw(player, "&eCzłonkowie: &7");
        guild.getMembers().forEach(member -> ChatUtils.sendRaw(player, " &8- " + guild.getDisplayName(member.nickname())));
        ChatUtils.sendRaw(player, "");
        ChatUtils.sendRaw(player, "&eZajęte Chunki: &7" + guild.getChunks().size());
        ChatUtils.sendRaw(player, "&3&m--&8&m------------------------------------------&3&m--");
    }
}
