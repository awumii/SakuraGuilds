package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.LogEntry;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.*;
import me.xneox.guilds.util.gui.InventorySize;
import me.xneox.guilds.util.gui.inventories.ClickableInventory;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ManagementGui extends ClickableInventory {
    private final NeonGuilds plugin;

    public ManagementGui(NeonGuilds plugin) {
        super("Menu zarządzania gildią", "manage", InventorySize.BIGGEST);
        this.plugin = plugin;
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);
        VisualUtils.playSound(player, Sound.BLOCK_ENDER_CHEST_OPEN);

        Guild guild = this.plugin.getGuildManager().getGuild(player.getName());
        if (guild == null) {
            player.closeInventory();
        }

        User user = this.plugin.getUserManager().getUser(player);

        ItemStack claims = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Zajęte Ziemie")
                .addLore("")
                .addLore("&7Kliknij, aby przeglądnąć zajęte tereny.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTI4OWQ1YjE3ODYyNmVhMjNkMGIwYzNkMmRmNWMwODVlODM3NTA1NmJmNjg1YjVlZDViYjQ3N2ZlODQ3MmQ5NCJ9fX0=")
                .build();

        ItemStack members = new ItemBuilder(Material.PLAYER_HEAD, guild.getMembers().size())
                .setName("&6Członkowie")
                .addLore("")
                .addLore("&7Kliknij, aby zarządzać członkami gildii")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTJmMzU3YWI1OWUwNGU2NzY3MjRjNjNkNzA0YzNkMWYyZjlhZTFhZDQyODNlOTFkN2RhMjZlZmM2YzQ4MDgifX19")
                .build();

        ItemStack home = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Baza Gildii")
                .addLore("")
                .addLore("&7Ustaw bazę komendą &7/g ustawbaze")
                .addLore("&7Lokalizacja: &6" + LocationUtils.toSimpleString(guild.getHome()))
                .addLore("")
                .addLore("&eKliknij aby przeteleportować.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNkMDJjZGMwNzViYjFjYzVmNmZlM2M3NzExYWU0OTc3ZTM4YjkxMGQ1MGVkNjAyM2RmNzM5MTNlNWU3ZmNmZiJ9fX0=")
                .build();

        ItemStack help = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Lista Komend")
                .addLore("")
                .addLore("&7Kliknij, aby zobaczyć dostępne komendy.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWNmMmFiZDYxNzlmNDE2YzI2YjRkMTI5NzE4ODFmMTI0Mzc2NDM5OWE2N2Y5Yzk0ZmQ4NTlmMTNlNzdjIn19fQ==")
                .build();

        ItemStack profile = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Profil")
                .addLore("")
                .addLore("&eNick:")
                .addLore("&f" + player.getName())
                .addLore("")
                .addLore("&ePozycja:")
                .addLore("&f" + guild.getPlayerRank(player).getDisplay())
                .addLore("")
                .addLore("&eWojna:")
                .addLore(" &7→ Zabójstwa: &f" + user.getKills())
                .addLore(" &7→ Śmierci: &f" + guild.getDeaths())
                .addLore("")
                .setSkullOwner(player.getName())
                .build();

        ItemStack info = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Informacje o Gildii")
                .addLore("")
                .addLore("&eNazwa:")
                .addLore("&f" + guild.getName())
                .addLore("")
                .addLore("&eLider:")
                .addLore("&f" + guild.getLeader())
                .addLore("")
                .addLore("&eLiczba członków:")
                .addLore("&f" + guild.getMembers().size() + "/" + guild.getMaxMembers() + " &7(&a" + guild.getOnlineMembers().size() + " &fonline&7)")
                .addLore("")
                .addLore("&eZajęte ziemie:")
                .addLore("&f" + guild.getChunks().size() + " &7(Limit: &f" + guild.getMaxChunks() + "&7)")
                .addLore("")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzBjZjc0ZTI2MzhiYTVhZDMyMjM3YTM3YjFkNzZhYTEyM2QxODU0NmU3ZWI5YTZiOTk2MWU0YmYxYzNhOTE5In19fQ==")
                .build();

        ItemStack division = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Statystyki Wojny")
                .addLore("")
                .addLore("&eRanking:")
                .addLore(" &7→ Dywizja: " + guild.getDivision().getName())
                .addLore(" &7→ Puchary: &f" + guild.getTrophies() + "★")
                .addLore("")
                .addLore("&eWalka:")
                .addLore(" &7→ Zabójstwa: &f" + guild.getKills())
                .addLore(" &7→ Śmierci: &f" + guild.getDeaths())
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRkZTRkOTViYTRhNDZkYjdlNTY2YjM0NWY3ODk0ZDFkMjU4Zjg5M2ViOTJjNzgwYjNkYTc3NWVlZGY5MSJ9fX0=")
                .build();

        ItemStack allies = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Zawarte Sojusze")
                .addLore("")
                .addLore("&7Kliknij, aby przeglądać zawarte sojusze.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJkZmJlMmQzMzhjZTI0NmIzYTBhMzRhNDAwM2RiODM5ZTA3NjMxOTMwYTQzZTBlMGU5NWM5YWZhYWE3YTVlMyJ9fX0=")
                .build();

        ItemStack upgrades = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Ulepszenia Gildii")
                .addLore("")
                .addLore("&7Kliknij, aby zakupić ulepszenia dla gildii.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWE2OWNjZjdhZDkwNGM5YTg1MmVhMmZmM2Y1YjRlMjNhZGViZjcyZWQxMmQ1ZjI0Yjc4Y2UyZDQ0YjRhMiJ9fX0=")
                .build();

        ItemStack nexus = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&c❤ Zdrowie Nexusa: &f" + guild.getHealth() + "/3 HP")
                .addLore("")
                .addLore("&7Nexus to serce twojej gildii.")
                .addLore("")
                .addLore("&7Jeśli &fHP Nexusa &7spadnie &c&ndo zera&7,")
                .addLore("&7a twoja gildia nie posiada &6Tarczy Wojennej&7,")
                .addLore("&7zostanie ona całkowicie &4rozwiązana.")
                .addLore("")
                .addLore(guild.isShieldActive() ? "&a(Tarcza wojenna jest aktywna)" : "&c(Tarcza wojenna jest nieaktywna)")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQ4NzYxN2E2NTQ3ZmY5MDE3NzdmYzYzNGU1MzFkZWU0MDhiZTNiYzQyM2ViODZhYWNiZTcyODU5M2I0ZWVjYyJ9fX0=")
                .build();

        ItemStack shield = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Tarcza Wojenna")
                .addLore("")
                .addLore("&7Tarcza wojenna chroni twoją gildię")
                .addLore("&7oraz jej nexus, przed atakami wrogów.")
                .addLore("")
                .addLore("&7Status tarczy: &c" + TimeUtils.futureMillisToTime(guild.getShield()))
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzhjZWI4NjMxYWRkN2NiYjU2NWRjYjcwNWYxMjEyMzQ5Y2NjZDc1NTk2NWM0NmE5MjI4NTJjOWZkOTQ4YjRiYiJ9fX0=")
                .build();

        ItemStack leaderboard = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Ranking Gildii")
                .addLore("")
                .addLore("&7Kliknij, aby przeglądać topowe gildie.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGJlNjIyNmQ2MWVjY2NkYjU3MzJiNmY3MTU2MGQ2NDAxZDJjYTBlZmY4ZTFhYWZiYmUzY2I3M2ZiNWE5ZiJ9fX0=")
                .build();

        ItemStack warInactive = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Wojna Gildii")
                .addLore("")
                .addLore("&cWojny gildii pojawią się wkrótce.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTIzMTJlNzJkMDMwMTJiZTEwNmI0OGFjY2QzMzgyY2VjN2NiY2VjZWIxNDJlYzc2MjM3OTM0NjM5YTZhMmU5In19fQ==")
                .build();

        /*
        ItemBuilder warActive = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Wojna Gildii")
                .addLore("")
                .addLore("&eTwoja gildia:");

                guild.getWarMembers().stream().map(warMember -> " &7→ &f" + warMember.getName()).forEach(warActive::addLore);

                warActive.addLore("")
                .addLore("&eGildia " + guild.getWarEnemy().getName() + ":");

                guild.getWarEnemy().getWarMembers().stream().map(warMember -> " &7→ &f" + warMember.getName()).forEach(warActive::addLore);

                warActive.addLore("")
                .addLore("&7Do rozpoczęcia: &c" + TimeUtils.secondsToTime(guild.getWarCountdown()))
                .addLore("")
                .addLore("&aKliknij aby dołączyć/opuścić wojnę.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTIzMTJlNzJkMDMwMTJiZTEwNmI0OGFjY2QzMzgyY2VjN2NiY2VjZWIxNDJlYzc2MjM3OTM0NjM5YTZhMmU5In19fQ==");

         */
        ItemStack nearby = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Pobliskie ziemie")
                .addLore("")
                .addLore("&cTa funkcja pojawi się wkrótce.")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZjODQ0N2E4YjZiMGUwYzdlNzYyOWM2ODk4ZWM5Yzc0OWE3YTBhMmI0NTJiOWMzODUyYzc4NDdiYjRkYzUifX19")
                .build();

        ItemBuilder logs = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6Logi Gildyjne")
                .addLore("");

                if (guild.getLogQueue().isEmpty()) {
                    logs.addLore("&cLogi są puste...");
                }

                for (LogEntry entry : guild.getLogQueue()) {
                    logs.addLore("&7" + entry.getHowMuchElapsed() + " temu &f- &3" + entry.getValue());
                }

                logs.setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODk1NjAyNGJmZTM1ZmZiZmQ0YjAzMDU5MmFlNDk3YTAxNGQ2ZGFlZmU5MjI4YmRmMzE3MGVjZDc4ZWQ3YzcxMiJ9fX0=");

        ItemStack plugin = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&6NeonGuilds by xNeox")
                .addLore("&7Wersja: &e" + this.plugin.getDescription().getVersion())
                .addLore("")
                .addLore("&7Ostatnie zmiany:")
                .addLore(" &8- &7Pierwsza wersja pluginu")
                .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTdlZDY2ZjVhNzAyMDlkODIxMTY3ZDE1NmZkYmMwY2EzYmYxMWFkNTRlZDVkODZlNzVjMjY1ZjdlNTAyOWVjMSJ9fX0=")
                .build();

        inventory.setItem(3, profile);
        inventory.setItem(4, shield);
        inventory.setItem(5, plugin);

        inventory.setItem(20, claims);
        inventory.setItem(21, members);
        inventory.setItem(22, home);
        inventory.setItem(23, allies);
        inventory.setItem(24, info);

        inventory.setItem(29, division);
        inventory.setItem(30, leaderboard);
        inventory.setItem(31, nexus);
        inventory.setItem(32, upgrades);
        inventory.setItem(33, help);

        inventory.setItem(48, warInactive);
        inventory.setItem(49, nearby);
        inventory.setItem(50, logs.build());
    }

    @Override
    public void onClick(InventoryClickEvent event, Player player) {
        event.setCancelled(true);
        VisualUtils.playSound(player, Sound.BLOCK_WOODEN_BUTTON_CLICK_ON);

        switch (event.getSlot()) {
            case 20:
                this.plugin.getInventoryManager().open("claim", player);
                break;
            case 21:
                this.plugin.getInventoryManager().open("members", player);
                break;
            case 22:
                player.closeInventory();
                player.performCommand("g home");
                break;
            case 33:
                player.closeInventory();
                player.performCommand("g help");
                break;
            case 32:
                this.plugin.getInventoryManager().open("upgrades", player);
                break;
            case 23:
                this.plugin.getInventoryManager().open("allies", player);
                break;
            case 30:
                this.plugin.getInventoryManager().open("leaderboards", player);
                break;
            case 48:
                Guild guild = this.plugin.getGuildManager().getGuild(player);
                if (guild.getWarEnemy() == null) {
                    return;
                }

                if (guild.getWarMembers().contains(player)) {
                    guild.getWarMembers().remove(player);
                } else {
                    guild.getWarMembers().add(player);
                }
                break;
        }
    }
}
