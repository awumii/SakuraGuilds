package me.xneox.guilds.command.impl;

import java.util.Date;
import java.util.List;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.enums.Rank;
import me.xneox.guilds.gui.ManagementGui;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;

public class JoinCommand implements SubCommand {

  @Override
  public void handle(GuildManager manager, Player player, String[] args) {
    if (args.length < 2) {
      ChatUtils.sendMessage(player, "&cMusisz podać nazwę gildii!");
      return;
    }

    if (manager.playerGuild(player.getName()) != null) {
      ChatUtils.sendMessage(player, "&cJuż posiadasz gildię.");
      return;
    }

    Guild guild = manager.get(args[1]);
    if (guild == null) {
      ChatUtils.sendMessage(player, "&cTaka gildia nie istnieje.");
      return;
    }

    if (!guild.invitations().contains(player.getName()) && !player.isOp()) {
      ChatUtils.sendMessage(player, "&cNie zostałeś zaproszony do tej gildii.");
      return;
    }

    if (guild.members().size() >= guild.maxSlots()) {
      ChatUtils.sendMessage(player, "&cTa gildia osiągnęła limit członków!");
      return;
    }

    guild.members().add(Member.create(player.getUniqueId(), Rank.NEWBIE));
    SakuraGuildsPlugin.get().userManager().user(player).joinDate(new Date().getTime());

    ManagementGui.INVENTORY.open(player);
    ChatUtils.broadcast("&e" + player.getName() + " &7dołącza do gildii &6" + guild.name());
  }

  @Override
  public List<String> suggest(String[] args) {
    return SakuraGuildsPlugin.get().guildManager().guildNames();
  }
}
