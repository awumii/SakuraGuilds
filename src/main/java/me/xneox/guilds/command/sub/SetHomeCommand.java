package me.xneox.guilds.command.sub;

import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.enums.Permission;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetHomeCommand implements SubCommand {

  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
    var config = ConfigManager.messages().commands();

    var guild = manager.playerGuild(player.getName());
    if (guild == null) {
      ChatUtils.sendMessage(player, config.noGuild());
      return;
    }

    if (!guild.member(player.getName()).hasPermission(Permission.SET_HOME)) {
      ChatUtils.sendMessage(player, "&cTwoja pozycja jest zbyt niska.");
      return;
    }

    if (!guild.inside(player.getLocation())) {
      ChatUtils.sendMessage(player, "&cBazę można ustawić tylko na zajętym terenie.");
      return;
    }

    guild.homeLocation(player.getLocation());
    ChatUtils.guildAlert(
        guild, guild.member(player).displayName() + " &7zmienił lokalizację bazy.");
  }
}
