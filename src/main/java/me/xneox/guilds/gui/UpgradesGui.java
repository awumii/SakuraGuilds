package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.type.Permission;
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
    public UpgradesGui(NeonGuilds plugin) {
        super(plugin, "Menu ulepszeń gildyjnych", InventorySize.MEDIUM);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
        Guild guild = this.plugin.getGuildManager().getGuild(player.getName());

        ItemStack members = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Zwiększenie miejsc")
                .lore("")
                .lore("&7Limit członków: &b" + guild.maxSlots() + " &7→ &b" + (guild.maxSlots() + 1))
                .lore("&7Koszt ulepszenia: &6" + cost(guild.maxSlots() / 5) + "$")
                .lore("")
                .lore("&eKliknij, aby ulepszyć.")
                .skullTexture("\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQ0YzFlOGU4MjY3MmJiYTU4OTJmZDQ2NTlmOGRhZDg0ZDE1NDVkYjI2ZGI1MmVjYzkxOGYzMmExMzkxNTEzIn19fQ==\"")
                .build();

        ItemStack claims = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Więcej terenu")
                .lore("")
                .lore("&7Limit chunków: &b" + guild.maxChunks() + " &7→ &b" + (guild.maxChunks() + 1))
                .lore("&7Koszt ulepszenia: &6" + cost(guild.maxSlots()) + "$")
                .lore("")
                .lore("&eKliknij, aby ulepszyć.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzgyZWU4NWZlNmRjNjMyN2RhZDIwMmZjYTkzYzlhOTRhYzk5YjdiMTY5NzUyNGJmZTk0MTc1ZDg4NzI1In19fQ==")
                .build();

        ItemStack bank = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Bank gildyjny")
                .lore("")
                .lore("&7W banku znajduje się: &6" + guild.money() + "$")
                .lore("")
                .lore("&7Aby wpłacać pieniądze, użyj komendy:")
                .lore(" &7→ &6/g donate <ilość>")
                .lore("")
                .lore("&cWpłaconych pieniędzy nie można wypłacić!")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM2ZTk0ZjZjMzRhMzU0NjVmY2U0YTkwZjJlMjU5NzYzODllYjk3MDlhMTIyNzM1NzRmZjcwZmQ0ZGFhNjg1MiJ9fX0=")
                .build();

        ItemStack close = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&cPowrót")
                .lore("&7Cofnij do menu gildii.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
                .build();

        inventory.setItem(10, members);
        inventory.setItem(12, claims);
        inventory.setItem(14, bank);
        inventory.setItem(16, close);
    }

    @Override
    public void event(ClickEvent event, Player player) {
        VisualUtils.click(player);

        ItemStack item = event.item();
        if (item.getType() == Material.PLAYER_HEAD) {
            if (item.getItemMeta().getDisplayName().contains("Powrót")) {
                this.plugin.getInventoryManager().open("management", player);
                return;
            }

            this.upgrade(player, event.slot());
        }
    }

    private void upgrade(Player player, int id) {
        Guild guild = this.plugin.getGuildManager().getGuild(player);

        if (!guild.member(player).hasPermission(Permission.BUILDINGS)) {
            VisualUtils.sound(player, Sound.ENTITY_VILLAGER_NO);
            ChatUtils.sendMessage(player, "&cNie posiadasz do tego uprawnień!");
            return;
        }

        switch (id) {
            case 10 -> {
                int cost = cost(guild.maxSlots() / 5);
                if (guild.money() < cost) {
                    VisualUtils.sound(player, Sound.ENTITY_VILLAGER_NO);
                    ChatUtils.sendMessage(player, "&cTwoja gildia nie posiada tyle pieniędzy!");
                    return;
                }

                guild.money(guild.money() - cost);
                guild.maxSlots(guild.maxSlots() + 1);

                ChatUtils.guildAlert(guild, guild.getDisplayName(player) + " &7zakupił ulepszenie &6Zwiększenie miejsc (" + guild.maxSlots() + ")");
                this.plugin.getInventoryManager().open("upgrades", player);
            }

            case 12 -> {
                int cost = cost(guild.maxChunks());
                if (guild.money() < cost) {
                    VisualUtils.sound(player, Sound.ENTITY_VILLAGER_NO);
                    ChatUtils.sendMessage(player, "&cTwoja gildia nie posiada tyle pieniędzy!");
                    return;
                }

                guild.money(guild.money() - cost);
                guild.maxChunks(guild.maxChunks() + 1);

                ChatUtils.guildAlert(guild, guild.getDisplayName(player) + " &7zakupił ulepszenie &6Więcej terenu (" + guild.maxChunks() + ")");
                this.plugin.getInventoryManager().open("upgrades", player);
            }
        }
    }

    private int cost(int number) {
        return number * 2000;
    }
}
