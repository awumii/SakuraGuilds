package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.type.ChatChannel;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.HookUtils;
import org.bukkit.entity.Player;

public class ChatChannelCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        Guild guild = manager.playerGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        User user = HookUtils.INSTANCE.userManager().getUser(player);
        switch (user.chatChannel()) {
            case GLOBAL -> {
                user.chatChannel(ChatChannel.GUILD);
                ChatUtils.sendMessage(player, "&7Przełączono na kanał &agildyjny.");
            }
            case GUILD -> {
                user.chatChannel(ChatChannel.ALLY);
                ChatUtils.sendMessage(player, "&7Przełączono na kanał &csojuszniczy.");
            }
            case ALLY -> {
                user.chatChannel(ChatChannel.GLOBAL);
                ChatUtils.sendMessage(player, "&7Przełączono na kanał &cglobalny.");
            }
        }
    }
}
