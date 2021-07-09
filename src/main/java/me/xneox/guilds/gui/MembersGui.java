package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.*;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.InventoryProviderImpl;
import me.xneox.guilds.util.gui.api.InventorySize;
import org.bukkit.Bukkit;
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
        Guild guild = this.plugin.guildManager().playerGuild(player.getName());

        guild.members().forEach(member -> {
            User user = this.plugin.userManager().getUser(member.nickname());
            ItemStack skull = ItemBuilder.of(Material.PLAYER_HEAD)
                    .name("&6" + member.nickname())
                    .lore("&e(Widziany ostatnio: &f" + TimeUtils.formatDate(Bukkit.getOfflinePlayer(member.nickname()).getLastSeen()) + "&e)")
                    .lore("")
                    .lore("&eRanga: " + member.rank().title())
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

        ItemStack close = ItemBuilder.of(Material.PLAYER_HEAD)
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
        if (item.getType() != Material.PLAYER_HEAD) {
            return;
        }

        if (isBackButton(item)) {
            this.plugin.inventoryManager().open("management", player);
            return;
        }

        String target = ChatUtils.plainString(item.getItemMeta().displayName());

        if (event.type() == ClickType.MIDDLE) {
            player.performCommand("g kick " + target);
        } else {
            this.plugin.userManager().getUser(player).setEditorSubject(target);
            this.plugin.inventoryManager().open("rank_editor", player);
        }
    }
}
