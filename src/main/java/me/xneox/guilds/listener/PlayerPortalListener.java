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

public class PlayerPortalListener implements Listener {
    private static final Date END_OPEN = parseDate();

    @EventHandler
    public void onPortal(PlayerPortalEvent event) {
        Player player = event.getPlayer();

        if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) if (new Date().before(END_OPEN)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String toOpen = sdf.format(END_OPEN) + " (za " + TimeUtils.futureMillisToTime(END_OPEN.getTime()) + ")";

            ChatUtils.sendMessage(player, "&7Portale do endu są jeszcze wyłączone.");
            ChatUtils.sendMessage(player, "&7Zostaną włączone: &c" + toOpen);
            event.setCancelled(true);
        }
    }

    @NonNull
    private static Date parseDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd-HH:mm").parse("2021-05-24-22:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
