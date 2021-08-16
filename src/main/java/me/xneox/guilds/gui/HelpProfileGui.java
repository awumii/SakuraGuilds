package me.xneox.guilds.gui;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.clip.placeholderapi.PlaceholderAPI;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.HookUtils;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.inventory.InventorySize;
import me.xneox.guilds.util.inventory.InventoryUtils;
import me.xneox.guilds.util.inventory.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class HelpProfileGui implements InventoryProvider {
  public static final SmartInventory INVENTORY = SmartInventory.builder()
      .title("Menu Gracza")
      .size(InventorySize.BIGGEST.rows(), 9)
      .provider(new HelpProfileGui())
      .build();

  @Override
  public void init(Player player, InventoryContents contents) {
    contents.fill(InventoryUtils.GLASS);
    InventoryUtils.insertBackButton(6, 4, contents, null);
    VisualUtils.sound(player, Sound.BLOCK_AMETHYST_BLOCK_STEP);

    contents.set(1, 4, ClickableItem.of(ItemBuilder.skullOf(player.getName())
        .name("      &6" + player.getName() + " &7(&fPoziom &e" + HookUtils.getAureliumLevel(player) + "✫&7)")
        .lore(PlaceholderAPI.setPlaceholders(player, " &fSiła: &4\uD83D\uDDE1%aureliumskills_strength_int%"))
        .lore(PlaceholderAPI.setPlaceholders(player, " &fZdrowie: &c❤%aureliumskills_health_int%"))
        .lore(PlaceholderAPI.setPlaceholders(player, " &fRegeneracja: &6❥%aureliumskills_regeneration_int%"))
        .lore(PlaceholderAPI.setPlaceholders(player, " &fSzczęście: &2☘%aureliumskills_luck_int%"))
        .lore(PlaceholderAPI.setPlaceholders(player, " &fInteligencja: &9✿%aureliumskills_wisdom_int%"))
        .lore(PlaceholderAPI.setPlaceholders(player, " &fTwardość: &5✦%aureliumskills_toughness_int%"))
        .lore("")
        .lore("&eKliknij, aby zobaczyć szczegóły.")
        .build(), event -> player.performCommand("staty")));

    contents.set(2, 1, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWE4YzY4NTE0ZDhjODE5ZTY4ZWI3NWI1OTY0NDVkODhiMzY3YWUzNTFiYWE5OTgzYmM2OWNmNmI1MjBmMzk4NSJ9fX0=")
        .name("&6Umiejętności")
        .lore("&7Zobacz poziomy twoich umiejętności,")
        .lore("&7oraz bonusy za zdobywanie doświadczenia.")
        .lore("")
        .lore("&eKliknij aby wyświetlić")
        .build(), event -> player.performCommand("skille")));

    contents.set(2, 2, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjYyNjUxODc5ZDg3MDQ5OWRhNTBlMzQwMzY4MDBkZGZmZDUyZjNlNGUxOTkzYzVmYzBmYzgyNWQwMzQ0NmQ4YiJ9fX0=")
        .name("&dKsięga Zaklęć")
        .lore("&7Na serwerze znajduje się ponad &5200")
        .lore("&7nowych enchantów, informacje o nich")
        .lore("&7znajdziesz właśnie w tym miejscu.")
        .lore("")
        .lore("&eKliknij, aby zobaczyć zaklęcia.")
        .build(), event -> player.performCommand("enchanty")));

    contents.set(2, 3, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDgwNjg2Zjg2ODUzMDBkOGYxNzA5NDEzZDc1NDQyMGZjZTk4ODYzNmRmNjc3NTVmYjg1YmQ1MTdlZjA0N2MwMSJ9fX0=")
        .name("&6Dziennik Wędkarski")
        .lore("&7Wyświetl statystyki, oraz rodzaje")
        .lore("&7ryb i przedmiotów które możesz wyłowić.")
        .lore("")
        .lore("&eKliknij, aby otworzyć.")
        .build(), event -> player.performCommand("rybactwo")));

    Guild guild = SakuraGuildsPlugin.get().guildManager().playerGuild(player);
    contents.set(2, 4, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQxNWFhY2I3MjEzYzgzMTFlZWQ3YmFmMzdlYmI1OGE1ZjRiOTI1NjMxN2Q4NDU4ZDE1ZDMzN2E3NGU0YmU2In19fQ==")
        .name("&6Gildia")
        .lore("&7Dzięki gildiom, możesz zajmować teren")
        .lore("&7oraz współpracować z innymi graczami.")
        .lore("&7Oczywiście możesz też grać sam...")
        .lore("")
        .lore(guild != null ? " &a\uD83C\uDFF9 Należysz do " + guild.name() : " &c✘ Nie posiadasz jeszcze gildiI!")
        .lore("")
        .lore(guild != null ? "&eKliknij, aby zarządzać gildią." : "&eKliknij, aby zobaczyć pomoc.")
        .build(), event -> player.performCommand("g")));

    contents.set(2, 5, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2YzYmU2NDAxNjczNmJlNDRiMWQ1YTVmM2FkYWI2ZDRjMDQzNzgyYzE3ZGQyOWMzYjhjNGNiNmU3M2Y5ODk4In19fQ==")
        .name("&6Groby")
        .lore("&7Zginąłeś? Jeśli nie było przy tobie żadnego")
        .lore("&7gracza, to spokojnie! W miejscu twojej śmierci")
        .lore("&7pojawia się grób z twoimi przedmiotami oraz expem.")
        .lore("")
        .lore("&eKliknij, aby zobaczyć aktywne groby.")
        .build(), event -> player.performCommand("groby")));

    contents.set(2, 6, ClickableItem.of(ItemBuilder.of(Material.CRAFTING_TABLE)
        .name("&6Stół Rzemieślniczy")
        .lore("&7Kliknij, aby otworzyć menu")
        .lore("&7stołu rzemieślniczego.")
        .build(), event -> player.openWorkbench(null, true)));

    contents.set(2, 7, ClickableItem.of(ItemBuilder.of(Material.ENDER_CHEST)
        .name("&6Enderchest")
        .lore("&7Kliknij, aby otworzyć enderchest.")
        .build(), event -> player.openInventory(player.getEnderChest())));

    contents.set(3, 2, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzFkODI3YTVkZWNiMGFlNzMwYWJiNjk2MTc3NzZlMTg5NGYyYmRiNDY5Njg1NDA0MzMxMTVkMzY4OGZiYWMzOCJ9fX0=")
        .name("&6Rynek")
        .lore("&7Na rynku, każdy gracz może wystawiać")
        .lore("&7dowolne przedmioty za własną cenę.")
        .lore("")
        .lore(" &a&l! &aWpisz &n/rynek sell <cena>&a aby wystawić przedmiot!")
        .lore("")
        .lore("&eKliknij, aby otworzyć rynek.")
        .build(), event -> player.performCommand("rynek")));

    contents.set(3, 3, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDkzNGE5ZjVhYjE3ODlhN2Q4ZGQ5NmQzMjQ5M2NkYWNmZjU3N2Q4YzgxZTdiMjM5MTdkZmYyZTMyYmQwYmMxMCJ9fX0=")
        .name("&6Podręcznik Slimefun")
        .lore("&7Slimefun dodaje &a900+ przedmiotów, maszyn &7itd...")
        .lore("&7Podręcznik możesz otrzymać komendą &a/sf guide")
        .lore("")
        .lore("&eKliknij, aby otworzyć podręcznik slimefun.")
        .build(), event -> player.performCommand("sf open_guide")));

    contents.set(3, 4, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19")
        .name("&6Losowy Teleport")
        .lore("&7Przeteleportuj się w losowe miejsce.")
        .build(), event -> {
          player.closeInventory();
          player.performCommand("rtp");
        }));

    contents.set(3, 5, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzgzZGI4NmJiODZkYzJlYzQ5NGUyZmZjYTc3NzY1ODEwZWQxMWE1MmVmZWY0ZTVjODUyNTJlYzM5YTI2YzAifX19")
        .name("&6Skiny")
        .lore("&7Zobacz menu skinów które możesz ustawić.")
        .lore("")
        .lore(" &a&l! &aUstaw własnego skina za pomocą &n/skin")
        .build(), event -> player.performCommand("skiny")));

    contents.set(3, 6, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y3Y2RlZWZjNmQzN2ZlY2FiNjc2YzU4NGJmNjIwODMyYWFhYzg1Mzc1ZTlmY2JmZjI3MzcyNDkyZDY5ZiJ9fX0=")
        .name("&6Dom")
        .lore("&7Aby ustawić dom, użyj komendy &a/sethome")
        .lore("")
        .lore("&eKliknij, aby przetelportować.")
        .build(), event -> {
          player.closeInventory();
          player.performCommand("home");
        }));

    contents.set(4, 0, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFhOGZjOGRlNjQxN2I0OGQ0OGM4MGI0NDNjZjUzMjZlM2Q5ZGE0ZGJlOWIyNWZjZDQ5NTQ5ZDk2MTY4ZmMwIn19fQ==")
        .name("&6Warpy")
        .lore("&7Zobacz przydatne miejsca, do których")
        .lore("&7możesz się przeteleportować.")
        .build(), event -> {
          player.closeInventory();
          player.performCommand("warp");
        }));

    contents.set(4, 8, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzA1YzZjZmU2Yzc2Y2RhN2YxM2FmNDc3YWVhNDc5NjNjN2ZkMzVlNjk3MzY5ZWI5MTFjNDEyMWQ4ZmNjNCJ9fX0=")
        .name("&6Lista Komend")
        .lore("&7Wyświetl wszystkie dostępne komendy.")
        .build(), event -> {
          player.closeInventory();
          player.performCommand("pomoc");
        }));

    contents.set(5, 0, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzViMTE2ZGM3NjlkNmQ1NzI2ZjEyYTI0ZjNmMTg2ZjgzOTQyNzMyMWU4MmY0MTM4Nzc1YTRjNDAzNjdhNDkifX19")
        .name("&6Zestawy")
        .lore("&7Zobacz dostępne zestawy przedmiotów.")
        .build(), event -> {
          player.closeInventory();
          player.performCommand("kit");
        }));

    contents.set(5, 8, ClickableItem.empty(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWQ4MzNiNTE1NjY1NjU2NThmOTAxMWRlODc4NGU5MGMxYWQ5YmE1ZDMzMzdmOGMwNjkyMTNiYmRlZTk4NjUyMyJ9fX0=")
        .name("&5Discord")
        .lore("&7Wejdź na naszego discorda!")
        .lore("")
        .lore("   &d&nwww.dronizja.pl/discord")
        .build()));

    contents.set(5, 1, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWI3M2NkNDEzZDgxZThjM2NlZTQ2ZmU4YTgzMjI1MjY1MmRjMzM1ODRkZGU0ZGRkZjNjYTgzNmRjZDE3NGUifX19")
        .name("&aWybrana rasa: " + SakuraGuildsPlugin.get().userManager().user(player).race().display())
        .lore("")
        .lore("&7Kliknij, aby zmienić rasę.")
        .build(), event -> player.performCommand("g race")));
  }

  @Override
  public void update(Player player, InventoryContents contents) {}
}
