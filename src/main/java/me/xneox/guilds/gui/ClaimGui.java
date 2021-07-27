package me.xneox.guilds.gui;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.*;
import me.xneox.guilds.util.gui.InventoryProviderImpl;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.api.InventorySize;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ClaimGui extends InventoryProviderImpl {
    public ClaimGui(SakuraGuildsPlugin plugin) {
        super(plugin, "Zarządzanie zajętymi ziemiami", InventorySize.BIGGEST);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);
        Guild guild = this.plugin.guildManager().playerGuild(player.getName());

        for (String chunk : guild.claims()) {
            List<Player> players = ChunkUtils.getPlayersAt(ChunkUtils.toChunk(chunk));
            ItemStack item = ItemBuilder.of(Material.GRASS_BLOCK)
                    .name("&6#" + guild.claims().indexOf(chunk) + " (" + LocationUtils.toSimpleString(ChunkUtils.getCenter(chunk)) + ")")
                    .lore("&7&oTwoja gildia posiada ten chunk.")
                    .lore("")
                    .lore("&7Gracze: &c" + (players.isEmpty() ? "Brak" : ChatUtils.formatPlayerList(players)))
                    .lore("")
                    .lore("&eKliknij, aby się przeteleportować")
                    .amount(players.isEmpty() ? 1 : players.size())
                    .build();
            inventory.addItem(item);
        }

        ItemStack close = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
                .name("&cPowrót")
                .lore("&7Cofnij do menu gildii.")
                .build();

        inventory.setItem(8, close);
    }

    @Override
    public void event(ClickEvent event, Player player) {
        VisualUtils.click(player);

        ItemStack item = event.item();
        if (isBackButton(item)) {
            this.plugin.inventoryManager().open("management", player);
            return;
        }

        if (item.getType() == Material.GRASS_BLOCK) {
            Guild guild = this.plugin.guildManager().playerGuild(player);

            String name = ChatUtils.plainString(item.getItemMeta().displayName()).split(" \\(")[0];
            int index = Integer.parseInt(name.replace("#", ""));

            Location center = ChunkUtils.getCenter(guild.claims().get(index));
            this.plugin.userManager().getUser(player).beginTeleport(player.getLocation(), center);

            player.closeInventory();
        }
    }
}
