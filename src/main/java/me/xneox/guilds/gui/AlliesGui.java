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
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AlliesGui extends InventoryProviderImpl {
    public AlliesGui(NeonGuilds plugin) {
        super(plugin, "Zarządzanie Sojuszami", InventorySize.BIGGEST);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);
        Guild guild = this.plugin.getGuildManager().getGuild(player.getName());

        for (String ally : guild.getAllies()) {
            Guild other = this.plugin.getGuildManager().getGuildExact(ally);

            ItemStack item = new ItemBuilder(Material.PLAYER_HEAD)
                    .name("&6" + ally)
                    .lore("")
                    .lore("&eLider:")
                    .lore("&f" + other.getLeader().nickname())
                    .lore("")
                    .lore("&eLiczba członków:")
                    .lore("&f" + other.getMembers().size() + "/" + other.maxSlots() + " &7(&a" + other.getOnlineMembers().size() + " &fonline&7)")
                    .lore("")
                    .lore("&eZajęte ziemie:")
                    .lore("&f" + other.getChunks().size() + " &7(Limit: &f" + other.maxChunks() + "&7)")
                    .lore("")
                    .lore("&eStatystyki Wojny:")
                    .lore("  &7→ &7Dywizja: " + other.getDivision().getName())
                    .lore("  &7→ &7Puchary rankingowe: &f" + other.getTrophies())
                    .lore("  &7→ &7Zabójstwa: &f" + other.getKills())
                    .lore("  &7→ &7Śmierci: &f" + other.getDeaths())
                    .lore("")
                    .lore("&cKliknij aby zerwać sojusz")
                    .skullOwner(other.getLeader().nickname())
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
    public void event(ClickEvent event, Player player) {
        VisualUtils.click(player);

        ItemStack item = event.item();
        if (item.getType() == Material.PLAYER_HEAD) {
            if (item.getItemMeta().getDisplayName().contains("Powrót")) {
                this.plugin.getInventoryManager().open("management", player);
            } else {
                Guild guild = this.plugin.getGuildManager().getGuild(player.getName());
                Guild otherGuild = this.plugin.getGuildManager().getGuildExact(ChatColor.stripColor(item.getItemMeta().getDisplayName()));

                guild.getAllies().remove(otherGuild.getName());
                otherGuild.getAllies().remove(guild.getName());

                player.closeInventory();
                VisualUtils.sound(player, Sound.BLOCK_ANVIL_DESTROY);
                ChatUtils.broadcast("&7Gildia &6" + guild.getName() + " &7zrywa sojusz z &6" + otherGuild.getName());
            }
        }
    }
}
