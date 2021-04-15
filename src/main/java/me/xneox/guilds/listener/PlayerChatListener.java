package me.xneox.guilds.listener;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.Colors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.concurrent.TimeUnit;

public class PlayerChatListener implements Listener {
    private final NeonGuilds plugin;

    public PlayerChatListener(NeonGuilds plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (this.plugin.getCooldownManager().hasCooldown(player, "chat")) {
            ChatUtils.sendRaw(player, " &4&l[!] &cPoczekaj chwilę przed następną wiadomością!");
            event.setCancelled(true);
            return;
        }

        this.plugin.getCooldownManager().add(player, "chat", 2, TimeUnit.SECONDS);

        event.setMessage(event.getMessage().replaceAll("(?i)kurw|jeb|pierda|huj|tasrv|com|eu", "***"));
        if (event.getMessage().contains("lag") || event.getMessage().contains("LAG")) {
            if (Bukkit.getTPS()[0] > 19) {
                ChatUtils.broadcastRaw(" &4&l[!] &c" + player.getName() + ", na serwerze nie ma lagów. Sprawdź swoje połączenie z internetem!");
            } else {
                ChatUtils.broadcastRaw(" &4&l[!] &c" + player.getName() + ", na serwerze mogą być chwilowe lagi. Poczekaj chwilę aż serwer się ustabilizuje...");
            }
        }

        Guild guild = this.plugin.getGuildManager().getGuild(player);
        if (guild == null) {
            event.setFormat(event.getFormat().replace("{GUILD}", "").replace("{POSITION}", ""));
            return;
        }

        User user = this.plugin.getUserManager().getUser(player);
        switch (user.getChatChannel()) {
            case GLOBAL:
                event.setFormat(event.getFormat().replace("{GUILD}", ChatUtils.colored("&8(&7" + Colors.ALIZARIN_RED + guild.getName() + "&8) ")));
                break;
            case GUILD:
                ChatUtils.guildAlertRaw(guild, " &7[CHAT GILDYJNY&7] " +
                        guild.getPlayerRank(player).getIcon() + " " + player.getName() + " &8» &f" + event.getMessage());
                event.setCancelled(true);
                break;
            case ALLY:
                String message = " &7[CHAT SOJUSZNICZY&7] &7(&d" + guild.getName() + "&7) " +
                        guild.getPlayerRank(player).getIcon() + " " + player.getName() + " &8» &f" + event.getMessage();

                ChatUtils.guildAlertRaw(guild, message);
                for (String ally : guild.getAllies()) {
                    ChatUtils.guildAlertRaw(this.plugin.getGuildManager().getGuildExact(ally), message);
                }
                event.setCancelled(true);
                break;
        }
    }
}
