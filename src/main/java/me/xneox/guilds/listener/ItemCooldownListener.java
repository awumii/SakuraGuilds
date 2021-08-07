package me.xneox.guilds.listener;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import java.util.concurrent.TimeUnit;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.projectiles.ProjectileSource;

public final class ItemCooldownListener implements Listener {
  private final SakuraGuildsPlugin plugin;

  public ItemCooldownListener(SakuraGuildsPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onElytraBoost(PlayerElytraBoostEvent event) {
    if (handleCooldown(event.getPlayer(), 5)) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onAppleEat(PlayerItemConsumeEvent event) {
    Material material = event.getItem().getType();
    if ((material == Material.GOLDEN_APPLE || material == Material.ENCHANTED_GOLDEN_APPLE)
        && handleCooldown(event.getPlayer(), 15)) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onPearlThrow(ProjectileLaunchEvent event) {
    ProjectileSource source = event.getEntity().getShooter();
    if (source instanceof Player
        && event.getEntity().getType() == EntityType.ENDER_PEARL
        && handleCooldown((Player) source, 10)) {
      event.setCancelled(true);
    }
  }

  /**
   * @param player The player who consumed.
   * @return whenever the event should be cancelled.
   */
  private boolean handleCooldown(Player player, int duration) {
    Material material = player.getInventory().getItemInMainHand().getType();
    if (this.plugin.cooldownManager().hasCooldown(player, material.name())) {
      ChatUtils.sendMessage(player, "&7Poczekaj jeszcze &c"
          + this.plugin.cooldownManager().getRemaining(player, material.name()));
      return true;
    }

    this.plugin.cooldownManager().add(player, material.name(), duration, TimeUnit.SECONDS);
    return false;
  }
}
