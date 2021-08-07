package me.xneox.guilds.gui;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.InventoryUtils;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.gui.InventoryProviderImpl;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.api.InventorySize;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class NewbieGui extends InventoryProviderImpl {
    public NewbieGui(SakuraGuildsPlugin plugin) {
        super(plugin, "Menu Nowicjusza", InventorySize.MEDIUM);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);

        User user = this.plugin.userManager().getUser(player);
        ItemStack race = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWI3M2NkNDEzZDgxZThjM2NlZTQ2ZmU4YTgzMjI1MjY1MmRjMzM1ODRkZGU0ZGRkZjNjYTgzNmRjZDE3NGUifX19")
                .name("&7Wybrana rasa: " + user.race().title())
                .lore("")
                .lore("&7Kliknij, aby wybrać bonusy rasy!")
                .build();

        ItemStack browse = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTJlYzlhOGE5MGQ5MGM3ODY1MWFmMmY5NjIwMTUxMTFmY2JmMTFhYzg3MzhmNTJiOGUyNGRhODYyYTM4NzFiYSJ9fX0=")
                .name("&cNie posiadasz gildii!")
                .lore("")
                .lore("&7Dołącz do gildii aby wyświetlić panel.")
                .build();

        ItemStack help = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzEwYTI2YTg3NTQ5ZTA2ZTA2NjlmNmM0OGY2MTQ3OGQyMzAyNWI5MWQ5MGVhMmM0YjlmODA0YjlmNzk2ZDA0YyJ9fX0=")
                .name("&6Lista komend")
                .lore("")
                .lore("&7Kliknij, aby wyświetlić pomoc.")
                .build();

        inventory.setItem(11, race);
        inventory.setItem(13, browse);
        inventory.setItem(15, help);
    }

    @Override
    public void event(ClickEvent event, Player player) {
        VisualUtils.click(player);

        if (event.slot() == 11) {
            player.closeInventory();
            player.performCommand("g help");
        }
    }
}
