package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.InventoryUtils;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.InventoryProviderImpl;
import me.xneox.guilds.util.gui.api.InventorySize;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WarGui extends InventoryProviderImpl {
    public WarGui(NeonGuilds plugin) {
        super(plugin, "Wyświetlanie aktywnych gildii...", InventorySize.BIGGEST);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);
        Guild guild = this.plugin.guildManager().playerGuild(player.getName());

        for (Guild other : this.plugin.guildManager().guildMap().values()) {
            if (other.equals(guild) || other.getOnlineMembers().isEmpty()) {
                continue;
            }

            ItemStack item = ItemBuilder.of(Material.PLAYER_HEAD)
                    .name("&6" + other.name())
                    .lore("")
                    .lore("&eLider:")
                    .lore("&f" + other.leader().nickname())
                    .lore("")
                    .lore("&eLiczba członków:")
                    .lore("&f" + other.members().size() + "/" + other.maxSlots() + " &7(&a" + other.getOnlineMembers().size() + " &fonline&7)")
                    .lore("")
                    .lore("&eStatystyki Wojny:")
                    .lore("  &7→ &7Dywizja: " + other.division().getName())
                    .lore("  &7→ &7Puchary rankingowe: &f" + other.trophies())
                    .lore("  &7→ &7Zabójstwa: &f" + other.kills())
                    .lore("  &7→ &7Śmierci: &f" + other.deaths())
                    .lore("")
                    .lore("&cKliknij aby wypowiedzieć wojnę")
                    .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTMyMWMwNzAzNzg2ZWVmYTMxODA5YTc1NmY5NmY1NmExMmQ3OWE1ZGMwMDJlZTRiNWUzZDA0YzlhZDZkM2JhYSJ9fX0=")
                    .build();
            inventory.addItem(item);
        }

        ItemStack close = ItemBuilder.of(Material.PLAYER_HEAD)
                .name("&cPowrót")
                .lore("&7Cofnij do menu gildii.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
                .build();

        inventory.setItem(8, close);
    }

    @Override
    public void event(ClickEvent event, Player player) {
        VisualUtils.click(player);

        ItemStack item = event.item();
        if (item.getType() == Material.PLAYER_HEAD) {
            if (item.getItemMeta().getDisplayName().contains("Powrót")) {
                this.plugin.inventoryManager().open("management", player);
            } else {
                Guild otherGuild = this.plugin.guildManager().get(ChatUtils.plainString(item.getItemMeta().displayName()));

                player.closeInventory();
                player.performCommand("g war " + otherGuild.name());
            }
        }
    }
}
