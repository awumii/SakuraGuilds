package me.xneox.guilds.command.sub;

import java.util.List;
import java.util.concurrent.TimeUnit;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InviteCommand implements SubCommand {

  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
    var config = ConfigManager.messages().commands();

    if (args.length < 2) {
      ChatUtils.sendMessage(player, ConfigManager.messages().commands().noNicknameProvided());
      return;
    }

    var target = Bukkit.getPlayer(args[1]);
    if (target == null) {
      ChatUtils.sendMessage(player, ConfigManager.messages().commands().notOnline());
      return;
    }

    if (manager.playerGuild(target) != null) {
      ChatUtils.sendMessage(player, ConfigManager.messages().commands().playerHasGuild());
      return;
    }

    var guild = manager.playerGuild(player.getName());
    if (guild == null) {
      ChatUtils.sendMessage(player, config.noGuild());
      return;
    }

    if (guild.members().size() >= guild.maxSlots()) {
      ChatUtils.sendMessage(player, ConfigManager.messages().commands().memberLimitReached());
      return;
    }

    if (guild.isMember(args[1])) {
      ChatUtils.sendMessage(player, ConfigManager.messages().commands().playerHasGuild());
      return;
    }

    if (SakuraGuildsPlugin.get().cooldownManager().hasCooldown(player, "invite-" + target.getName())) {
      ChatUtils.sendMessage(player, ConfigManager.messages().commands().cooldown()
              .replace("{TIME}", SakuraGuildsPlugin.get().cooldownManager().getRemaining(player, "invite-" + target.getName())));
      return;
    }

    ChatUtils.sendMessage(player, ConfigManager.messages().commands().inviteSuccess().replace("{PLAYER}", args[1]));
    guild.playerInvitations().add(target.getUniqueId());

    for (var line : ConfigManager.messages().commands().invitationReceived()) {
      ChatUtils.sendClickableMessage(target, line.replace("{GUILD}", manager.playerGuild(player.getName()).name()),
          ConfigManager.messages().commands().invitationReceivedHover(),
          "/g join " + guild.name());
    }

    SakuraGuildsPlugin.get().cooldownManager().add(player, "invite-" + target.getName(), 10, TimeUnit.MINUTES);
  }

  @Override
  public @Nullable List<String> suggest(String[] args) {
    return Bukkit.getOnlinePlayers().stream()
        .map(Player::getName)
        .toList();
  }
}
