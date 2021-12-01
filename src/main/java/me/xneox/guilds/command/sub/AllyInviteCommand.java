package me.xneox.guilds.command.sub;

import java.util.List;
import java.util.concurrent.TimeUnit;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.enums.Permission;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AllyInviteCommand implements SubCommand {

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

    if (!guild.member(player.getName()).hasPermission(Permission.ALLIES)) {
      ChatUtils.sendMessage(player, config.noGuildPermission());
      return;
    }

    Guild otherGuild = manager.get(args[1]);
    if (otherGuild == null) {
      ChatUtils.sendMessage(player, config.unknownGuild());
      return;
    }

    if (guild.name().equals(otherGuild.name())) {
      ChatUtils.sendMessage(player, config.allyCantSelf());
      return;
    }

    if (guild.allies().contains(otherGuild)) {
      ChatUtils.sendMessage(player, config.allyAlready());
      return;
    }

    if (SakuraGuildsPlugin.get().cooldownManager().hasCooldown(player, "ally-" + otherGuild.name())) {
        ChatUtils.sendMessage(player, config.cooldown()
            .replace("{TIME}", SakuraGuildsPlugin.get().cooldownManager().getRemaining(player, "ally-" + otherGuild.name())));
      return;
    }

    // Add this guild to the other guild's invitations
    otherGuild.allyInvitations().add(guild);

    ChatUtils.sendMessage(player, config.allyInvitationSent()
        .replace("{GUILD}", otherGuild.name()));

    // Notify the other guild's members (prefix)
    for (String line : config.allyInvitationPrefix()) {
      ChatUtils.guildAlertRaw(otherGuild, line.replace("{GUILD}", guild.name()));
    }

    // Send accept/deny buttons to other guild's members
    for (Player otherMember : otherGuild.getOnlineMembers()) {
      ChatUtils.sendClickableMessage(otherMember,
          config.allyInvitationButtonAccept(),
          config.allyInvitationButtonAcceptHover(),
          "/g acceptally " + guild.name());

      ChatUtils.sendClickableMessage(otherMember,
          config.allyInvitationButtonDeny(),
          config.allyInvitationButtonDenyHover(),
          "/g acceptally " + guild.name());
    }

    // Notify the other guild's members (suffix)
    for (String line : config.allyInvitationSuffix()) {
      ChatUtils.guildAlertRaw(otherGuild, line);
    }

    // Cooldown >:(
    SakuraGuildsPlugin.get().cooldownManager().add(player, "ally-" + otherGuild.name(), 10, TimeUnit.MINUTES);
  }

  @Override
  public List<String> suggest(String[] args) {
    return SakuraGuildsPlugin.get().guildManager().guildNames();
  }
}
