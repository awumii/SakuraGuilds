package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.type.Permission;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.InventoryUtils;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.gui.ClickEvent;
import me.xneox.guilds.util.gui.ClickableInventory;
import me.xneox.guilds.util.gui.InventorySize;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UpgradesGui extends ClickableInventory {
    public UpgradesGui(NeonGuilds plugin) {
        super(plugin, "Menu ulepszeń gildyjnych", InventorySize.MEDIUM);
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
        Guild guild = this.plugin.getGuildManager().getGuild(player.getName());

        ItemStack members = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Zwiększenie miejsc")
                .addLore("")
                .addLore("&7Limit członków: &b" + guild.getMaxMembers() + " &7→ &b" + (guild.getMaxMembers() + 1))
                .addLore("&7Koszt ulepszenia: &6" + guild.getUpgradeCost(guild.getMaxMembers() / 5) + "$")
                .addLore("")
                .addLore("&eKliknij, aby ulepszyć.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTJmMzU3YWI1OWUwNGU2NzY3MjRjNjNkNzA0YzNkMWYyZjlhZTFhZDQyODNlOTFkN2RhMjZlZmM2YzQ4MDgifX19")
                .build();

        ItemStack claims = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Więcej terenu")
                .addLore("")
                .addLore("&7Limit chunków: &b" + guild.getMaxChunks() + " &7→ &b" + (guild.getMaxChunks() + 1))
                .addLore("&7Koszt ulepszenia: &6" + guild.getUpgradeCost(guild.getMaxChunks()) + "$")
                .addLore("")
                .addLore("&eKliknij, aby ulepszyć.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTI4OWQ1YjE3ODYyNmVhMjNkMGIwYzNkMmRmNWMwODVlODM3NTA1NmJmNjg1YjVlZDViYjQ3N2ZlODQ3MmQ5NCJ9fX0=")
                .build();

        ItemStack bank = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Bank gildyjny")
                .addLore("")
                .addLore("&7W banku znajduje się: &6" + guild.getMoney() + "$")
                .addLore("")
                .addLore("&7Aby wpłacać pieniądze, użyj komendy:")
                .addLore(" &7→ &6/g donate <ilość>")
                .addLore("")
                .addLore("&cWpłaconych pieniędzy nie można wypłacić!")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM2ZTk0ZjZjMzRhMzU0NjVmY2U0YTkwZjJlMjU5NzYzODllYjk3MDlhMTIyNzM1NzRmZjcwZmQ0ZGFhNjg1MiJ9fX0=")
                .build();

        ItemStack close = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&cPowrót")
                .addLore("&7Cofnij do menu gildii.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
                .build();

        inventory.setItem(10, members);
        inventory.setItem(12, claims);
        inventory.setItem(14, bank);
        inventory.setItem(16, close);
    }

    @Override
    public void onClick(ClickEvent event, Player player) {
        VisualUtils.click(player);

        ItemStack item = event.getItem();
        if (item.getType() == Material.PLAYER_HEAD) {
            if (item.getItemMeta().getDisplayName().contains("Powrót")) {
                this.plugin.getInventoryManager().open("manage", player);
            } else {
                if (event.getSlot() == 10) {
                    this.upgrade(player, false);
                } else if (event.getSlot() == 12) {
                    this.upgrade(player, true);
                }
            }
        }
    }

    private void upgrade(Player player, boolean claims) {
        Guild guild = this.plugin.getGuildManager().getGuild(player);
        if (!guild.getPlayerRank(player).hasPermission(Permission.RANKS)) {
            VisualUtils.playSound(player, Sound.ENTITY_VILLAGER_NO);
            ChatUtils.sendMessage(player, "&cTwoja ranga jest zbyt niska!");
            return;
        }

        int cost = claims ? guild.getUpgradeCost(guild.getMaxChunks()) : guild.getUpgradeCost(guild.getMaxMembers() / 5);
        if (guild.getMoney() >= cost) {
            guild.setMoney(guild.getMoney() - cost);
            ChatUtils.forGuildMembers(guild, m -> VisualUtils.playSound(m, Sound.BLOCK_NOTE_BLOCK_PLING));

            if (claims) {
                guild.setMaxChunks(guild.getMaxChunks() + 1);
                ChatUtils.guildAlert(guild, guild.getDisplayName(player) + " &7zakupił ulepszenie &6Więcej terenu (" + guild.getMaxChunks() + ")");
            } else {
                guild.setMaxMembers(guild.getMaxMembers() + 1);
                ChatUtils.guildAlert(guild, guild.getDisplayName(player) + " &7zakupił ulepszenie &6Zwiększenie miejsc (" + guild.getMaxMembers() + ")");
            }

            this.plugin.getInventoryManager().open("upgrades", player);
        } else {
            VisualUtils.playSound(player, Sound.ENTITY_VILLAGER_NO);
            ChatUtils.sendMessage(player, "&cTwoja gildia nie posiada tyle pieniędzy!");
        }
    }
}
