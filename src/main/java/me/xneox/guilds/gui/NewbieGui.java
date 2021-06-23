package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.util.InventoryUtils;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.api.InventorySize;
import me.xneox.guilds.util.gui.InventoryProviderImpl;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class NewbieGui extends InventoryProviderImpl {
    public NewbieGui(NeonGuilds plugin) {
        super(plugin, "Menu Nowicjusza", InventorySize.MEDIUM);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);

        ItemStack browse = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Publiczne gildie")
                .lore("")
                .lore("&7Kliknij, aby przeglądać gildie.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTJlYzlhOGE5MGQ5MGM3ODY1MWFmMmY5NjIwMTUxMTFmY2JmMTFhYzg3MzhmNTJiOGUyNGRhODYyYTM4NzFiYSJ9fX0=")
                .build();

        ItemStack help = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Lista komend")
                .lore("")
                .lore("&7Kliknij, aby wyświetlić pomoc.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzEwYTI2YTg3NTQ5ZTA2ZTA2NjlmNmM0OGY2MTQ3OGQyMzAyNWI5MWQ5MGVhMmM0YjlmODA0YjlmNzk2ZDA0YyJ9fX0=")
                .build();

        inventory.setItem(11, browse);
        inventory.setItem(15, help);
    }

    @Override
    public void event(ClickEvent event, Player player) {
        VisualUtils.click(player);

        if (event.slot() == 11) {
            this.plugin.getInventoryManager().open("browse", player);
        } else if (event.slot() == 15) {
            player.closeInventory();
            player.performCommand("g help");
        }
    }
}