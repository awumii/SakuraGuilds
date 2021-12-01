package me.xneox.guilds.command.sub;

import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.enums.Permission;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.LocationUtils;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;

public class UnClaimCommand implements SubCommand {

  @Override
  public void handle(GuildManager manager, Player player, String[] args) {
    var config = ConfigManager.messages().commands();

    var guild = manager.playerGuild(player.getName());
    if (guild == null) {
      ChatUtils.sendMessage(player, config.noGuild());
      return;
    }

    if (!guild.member(player.getName()).hasPermission(Permission.CLAIM)) {
      ChatUtils.sendMessage(player, "&cNie posiadasz uprawnień do zajmowania terenu.");
      return;
    }

    if (!guild.claims().contains(player.getChunk())) {
      ChatUtils.sendMessage(player, "&cTen chunk nie został zajęty.");
      return;
    }

    if (guild.isNexusChunk(player.getChunk())) {
      ChatUtils.sendMessage(player, "&cTen chunk zawiera nexusa i nie może być porzucony.");
      return;
    }

    guild.claims().remove(player.getChunk());
    ChatUtils.guildAlert(guild, guild.member(player).displayName() +
        " &cporzuca chunk: &6" + LocationUtils.legacyDeserialize(player.getLocation()));
  }
}
