package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.Hidden;
import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.enums.Permission;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;

@Hidden
public class AllyAcceptCommand implements SubCommand {

  @Override
  public void handle(GuildManager manager, Player player, String[] args) {
    Guild guild = manager.playerGuild(player);
    Guild other = manager.get(args[2]);

    if (guild.allies().contains(other.name())) {
      ChatUtils.sendMessage(player, "&cSojusz już został zawarty!");
      return;
    }

    if (!guild.member(player.getName()).hasPermission(Permission.ALLIES)) {
      ChatUtils.sendMessage(player, "&cTwoja ranga w gildii jest zbyt niska!");
      return;
    }

    if (args[1].equals("IJAD98jdksldM")) {
      ChatUtils.broadcast("&7Gildie &6" + guild.name() + " &7oraz &6" + other.name() + " &7zawarły &aSOJUSZ!");

      guild.allies().add(other.name());
      other.allies().add(guild.name());
    } else if (args[1].equals("dh98jadOAKD")) {
        ChatUtils.guildAlert(guild, guild.member(player).displayName()
            + " &7odrzucił zaproszenie sojuszu od &6" + other.name());

        ChatUtils.guildAlert(other, guild.member(player).displayName()
            + " &7z gildii &6" + guild.name() + " &7odrzucił wasze zaproszenie do sojuszu.");
    }
  }
}
