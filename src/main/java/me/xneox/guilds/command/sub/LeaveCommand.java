package me.xneox.guilds.command.sub;

import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;

public class LeaveCommand implements SubCommand {
  @Override
  public void handle(GuildManager manager, Player player, String[] args) {
    var config = ConfigManager.messages().commands();

    var guild = manager.playerGuild(player.getName());
    if (guild == null) {
      ChatUtils.sendMessage(player, config.noGuild());
      return;
    }

    if (player.getName().equals(guild.leader().nickname())) {
      ChatUtils.sendMessage(player, "&cJesteś liderem! Awansuj kogoś, lub rozwiąż gildię.");
      return;
    }

    ChatUtils.broadcast("&e" + player.getName() + " &7opuszcza gildię &6" + guild.name());
    guild.members().remove(guild.member(player.getName()));
  }
}
