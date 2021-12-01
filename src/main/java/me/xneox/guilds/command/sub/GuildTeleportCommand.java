package me.xneox.guilds.command.sub;

import java.util.List;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.annotations.AdminOnly;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@AdminOnly
public class GuildTeleportCommand implements SubCommand {
  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
    var config = ConfigManager.messages().commands();

    if (args.length < 2) {
      ChatUtils.sendMessage(player, config.noGuildSpecified());
      return;
    }

    Guild guild = manager.get(args[1]);
    if (guild == null) {
      ChatUtils.sendMessage(player, config.noGuild());
      return;
    }

    player.teleportAsync(guild.nexusLocation());
  }

  @Override
  public List<String> suggest(String[] args) {
    return SakuraGuildsPlugin.get().guildManager().guildNames();
  }
}
