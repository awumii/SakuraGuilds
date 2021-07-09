package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.*;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.InventoryProviderImpl;
import me.xneox.guilds.util.gui.api.InventorySize;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ManagementGui extends InventoryProviderImpl {
    public ManagementGui(NeonGuilds plugin) {
        super(plugin, "Menu zarządzania gildią", InventorySize.BIGGEST);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);

        Guild guild = this.plugin.guildManager().playerGuild(player.getName());
        if (guild == null) {
            player.closeInventory();
            return;
        }

        User user = this.plugin.userManager().getUser(player);
        ItemStack claims = ItemBuilder.of(Material.PLAYER_HEAD)
                .name("&6Zajęte Ziemie")
                .lore("")
                .lore("&7Kliknij, aby przeglądnąć zajęte tereny.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTI4OWQ1YjE3ODYyNmVhMjNkMGIwYzNkMmRmNWMwODVlODM3NTA1NmJmNjg1YjVlZDViYjQ3N2ZlODQ3MmQ5NCJ9fX0=")
                .build();

        ItemStack members = ItemBuilder.of(Material.PLAYER_HEAD)
                .name("&6Członkowie")
                .lore("")
                .lore("&7Kliknij, aby zarządzać członkami gildii")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzgyZWU4NWZlNmRjNjMyN2RhZDIwMmZjYTkzYzlhOTRhYzk5YjdiMTY5NzUyNGJmZTk0MTc1ZDg4NzI1In19fQ==")
                .amount(guild.members().size())
                .build();

        ItemStack home = ItemBuilder.of(Material.PLAYER_HEAD)
                .name("&6Baza Gildii")
                .lore("")
                .lore("&7Kliknij aby przeteleportować się do bazy")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNkMDJjZGMwNzViYjFjYzVmNmZlM2M3NzExYWU0OTc3ZTM4YjkxMGQ1MGVkNjAyM2RmNzM5MTNlNWU3ZmNmZiJ9fX0=")
                .build();

        ItemStack help = ItemBuilder.of(Material.PLAYER_HEAD)
                .name("&6Lista Komend")
                .lore("")
                .lore("&7Kliknij, aby zobaczyć dostępne komendy.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWNmMmFiZDYxNzlmNDE2YzI2YjRkMTI5NzE4ODFmMTI0Mzc2NDM5OWE2N2Y5Yzk0ZmQ4NTlmMTNlNzdjIn19fQ==")
                .build();

        ItemStack profile = ItemBuilder.of(Material.PLAYER_HEAD)
                .name("&6Profil")
                .lore("")
                .lore("&eNick:")
                .lore("&f" + player.getName())
                .lore("")
                .lore("&ePozycja:")
                .lore("&f" + guild.member(player).rank().title())
                .lore("")
                .lore("&eWojna:")
                .lore(" &7→ Zabójstwa: &f" + user.getKills())
                .lore(" &7→ Śmierci: &f" + user.getDeaths())
                .lore("")
                .skullOwner(player.getName())
                .build();

        ItemStack info = ItemBuilder.of(Material.PLAYER_HEAD)
                .name("&6Informacje o Gildii")
                .lore("")
                .lore("&eNazwa:")
                .lore("&f" + guild.name())
                .lore("")
                .lore("&eLider:")
                .lore("&f" + guild.leader().nickname())
                .lore("")
                .lore("&eLiczba członków:")
                .lore("&f" + guild.members().size() + "/" + guild.maxSlots() + " &7(&a" + guild.getOnlineMembers().size() + " &fonline&7)")
                .lore("")
                .lore("&eZajęte ziemie:")
                .lore("&f" + guild.claims().size() + " &7(Limit: &f" + guild.maxChunks() + "&7)")
                .lore("")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzBjZjc0ZTI2MzhiYTVhZDMyMjM3YTM3YjFkNzZhYTEyM2QxODU0NmU3ZWI5YTZiOTk2MWU0YmYxYzNhOTE5In19fQ==")
                .build();

        ItemStack division = ItemBuilder.of(Material.PLAYER_HEAD)
                .name("&6Statystyki Wojny")
                .lore("")
                .lore("&eRanking:")
                .lore(" &7→ Dywizja: " + guild.division().getName())
                .lore(" &7→ Puchary: &f" + guild.trophies() + "★")
                .lore("")
                .lore("&eWalka:")
                .lore(" &7→ Zabójstwa: &f" + guild.kills())
                .lore(" &7→ Śmierci: &f" + guild.deaths())
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRkZTRkOTViYTRhNDZkYjdlNTY2YjM0NWY3ODk0ZDFkMjU4Zjg5M2ViOTJjNzgwYjNkYTc3NWVlZGY5MSJ9fX0=")
                .build();

        ItemStack allies = ItemBuilder.of(Material.PLAYER_HEAD)
                .name("&6Zawarte Sojusze")
                .lore("")
                .lore("&7Kliknij, aby przeglądać zawarte sojusze.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJkZmJlMmQzMzhjZTI0NmIzYTBhMzRhNDAwM2RiODM5ZTA3NjMxOTMwYTQzZTBlMGU5NWM5YWZhYWE3YTVlMyJ9fX0=")
                .build();

        ItemStack buildings = ItemBuilder.of(Material.PLAYER_HEAD)
                .name("&6Ulepszenia Gildii")
                .lore("")
                .lore("&7Kliknij, aby zakupić ulepszenia.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQwMmM2MjVkYzA0MWExYTQxOGFhNmU1MTQ3MGMyMDNmMDMwZmZkNjMxYTQ3YWFlNDAxNTliMDg5YzkyNmQ1NSJ9fX0=")
                .build();

        ItemStack nexus = ItemBuilder.of(Material.PLAYER_HEAD)
                .name("&c❤ Zdrowie Nexusa: &f" + guild.health() + "/3 HP")
                .lore("")
                .lore("&7Nexus to serce twojej gildii.")
                .lore("")
                .lore("&7Jeśli &fHP Nexusa &7spadnie &c&ndo zera&7,")
                .lore("&7a twoja gildia nie posiada &6Tarczy Wojennej&7,")
                .lore("&7zostanie ona całkowicie &4rozwiązana.")
                .lore("")
                .lore(guild.isShieldActive() ? "&a(Tarcza wojenna jest aktywna)" : "&c(Tarcza wojenna jest nieaktywna)")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQ4NzYxN2E2NTQ3ZmY5MDE3NzdmYzYzNGU1MzFkZWU0MDhiZTNiYzQyM2ViODZhYWNiZTcyODU5M2I0ZWVjYyJ9fX0=")
                .build();

        ItemStack shield = ItemBuilder.of(Material.PLAYER_HEAD)
                .name("&6Tarcza Wojenna")
                .lore("")
                .lore("&7Tarcza wojenna chroni twoją gildię")
                .lore("&7oraz jej nexus, przed atakami wrogów.")
                .lore("")
                .lore("&7Status tarczy: &c" + TimeUtils.futureMillisToTime(guild.shieldDuration()))
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzhjZWI4NjMxYWRkN2NiYjU2NWRjYjcwNWYxMjEyMzQ5Y2NjZDc1NTk2NWM0NmE5MjI4NTJjOWZkOTQ4YjRiYiJ9fX0=")
                .build();

        ItemStack leaderboard = ItemBuilder.of(Material.PLAYER_HEAD)
                .name("&6Ranking Gildii")
                .lore("")
                .lore("&7Kliknij, aby przeglądać topowe gildie.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGJlNjIyNmQ2MWVjY2NkYjU3MzJiNmY3MTU2MGQ2NDAxZDJjYTBlZmY4ZTFhYWZiYmUzY2I3M2ZiNWE5ZiJ9fX0=")
                .build();

        ItemStack soon = ItemBuilder.of(Material.RED_STAINED_GLASS_PANE)
                .name("&cWkrótce...")
                .build();

        ItemStack storage = ItemBuilder.of(Material.PLAYER_HEAD)
                .name("&6Magazyn Gildyjny")
                .lore("")
                .lore("&7Kliknij, aby otworzyć magazyn.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzc0ZWUxNTQyYzQ1NjNmZDZlN2Q3MmRlMjZlNzM3Y2YxOGZiZDA0Y2NhYjFiOGIyODM1M2RhODczNDhlY2ZiIn19fQ==")
                .amount(guild.maxStorage())
                .build();

        ItemStack soon1 = ItemBuilder.of(Material.RED_STAINED_GLASS_PANE)
                .name("&cWkrótce...")
                .build();

        inventory.setItem(3, profile);
        inventory.setItem(4, shield);
        inventory.setItem(5, help);

        inventory.setItem(20, claims);
        inventory.setItem(21, members);
        inventory.setItem(22, home);
        inventory.setItem(23, allies);
        inventory.setItem(24, soon);

        inventory.setItem(29, division);
        inventory.setItem(30, leaderboard);
        inventory.setItem(31, nexus);
        inventory.setItem(32, buildings);
        inventory.setItem(33, storage);

        inventory.setItem(48, info);
        inventory.setItem(50, soon1);
    }

    @Override
    public void event(ClickEvent event, Player player) {
        VisualUtils.click(player);
        Guild guild = this.plugin.guildManager().playerGuild(player);

        switch (event.slot()) {
            case 20 -> this.plugin.inventoryManager().open("claim", player);
            case 21 -> this.plugin.inventoryManager().open("members", player);
            case 22 -> {
                player.closeInventory();
                player.performCommand("g home");
            }
            case 5 -> {
                player.closeInventory();
                player.performCommand("g help");
            }
            case 32 -> this.plugin.inventoryManager().open("upgrades", player);
            case 23 -> this.plugin.inventoryManager().open("allies", player);
            case 30 -> this.plugin.inventoryManager().open("leaderboards", player);
            case 33 -> player.openInventory(guild.storage());
        }
    }
}
