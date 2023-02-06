package me.xneox.guilds.listener;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.enums.Permission;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

/**
 * This listener handles building, explosions etc... on the guild regions.
 */
public record GuildProtectionListener(@NotNull SakuraGuildsPlugin plugin) implements Listener {

  /**
   * @param player   The player who invoked the event.
   * @param location Location where the event happened.
   * @return whenever the event should be cancelled.
   */
  private boolean isProtected(Player player, Location location, boolean isBlockPlace) {
    var guild = this.plugin.guildManager().findAt(location);
    if (guild == null) {
      return false;
    }

    // No one should place blocks too close to the nexus.
    if (isBlockPlace && location.distance(guild.nexusLocation()) < 3) {
      ChatUtils.sendTitle(player, "", "&cNie można budować tak blisko nexusa!");
      return true;
    }

    // Check if player is member of the guild and has permission to build
    var member = guild.member(player);
    if (member != null && member.hasPermission(Permission.BUILD)) {
      return false;
    }

    ChatUtils.sendTitle(player, "", "&cNie możesz tego zrobić na tym terenie.");
    return true;
  }

  private boolean isProtected(Player player, Location location) {
    return this.isProtected(player, location, false);
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    event.getPlayer().getInventory().getItemInMainHand();
    if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.SPLASH_POTION) {
      if (this.isProtected(event.getPlayer(), event.getPlayer().getLocation())) {
        event.setCancelled(true);
      }
    }

    if (event.getAction() != Action.PHYSICAL && event.getClickedBlock() != null) {
      var mat = event.getClickedBlock().getType();
      if (mat.name().contains("DOOR")
          || mat.name().contains("BUTTON")
          || mat.name().contains("CHEST")
          || mat.name().contains("FENCE")
          || mat.name().contains("BARREL")
          || mat.name().contains("SHULKER")
          || mat == Material.LEVER) {
        if (this.isProtected(event.getPlayer(), event.getClickedBlock().getLocation())) {
          event.setCancelled(true);
        }
      }
    }
  }

  @EventHandler
  public void onBucketEmpty(PlayerBucketEmptyEvent event) {
    if (this.isProtected(event.getPlayer(), event.getBlock().getLocation())) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onBucketFill(PlayerBucketFillEvent event) {
    if (this.isProtected(event.getPlayer(), event.getBlock().getLocation())) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onBlockFromTo(BlockFromToEvent event) {
    if (this.plugin.guildManager().findAt(event.getBlock().getLocation()) != null) {
      return;
    }

    if (this.plugin.guildManager().findAt(event.getToBlock().getLocation()) != null) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onPistonExtend(BlockPistonExtendEvent event) {
    for (Block block : event.getBlocks()) {
      if (this.plugin.guildManager().findAt(block.getLocation()) != null) {
        event.setCancelled(true);
      }
    }
  }

  @EventHandler
  public void onPistonRetread(BlockPistonRetractEvent event) {
    for (Block block : event.getBlocks()) {
      if (this.plugin.guildManager().findAt(block.getLocation()) != null) {
        event.setCancelled(true);
      }
    }
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (this.isProtected(event.getPlayer(), event.getBlock().getLocation())) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    if (this.isProtected(event.getPlayer(), event.getBlock().getLocation(), true)) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onEntityExplode(EntityExplodeEvent event) {
    var affectedBlocks = event.blockList().iterator();

    while (affectedBlocks.hasNext()) {
      var block = affectedBlocks.next();
      var guild = this.plugin.guildManager().findAt(block.getLocation());

      if (guild != null && guild.isShieldActive()) {
        affectedBlocks.remove();
      }
    }
  }
}
