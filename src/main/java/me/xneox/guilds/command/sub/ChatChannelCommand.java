package me.xneox.guilds.command.sub;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.enums.ChatChannel;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;

public class ChatChannelCommand implements SubCommand {

  @Override
  public void handle(GuildManager manager, Player player, String[] args) {
    Guild guild = manager.playerGuild(player.getName());
    if (guild == null) {
      ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
      return;
    }

    User user = SakuraGuildsPlugin.get().userManager().user(player);
    switch (user.chatChannel()) {
      case GLOBAL -> user.chatChannel(ChatChannel.GUILD);
      case GUILD -> user.chatChannel(ChatChannel.ALLY);
      case ALLY -> user.chatChannel(ChatChannel.GLOBAL);
    }

    ChatUtils.sendMessage(player, "&7Przełączono na kanał " + user.chatChannel().getName());
  }
}
