package me.xneox.guilds.gui;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.*;
import me.xneox.guilds.util.gui.InventoryProviderImpl;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.api.InventorySize;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MembersGui extends InventoryProviderImpl {
    public MembersGui(SakuraGuildsPlugin plugin) {
        super(plugin, "Menu zarządzania członkami", InventorySize.BIGGEST);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);
        Guild guild = this.plugin.guildManager().playerGuild(player.getName());

        guild.members().forEach(member -> {
            User user = this.plugin.userManager().getUser(member.nickname());
            ItemStack skull = ItemBuilder.skullOf(member.nickname())
                    .name("&6" + member.nickname())
                    .lore("&e(Dołączył do gildii: &f" + user.joinDate() + "&e)")
                    .lore("&e(Widziany ostatnio: &f" + TimeUtils.timeSince(Bukkit.getOfflinePlayer(member.nickname()).getLastSeen()) + " temu&e)")
                    .lore("")
                    .lore("&eRanga: " + member.rank().title())
                    .lore("&eWojna: ")
                    .lore(" &7→ Zabójstwa: &f" + user.kills())
                    .lore(" &7→ Śmierci: &f" + user.deaths())
                    .lore("")
                    .lore("&7&nKliknij PRAWYM aby")
                    .lore("  &fzarządzać rangą w gildii")
                    .lore("")
                    .lore("&7&nKliknij ŚRODKOWYM aby")
                    .lore("  &fwyrzucić gracza z gildii.")
                    .build();
            inventory.addItem(skull);
        });

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

        if (isBackButton(item)) {
            this.plugin.inventoryManager().open("management", player);
            return;
        }

        String target = ChatUtils.plainString(item.getItemMeta().displayName());

        if (event.type() == ClickType.MIDDLE) {
            player.performCommand("g kick " + target);
        } else {
            this.plugin.userManager().getUser(player).editorSubject(target);
            this.plugin.inventoryManager().open("rank_editor", player);
        }
    }
}
