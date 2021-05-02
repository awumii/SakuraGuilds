package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.util.InventoryUtils;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.gui.ClickEvent;
import me.xneox.guilds.util.gui.InventorySize;
import me.xneox.guilds.util.gui.ClickableInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class NewbieGui extends ClickableInventory {
    public NewbieGui(NeonGuilds plugin) {
        super(plugin, "Menu Nowicjusza", InventorySize.MEDIUM);
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);

        ItemStack browse = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Publiczne gildie")
                .addLore("")
                .addLore("&7Kliknij, aby przeglądać gildie.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTJlYzlhOGE5MGQ5MGM3ODY1MWFmMmY5NjIwMTUxMTFmY2JmMTFhYzg3MzhmNTJiOGUyNGRhODYyYTM4NzFiYSJ9fX0=")
                .build();

        ItemStack help = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Lista komend")
                .addLore("")
                .addLore("&7Kliknij, aby wyświetlić pomoc.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzEwYTI2YTg3NTQ5ZTA2ZTA2NjlmNmM0OGY2MTQ3OGQyMzAyNWI5MWQ5MGVhMmM0YjlmODA0YjlmNzk2ZDA0YyJ9fX0=")
                .build();

        inventory.setItem(11, browse);
        inventory.setItem(15, help);
    }

    @Override
    public void onClick(ClickEvent event, Player player) {
        VisualUtils.click(player);

        if (event.getSlot() == 11) {
            this.plugin.getInventoryManager().open("browse", player);
        } else if (event.getSlot() == 15) {
            player.closeInventory();
            player.performCommand("g help");
        }
    }
}
