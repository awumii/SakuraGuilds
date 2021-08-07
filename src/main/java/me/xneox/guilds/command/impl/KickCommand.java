package me.xneox.guilds.command.impl;

import java.util.List;
import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.enums.Permission;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class KickCommand implements SubCommand {

  @Override
  public void handle(GuildManager manager, Player player, String[] args) {
    if (args.length < 2) {
      ChatUtils.sendMessage(player, "&cPodaj nick gracza.");
      return;
    }

    Guild guild = manager.playerGuild(player.getName());
    if (guild == null) {
      ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
      return;
    }

    if (player.getName().equals(args[1])) {
      ChatUtils.sendMessage(player, "&cNie możesz wyrzucić siebie.");
      return;
    }

    Member member = guild.member(player);
    if (!guild.isMember(args[1])) {
      ChatUtils.sendMessage(player, "&cNie znaleziono członka gildii o nicku " + args[1]);
      return;
    }

    if (!member.hasPermission(Permission.KICK)) {
      ChatUtils.sendMessage(player, "&cNie posiadasz uprawnień do wyrzucania graczy.");
      return;
    }

    if (!member.rank().isHigher(guild.member(args[1]).rank())) {
      ChatUtils.sendMessage(player, "&cTwoja ranga w gildii jest niższa od docelowego gracza.");
      return;
    }

    guild.members().remove(guild.member(args[1]));
    ChatUtils.broadcast(
        member.displayName() + " &7wyrzuca &e" + args[1] + " &7z gildii &6" + guild.name());
  }
}
