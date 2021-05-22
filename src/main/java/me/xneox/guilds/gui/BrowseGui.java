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

public class BrowseGui extends ClickableInventory {
    public BrowseGui(NeonGuilds plugin) {
        super(plugin, "Przeglądanie publicznych gildii", InventorySize.BIGGEST);
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);

        for (Guild guild : this.plugin.getGuildManager().getGuildMap().values()) {
            ItemStack item = new ItemBuilder(Material.PLAYER_HEAD)
                    .name("&6" + guild.getName())
                    .lore(guild.isPublic() ? "&a&l&nPUBLICZNA" : "&c&l&nPRYWATNA")
                    .lore("")
                    .lore("&eLider:")
                    .lore("&f" + guild.getLeader().getName())
                    .lore("")
                    .lore("&eLiczba członków:")
                    .lore("&f" + guild.getMembers().size() + "/" + guild.getMaxMembers() + " &7(&a" + guild.getOnlineMembers().size() + " &fonline&7)")
                    .lore("")
                    .lore("&eStatystyki Wojny:")
                    .lore("  &7→ &7Dywizja: " + guild.getDivision().getName())
                    .lore("  &7→ &7Puchary rankingowe: &f" + guild.getTrophies())
                    .lore("  &7→ &7Zabójstwa: &f" + guild.getKills())
                    .lore("  &7→ &7Śmierci: &f" + guild.getDeaths())
                    .lore("")
                    .lore(guild.isPublic() ? "&aKliknij aby dołączyć do gildii." : "&cNie można dołączyć do tej gildii.")
                    .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTMyMWMwNzAzNzg2ZWVmYTMxODA5YTc1NmY5NmY1NmExMmQ3OWE1ZGMwMDJlZTRiNWUzZDA0YzlhZDZkM2JhYSJ9fX0=")
                    .build();
            inventory.addItem(item);
        }
    }

    @Override
    public void onClick(ClickEvent event, Player player) {
        VisualUtils.click(player);

        ItemStack item = event.getItem();
        if (item.getType() == Material.PLAYER_HEAD) {
            Guild otherGuild = this.plugin.getGuildManager().getGuildExact(ChatColor.stripColor(item.getItemMeta().getDisplayName()));

            player.closeInventory();
            player.performCommand("g join " + otherGuild.getName());
        }
    }
}
