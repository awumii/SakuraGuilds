package me.xneox.guilds.listener;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import java.util.concurrent.TimeUnit;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.util.LocationUtils;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

/**
 * This listener adds cooldowns to elytras, golden apples and ender pearls.
 * TODO: This shouldn't be a part of a guild plugin. Maybe i'll leave it there idk.
 */
@Deprecated
@ScheduledForRemoval
public record ItemCooldownListener(@NotNull SakuraGuildsPlugin plugin) implements Listener {

  @EventHandler
  public void onElytraBoost(PlayerElytraBoostEvent event) {
    if (this.handleCooldown(event.getPlayer(), 8)) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onAppleEat(PlayerItemConsumeEvent event) {
    Material material = event.getItem().getType();
    if ((material == Material.GOLDEN_APPLE || material == Material.ENCHANTED_GOLDEN_APPLE)
        && this.handleCooldown(event.getPlayer(), 15)) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onPearlThrow(ProjectileLaunchEvent event) {
    ProjectileSource source = event.getEntity().getShooter();
    if (source instanceof Player player
        && event.getEntity().getType() == EntityType.ENDER_PEARL
        && this.handleCooldown(player, 10)) {
      event.setCancelled(true);
    }
  }

  /**
   * @param player The player who consumed.
   * @return whenever the event should be cancelled.
   */
  private boolean handleCooldown(Player player, int duration) {
    if (LocationUtils.isWorldNotAllowed(player.getLocation())) {
      return false;
    }

    Material material = player.getInventory().getItemInMainHand().getType();
    if (this.plugin.cooldownManager().hasCooldown(player, material.name())) {
      ChatUtils.sendMessage(player, "&7Poczekaj jeszcze &c" + this.plugin.cooldownManager()
          .getRemaining(player, material.name()));
      return true;
    }

    this.plugin.cooldownManager().add(player, material.name(), duration, TimeUnit.SECONDS);
    return false;
  }
}
