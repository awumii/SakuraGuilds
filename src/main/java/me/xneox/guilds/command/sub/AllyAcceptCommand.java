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

public class AllyAcceptCommand implements SubCommand {

  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
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

    // Make them allies
    guild.allies().add(otherGuild);
    otherGuild.allies().add(guild);

    // Announce publicly about the allies
    ChatUtils.broadcast("&7Gildie &6" + guild.name() + " &7oraz &6" + otherGuild.name() + " &7zawarły &aSOJUSZ!");
  }

  @Override
  public List<String> suggest(String[] args) {
    return SakuraGuildsPlugin.get().guildManager().guildNames();
  }
}
