package me.xneox.guilds.listener;

import java.util.concurrent.TimeUnit;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.hook.HookUtils;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * This listener manages global chat formatting and using private chat channels.
 *
 * TODO: use Paper's AsyncChatEvent, currently not possible because of the legacy string formatting.
 */
public record PlayerChatListener(SakuraGuildsPlugin plugin) implements Listener {

  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void onChat(AsyncPlayerChatEvent event) {
    var config = ConfigManager.messages().chat();
    var player = event.getPlayer();

    // todo move cooldowns to addon
    if (this.plugin.cooldownManager().hasCooldown(player, "chat")) {
      ChatUtils.sendNoPrefix(player, " &4&l! &cPoczekaj chwilę przed następną wiadomością!");
      event.setCancelled(true);
      return;
    }

    this.plugin.cooldownManager().add(player, "chat", 2, TimeUnit.SECONDS);
    event.setMessage(event.getMessage().replaceAll("(?i)kurw|jeb|pierda|huj", "***"));

    var guild = this.plugin.guildManager().playerGuild(player);
    var user = this.plugin.userManager().user(player);

    switch (user.chatChannel()) {
      case GLOBAL -> event.setFormat(event.getFormat()
          .replace("{GUILD}", guild != null ?
              ChatUtils.legacyColor(config.guildPlaceholder()) : config.noGuildPlaceholder())
          .replace("{LEVEL}", String.valueOf(HookUtils.aureliumSkillsLevel(player)))); // todo move this to addon

      case GUILD -> {
        if (guild != null) {
          ChatUtils.guildAlertRaw(guild, config.guildChatFormat()
              .replace("{MEMBER}", guild.member(player).displayName())
              .replace("{MESSAGE}", event.getMessage()));
          event.setCancelled(true);
        }
      }

      case ALLY -> {
        if (guild != null) {
          var message = ChatUtils.format(config.allyChatFormat()
              .replace("{GUILD}", guild.name())
              .replace("{MEMBER}", guild.member(player).displayName())
              .replace("{MESSAGE}", event.getMessage()));

          // Send to member's own guild.
          ChatUtils.guildAlertRaw(guild, message);

          // Send to all allies.
          for (var ally : guild.allies()) {
            ChatUtils.guildAlertRaw(ally, message);
          }

          event.setCancelled(true);
        }
      }
    }
  }
}
