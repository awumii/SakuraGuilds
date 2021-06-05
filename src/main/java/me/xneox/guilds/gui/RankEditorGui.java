package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.type.Permission;
import me.xneox.guilds.type.Rank;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.InventoryUtils;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.InventoryProviderImpl;
import me.xneox.guilds.util.gui.api.InventorySize;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RankEditorGui extends InventoryProviderImpl {
    public RankEditorGui(NeonGuilds plugin) {
        super(plugin, "Zarządzanie rangami użytkownika", InventorySize.BIG);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        Guild guild = this.plugin.getGuildManager().getGuild(player.getName());
        String target = this.plugin.getUserManager().getUser(player).getEditorSubject();
        Member member = guild.findMember(target);

        ItemStack user = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6" + target)
                .lore("&7Ranga: " + member.getRank().getDisplay())
                .skullOwner(target)
                .build();

        ItemStack leader = new ItemBuilder(Material.PLAYER_HEAD)
                .name(Rank.LEADER.getDisplay())
                .lore("")
                .lore("&cUWAGA: Po nadaniu tej rangi,")
                .lore("&cutracisz status lidera gildii!")
                .lore("")
                .lore("&eKliknij aby nadać.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQxNWFhY2I3MjEzYzgzMTFlZWQ3YmFmMzdlYmI1OGE1ZjRiOTI1NjMxN2Q4NDU4ZDE1ZDMzN2E3NGU0YmU2In19fQ==")
                .build();

        ItemStack oficer = new ItemBuilder(Material.PLAYER_HEAD)
                .name(Rank.GENERAL.getDisplay())
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
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZlZjMwNzBmNmQyZTQ3YjViMTA1OWI0NWRiYzMyMmIyNDI1ZWEzZGUxZjFiY2I5NDI5MzM3ZmQ2OTNmYzYifX19")
                .build();

        ItemStack general = new ItemBuilder(Material.PLAYER_HEAD)
                .name(Rank.OFICER.getDisplay())
                .lore("")
                .lore("&eDomyślne Uprawnienia:")
                .lore(" &8▸ &7Budowanie")
                .lore(" &8▸ &7Zmiana bazy gildii")
                .lore(" &8▸ &7Zajmowanie terenu")
                .lore(" &8▸ &7Kupowanie ulepszeń")
                .lore(" &8▸ &7Zarządzanie sojuszami")
                .lore("")
                .lore("&eKliknij aby nadać.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmI0MTZiM2Q5NzNlMzQ4MDY2NDE2MmM5MjhjMGY3ZDJmNGYzMmI4ZDIwMjIyNWFiNjIyN2ZmNTllZTFjNWMzMSJ9fX0=")
                .build();

        ItemStack kapral = new ItemBuilder(Material.PLAYER_HEAD)
                .name(Rank.KAPRAL.getDisplay())
                .lore("")
                .lore("&eDomyślne Uprawnienia:")
                .lore(" &8▸ &7Budowanie")
                .lore(" &8▸ &7Zmiana bazy gildii")
                .lore(" &8▸ &7Zajmowanie terenu")
                .lore("")
                .lore("&eKliknij aby nadać.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY3ZWRhNjhhZTJmZmY1MjRlODNmOGE5MTZlYzMzOTBhZGVjYTM5NGY3ZDg2MTdhNGQ3N2ZiYTNlNjg5Yjc1In19fQ==")
                .build();

        ItemStack rekrut = new ItemBuilder(Material.PLAYER_HEAD)
                .name(Rank.REKRUT.getDisplay())
                .lore("")
                .lore("&eDomyślne Uprawnienia:")
                .lore(" &8▸ &7Budowanie")
                .lore("")
                .lore("&eKliknij aby nadać.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZThiOGM2YTQ2ZDg3Y2Y4NmE1NWRmMjE0Y2Y4NGJmNDVjY2EyNWVkYjlhNjc2ZTk2MzY0ZGQ2YTZlZWEyMzViMyJ9fX0=")
                .build();

        ItemStack close = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&cPowrót")
                .lore("&7Cofnij do menu gildii.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
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
            ItemStack stack = new ItemBuilder(Material.PLAYER_HEAD)
                    .name((member.hasPermission(permission) ? "&a" : "&c") + permission.getDescription())
                    .skullTexture(member.hasPermission(permission)
                            ? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDMxMmNhNDYzMmRlZjVmZmFmMmViMGQ5ZDdjYzdiNTVhNTBjNGUzOTIwZDkwMzcyYWFiMTQwNzgxZjVkZmJjNCJ9fX0="
                            : "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmViNTg4YjIxYTZmOThhZDFmZjRlMDg1YzU1MmRjYjA1MGVmYzljYWI0MjdmNDYwNDhmMThmYzgwMzQ3NWY3In19fQ==")
                    .build();

            inventory.addItem(stack);
        }

        inventory.setItem(1, null);
        inventory.setItem(7, null);
    }

    @Override
    public void event(ClickEvent event, Player player) {
        VisualUtils.click(player);

        int slot = event.slot();
        ItemStack item = event.item();

        if (item.getType() == Material.PLAYER_HEAD) {
            if (item.getItemMeta().getDisplayName().contains("Powrót")) {
                this.plugin.getInventoryManager().open("members", player);
                return;
            }

            Guild guild = this.plugin.getGuildManager().getGuild(player.getName());
            String target = this.plugin.getUserManager().getUser(player).getEditorSubject();
            Member member = guild.findMember(target);

            // Block editing your own rank.
            if (player.getName().equals(target)) {
                VisualUtils.playSound(player, Sound.ENTITY_VILLAGER_NO);
                ChatUtils.sendMessage(player, "&cNie możesz zarządzać własnymi uprawnieniami!");
                return;
            }

            // Block editing if lower rank or no permission.
            if (!guild.findMember(player.getName()).getPermissions().contains(Permission.RANKS) || !guild.isHigher(player.getName(), target)) {
                VisualUtils.playSound(player, Sound.ENTITY_VILLAGER_NO);
                ChatUtils.sendMessage(player, "&cTwoja ranga w gildii jest zbyt niska!");
                return;
            }

            // Editing the member's rank
            if (item.getItemMeta().getLore() != null) {
                Rank rank = Rank.REKRUT;
                if (slot == 2) {
                    rank = Rank.LEADER;
                } else if (slot == 4) {
                    rank = Rank.GENERAL;
                } else if (slot == 3) {
                    rank = Rank.OFICER;
                } else if (slot == 5) {
                    rank = Rank.KAPRAL;
                }

                guild.changeRank(target, rank);
                ChatUtils.guildAlert(guild, guild.getDisplayName(player) + " &7zmienił pozycję gracza &6" + target + " &7na " + rank.getDisplay());
            } else {
                // Editing the member's custom permissions.
                Permission permission = Permission.find(item.getItemMeta().getDisplayName());
                if (permission != null) {
                    guild.changePermission(target, permission, !member.hasPermission(permission));
                }
            }

            this.plugin.getInventoryManager().open("rank_editor", player);
        }
    }
}
