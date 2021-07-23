package me.xneox.guilds.listener;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.util.HookUtils;
import me.xneox.guilds.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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

public class PlayerMenuListener implements Listener {
    private final NeonGuilds plugin;

    public PlayerMenuListener(NeonGuilds plugin) {
        this.plugin = plugin;
    }

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

        if (!HookUtils.hasCombatTag(event.getPlayer()) && isMenuItem(event.getItem())) {
            event.setCancelled(true);
            this.plugin.inventoryManager().open("profile", event.getPlayer());
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
        return itemStack != null && itemStack.getType() == Material.PLAYER_HEAD && itemStack.getItemFlags().contains(ItemFlag.HIDE_ENCHANTS);
    }

    private void giveMenuItem(Player player) {
        ItemStack menu = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDUwMDI5MmY0YWZlNTJkMTBmMjk5ZGZiMjYwMzYzMjI4MzA0NTAzMzFlMDAzMDg0YmIyMjAzMzM1MzA2NjRlMSJ9fX0=")
                .name("&6Menu Gracza")
                .lore("&7Kliknij prawym trzymając")
                .lore("&7w ręce, aby otworzyć menu.")
                .flags(ItemFlag.HIDE_ENCHANTS)
                .build();

        player.getInventory().setItem(8, menu);
    }
}
