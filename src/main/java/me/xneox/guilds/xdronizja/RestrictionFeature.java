package me.xneox.guilds.xdronizja;

import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RestrictionFeature implements Listener {

    private static final Date DIAMOND_GEAR = parseDate("2021-03-02-14:00");
    private static final Date NETHERITE_GEAR = parseDate("2021-04-03-20:00");

    private static final Date NETHER_OPEN = parseDate("2021-03-28-12:14");
    private static final Date END_OPEN = parseDate("2021-04-02-20:00");

    public RestrictionFeature(JavaPlugin plugin) {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerInventory inv = player.getInventory();

                if (handleSlot(inv.getHelmet(), player)) inv.setHelmet(null);
                if (handleSlot(inv.getChestplate(), player)) inv.setChestplate(null);
                if (handleSlot(inv.getLeggings(), player)) inv.setLeggings(null);
                if (handleSlot(inv.getBoots(), player)) inv.setBoots(null);
            }
        }, 10L, 80L);
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent event) {
        Player player = event.getPlayer();

        if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            if (isBefore(END_OPEN)) {
                event.setCancelled(true);
                ChatUtils.sendMessage(player, "&7Portale do endu są jeszcze wyłączone.");
                ChatUtils.sendMessage(player, "&7Zostaną włączone: &c" + formatDate(END_OPEN));
            }
        } else if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            if (isBefore(NETHER_OPEN)) {
                event.setCancelled(true);
                ChatUtils.sendMessage(player, "&7Portale do netheru są jeszcze wyłączone.");
                ChatUtils.sendMessage(player, "&7Zostaną włączone: &c" + formatDate(NETHER_OPEN));
            }
        }
    }

    private boolean isBefore(Date target) {
        return new Date().before(target);
    }

    private boolean handleSlot(ItemStack stack, Player player) {
        if (stack == null) {
            return false;
        }

        if (stack.getType().name().contains("DIAMOND")) {
            if (isBefore(DIAMOND_GEAR)) {
                player.getWorld().dropItem(player.getLocation(), stack);
                ChatUtils.sendMessage(player, "&7Diamentowe zbroje są jeszcze wyłączone.");
                ChatUtils.sendMessage(player, "&7Zostaną włączone: &c" + formatDate(DIAMOND_GEAR));
                return true;
            }
        } else if (stack.getType().name().contains("NETHERITE")) {
            if (isBefore(NETHERITE_GEAR)) {
                player.getWorld().dropItem(player.getLocation(), stack);
                ChatUtils.sendMessage(player, "&7Netherytowe zbroje są jeszcze wyłączone.");
                ChatUtils.sendMessage(player, "&7Zostaną włączone: &c" + formatDate(NETHERITE_GEAR));
                return true;
            }
        }
        return false;
    }

    private static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date) + " (za " + TimeUtils.futureMillisToTime(date.getTime()) + ")";
    }

    @NonNull
    private static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd-HH:mm").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
