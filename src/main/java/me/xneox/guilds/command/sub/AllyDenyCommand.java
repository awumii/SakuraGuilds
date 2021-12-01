package me.xneox.guilds.command.sub;

import java.util.List;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.enums.Permission;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AllyDenyCommand implements SubCommand {

  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, @NotNull String[] args) {
    if (args.length < 2) {
      ChatUtils.sendMessage(player, "&cPodaj nazwę gildii.");
      return;
    }

    Guild guild = manager.playerGuild(player);
    if (guild == null) {
      ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
      return;
    }

    Guild otherGuild = manager.get(args[1]);
    if (otherGuild == null) {
      ChatUtils.sendMessage(player, "&cPodana gildia nie istnieje.");
      return;
    }

    if (guild.allies().contains(otherGuild)) {
      ChatUtils.sendMessage(player, "&cSojusz już został zawarty!");
      return;
    }

    if (!guild.member(player.getName()).hasPermission(Permission.ALLIES)) {
      ChatUtils.sendMessage(player, "&cTwoja ranga w gildii jest zbyt niska!");
      return;
    }

    // Check if the other guild has actually sent an invitation
    if (!guild.allyInvitations().contains(otherGuild)) {
      ChatUtils.sendMessage(player, "&cTa gildia nie wysłała wam zaproszenia, lub zaproszenie wygasło.");
      return;
    }

    // Remove their invitation.
    guild.allyInvitations().remove(otherGuild);

    // Notify members of this guild about the rejection.
    ChatUtils.guildAlert(guild, guild.member(player).displayName() + " &7odrzucił zaproszenie sojuszu od &6" + otherGuild.name());

    // Notify the other guild about the rejection
    ChatUtils.guildAlert(otherGuild, guild.member(player).displayName() + " &7z gildii &6" + guild.name() + " &7odrzucił wasze zaproszenie do sojuszu.");
  }

  @Override
  public List<String> suggest(String[] args) {
    return SakuraGuildsPlugin.get().guildManager().guildNames();
  }
}
