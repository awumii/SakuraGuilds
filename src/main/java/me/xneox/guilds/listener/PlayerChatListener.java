package me.xneox.guilds.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import java.util.concurrent.TimeUnit;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.enums.Race;
import me.xneox.guilds.util.HookUtils;
import me.xneox.guilds.util.text.ChatUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class PlayerChatListener implements Listener {
  private final SakuraGuildsPlugin plugin;

  public PlayerChatListener(SakuraGuildsPlugin plugin) {
    this.plugin = plugin;
  }

  /*
  @EventHandler
  public void onChat(AsyncChatEvent event) {
    Player player = event.getPlayer();
    String format = "{0}&8[&#ffc24d{1}✫&8] {2}&7{3}&7: &f{4}";
    Component component = ChatUtils.color(ChatUtils.format(format,
        guild != null ? ChatUtils.legacyColor("&8[" + ChatColor.of("#E74C3C") + guild.name() + "&8] ") : ""))

    for (Player online : Bukkit.getOnlinePlayers()) {
      online.sendMessage(player.displayName().append(Component.text(" -> ")).append(event.message())
              .hoverEvent(ChatUtils.color("na co sie kurwa gapisz"))
              .clickEvent(ClickEvent.suggestCommand("/msg " + player.getName())));
    }

    event.setCancelled(true);
  }
   */

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

    Guild guild = this.plugin.guildManager().playerGuild(player);
    User user = this.plugin.userManager().user(player);

    switch (user.chatChannel()) {
      case GLOBAL -> event.setFormat(event.getFormat()
          .replace("{GUILD}", guild != null ? ChatUtils.legacyColor("&8[" + ChatColor.of("#E74C3C") + guild.name() + "&8] ") : "")
          .replace("{RACE}", user.race() != Race.NONE ? ChatUtils.legacyColor("&8[" + ChatColor.of(user.race().display().color().asHexString()) + user.race().display().icon() + "&8] ") : "")
          .replace("{LEVEL}", String.valueOf(HookUtils.getAureliumLevel(player))));
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
