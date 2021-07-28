package me.xneox.guilds.gui;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.InventoryUtils;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.TimeUtils;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.gui.InventoryProviderImpl;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.api.InventorySize;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ManagementGui extends InventoryProviderImpl {
    public ManagementGui(SakuraGuildsPlugin plugin) {
        super(plugin, "Menu zarządzania gildią", InventorySize.BIGGEST);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        Guild guild = this.plugin.guildManager().playerGuild(player.getName());
        if (guild == null) {
            player.closeInventory();
            return;
        }

        User user = this.plugin.userManager().getUser(player);
        ItemStack claims = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19")
                .name("&6Zajęte Ziemie")
                .lore("")
                .lore("&7Kliknij, aby przeglądnąć zajęte tereny.")
                .build();

        ItemStack members = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzgyZWU4NWZlNmRjNjMyN2RhZDIwMmZjYTkzYzlhOTRhYzk5YjdiMTY5NzUyNGJmZTk0MTc1ZDg4NzI1In19fQ==")
                .name("&6Członkowie")
                .lore("")
                .lore("&7Kliknij, aby zarządzać członkami gildii")
                .amount(guild.members().size())
                .build();

        ItemStack home = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNkMDJjZGMwNzViYjFjYzVmNmZlM2M3NzExYWU0OTc3ZTM4YjkxMGQ1MGVkNjAyM2RmNzM5MTNlNWU3ZmNmZiJ9fX0=")
                .name("&6Baza Gildii")
                .lore("")
                .lore("&7Kliknij aby przeteleportować się do bazy")
                .build();

        ItemStack help = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWNmMmFiZDYxNzlmNDE2YzI2YjRkMTI5NzE4ODFmMTI0Mzc2NDM5OWE2N2Y5Yzk0ZmQ4NTlmMTNlNzdjIn19fQ==")
                .name("&6Lista Komend")
                .lore("")
                .lore("&7Kliknij, aby zobaczyć dostępne komendy.")
                .build();

        ItemStack profile = ItemBuilder.skullOf(player.getName())
                .name("&6Profil")
                .lore("")
                .lore("&eNick:")
                .lore("&f" + player.getName())
                .lore("")
                .lore("&ePozycja:")
                .lore("&f" + guild.member(player).rank().title())
                .lore("")
                .lore("&eWojna:")
                .lore(" &7→ Puchary: &6" + user.trophies() + "★")
                .lore(" &7→ Zabójstwa: &f" + user.kills())
                .lore(" &7→ Śmierci: &f" + user.deaths())
                .lore("")
                .build();

        ItemStack info = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmUzZjUwYmE2MmNiZGEzZWNmNTQ3OWI2MmZlZGViZDYxZDc2NTg5NzcxY2MxOTI4NmJmMjc0NWNkNzFlNDdjNiJ9fX0=")
                .name("&6Informacje o Gildii")
                .lore("")
                .lore("&eNazwa:")
                .lore("&f" + guild.name())
                .lore("")
                .lore("&eData założenia:")
                .lore("&f" + guild.creationDate())
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
                .build();

        ItemStack division = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRkZTRkOTViYTRhNDZkYjdlNTY2YjM0NWY3ODk0ZDFkMjU4Zjg5M2ViOTJjNzgwYjNkYTc3NWVlZGY5MSJ9fX0=")
                .name("&6Statystyki Wojny")
                .lore("")
                .lore("&eRanking:")
                .lore(" &7→ Dywizja: " + guild.division().getName())
                .lore(" &7→ Puchary: &f" + guild.trophies() + "★")
                .lore("")
                .lore("&eWalka:")
                .lore(" &7→ Zabójstwa: &f" + guild.kills())
                .lore(" &7→ Śmierci: &f" + guild.deaths())
                .build();

        ItemStack allies = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg0MzI2MDdlODZiMDJlYTNjYWFmOTM1NzA2NmNhM2Y1ZTRlZGVlZjdiZTllZGNiYjVlNDU3MTFjNjU1NyJ9fX0=")
                .name("&6Zawarte Sojusze")
                .lore("")
                .lore("&7Kliknij, aby przeglądać zawarte sojusze.")
                .build();

        ItemStack buildings = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQzMTZhNjQwOTlkODk2ZjY2OWQwZjA4ODUyMDIxN2E4M2RlY2Q0YTNiNjdlNTdhZjg5YjMzZDIwYzMyMWYzNCJ9fX0=")
                .name("&6Ulepszenia Gildii")
                .lore("")
                .lore("&7Kliknij, aby zakupić ulepszenia.")
                .build();

        ItemStack nexus = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZhNzY2MzFjMjNlZTJhNzAwZjMzZGNmYTY1Yzc0ZGQzMGQwZjE1N2YzNTE1ZGIyMjU3Y2FkNjhlMmRjN2MzYSJ9fX0=")
                .name("&c❤ Zdrowie Nexusa: &f" + guild.health() + "/3 HP")
                .lore("")
                .lore("&7Nexus to serce twojej gildii.")
                .lore("")
                .lore("&7Jeśli &fHP Nexusa &7spadnie &c&ndo zera&7,")
                .lore("&7a twoja gildia nie posiada &6Tarczy Wojennej&7,")
                .lore("&7zostanie ona całkowicie &4rozwiązana.")
                .lore("")
                .lore(guild.isShieldActive() ? "&a(Tarcza wojenna jest aktywna)" : "&c(Tarcza wojenna jest nieaktywna)")
                .build();

        ItemStack shield = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzhjZWI4NjMxYWRkN2NiYjU2NWRjYjcwNWYxMjEyMzQ5Y2NjZDc1NTk2NWM0NmE5MjI4NTJjOWZkOTQ4YjRiYiJ9fX0=")
                .name("&6Tarcza Wojenna")
                .lore("")
                .lore("&7Tarcza wojenna chroni twoją gildię")
                .lore("&7oraz jej nexus, przed atakami wrogów.")
                .lore("")
                .lore("&7Status tarczy: &c" + TimeUtils.futureMillisToTime(guild.shieldDuration()))
                .build();

        ItemStack leaderboard = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGJlNjIyNmQ2MWVjY2NkYjU3MzJiNmY3MTU2MGQ2NDAxZDJjYTBlZmY4ZTFhYWZiYmUzY2I3M2ZiNWE5ZiJ9fX0=")
                .name("&6Ranking Gildii")
                .lore("")
                .lore("&7Kliknij, aby przeglądać topowe gildie.")
                .build();

        ItemStack storage = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGMxYmQwYWNmMmY5NDA2NGNjYTI3YjBhZWYzNWQ4YzEyYTg5MjhkNDM2MzdhN2ZlNzFmNWVlZTQ1ODk3NWUxYSJ9fX0=")
                .name("&6Magazyn Gildyjny")
                .lore("")
                .lore("&7Kliknij, aby otworzyć magazyn.")
                .amount(guild.maxStorage())
                .build();

        InventoryUtils.drawBorder(inventory);
        inventory.setItem(4, profile);

        inventory.setItem(20, claims);
        inventory.setItem(21, members);
        inventory.setItem(22, home);
        inventory.setItem(23, allies);
        inventory.setItem(24, shield);

        inventory.setItem(29, division);
        inventory.setItem(30, leaderboard);
        inventory.setItem(31, nexus);
        inventory.setItem(32, buildings);
        inventory.setItem(33, storage);

        inventory.setItem(48, info);
        inventory.setItem(50, help);
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
