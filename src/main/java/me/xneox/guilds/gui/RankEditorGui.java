package me.xneox.guilds.gui;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.type.Permission;
import me.xneox.guilds.type.Rank;
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

public class RankEditorGui extends InventoryProviderImpl {
    public RankEditorGui(SakuraGuildsPlugin plugin) {
        super(plugin, "Zarządzanie rangami użytkownika", InventorySize.BIG);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        Guild guild = this.plugin.guildManager().playerGuild(player.getName());
        String target = this.plugin.userManager().getUser(player).editorSubject();
        Member member = guild.member(target);

        ItemStack user = ItemBuilder.skullOf(target)
                .name("&6" + target)
                .lore("&7Ranga: " + member.rank().title())
                .build();

        ItemStack leader = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQxNWFhY2I3MjEzYzgzMTFlZWQ3YmFmMzdlYmI1OGE1ZjRiOTI1NjMxN2Q4NDU4ZDE1ZDMzN2E3NGU0YmU2In19fQ==")
                .name(Rank.LEADER.title())
                .lore("")
                .lore("&cUWAGA: Po nadaniu tej rangi,")
                .lore("&cutracisz status lidera gildii!")
                .lore("")
                .lore("&eKliknij aby nadać.")
                .build();

        ItemStack oficer = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZlZjMwNzBmNmQyZTQ3YjViMTA1OWI0NWRiYzMyMmIyNDI1ZWEzZGUxZjFiY2I5NDI5MzM3ZmQ2OTNmYzYifX19")
                .name(Rank.GENERAL.title())
                .lore("")
                .lore("&eDomyślne Uprawnienia:")
                .lore(" &8▸ &7Budowanie")
                .lore(" &8▸ &7Wyrzucanie członków")
                .lore(" &8▸ &7Zmiana bazy gildii")
                .lore(" &8▸ &7Zajmowanie terenu")
                .lore(" &8▸ &7Zarządzanie rangami")
                .lore(" &8▸ &7Kupowanie ulepszeń")
                .lore(" &8▸ &7Zarządzanie sojuszami")
                .lore("")
                .lore("&eKliknij aby nadać.")
                .build();

        ItemStack general = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmI0MTZiM2Q5NzNlMzQ4MDY2NDE2MmM5MjhjMGY3ZDJmNGYzMmI4ZDIwMjIyNWFiNjIyN2ZmNTllZTFjNWMzMSJ9fX0=")
                .name(Rank.OFICER.title())
                .lore("")
                .lore("&eDomyślne Uprawnienia:")
                .lore(" &8▸ &7Budowanie")
                .lore(" &8▸ &7Zmiana bazy gildii")
                .lore(" &8▸ &7Zajmowanie terenu")
                .lore(" &8▸ &7Kupowanie ulepszeń")
                .lore(" &8▸ &7Zarządzanie sojuszami")
                .lore("")
                .lore("&eKliknij aby nadać.")
                .build();

        ItemStack kapral = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY3ZWRhNjhhZTJmZmY1MjRlODNmOGE5MTZlYzMzOTBhZGVjYTM5NGY3ZDg2MTdhNGQ3N2ZiYTNlNjg5Yjc1In19fQ==")
                .name(Rank.KAPRAL.title())
                .lore("")
                .lore("&eDomyślne Uprawnienia:")
                .lore(" &8▸ &7Budowanie")
                .lore(" &8▸ &7Zmiana bazy gildii")
                .lore(" &8▸ &7Zajmowanie terenu")
                .lore("")
                .lore("&eKliknij aby nadać.")
                .build();

        ItemStack rekrut = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZThiOGM2YTQ2ZDg3Y2Y4NmE1NWRmMjE0Y2Y4NGJmNDVjY2EyNWVkYjlhNjc2ZTk2MzY0ZGQ2YTZlZWEyMzViMyJ9fX0=")
                .name(Rank.REKRUT.title())
                .lore("")
                .lore("&eDomyślne Uprawnienia:")
                .lore(" &8▸ &7Budowanie")
                .lore("")
                .lore("&eKliknij aby nadać.")
                .build();

        ItemStack close = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
                .name("&cPowrót")
                .lore("&7Cofnij do menu gildii.")
                .build();

        inventory.setItem(0, user);
        inventory.setItem(1, InventoryUtils.BLACK_GLASS);
        inventory.setItem(2, leader);
        inventory.setItem(3, oficer);
        inventory.setItem(4, general);
        inventory.setItem(5, kapral);
        inventory.setItem(6, rekrut);
        inventory.setItem(7, InventoryUtils.BLACK_GLASS);
        inventory.setItem(8, close);

        for (int i = 9; i < 18; i++) {
            inventory.setItem(i, InventoryUtils.BLACK_GLASS);
        }

        for (Permission permission : Permission.values()) {
            ItemStack stack = ItemBuilder.skull(member.hasPermission(permission)
                    ? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDMxMmNhNDYzMmRlZjVmZmFmMmViMGQ5ZDdjYzdiNTVhNTBjNGUzOTIwZDkwMzcyYWFiMTQwNzgxZjVkZmJjNCJ9fX0="
                    : "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmViNTg4YjIxYTZmOThhZDFmZjRlMDg1YzU1MmRjYjA1MGVmYzljYWI0MjdmNDYwNDhmMThmYzgwMzQ3NWY3In19fQ==")
                    .name((member.hasPermission(permission) ? "&a" : "&c") + permission.getDescription())
                    .build();

            inventory.addItem(stack);
        }

        inventory.setItem(1, null);
        inventory.setItem(7, null);
    }

