package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.InventoryUtils;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.gui.ClickEvent;
import me.xneox.guilds.util.gui.ClickableInventory;
import me.xneox.guilds.util.gui.InventorySize;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AlliesGui extends ClickableInventory {
    public AlliesGui(NeonGuilds plugin) {
        super(plugin, "Zarządzanie Sojuszami", InventorySize.BIGGEST);
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);
        Guild guild = this.plugin.getGuildManager().getGuild(player.getName());

        for (String ally : guild.getAllies()) {
            Guild other = this.plugin.getGuildManager().getGuildExact(ally);

            ItemStack item = new ItemBuilder(Material.PLAYER_HEAD)
                    .setName("&6" + ally)
                    .addLore("")
                    .addLore("&eLider:")
                    .addLore("&f" + other.getLeader())
                    .addLore("")
                    .addLore("&eLiczba członków:")
                    .addLore("&f" + other.getMembers().size() + "/" + other.getMaxMembers() + " &7(&a" + other.getOnlineMembers().size() + " &fonline&7)")
                    .addLore("")
                    .addLore("&eZajęte ziemie:")
                    .addLore("&f" + other.getChunks().size() + " &7(Limit: &f" + other.getMaxChunks() + "&7)")
                    .addLore("")
                    .addLore("&eStatystyki Wojny:")
                    .addLore("  &7→ &7Dywizja: " + other.getDivision().getName())
                    .addLore("  &7→ &7Puchary rankingowe: &f" + other.getTrophies())
                    .addLore("  &7→ &7Zabójstwa: &f" + other.getKills())
                    .addLore("  &7→ &7Śmierci: &f" + other.getDeaths())
                    .addLore("")
                    .addLore("&cKliknij aby zerwać sojusz")
                    .setSkullOwner(other.getLeader())
                    .build();
            inventory.addItem(item);
        }

        ItemStack close = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&cPowrót")
                .addLore("&7Cofnij do menu gildii.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
                .build();

        inventory.setItem(8, close);
    }

    @Override
    public void onClick(ClickEvent event, Player player) {
        VisualUtils.click(player);

        ItemStack item = event.getItem();
        if (item.getType() == Material.PLAYER_HEAD) {
            if (item.getItemMeta().getDisplayName().contains("Powrót")) {
                this.plugin.getInventoryManager().open("manage", player);
            } else {
                Guild guild = this.plugin.getGuildManager().getGuild(player.getName());
                Guild otherGuild = this.plugin.getGuildManager().getGuildExact(ChatColor.stripColor(item.getItemMeta().getDisplayName()));

                guild.getAllies().remove(otherGuild.getName());
                otherGuild.getAllies().remove(guild.getName());

                player.closeInventory();
                VisualUtils.playSound(player, Sound.BLOCK_ANVIL_DESTROY);
                ChatUtils.broadcast("&7Gildia &6" + guild.getName() + " &7zrywa sojusz z &6" + otherGuild.getName());
            }
        }
    }
}
