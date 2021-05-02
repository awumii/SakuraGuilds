package me.xneox.guilds.command.impl.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.type.ChatChannel;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ServiceUtils;
import org.bukkit.entity.Player;

public class ChatChannelCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        Guild guild = manager.getGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        User user = ServiceUtils.INSTANCE.getUserManager().getUser(player);
        switch (user.getChatChannel()) {
            case GLOBAL:
                user.setChatChannel(ChatChannel.GUILD);
                ChatUtils.sendMessage(player, "&7Przełączono na kanał &agildyjny.");
                break;
            case GUILD:
                user.setChatChannel(ChatChannel.ALLY);
                ChatUtils.sendMessage(player, "&7Przełączono na kanał &csojuszniczy.");
                break;
            case ALLY:
                user.setChatChannel(ChatChannel.GLOBAL);
                ChatUtils.sendMessage(player, "&7Przełączono na kanał &cglobalny.");
                break;
        }
    }
}
