package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.type.Permission;
import me.xneox.guilds.type.Rank;
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

public class RankEditorGui extends ClickableInventory {
    public RankEditorGui(NeonGuilds plugin) {
        super(plugin, "Zarządzanie rangami użytkownika", InventorySize.BIGGEST);
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);
        Guild guild = this.plugin.getGuildManager().getGuild(player.getName());
        String target = this.plugin.getUserManager().getUser(player).getEditorSubject();

        ItemStack user = new ItemBuilder(Material.PLAYER_HEAD)
                .setName(guild.getDisplayName(target))
                .addLore("&7Wybierz rangę dla tego członka")
                .setSkullOwner(target)
                .build();

        ItemStack leader = new ItemBuilder(Material.PLAYER_HEAD)
                .setName(Rank.LEADER.getDisplay())
                .addLore("")
                .addLore("&eUprawnienia:")
                .addLore(" &8→ &7Wszystkie uprawnienia gildyjne.")
                .addLore("")
                .addLore("&cUWAGA: Po nadaniu tej rangi,")
                .addLore("&cutracisz status lidera gildii!")
                .addLore("")
                .addLore("&eKliknij aby nadać.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQxNWFhY2I3MjEzYzgzMTFlZWQ3YmFmMzdlYmI1OGE1ZjRiOTI1NjMxN2Q4NDU4ZDE1ZDMzN2E3NGU0YmU2In19fQ==")
                .build();

        ItemStack oficer = new ItemBuilder(Material.PLAYER_HEAD)
                .setName(Rank.GENERAL.getDisplay())
                .addLore("")
                .addLore("&eUprawnienia:")
                .addLore(" &8→ &7Budowanie")
                .addLore(" &8→ &7Wpłacanie pieniędzy")
                .addLore(" &8→ &7Wyrzucanie członków")
                .addLore(" &8→ &7Zmiana bazy gildii")
                .addLore(" &8→ &7Zajmowanie terenu")
                .addLore(" &8→ &7Zarządzanie rangami")
                .addLore(" &8→ &7Kupowanie ulepszeń")
                .addLore(" &8→ &7Zarządzanie sojuszami")
                .addLore("")
                .addLore("&eKliknij aby nadać.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZlZjMwNzBmNmQyZTQ3YjViMTA1OWI0NWRiYzMyMmIyNDI1ZWEzZGUxZjFiY2I5NDI5MzM3ZmQ2OTNmYzYifX19")
                .build();

        ItemStack general = new ItemBuilder(Material.PLAYER_HEAD)
                .setName(Rank.OFICER.getDisplay())
                .addLore("")
                .addLore("&eUprawnienia:")
                .addLore(" &8→ &7Budowanie")
                .addLore(" &8→ &7Wpłacanie pieniędzy")
                .addLore(" &8→ &7Zmiana bazy gildii")
                .addLore(" &8→ &7Zajmowanie terenu")
                .addLore(" &8→ &7Kupowanie ulepszeń")
                .addLore(" &8→ &7Zarządzanie sojuszami")
                .addLore("")
                .addLore("&eKliknij aby nadać.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmI0MTZiM2Q5NzNlMzQ4MDY2NDE2MmM5MjhjMGY3ZDJmNGYzMmI4ZDIwMjIyNWFiNjIyN2ZmNTllZTFjNWMzMSJ9fX0=")
                .build();

        ItemStack kapral = new ItemBuilder(Material.PLAYER_HEAD)
                .setName(Rank.KAPRAL.getDisplay())
                .addLore("")
                .addLore("&eUprawnienia:")
                .addLore(" &8→ &7Budowanie")
                .addLore(" &8→ &7Wpłacanie pieniędzy")
                .addLore(" &8→ &7Zmiana bazy gildii")
                .addLore(" &8→ &7Zajmowanie terenu")
                .addLore("")
                .addLore("&eKliknij aby nadać.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY3ZWRhNjhhZTJmZmY1MjRlODNmOGE5MTZlYzMzOTBhZGVjYTM5NGY3ZDg2MTdhNGQ3N2ZiYTNlNjg5Yjc1In19fQ==")
                .build();

        ItemStack rekrut = new ItemBuilder(Material.PLAYER_HEAD)
                .setName(Rank.REKRUT.getDisplay())
                .addLore("")
                .addLore("&eUprawnienia:")
                .addLore(" &8→ &7Budowanie")
                .addLore(" &8→ &7Wpłacanie pieniędzy")
                .addLore("")
                .addLore("&eKliknij aby nadać.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZThiOGM2YTQ2ZDg3Y2Y4NmE1NWRmMjE0Y2Y4NGJmNDVjY2EyNWVkYjlhNjc2ZTk2MzY0ZGQ2YTZlZWEyMzViMyJ9fX0=")
                .build();

        ItemStack close = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&cPowrót")
                .addLore("&7Cofnij do menu gildii.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
                .build();

        inventory.setItem(8, close);
        inventory.setItem(22, user);

        inventory.setItem(29, leader);
        inventory.setItem(30, oficer);
        inventory.setItem(31, general);
        inventory.setItem(32, kapral);
        inventory.setItem(33, rekrut);
    }

    @Override
    public void onClick(ClickEvent event, Player player) {
        VisualUtils.click(player);

        int slot = event.getSlot();
        ItemStack item = event.getItem();

        if (item.getType() == Material.PLAYER_HEAD) {
            if (item.getItemMeta().getDisplayName().contains("Powrót")) {
                this.plugin.getInventoryManager().open("members", player);
            } else {
                Guild guild = this.plugin.getGuildManager().getGuild(player.getName());
                String target = this.plugin.getUserManager().getUser(player).getEditorSubject();

                if (player.getName().equals(target)) {
                    VisualUtils.playSound(player, Sound.ENTITY_VILLAGER_NO);
                    ChatUtils.sendMessage(player, "&cNie możesz zmienić własnej rangi!");
                    return;
                }

                if (guild.getPlayerRank(player).hasPermission(Permission.RANKS) && guild.isHigher(player.getName(), target)) {
                    Rank rank = null;
                    if (slot == 29) {
                        rank = Rank.LEADER;
                    } else if (slot == 30) {
                        rank = Rank.GENERAL;
                    } else if (slot == 31) {
                        rank = Rank.OFICER;
                    } else if (slot == 32) {
                        rank = Rank.KAPRAL;
                    } else if (slot == 33) {
                        rank = Rank.REKRUT;
                    }

                    if (rank == null) {
                        return;
                    }

                    guild.changeRank(target, rank);
                    ChatUtils.guildAlert(guild, guild.getDisplayName(player) + " &7zmienił pozycję gracza &6" + target + " &7na " + rank.getDisplay());
                    this.plugin.getInventoryManager().open("members", player);
                } else {
                    VisualUtils.playSound(player, Sound.ENTITY_VILLAGER_NO);
                    ChatUtils.sendMessage(player, "&cTwoja ranga w gildii jest zbyt niska!");
                }
            }
        }
    }
}
