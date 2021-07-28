package me.xneox.guilds.gui;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.type.Permission;
import me.xneox.guilds.type.Upgrade;
import me.xneox.guilds.util.ChatUtils;
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

public class UpgradesGui extends InventoryProviderImpl {
    public UpgradesGui(SakuraGuildsPlugin plugin) {
        super(plugin, "Menu ulepszeń gildyjnych", InventorySize.MEDIUM);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
        Guild guild = this.plugin.guildManager().playerGuild(player.getName());

        ItemStack members = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQ0YzFlOGU4MjY3MmJiYTU4OTJmZDQ2NTlmOGRhZDg0ZDE1NDVkYjI2ZGI1MmVjYzkxOGYzMmExMzkxNTEzIn19fQ==")
                .name("&6Zwiększenie Slotów")
                .lore("")
                .lore("&7Limit członków: &f" + guild.maxSlots() + " &a(+" + Upgrade.SLOTS.multiplier() + ")")
                .lore("&7Koszt ulepszenia: &6" + Upgrade.SLOTS.cost(guild) + "$")
                .lore("")
                .lore("&eKliknij, aby ulepszyć.")
                .build();

        ItemStack claims = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzgyZWU4NWZlNmRjNjMyN2RhZDIwMmZjYTkzYzlhOTRhYzk5YjdiMTY5NzUyNGJmZTk0MTc1ZDg4NzI1In19fQ==")
                .name("&6Więcej Terenu")
                .lore("")
                .lore("&7Limit chunków: &f" + guild.maxChunks() + " &a(+" + Upgrade.CHUNKS.multiplier() + ")")
                .lore("&7Koszt ulepszenia: &6" + Upgrade.CHUNKS.cost(guild) + "$")
                .lore("")
                .lore("&eKliknij, aby ulepszyć.")
                .build();

        ItemStack storage = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGMxYmQwYWNmMmY5NDA2NGNjYTI3YjBhZWYzNWQ4YzEyYTg5MjhkNDM2MzdhN2ZlNzFmNWVlZTQ1ODk3NWUxYSJ9fX0=")
                .name("&6Zwiększony Magazyn")
                .lore("")
                .lore("&7Ilość slotów: &f" + guild.maxStorage() + " &a(+" + Upgrade.STORAGE.multiplier() + ")")
                .lore("&7Koszt ulepszenia: &6" + Upgrade.STORAGE.cost(guild) + "$")
                .lore("")
                .lore("&eKliknij, aby ulepszyć.")
                .build();

        ItemStack bank = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM2ZTk0ZjZjMzRhMzU0NjVmY2U0YTkwZjJlMjU5NzYzODllYjk3MDlhMTIyNzM1NzRmZjcwZmQ0ZGFhNjg1MiJ9fX0=")
                .name("&6Bank gildyjny")
                .lore("")
                .lore("&7W banku znajduje się: &6" + guild.money() + "$")
                .lore("")
                .lore("&7Aby wpłacać pieniądze, użyj komendy:")
                .lore(" &8→ &6&n/g donate <ilość>")
                .lore("")
                .lore("&cWpłaconych pieniędzy nie można wypłacić!")
                .build();

        ItemStack close = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
                .name("&cPowrót")
                .lore("&7Cofnij do menu gildii.")
                .build();

        inventory.setItem(10, members);
        inventory.setItem(11, claims);
        inventory.setItem(12, storage);
        inventory.setItem(14, bank);
        inventory.setItem(16, close);
    }

    @Override
    public void event(ClickEvent event, Player player) {
        VisualUtils.click(player);

        ItemStack item = event.item();
        if (isBackButton(item)) {
            this.plugin.inventoryManager().open("management", player);
            return;
        }

        Guild guild = this.plugin.guildManager().playerGuild(player);
        // Check for permission
        if (!guild.member(player).hasPermission(Permission.UPGRADES)) {
            VisualUtils.sound(player, Sound.ENTITY_VILLAGER_NO);
            ChatUtils.sendMessage(player, "&cNie posiadasz do tego uprawnień!");
            return;
        }

        // Define upgrade type
        Upgrade upgrade = switch (event.slot()) {
            case 10 -> Upgrade.SLOTS;
            case 11 -> Upgrade.CHUNKS;
            case 12 -> Upgrade.STORAGE;
            default -> null;
        };

        if (upgrade != null) {
            int cost = upgrade.cost(guild);
            if (guild.money() < cost) {
                VisualUtils.sound(player, Sound.ENTITY_VILLAGER_NO);
                ChatUtils.sendMessage(player, "&cTwoja gildia nie posiada tyle pieniędzy!");
                return;
            }

            if (upgrade.currentValue(guild) >= upgrade.maxValue()) {
                VisualUtils.sound(player, Sound.ENTITY_VILLAGER_NO);
                ChatUtils.sendMessage(player, "&cTo ulepszenie jest już na maksymalnym poziomie!");
                return;
            }

            guild.money(guild.money() - cost);
            upgrade.performUpgrade(guild);

            // Play visual alerts
            VisualUtils.sound(player, Sound.ENTITY_VILLAGER_TRADE);
            ChatUtils.guildAlert(guild, guild.member(player).displayName() + " &7kupuje ulepszenie &e" + upgrade.title() +
                    " &8(&7wartość: &6" + (upgrade.currentValue(guild) - upgrade.multiplier()) + " ➠ " + upgrade.currentValue(guild) +
                    "&7, &a+" + upgrade.multiplier() + "&8)");

            // Refresh inventory
            this.plugin.inventoryManager().open("upgrades", player);
        }
    }
}
