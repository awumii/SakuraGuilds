package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.InventoryUtils;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.TimeUtils;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.gui.InventorySize;
import me.xneox.guilds.util.gui.inventories.ClickableInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MembersGui extends ClickableInventory {
    private final NeonGuilds plugin;

    public MembersGui(NeonGuilds plugin) {
        super("Menu zarządzania członkami", "members", InventorySize.BIGGEST);
        this.plugin = plugin;
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);
        Guild guild = this.plugin.getGuildManager().getGuild(player.getName());

        guild.getMembers().forEach((name, rank) -> {
            User user = this.plugin.getUserManager().getUser(name);
            ItemStack skull = new ItemBuilder(Material.PLAYER_HEAD)
                    .setName("&6" + name)
                    .addLore("&e(Widziany ostatnio: &f" + TimeUtils.formatDate(Bukkit.getOfflinePlayer(name).getLastSeen()) + "&e)")
                    .addLore("")
                    .addLore("&eRanga: " + rank.getDisplay())
                    .addLore("&eWojna: ")
                    .addLore(" &7→ Zabójstwa: &f" + user.getKills())
                    .addLore(" &7→ Śmierci: &f" + user.getDeaths())
                    .addLore("")
                    .addLore("&7&nKliknij PRAWYM aby")
                    .addLore("  &fzarządzać rangą w gildii")
                    .addLore("")
                    .addLore("&7&nKliknij ŚRODKOWYM aby")
                    .addLore("  &fwyrzucić gracza z gildii.")
                    .setSkullOwner(name)
                    .build();
            inventory.addItem(skull);
        });

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
            } else  {
                String target = ChatColor.stripColor(item.getItemMeta().getDisplayName());

                if (event.getClick() == ClickType.MIDDLE) {
                    player.performCommand("g kick " + target);
                } else {
                    this.plugin.getUserManager().getUser(player).setEditorSubject(target);
                    this.plugin.getInventoryManager().open("rank_editor", player);
                }
            }
        }
    }
}
