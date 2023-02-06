package me.xneox.guilds.command.sub;

import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.integration.Integrations;
import me.xneox.guilds.integration.VaultIntegration;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DonateCommand implements SubCommand {

  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
    var config = ConfigManager.messages().commands();

    if (args.length < 2) {
      ChatUtils.sendMessage(player, config.donateAmount());
      return;
    }

    Guild guild = manager.playerGuild(player.getName());
    if (guild == null) {
      ChatUtils.sendMessage(player, config.noGuild());
      return;
    }

    // no vault lol
    if (!Integrations.VAULT_AVAILABLE) {
      ChatUtils.sendMessage(player, config.donateNoEconomy());
      return;
    }

    // player has no money :cry:
    int money = Integer.parseInt(args[1]);
    if (!VaultIntegration.ecoHasAtLeast(player, money)) {
      ChatUtils.sendMessage(player, config.donateYouArePoor());
      return;
    }

    VaultIntegration.ecoWithdraw(player, money);
    guild.money(guild.money() + money);

    // todo configure sounds
    VisualUtils.sound(player, Sound.BLOCK_NOTE_BLOCK_PLING);
    ChatUtils.guildAlert(guild, config.donateGuildNotify()
        .replace("{MEMBER}", guild.member(player).displayName())
        .replace("{AMOUNT}", String.valueOf(money)));
  }
}
