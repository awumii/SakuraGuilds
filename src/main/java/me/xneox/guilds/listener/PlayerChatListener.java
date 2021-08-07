package me.xneox.guilds.listener;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.enums.Race;
import me.xneox.guilds.util.text.ChatUtils;
import me.xneox.guilds.util.HookUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.concurrent.TimeUnit;

public final class PlayerChatListener implements Listener {
    private final SakuraGuildsPlugin plugin;

    public PlayerChatListener(SakuraGuildsPlugin plugin) {
        this.plugin = plugin;
    }

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
        User user = this.plugin.userManager().getUser(player);

        if (guild == null) {
            event.setFormat(event.getFormat()
                    .replace("{GUILD}", "")
                    .replace("{RACE}", user.race() != Race.NONE ? ChatUtils.legacyColor("&8[" + user.race().title() + "&8] ") : "")
                    .replace("{LEVEL}", String.valueOf(HookUtils.getAureliumLevel(player))));
            return;
        }

        switch (user.chatChannel()) {
            case GLOBAL -> event.setFormat(event.getFormat()
                    .replace("{GUILD}", ChatUtils.legacyColor("&8[" + ChatColor.of("#E74C3C") + guild.name() + "&8] "))
                    .replace("{RACE}", user.race() != Race.NONE ? ChatUtils.legacyColor("&8[" + user.race().title() + "&8] ") : "")
                    .replace("{LEVEL}", String.valueOf(HookUtils.getAureliumLevel(player))));
            case GUILD -> {
                ChatUtils.guildAlertRaw(guild, " &8[&aGILDIA&8] " +
                        guild.member(player).displayName() + "&8: &a" + event.getMessage());
                event.setCancelled(true);
            }
            case ALLY -> {
                String message = " &8[&bSOJUSZ&8] &7(&d" + guild.name() + "&7) " +
                        guild.member(player).displayName() + "&8: &d" + event.getMessage();
                ChatUtils.guildAlertRaw(guild, message);
                for (String ally : guild.allies()) {
                    ChatUtils.guildAlertRaw(this.plugin.guildManager().get(ally), message);
                }
                event.setCancelled(true);
            }
        }
    }
}
