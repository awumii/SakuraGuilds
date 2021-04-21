package me.xneox.guilds.listener;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.concurrent.TimeUnit;

public class ItemCooldownListener implements Listener {
    private final NeonGuilds plugin;

    public ItemCooldownListener(NeonGuilds plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAppleEat(PlayerItemConsumeEvent event) {
        Material material = event.getItem().getType();
        if ((material == Material.GOLDEN_APPLE || material == Material.ENCHANTED_GOLDEN_APPLE) && this.handleCooldown(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPearlThrow(ProjectileLaunchEvent event) {
        ProjectileSource source = event.getEntity().getShooter();
        if (source instanceof Player && event.getEntity().getType() == EntityType.ENDER_PEARL && this.handleCooldown((Player) source)) {
            event.setCancelled(true);
        }
    }

    /**
     * @param player The player who consumed.
     * @return whenever the event should be cancelled.
     */
    private boolean handleCooldown(Player player) {
        Material material = player.getInventory().getItemInMainHand().getType();
        if (this.plugin.getCooldownManager().hasCooldown(player, material.name())) {
            ChatUtils.sendMessage(player, "&7Poczekaj jeszcze &c" + this.plugin.getCooldownManager().getRemaining(player, material.name()));
            return true;
        }

        this.plugin.getCooldownManager().add(player, material.name(), 15, TimeUnit.SECONDS);
        return false;
    }
}