    @Override
    public void event(ClickEvent event, Player player) {
        VisualUtils.click(player);

        ItemStack item = event.item();
        if (item.getType() != Material.PLAYER_HEAD) {
            return;
        }

        if (isBackButton(item)) {
            this.plugin.inventoryManager().open("members", player);
            return;
        }

        Guild guild = this.plugin.guildManager().playerGuild(player.getName());
        String targetName = this.plugin.userManager().getUser(player).editorSubject();

        Member member = guild.member(player);
        Member targetMember = guild.member(targetName);

        // Block editing your own rank.
        if (player.getName().equals(targetName)) {
            VisualUtils.sound(player, Sound.ENTITY_VILLAGER_NO);
            ChatUtils.sendMessage(player, "&cNie możesz zarządzać własnymi uprawnieniami!");
            return;
        }

        // Block editing if lower rank or no permission.
        if (!member.hasPermission(Permission.RANKS)) {
            VisualUtils.sound(player, Sound.ENTITY_VILLAGER_NO);
            ChatUtils.sendMessage(player, "&cNie posiadasz uprawnień do zarządzania uprawnieniami.");
            return;
        }

        if (!member.rank().isHigher(targetMember.rank())) {
            VisualUtils.sound(player, Sound.ENTITY_VILLAGER_NO);
            ChatUtils.sendMessage(player, "&cTwoja ranga w gildii jest niższa od docelowego gracza.");
            return;
        }

        // Editing the targetMember's rank
        if (item.getItemMeta().getLore() != null) {
            Rank rank = switch (event.slot()) {
                case 2 -> Rank.LEADER;
                case 4 -> Rank.GENERAL;
                case 3 -> Rank.OFICER;
                case 5 -> Rank.KAPRAL;
                default -> Rank.REKRUT;
            };

            guild.changeRank(targetName, rank);
            ChatUtils.guildAlert(guild, member.displayName() + " &7zmienił pozycję gracza &6" + targetName + " &7na " + rank.title());
        } else {
            // Editing the targetMember's custom permissions.
            Permission permission = Permission.find(ChatUtils.plainString(item.getItemMeta().displayName()));
            if (permission != null) {
                if (targetMember.hasPermission(permission)) {
                    targetMember.permissions().remove(permission);
                } else {
                    targetMember.permissions().add(permission);
                }
            }
        }

        this.plugin.inventoryManager().open("rank_editor", player);
    }
}
