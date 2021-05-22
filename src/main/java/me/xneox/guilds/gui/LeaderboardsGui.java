package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.InventoryUtils;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.RankedUtils;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.gui.ClickEvent;
import me.xneox.guilds.util.gui.ClickableInventory;
import me.xneox.guilds.util.gui.InventorySize;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LeaderboardsGui extends ClickableInventory {
    private List<Guild> guilds;

    public LeaderboardsGui(NeonGuilds plugin) {
        super(plugin, "Tabela Rankingowa", InventorySize.BIGGEST);
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
                .name("&cPowrót")
                .lore("&7Cofnij do menu gildii.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
                .build();

        inventory.setItem(8, close);
    }

    private ItemStack getForGuild(int position) {
        int realPosition = position + 1;
        if (position >= this.guilds.size()) {
            return new ItemBuilder(Material.PLAYER_HEAD)
                    .name("&cNikt nie zajął " + realPosition + " pozycji.")
                    .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjk3ZTJjOGI4Mjc2Mjc2ZTM4ZGVjYTg4NTA4NzJkNTllY2M5YzFmMzhmMmFkY2U0MDg1OGVkYjllNjM0ZDdiYSJ9fX0=")
                    .build();
        }

        Guild guild = this.guilds.get(position);
        return new ItemBuilder(Material.PLAYER_HEAD)
                .name("&7" + realPosition + ". &6" + guild.getName())
                .lore("")
                .lore("&eLider:")
                .lore("&f" + guild.getLeader().getName())
                .lore("")
                .lore("&eLiczba członków:")
                .lore("&f" + guild.getMembers().size() + "/" + guild.getMaxMembers() + " &7(&a" + guild.getOnlineMembers().size() + " &fonline&7)")
                .lore("")
                .lore("&eZajęte ziemie:")
                .lore("&f" + guild.getChunks().size() + " &7(Limit: &f" + guild.getMaxChunks() + "&7)")
                .lore("")
                .lore("&eStatystyki Wojny:")
                .lore("  &7→ &7Dywizja: " + guild.getDivision().getName())
                .lore("  &7→ &7Puchary rankingowe: &f" + guild.getTrophies())
                .lore("  &7→ &7Zabójstwa: &f" + guild.getKills())
                .lore("  &7→ &7Śmierci: &f" + guild.getDeaths())
                .lore("")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRkZTRkOTViYTRhNDZkYjdlNTY2YjM0NWY3ODk0ZDFkMjU4Zjg5M2ViOTJjNzgwYjNkYTc3NWVlZGY5MSJ9fX0=")
                .build();
    }

    @Override
    public void onClick(ClickEvent event, Player player) {
        VisualUtils.click(player);

        ItemStack item = event.getItem();
        if (item.getItemMeta().getDisplayName().contains("Powrót")) {
            this.plugin.getInventoryManager().open("management", player);
        }
    }
}
