package me.xneox.guilds.listener;

import java.util.concurrent.TimeUnit;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.hook.HookUtils;
import me.xneox.guilds.util.text.ChatUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
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
    Player player = event.getPlayer();

    if (this.plugin.cooldownManager().hasCooldown(player, "chat")) {
      ChatUtils.sendNoPrefix(player, " &4&l! &cPoczekaj chwilę przed następną wiadomością!");
      event.setCancelled(true);
      return;
    }

    this.plugin.cooldownManager().add(player, "chat", 2, TimeUnit.SECONDS);
    event.setMessage(event.getMessage().replaceAll("(?i)kurw|jeb|pierda|huj", "***"));

    var guild = this.plugin.guildManager().playerGuild(player);
    var user = this.plugin.userManager().user(player);

    // todo fix null issues and localization

    switch (user.chatChannel()) {
      case GLOBAL -> event.setFormat(event.getFormat()
          .replace("{GUILD}", guild != null ? ChatUtils.legacyColor(
              "&8[" + ChatColor.of("#E74C3C") + guild.name() + "&8] ") : "")
          .replace("{LEVEL}", String.valueOf(HookUtils.aureliumSkillsLevel(player))));
      case GUILD -> {
        ChatUtils.guildAlertRaw(guild, " &8[&aGILDIA&8] {0}&8: &a{1}",
            guild.member(player).displayName(), event.getMessage());

        event.setCancelled(true);
      }
      case ALLY -> {
        String message = ChatUtils.format(" &8[&bSOJUSZ&8] &7(&d{0}&7) {1}&8: &d{2}",
            guild.name(), guild.member(player).displayName(), event.getMessage());

        ChatUtils.guildAlertRaw(guild, message);
        guild.allies().forEach(ally -> ChatUtils.guildAlertRaw(this.plugin.guildManager().get(ally), message));

        event.setCancelled(true);
      }
    }
  }
}
