package me.xneox.guilds.command.sub;

import java.util.List;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.enums.Permission;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AllyAcceptCommand implements SubCommand {

  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
    var config = ConfigManager.messages().commands();

    if (args.length < 2) {
      ChatUtils.sendMessage(player, config.noGuildSpecified());
      return;
    }

    Guild guild = manager.playerGuild(player);
    if (guild == null) {
      ChatUtils.sendMessage(player, config.noGuild());
      return;
    }

    Guild otherGuild = manager.get(args[1]);
    if (otherGuild == null) {
      ChatUtils.sendMessage(player, config.unknownGuild());
      return;
    }

    if (guild.allies().contains(otherGuild)) {
      ChatUtils.sendMessage(player, config.allyAlready());
      return;
    }

    if (!guild.member(player.getName()).hasPermission(Permission.ALLIES)) {
      ChatUtils.sendMessage(player, config.noGuildPermission());
      return;
    }

    // Check if the other guild has actually sent an invitation
    if (!guild.allyInvitations().contains(otherGuild)) {
      ChatUtils.sendMessage(player, config.allyNoInvitation());
      return;
    }

    // Make them allies
    guild.allies().add(otherGuild);
    otherGuild.allies().add(guild);

    // Announce publicly about the allies
    ChatUtils.broadcast(config.allySuccessBroadcast()
        .replace("{1}", guild.name())
        .replace("{2}", otherGuild.name()));
  }

  @Override
  public List<String> suggest(String[] args) {
    return SakuraGuildsPlugin.get().guildManager().guildNames();
  }
}
