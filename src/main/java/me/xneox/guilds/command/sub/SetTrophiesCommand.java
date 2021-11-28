package me.xneox.guilds.command.sub;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.annotations.AdminOnly;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.User;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@AdminOnly
public class SetTrophiesCommand implements SubCommand {
  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
    if (args.length < 3) {
      ChatUtils.sendMessage(player, "&cPodaj nick gracza.");
      return;
    }

    User user = SakuraGuildsPlugin.get().userManager().user(Bukkit.getPlayerUniqueId(args[1]));
    user.trophies(Integer.parseInt(args[2]));
    ChatUtils.sendMessage(player, "&7Ustawiono pucharki gracza na " + args[2]);
  }
}
