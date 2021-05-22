package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.InventoryUtils;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.gui.ClickEvent;
import me.xneox.guilds.util.gui.ClickableInventory;
import me.xneox.guilds.util.gui.InventorySize;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WarGui extends ClickableInventory {
    public WarGui(NeonGuilds plugin) {
        super(plugin, "Wyświetlanie aktywnych gildii...", InventorySize.BIGGEST);
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);
        Guild guild = this.plugin.getGuildManager().getGuild(player.getName());

        for (Guild other : this.plugin.getGuildManager().getGuildMap().values()) {
            if (other.equals(guild) || other.getOnlineMembers().isEmpty()) {
                continue;
            }

            ItemStack item = new ItemBuilder(Material.PLAYER_HEAD)
                    .name("&6" + other.getName())
                    .lore("")
                    .lore("&eLider:")
                    .lore("&f" + other.getLeader().getName())
                    .lore("")
                    .lore("&eLiczba członków:")
                    .lore("&f" + other.getMembers().size() + "/" + other.getMaxMembers() + " &7(&a" + other.getOnlineMembers().size() + " &fonline&7)")
                    .lore("")
                    .lore("&eStatystyki Wojny:")
                    .lore("  &7→ &7Dywizja: " + other.getDivision().getName())
                    .lore("  &7→ &7Puchary rankingowe: &f" + other.getTrophies())
                    .lore("  &7→ &7Zabójstwa: &f" + other.getKills())
                    .lore("  &7→ &7Śmierci: &f" + other.getDeaths())
                    .lore("")
                    .lore("&cKliknij aby wypowiedzieć wojnę")
                    .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTMyMWMwNzAzNzg2ZWVmYTMxODA5YTc1NmY5NmY1NmExMmQ3OWE1ZGMwMDJlZTRiNWUzZDA0YzlhZDZkM2JhYSJ9fX0=")
                    .build();
            inventory.addItem(item);
        }

        ItemStack close = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&cPowrót")
                .lore("&7Cofnij do menu gildii.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
                .build();

        inventory.setItem(8, close);
    }

    @Override
    public void onClick(ClickEvent event, Player player) {
        VisualUtils.click(player);

        ItemStack item = event.getItem();
        if (item.getType() == Material.PLAYER_HEAD) {
            if (item.getItemMeta().getDisplayName().contains("Powrót")) {
                this.plugin.getInventoryManager().open("management", player);
            } else {
                Guild otherGuild = this.plugin.getGuildManager().getGuildExact(ChatColor.stripColor(item.getItemMeta().getDisplayName()));

                player.closeInventory();
                player.performCommand("g war " + otherGuild.getName());
            }
        }
    }
}
