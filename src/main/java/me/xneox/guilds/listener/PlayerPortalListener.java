package me.xneox.guilds.listener;

import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.TimeUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PlayerPortalListener implements Listener {
    private final Date END_OPEN = TimeUtils.parseDate("01/08/2021 20:00");

    @EventHandler
    public void onPortal(PlayerPortalEvent event) {
        Player player = event.getPlayer();

        if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL && new Date().before(END_OPEN)) {
            ChatUtils.sendMessage(player, "&7Portale do endu są jeszcze wyłączone.");
            ChatUtils.sendMessage(player, "&7Zostaną włączone za: &c" + TimeUtils.futureMillisToTime(END_OPEN.getTime()));
            event.setCancelled(true);
        }
    }
}
