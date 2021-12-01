package me.xneox.guilds.command.sub;

import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.hook.HookUtils;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DonateCommand implements SubCommand {
  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
    if (args.length < 2) {
      ChatUtils.sendMessage(player, "&cPodaj ilość do wpłacenia.");
      return;
    }

    Guild guild = manager.playerGuild(player.getName());
    if (guild == null) {
      ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
      return;
    }

    int money = Integer.parseInt(args[1]);
    if (HookUtils.ecoHasAtLeast(player, money)) {
      VisualUtils.sound(player, Sound.ENTITY_WITHER_BREAK_BLOCK);
      HookUtils.ecoWithdraw(player, money);

      guild.money(guild.money() + money);
      ChatUtils.guildAlert(guild, guild.member(player).displayName() + " &7wpłacił &6" + money + "$ &7do banku gildii.");
    } else {
      ChatUtils.sendMessage(player, "&cNie posiadasz tyle pieniędzy!");
    }
  }
}
