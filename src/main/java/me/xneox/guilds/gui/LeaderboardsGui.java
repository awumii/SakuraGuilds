package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.*;
import me.xneox.guilds.util.gui.InventorySize;
import me.xneox.guilds.util.gui.inventories.ClickableInventory;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LeaderboardsGui extends ClickableInventory {
    private final NeonGuilds plugin;
    private List<Guild> guilds;

    public LeaderboardsGui(NeonGuilds plugin) {
        super("Tabela Rankingowa", "leaderboards", InventorySize.BIGGEST);
        this.plugin = plugin;
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);

        this.guilds = RankedUtils.getLeaderboard(this.plugin.getGuildManager().getGuildMap().values());

        inventory.setItem(13, getForGuild(0));
        inventory.setItem(21, getForGuild(1));
        inventory.setItem(23, getForGuild(2));
        inventory.setItem(29, getForGuild(3));
        inventory.setItem(31, getForGuild(4));
        inventory.setItem(33, getForGuild(5));
        inventory.setItem(37, getForGuild(6));
        inventory.setItem(39, getForGuild(7));
        inventory.setItem(41, getForGuild(8));
        inventory.setItem(43, getForGuild(9));

        ItemStack close = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&cPowrót")
                .addLore("&7Cofnij do menu gildii.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
                .build();

        inventory.setItem(8, close);
    }

    private ItemStack getForGuild(int position) {
        int realPosition = position + 1;
        if (position >= this.guilds.size()) {
            return new ItemBuilder(Material.PLAYER_HEAD)
                    .setName("&cNikt nie zajął " + realPosition + " pozycji.")
                    .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjk3ZTJjOGI4Mjc2Mjc2ZTM4ZGVjYTg4NTA4NzJkNTllY2M5YzFmMzhmMmFkY2U0MDg1OGVkYjllNjM0ZDdiYSJ9fX0=")
                    .build();
        }

        Guild guild = this.guilds.get(position);
        return new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&7" + realPosition + ". &6" + guild.getName())
                .addLore("")
                .addLore("&eLider:")
                .addLore("&f" + guild.getLeader())
                .addLore("")
                .addLore("&eLiczba członków:")
                .addLore("&f" + guild.getMembers().size() + "/" + guild.getMaxMembers() + " &7(&a" + guild.getOnlineMembers().size() + " &fonline&7)")
                .addLore("")
                .addLore("&eZajęte ziemie:")
                .addLore("&f" + guild.getChunks().size() + " &7(Limit: &f" + guild.getMaxChunks() + "&7)")
                .addLore("")
                .addLore("&eStatystyki Wojny:")
                .addLore("  &7→ &7Dywizja: " + guild.getDivision().getName())
                .addLore("  &7→ &7Puchary rankingowe: &f" + guild.getTrophies())
                .addLore("  &7→ &7Zabójstwa: &f" + guild.getKills())
                .addLore("  &7→ &7Śmierci: &f" + guild.getDeaths())
                .addLore("")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRkZTRkOTViYTRhNDZkYjdlNTY2YjM0NWY3ODk0ZDFkMjU4Zjg5M2ViOTJjNzgwYjNkYTc3NWVlZGY5MSJ9fX0=")
                .build();
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
            this.plugin.getInventoryManager().open("manage", player);
        }
    }
}
