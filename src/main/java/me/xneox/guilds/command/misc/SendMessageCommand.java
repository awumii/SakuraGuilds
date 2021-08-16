package me.xneox.guilds.command.misc;

import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SendMessageCommand implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    Player player = Bukkit.getPlayer(args[0]);
    if (player == null) {
      return false;
    }

    player.sendMessage(ChatUtils.color(ChatUtils.buildString(args, 1)));
    return true;
  }
}
