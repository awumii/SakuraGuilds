package me.xneox.guilds.command.sub;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;

public class HomeCommand implements SubCommand {

  @Override
  public void handle(GuildManager manager, Player player, String[] args) {
    Guild guild = manager.playerGuild(player.getName());
    if (guild == null) {
      ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
      return;
    }

    SakuraGuildsPlugin.get()
        .userManager()
        .user(player)
        .beginTeleport(player.getLocation(), guild.homeLocation());
  }
}
