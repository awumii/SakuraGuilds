package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.*;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.InventoryProviderImpl;
import me.xneox.guilds.util.gui.api.InventorySize;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ClaimGui extends InventoryProviderImpl {
    public ClaimGui(NeonGuilds plugin) {
        super(plugin, "Zarządzanie zajętymi ziemiami", InventorySize.BIGGEST);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);
        Guild guild = this.plugin.getGuildManager().getGuild(player.getName());

        for (String chunk : guild.getChunks()) {
            List<Player> players = ChunkUtils.getPlayersAt(ChunkUtils.toChunk(chunk));
            ItemStack item = new ItemBuilder(Material.GRASS_BLOCK, players.isEmpty() ? 1 : players.size())
                    .name("&6#" + guild.getChunks().indexOf(chunk) + " (" + LocationUtils.toSimpleString(ChunkUtils.getCenter(chunk)) + ")")
                    .lore("&7&oTwoja gildia posiada ten chunk.")
                    .lore("")
                    .lore("&7Gracze: &c" + (players.isEmpty() ? "Brak" : ChatUtils.formatPlayerList(players)))
                    .lore("")
                    .lore("&eKliknij, aby się przeteleportować")
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
        if (item.getType() == Material.GRASS_BLOCK) {
            Guild guild = this.plugin.getGuildManager().getGuild(player);

            String name = ChatColor.stripColor(item.getItemMeta().getDisplayName()).split(" \\(")[0];
            int index = Integer.parseInt(name.replace("#", ""));

            Location center = ChunkUtils.getCenter(guild.getChunks().get(index));
            this.plugin.getUserManager().getUser(player).beginTeleport(player.getLocation(), center);

            player.closeInventory();
        } else if (item.getType() == Material.PLAYER_HEAD) {
            this.plugin.getInventoryManager().open("management", player);
        }
    }
}
