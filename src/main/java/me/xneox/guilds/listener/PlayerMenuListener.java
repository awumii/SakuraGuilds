package me.xneox.guilds.listener;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import me.xneox.guilds.gui.HelpProfileGui;
import me.xneox.guilds.util.inventory.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public final class PlayerMenuListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    giveMenuItem(event.getPlayer());
  }

  @EventHandler
  public void onRespawn(PlayerPostRespawnEvent event) {
    giveMenuItem(event.getPlayer());
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
      return;
    }

    if (isMenuItem(event.getItem())) {
      event.setCancelled(true);
      HelpProfileGui.INVENTORY.open(event.getPlayer());
    }
  }

  @EventHandler
  public void onItemDrop(PlayerDropItemEvent event) {
    if (isMenuItem(event.getItemDrop().getItemStack())) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    if (isMenuItem(event.getCurrentItem())) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    event.getDrops().removeIf(this::isMenuItem);
  }

  private boolean isMenuItem(ItemStack itemStack) {
    return itemStack != null
        && itemStack.getType() == Material.PLAYER_HEAD
        && itemStack.getItemFlags().contains(ItemFlag.HIDE_ENCHANTS);
  }

  private void giveMenuItem(Player player) {
    ItemStack menu = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTA5Y2RlMWFmYzk1YTQ3NGQyMjI1NTQwOTdlZDZkMzkxZTdjYzdhZTFmMjAyZmRiZmQyZDZkYmM5ODMwOTM3MCJ9fX0=")
        .name("&#CDC392Menu Gracza &7(Kliknij Prawym)")
        .lore("&7Menu które zawiera wszystkie przydatne")
        .lore("&7funkcje podczas grania na serwerze.")
        .lore("")
        .lore("&eKliknij prawym podczas trzymania.")
        .flags(ItemFlag.HIDE_ENCHANTS)
        .build();

    player.getInventory().setItem(8, menu);
  }
}
