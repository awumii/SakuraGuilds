package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.*;
import me.xneox.guilds.util.gui.InventorySize;
import me.xneox.guilds.util.gui.inventories.ClickableInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AlliesGui extends ClickableInventory {
    private final NeonGuilds plugin;

    public AlliesGui(NeonGuilds plugin) {
        super("Zarządzanie Sojuszami", "allies", InventorySize.BIGGEST);
        this.plugin = plugin;
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
                    .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTMyMWMwNzAzNzg2ZWVmYTMxODA5YTc1NmY5NmY1NmExMmQ3OWE1ZGMwMDJlZTRiNWUzZDA0YzlhZDZkM2JhYSJ9fX0=")
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
    public void onClick(InventoryClickEvent event, Player player) {
        event.setCancelled(true);
        VisualUtils.playSound(player, Sound.BLOCK_WOODEN_BUTTON_CLICK_ON);

        ItemStack item = event.getCurrentItem();
        if (item == null) {
            return;
        }

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
