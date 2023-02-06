package me.xneox.guilds.command.sub;

import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DeleteCommand implements SubCommand {

  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
    var config = ConfigManager.messages().commands();

    var guild = manager.playerGuild(player.getName());
    if (guild == null) {
      ChatUtils.sendMessage(player, config.noGuild());
      return;
    }

    // Needs leader
    if (!player.getName().equals(guild.leader().nickname())) {
      ChatUtils.sendMessage(player, config.deleteNoPermission());
      return;
    }

    // Prompt for confirmation.
    if (!guild.deleteConfirmation()) {
      guild.deleteConfirmation(true);
      ChatUtils.sendMessage(player, config.deleteConfirm());
      return;
    }

    ChatUtils.broadcast(config.deleteAnnoucement()
        .replace("{PLAYER}", player.getName())
        .replace("{GUILD}", guild.name()));

    manager.delete(guild);
  }
}
