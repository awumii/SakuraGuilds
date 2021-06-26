package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.InventoryUtils;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.TimeUtils;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.InventoryProviderImpl;
import me.xneox.guilds.util.gui.api.InventorySize;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MembersGui extends InventoryProviderImpl {
    public MembersGui(NeonGuilds plugin) {
        super(plugin, "Menu zarządzania członkami", InventorySize.BIGGEST);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);
        Guild guild = this.plugin.getGuildManager().getGuild(player.getName());

        guild.getMembers().forEach(member -> {
            User user = this.plugin.getUserManager().getUser(member.nickname());
            ItemStack skull = new ItemBuilder(Material.PLAYER_HEAD)
                    .name("&6" + member.nickname())
                    .lore("&e(Widziany ostatnio: &f" + TimeUtils.formatDate(Bukkit.getOfflinePlayer(member.nickname()).getLastSeen()) + "&e)")
                    .lore("")
                    .lore("&eRanga: " + member.rank().getDisplay())
                    .lore("&eWojna: ")
                    .lore(" &7→ Zabójstwa: &f" + user.getKills())
                    .lore(" &7→ Śmierci: &f" + user.getDeaths())
                    .lore("")
                    .lore("&7&nKliknij PRAWYM aby")
                    .lore("  &fzarządzać rangą w gildii")
                    .lore("")
                    .lore("&7&nKliknij ŚRODKOWYM aby")
                    .lore("  &fwyrzucić gracza z gildii.")
                    .skullOwner(member.nickname())
                    .build();
            inventory.addItem(skull);
        });

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
        if (item.getType() == Material.PLAYER_HEAD) {
            if (item.getItemMeta().getDisplayName().contains("Powrót")) {
                this.plugin.getInventoryManager().open("management", player);
            } else  {
                String target = ChatColor.stripColor(item.getItemMeta().getDisplayName());

                if (event.type() == ClickType.MIDDLE) {
                    player.performCommand("g kick " + target);
                } else {
                    this.plugin.getUserManager().getUser(player).setEditorSubject(target);
                    this.plugin.getInventoryManager().open("rank_editor", player);
                }
            }
        }
    }
}
