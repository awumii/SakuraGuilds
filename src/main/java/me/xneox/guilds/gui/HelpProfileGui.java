package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.gui.InventoryProviderImpl;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.api.InventorySize;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class HelpProfileGui extends InventoryProviderImpl {
    public HelpProfileGui(NeonGuilds plugin) {
        super(plugin, "Menu Gracza", InventorySize.BIGGEST);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        ItemStack enchants = ItemBuilder.of(Material.AIR)
                .name("&dKsięga Zaklęć")
                .lore("&7Kliknij, aby wyświetlić wszystkie")
                .lore("&7dostępne enchanty na serwerze &8(200+)")
                .build();

        ItemStack help = ItemBuilder.of(Material.BOOK)
                .name("&6Lista Komend")
                .lore("&7Kliknij, aby wyświetlić wszystkie")
                .lore("&7dostepne komendy na serwerze.")
                .build();

        inventory.setItem(21, help);
        inventory.setItem(22, enchants);
    }

    @Override
    public void event(ClickEvent event, Player player) {
        switch (event.slot()) {
            case 22 -> player.performCommand("ecogui");
            case 21 -> {
                player.closeInventory();
                player.performCommand("pomoc");
            }
        }
    }
}
