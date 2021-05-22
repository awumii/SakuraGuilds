package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.*;
import me.xneox.guilds.util.gui.ClickEvent;
import me.xneox.guilds.util.gui.ClickableInventory;
import me.xneox.guilds.util.gui.InventorySize;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ManagementGui extends ClickableInventory {
    public ManagementGui(NeonGuilds plugin) {
        super(plugin, "Menu zarządzania gildią", InventorySize.BIGGEST);
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        InventoryUtils.drawBorder(inventory);

        Guild guild = this.plugin.getGuildManager().getGuild(player.getName());
        if (guild == null) {
            player.closeInventory();
            return;
        }

        User user = this.plugin.getUserManager().getUser(player);
        ItemStack claims = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Zajęte Ziemie")
                .lore("")
                .lore("&7Kliknij, aby przeglądnąć zajęte tereny.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTI4OWQ1YjE3ODYyNmVhMjNkMGIwYzNkMmRmNWMwODVlODM3NTA1NmJmNjg1YjVlZDViYjQ3N2ZlODQ3MmQ5NCJ9fX0=")
                .build();

        ItemStack members = new ItemBuilder(Material.PLAYER_HEAD, guild.getMembers().size())
                .name("&6Członkowie")
                .lore("")
                .lore("&7Kliknij, aby zarządzać członkami gildii")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTJmMzU3YWI1OWUwNGU2NzY3MjRjNjNkNzA0YzNkMWYyZjlhZTFhZDQyODNlOTFkN2RhMjZlZmM2YzQ4MDgifX19")
                .build();

        ItemStack home = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Baza Gildii")
                .lore("")
                .lore("&7Kliknij aby przeteleportować się do bazy")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNkMDJjZGMwNzViYjFjYzVmNmZlM2M3NzExYWU0OTc3ZTM4YjkxMGQ1MGVkNjAyM2RmNzM5MTNlNWU3ZmNmZiJ9fX0=")
                .build();

        ItemStack help = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Lista Komend")
                .lore("")
                .lore("&7Kliknij, aby zobaczyć dostępne komendy.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWNmMmFiZDYxNzlmNDE2YzI2YjRkMTI5NzE4ODFmMTI0Mzc2NDM5OWE2N2Y5Yzk0ZmQ4NTlmMTNlNzdjIn19fQ==")
                .build();

        ItemStack profile = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Profil")
                .lore("")
                .lore("&eNick:")
                .lore("&f" + player.getName())
                .lore("")
                .lore("&ePozycja:")
                .lore("&f" + guild.getPlayerRank(player).getDisplay())
                .lore("")
                .lore("&eWojna:")
                .lore(" &7→ Zabójstwa: &f" + user.getKills())
                .lore(" &7→ Śmierci: &f" + user.getDeaths())
                .lore("")
                .skullOwner(player.getName())
                .build();

        ItemStack info = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Informacje o Gildii")
                .lore("")
                .lore("&eNazwa:")
                .lore("&f" + guild.getName())
                .lore("")
                .lore("&eLider:")
                .lore("&f" + guild.getLeader().getName())
                .lore("")
                .lore("&eLiczba członków:")
                .lore("&f" + guild.getMembers().size() + "/" + guild.getMaxMembers() + " &7(&a" + guild.getOnlineMembers().size() + " &fonline&7)")
                .lore("")
                .lore("&eZajęte ziemie:")
                .lore("&f" + guild.getChunks().size() + " &7(Limit: &f" + guild.getMaxChunks() + "&7)")
                .lore("")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzBjZjc0ZTI2MzhiYTVhZDMyMjM3YTM3YjFkNzZhYTEyM2QxODU0NmU3ZWI5YTZiOTk2MWU0YmYxYzNhOTE5In19fQ==")
                .build();

        ItemStack division = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Statystyki Wojny")
                .lore("")
                .lore("&eRanking:")
                .lore(" &7→ Dywizja: " + guild.getDivision().getName())
                .lore(" &7→ Puchary: &f" + guild.getTrophies() + "★")
                .lore("")
                .lore("&eWalka:")
                .lore(" &7→ Zabójstwa: &f" + guild.getKills())
                .lore(" &7→ Śmierci: &f" + guild.getDeaths())
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRkZTRkOTViYTRhNDZkYjdlNTY2YjM0NWY3ODk0ZDFkMjU4Zjg5M2ViOTJjNzgwYjNkYTc3NWVlZGY5MSJ9fX0=")
                .build();

        ItemStack allies = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Zawarte Sojusze")
                .lore("")
                .lore("&7Kliknij, aby przeglądać zawarte sojusze.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJkZmJlMmQzMzhjZTI0NmIzYTBhMzRhNDAwM2RiODM5ZTA3NjMxOTMwYTQzZTBlMGU5NWM5YWZhYWE3YTVlMyJ9fX0=")
                .build();

        ItemStack buildings = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Budowle Gildyjne")
                .lore("")
                .lore("&7Kliknij, aby wyświetlić dostępne budowle.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQwMmM2MjVkYzA0MWExYTQxOGFhNmU1MTQ3MGMyMDNmMDMwZmZkNjMxYTQ3YWFlNDAxNTliMDg5YzkyNmQ1NSJ9fX0=")
                .build();

        ItemStack nexus = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&c❤ Zdrowie Nexusa: &f" + guild.getHealth() + "/3 HP")
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

        ItemStack shield = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Tarcza Wojenna")
                .lore("")
                .lore("&7Tarcza wojenna chroni twoją gildię")
                .lore("&7oraz jej nexus, przed atakami wrogów.")
                .lore("")
                .lore("&7Status tarczy: &c" + TimeUtils.futureMillisToTime(guild.getShield()))
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzhjZWI4NjMxYWRkN2NiYjU2NWRjYjcwNWYxMjEyMzQ5Y2NjZDc1NTk2NWM0NmE5MjI4NTJjOWZkOTQ4YjRiYiJ9fX0=")
                .build();

        ItemStack leaderboard = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Ranking Gildii")
                .lore("")
                .lore("&7Kliknij, aby przeglądać topowe gildie.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGJlNjIyNmQ2MWVjY2NkYjU3MzJiNmY3MTU2MGQ2NDAxZDJjYTBlZmY4ZTFhYWZiYmUzY2I3M2ZiNWE5ZiJ9fX0=")
                .build();

        ItemStack warInactive = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Wojna Gildii")
                .lore("")
                .lore("&7Wojny to dobry sposób na walki pomiędzy gildiami.")
                .lore("&7Rozgrywają się one na arenie, uczestniczą w niej")
                .lore("&7wszyscy członkowie online obu gildii.")
                .lore("")
                .lore("&cWalczysz z własnym wyposażeniem,")
                .lore("&cale nie tracisz przedmiotów!")
                .lore("")
                .lore("&7Możliwe nagrody za uczestnictwo:")
                .lore("  &8▸ &fPuchary rankingowe &7(&a0~100&7)")
                .lore("  &8▸ &fPieniądze dla gildii &7(&a500~3000&7)")
                .lore("  &8▸ &fButelki EXP &7(&a1~16&7)")
                .lore("  &8▸ &fDiamenty &7(&a1~6&7)")
                .lore("  &8▸ &fSzmaragdy &7(&a4~8&7)")
                .lore("")
                .lore("&7Kliknij, aby wyszukać przeciwników.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTIzMTJlNzJkMDMwMTJiZTEwNmI0OGFjY2QzMzgyY2VjN2NiY2VjZWIxNDJlYzc2MjM3OTM0NjM5YTZhMmU5In19fQ==")
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
        ItemStack storage = new ItemBuilder(Material.PLAYER_HEAD, guild.getStorageRows())
                .name("&6Magazyn Gildyjny")
                .lore("")
                .lore("&7Zawartość magazynu: &f" + guild.getStorageItems() + "x"
                        + " (" + ChatUtils.percentage(guild.getTakenStorageRows(), guild.getStorageRows()) + "%)")
                .lore(" &8⇨ " + ChatUtils.progressBar(guild.getTakenStorageRows(), guild.getStorageRows()))
                .lore("")
                .lore("&7Kliknij, aby otworzyć magazyn.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzc0ZWUxNTQyYzQ1NjNmZDZlN2Q3MmRlMjZlNzM3Y2YxOGZiZDA0Y2NhYjFiOGIyODM1M2RhODczNDhlY2ZiIn19fQ==")
                .build();

        ItemStack publicState = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&aTwoja gildia jest publiczna")
                .lore("")
                .lore("&7Każdy może dołączyć bez zaproszenia.")
                .lore("")
                .lore("&c&nKliknij, aby ustawić na prywatną.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRkNDZjM2ZmODhlMGRiYzUxZjY2MjE5M2UxOWViNGEwMWNkMWY0MGFkZDNhMWJiMTU2NjcwOTlhOGJiYjIifX19")
                .build();

        ItemStack privateState = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&cTwoja gildia jest prywatna")
                .lore("")
                .lore("&7Wymagane jest zaproszenie aby dołączyć.")
                .lore("")
                .lore("&a&nKliknij, aby ustawić na publiczną.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmZkYzRjODg5NWUzZjZiM2FjNmE5YjFjZDU1ZGUzYTI5YmJjOGM3ODVlN2ZiZGJkOTMyMmQ4YzIyMzEifX19")
                .build();

        inventory.setItem(3, profile);
        inventory.setItem(4, shield);
        inventory.setItem(5, help);

        inventory.setItem(20, claims);
        inventory.setItem(21, members);
        inventory.setItem(22, home);
        inventory.setItem(23, allies);
        inventory.setItem(24, warInactive);

        inventory.setItem(29, division);
        inventory.setItem(30, leaderboard);
        inventory.setItem(31, nexus);
        inventory.setItem(32, buildings);
        inventory.setItem(33, storage);

        inventory.setItem(48, info);
        inventory.setItem(50, guild.isPublic() ? publicState : privateState);
    }

    @Override
    public void onClick(ClickEvent event, Player player) {
        VisualUtils.click(player);
        Guild guild = this.plugin.getGuildManager().getGuild(player);

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
            case 5:
                player.closeInventory();
                player.performCommand("g help");
                break;
            case 50:
                this.plugin.getInventoryManager().open("management", player);
                player.performCommand("g public");
                break;
            case 32:
                this.plugin.getInventoryManager().open("buildings", player);
                break;
            case 23:
                this.plugin.getInventoryManager().open("allies", player);
                break;
            case 30:
                this.plugin.getInventoryManager().open("leaderboards", player);
                break;
            case 24:
                this.plugin.getInventoryManager().open("war", player);
                break;
            case 33:
                player.openInventory(guild.getStorage());
                break;
        }
    }
}
