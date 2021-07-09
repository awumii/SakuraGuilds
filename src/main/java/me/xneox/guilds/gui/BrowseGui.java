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

public class BrowseGui extends InventoryProviderImpl {
    public BrowseGui(NeonGuilds plugin) {
        super(plugin, "Przeglądanie gildii", InventorySize.BIGGEST);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);

        for (Guild guild : this.plugin.guildManager().guildMap().values()) {
            ItemStack item = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTMyMWMwNzAzNzg2ZWVmYTMxODA5YTc1NmY5NmY1NmExMmQ3OWE1ZGMwMDJlZTRiNWUzZDA0YzlhZDZkM2JhYSJ9fX0=")
                    .name("&6" + guild.name())
                    .lore("")
                    .lore("&eLider:")
                    .lore("&f" + guild.leader().nickname())
                    .lore("")
                    .lore("&eLiczba członków:")
                    .lore("&f" + guild.members().size() + "/" + guild.maxSlots() + " &7(&a" + guild.getOnlineMembers().size() + " &fonline&7)")
                    .lore("")
                    .lore("&eStatystyki Wojny:")
                    .lore("  &7→ &7Dywizja: " + guild.division().getName())
                    .lore("  &7→ &7Puchary rankingowe: &f" + guild.trophies())
                    .lore("  &7→ &7Zabójstwa: &f" + guild.kills())
                    .lore("  &7→ &7Śmierci: &f" + guild.deaths())
                    .lore("")
                    .lore("&cMusisz poprosić o zaproszenie aby dołączyć.")
                    .build();
            inventory.addItem(item);
        }
    }

    @Override
    public void event(ClickEvent event, Player player) {
        // Nothing
    }
}
