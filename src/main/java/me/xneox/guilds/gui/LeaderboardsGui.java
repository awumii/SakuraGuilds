package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.InventoryUtils;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.RankedUtils;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.InventoryProviderImpl;
import me.xneox.guilds.util.gui.api.InventorySize;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LeaderboardsGui extends InventoryProviderImpl {
    private List<Guild> guilds;

    public LeaderboardsGui(NeonGuilds plugin) {
        super(plugin, "Tabela Rankingowa", InventorySize.BIGGEST);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);

        this.guilds = RankedUtils.getLeaderboard(this.plugin.guildManager().guildMap().values());

        inventory.setItem(13, buildGuildInfo(0));
        inventory.setItem(21, buildGuildInfo(1));
        inventory.setItem(23, buildGuildInfo(2));
        inventory.setItem(29, buildGuildInfo(3));
        inventory.setItem(31, buildGuildInfo(4));
        inventory.setItem(33, buildGuildInfo(5));
        inventory.setItem(37, buildGuildInfo(6));
        inventory.setItem(39, buildGuildInfo(7));
        inventory.setItem(41, buildGuildInfo(8));
        inventory.setItem(43, buildGuildInfo(9));

        ItemStack close = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
                .name("&cPowrót")
                .lore("&7Cofnij do menu gildii.")
                .build();

        inventory.setItem(8, close);
    }

    private ItemStack buildGuildInfo(int position) {
        int realPosition = position + 1;
        if (position >= this.guilds.size()) {
            return ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjk3ZTJjOGI4Mjc2Mjc2ZTM4ZGVjYTg4NTA4NzJkNTllY2M5YzFmMzhmMmFkY2U0MDg1OGVkYjllNjM0ZDdiYSJ9fX0=")
                    .name("&cNikt nie zajął " + realPosition + " pozycji.")
                    .build();
        }

        Guild guild = this.guilds.get(position);
        return ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRkZTRkOTViYTRhNDZkYjdlNTY2YjM0NWY3ODk0ZDFkMjU4Zjg5M2ViOTJjNzgwYjNkYTc3NWVlZGY5MSJ9fX0=")
                .name("&7" + realPosition + ". &6" + guild.name())
                .lore("")
                .lore("&eLider:")
                .lore("&f" + guild.leader().nickname())
                .lore("")
                .lore("&eLiczba członków:")
                .lore("&f" + guild.members().size() + "/" + guild.maxSlots() + " &7(&a" + guild.getOnlineMembers().size() + " &fonline&7)")
                .lore("")
                .lore("&eZajęte ziemie:")
                .lore("&f" + guild.claims().size() + " &7(Limit: &f" + guild.maxChunks() + "&7)")
                .lore("")
                .lore("&eStatystyki Wojny:")
                .lore("  &7→ &7Dywizja: " + guild.division().getName())
                .lore("  &7→ &7Puchary rankingowe: &f" + guild.trophies())
                .lore("  &7→ &7Zabójstwa: &f" + guild.kills())
                .lore("  &7→ &7Śmierci: &f" + guild.deaths())
                .lore("")
                .build();
    }

    @Override
    public void event(ClickEvent event, Player player) {
        VisualUtils.click(player);

        ItemStack item = event.item();
        isBackButton(item); // no need to do anything here
    }
}
