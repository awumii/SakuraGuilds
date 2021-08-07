package me.xneox.guilds.gui;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.text.ChatUtils;
import me.xneox.guilds.util.InventoryUtils;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.gui.InventoryProviderImpl;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.api.InventorySize;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AlliesGui extends InventoryProviderImpl {
    public AlliesGui(SakuraGuildsPlugin plugin) {
        super(plugin, "Zarządzanie Sojuszami", InventorySize.BIGGEST);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);
        Guild guild = this.plugin.guildManager().playerGuild(player.getName());

        if (guild.allies().isEmpty()) {
            ItemStack empty = ItemBuilder.of(Material.WRITABLE_BOOK)
                    .name("&c&n&oNie znaleziono sojuszy")
                    .build();
            inventory.setItem(22, empty);
        }

        for (String ally : guild.allies()) {
            Guild other = this.plugin.guildManager().get(ally);

            ItemStack item = ItemBuilder.skullOf(other.leader().nickname())
                    .name("&6" + ally)
                    .lore("")
                    .lore("&eLider:")
                    .lore("&f" + other.leader().nickname())
                    .lore("")
                    .lore("&eLiczba członków:")
                    .lore("&f" + other.members().size() + "/" + other.maxSlots() + " &7(&a" + other.getOnlineMembers().size() + " &fonline&7)")
                    .lore("")
                    .lore("&eZajęte ziemie:")
                    .lore("&f" + other.claims().size() + " &7(Limit: &f" + other.maxChunks() + "&7)")
                    .lore("")
                    .lore("&eStatystyki Wojny:")
                    .lore("  &7→ &7Dywizja: " + other.division().getName())
                    .lore("  &7→ &7Puchary rankingowe: &f" + other.trophies())
                    .lore("")
                    .lore("&cKliknij aby zerwać sojusz")
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
        if (item.getType() != Material.PLAYER_HEAD) {
            return;
        }

        if (isBackButton(event.item())) {
            this.plugin.inventoryManager().open("management", player);
            return;
        }

        Guild guild = this.plugin.guildManager().playerGuild(player.getName());
        Guild otherGuild = this.plugin.guildManager().get(ChatUtils.plainString(item.getItemMeta().displayName()));

        guild.allies().remove(otherGuild.name());
        otherGuild.allies().remove(guild.name());

        player.closeInventory();
        VisualUtils.sound(player, Sound.BLOCK_ANVIL_DESTROY);
        ChatUtils.broadcast("&7Gildia &6" + guild.name() + " &7zrywa sojusz z &6" + otherGuild.name());
    }
}
