package me.xneox.guilds.gui;

import me.clip.placeholderapi.PlaceholderAPI;
import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.HookUtils;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.gui.InventoryProviderImpl;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.api.InventorySize;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class HelpProfileGui extends InventoryProviderImpl {
    public HelpProfileGui(NeonGuilds plugin) {
        super(plugin, "Menu Gracza", InventorySize.BIGGEST);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        Guild guild = this.plugin.guildManager().playerGuild(player);
        VisualUtils.sound(player, Sound.BLOCK_NOTE_BLOCK_PLING);

        // FIRST ROW
        ItemStack stats = ItemBuilder.skullOf(player.getName())
                .name("&6" + player.getName() + " &8// &7Poziom &6" + HookUtils.getAureliumLevel(player) + "✫")
                .lore(PlaceholderAPI.setPlaceholders(player, " &4➽ Siła &f%aureliumskills_strength%"))
                .lore(PlaceholderAPI.setPlaceholders(player, " &c❤ Zdrowie &f%aureliumskills_health%"))
                .lore(PlaceholderAPI.setPlaceholders(player, " &6❥ Regeneracja &f%aureliumskills_regeneration%"))
                .lore(PlaceholderAPI.setPlaceholders(player, " &2☘ Szczęście &f%aureliumskills_luck%"))
                .lore(PlaceholderAPI.setPlaceholders(player, " &9✿ Inteligencja Siła &f%aureliumskills_wisdom%"))
                .lore(PlaceholderAPI.setPlaceholders(player, " &5✦ Twardość &f%aureliumskills_toughness%"))
                .lore("")
                .lore("&eKliknij, aby zobaczyć szczegóły.")
                .build();

        // SECOND ROW
        ItemStack skills = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWE4YzY4NTE0ZDhjODE5ZTY4ZWI3NWI1OTY0NDVkODhiMzY3YWUzNTFiYWE5OTgzYmM2OWNmNmI1MjBmMzk4NSJ9fX0=")
                .name("&6Umiejętności")
                .lore("&7Zobacz poziomy twoich umiejętności,")
                .lore("&7oraz bonusy za zdobywanie doświadczenia.")
                .lore("")
                .lore("&eKliknij aby wyświetlić")
                .build();

        ItemStack enchants = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjYyNjUxODc5ZDg3MDQ5OWRhNTBlMzQwMzY4MDBkZGZmZDUyZjNlNGUxOTkzYzVmYzBmYzgyNWQwMzQ0NmQ4YiJ9fX0=")
                .name("&dKsięga Zaklęć")
                .lore("&7Na serwerze znajduje się ponad &5200")
                .lore("&7nowych enchantów, informacje o nich")
                .lore("&7znajdziesz właśnie w tym miejscu.")
                .lore("")
                .lore("&eKliknij, aby zobaczyć zaklęcia.")
                .build();

        ItemStack fishing = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDgwNjg2Zjg2ODUzMDBkOGYxNzA5NDEzZDc1NDQyMGZjZTk4ODYzNmRmNjc3NTVmYjg1YmQ1MTdlZjA0N2MwMSJ9fX0=")
                .name("&6Dziennik Wędkarski")
                .lore("&7Wyświetl statystyki, oraz rodzaje")
                .lore("&7ryb i przedmiotów które możesz wyłowić.")
                .lore("")
                .lore("&eKliknij, aby otworzyć.")
                .enchantment(Enchantment.LUCK, 0)
                .flags(ItemFlag.HIDE_ENCHANTS)
                .build();

        ItemStack guildInfo = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQxNWFhY2I3MjEzYzgzMTFlZWQ3YmFmMzdlYmI1OGE1ZjRiOTI1NjMxN2Q4NDU4ZDE1ZDMzN2E3NGU0YmU2In19fQ==")
                .name("&6Gildia")
                .lore("&7Dzięki gildiom, możesz zajmować teren")
                .lore("&7oraz współpracować z innymi graczami.")
                .lore("&7Oczywiście możesz też grać sam...")
                .lore("")
                .lore(guild != null ? " &a\uD83C\uDFF9 Należysz do " + guild.name() : " &c✘ Nie posiadasz jeszcze gildiI!")
                .lore("")
                .lore(guild != null ? "&eKliknij, aby zarządzać gildią." : "&eKliknij, aby zobaczyć pomoc.")
                .build();

        ItemStack graves = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2YzYmU2NDAxNjczNmJlNDRiMWQ1YTVmM2FkYWI2ZDRjMDQzNzgyYzE3ZGQyOWMzYjhjNGNiNmU3M2Y5ODk4In19fQ==")
                .name("&6Groby")
                .lore("&7Zginąłeś? Jeśli nie było przy tobie żadnego")
                .lore("&7gracza, to spokojnie! W miejscu twojej śmierci")
                .lore("&7pojawia się grób z twoimi przedmiotami oraz expem.")
                .lore("")
                .lore("&eKliknij, aby zobaczyć aktywne groby.")
                .build();

        ItemStack crafting = ItemBuilder.of(Material.CRAFTING_TABLE)
                .name("&6Stół Rzemieślniczy")
                .lore("&7Kliknij, aby otworzyć menu")
                .lore("&7stołu rzemieślniczego.")
                .build();

        ItemStack enderchest = ItemBuilder.of(Material.ENDER_CHEST)
                .name("&6Enderchest")
                .lore("&7Kliknij, aby otworzyć enderchest.")
                .build();

        // THIRD ROW

        ItemStack auction = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzFkODI3YTVkZWNiMGFlNzMwYWJiNjk2MTc3NzZlMTg5NGYyYmRiNDY5Njg1NDA0MzMxMTVkMzY4OGZiYWMzOCJ9fX0=")
                .name("&6Rynek")
                .lore("&7Na rynku, każdy gracz może wystawiać")
                .lore("&7dowolne przedmioty za własną cenę.")
                .lore("")
                .lore(" &a&l! &aWpisz &n/ah sell <cena>&a aby wystawić przedmiot!")
                .lore("")
                .lore("&eKliknij, aby otworzyć rynek.")
                .build();

        ItemStack slimefun = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDkzNGE5ZjVhYjE3ODlhN2Q4ZGQ5NmQzMjQ5M2NkYWNmZjU3N2Q4YzgxZTdiMjM5MTdkZmYyZTMyYmQwYmMxMCJ9fX0=")
                .name("&6Podręcznik Slimefun")
                .lore("&7Slimefun dodaje &a900+ przedmiotów, maszyn &7itd...")
                .lore("&7Podręcznik możesz otrzymać komendą &a/sf guide")
                .lore("")
                .lore("&eKliknij, aby otworzyć podręcznik slimefun.")
                .build();

        ItemStack rtp = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19")
                .name("&6Losowy Teleport")
                .lore("&7Przeteleportuj się w losowe miejsce.")
                .build();

        ItemStack skins = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzgzZGI4NmJiODZkYzJlYzQ5NGUyZmZjYTc3NzY1ODEwZWQxMWE1MmVmZWY0ZTVjODUyNTJlYzM5YTI2YzAifX19")
                .name("&6Skiny")
                .lore("&7Zobacz menu skinów które możesz ustawić.")
                .lore("")
                .lore(" &a&l! &aUstaw własnego skina za pomocą &n/skin")
                .build();

        ItemStack home = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y3Y2RlZWZjNmQzN2ZlY2FiNjc2YzU4NGJmNjIwODMyYWFhYzg1Mzc1ZTlmY2JmZjI3MzcyNDkyZDY5ZiJ9fX0=")
                .name("&6Dom")
                .lore("&7Aby ustawić dom, użyj komendy &a/sethome")
                .lore("")
                .lore("&eKliknij, aby przetelportować.")
                .build();

        // LAST ROW
        ItemStack warps = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFhOGZjOGRlNjQxN2I0OGQ0OGM4MGI0NDNjZjUzMjZlM2Q5ZGE0ZGJlOWIyNWZjZDQ5NTQ5ZDk2MTY4ZmMwIn19fQ==")
                .name("&6Warpy")
                .lore("&7Zobacz przydatne miejsca, do których")
                .lore("&7możesz się przeteleportować.")
                .build();

        ItemStack kits = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzViMTE2ZGM3NjlkNmQ1NzI2ZjEyYTI0ZjNmMTg2ZjgzOTQyNzMyMWU4MmY0MTM4Nzc1YTRjNDAzNjdhNDkifX19")
                .name("&6Zestawy")
                .lore("&7Zobacz dostępne zestawy przedmiotów.")
                .build();

        ItemStack help = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzA1YzZjZmU2Yzc2Y2RhN2YxM2FmNDc3YWVhNDc5NjNjN2ZkMzVlNjk3MzY5ZWI5MTFjNDEyMWQ4ZmNjNCJ9fX0=")
                .name("&6Lista Komend")
                .lore("&7Wyświetl wszystkie dostępne komendy.")
                .build();

        ItemStack discord = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWQ4MzNiNTE1NjY1NjU2NThmOTAxMWRlODc4NGU5MGMxYWQ5YmE1ZDMzMzdmOGMwNjkyMTNiYmRlZTk4NjUyMyJ9fX0=")
                .name("&5Discord")
                .lore("&7Wejdź na naszego discorda!")
                .lore("")
                .lore("   &d&nwww.dronizja.pl/discord")
                .build();

        ItemStack close = ItemBuilder.of(Material.BARRIER)
                .name("&cZamknij")
                .build();

        // FIRST ROW
        inventory.setItem(13, stats);

        // SECOND ROW
        inventory.setItem(19, skills);
        inventory.setItem(20, enchants);
        inventory.setItem(21, fishing);
        inventory.setItem(22, guildInfo);
        inventory.setItem(23, graves);
        inventory.setItem(24, crafting);
        inventory.setItem(25, enderchest);

        // THIRD ROW
        inventory.setItem(29, auction);
        inventory.setItem(30, slimefun);
        inventory.setItem(31, rtp);
        inventory.setItem(32, skins);
        inventory.setItem(33, home);

        // LAST ROW
        inventory.setItem(36, warps);
        inventory.setItem(45, kits);

        inventory.setItem(44, help);
        inventory.setItem(53, discord);

        inventory.setItem(49, close);
    }

    @Override
    public void event(ClickEvent event, Player player) {
        VisualUtils.click(player);
        switch (event.slot()) {
            case 13 -> player.performCommand("staty");

            // SECOND ROW
            case 19 -> player.performCommand("skille");
            case 20 -> player.performCommand("enchanty");
            case 21 -> player.performCommand("rybactwo");
            case 22 -> {
                if (this.plugin.guildManager().playerGuild(player) != null) {
                    this.plugin.inventoryManager().open("management", player);
                } else {
                    player.performCommand("g help");
                }
            }
            case 23 -> player.performCommand("groby");
            case 24 -> player.openWorkbench(null, true);
            case 25 -> player.openInventory(player.getEnderChest());

            // THIRD ROW
            case 29 -> player.performCommand("rynek");
            case 30 -> player.performCommand("sf open_guide");
            case 31 -> {
                player.closeInventory();
                player.performCommand("rtp");
            }
            case 32 -> player.performCommand("skiny");
            case 33 -> {
                player.closeInventory();
                player.performCommand("home");
            }

            // LAST ROW
            case 36 -> player.performCommand("warp");
            case 45 -> player.performCommand("kit");
            case 44 -> {
                player.closeInventory();
                player.performCommand("pomoc");
            }

            case 49 -> player.closeInventory();
        }
    }
}
