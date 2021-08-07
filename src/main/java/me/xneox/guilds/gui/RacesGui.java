package me.xneox.guilds.gui;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.User;
import me.xneox.guilds.enums.Race;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.inventory.InventorySize;
import me.xneox.guilds.util.inventory.InventoryUtils;
import me.xneox.guilds.util.inventory.ItemBuilder;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class RacesGui implements InventoryProvider {
  public static final SmartInventory INVENTORY = SmartInventory.builder()
      .title("Menu wyboru rasy")
      .size(InventorySize.BIG.rows(), 9)
      .build();

  @Override
  public void init(Player player, InventoryContents contents) {
    contents.fillRect(1, 0, 0, 8, InventoryUtils.GLASS);

    User user = SakuraGuildsPlugin.get().userManager().user(player);
    contents.set(0, 3, ClickableItem.empty(ItemBuilder.of(Material.OAK_SIGN)
        .name("&aInformacje:")
        .lore("&7Wybrana rasa: " + user.race().title())
        .lore("")
        .lore("&7Każda rasa zwiększa jedną &fstatystykę postaci.")
        .lore("&7Nie dają one żadnych negatywnych efektów.")
        .build()));

    if (SakuraGuildsPlugin.get().cooldownManager().hasCooldown(player, "race_change")) {
      contents.set(0, 5, ClickableItem.empty(ItemBuilder.of(Material.CLOCK)
          .name("&cNie możesz zmienić swojej rasy.")
          .lore("&7Poczekaj jeszcze: &c" + SakuraGuildsPlugin.get().cooldownManager().getRemaining(player, "race_change"))
          .build()));
    } else {
      contents.set(0, 5, ClickableItem.empty(ItemBuilder.of(Material.CLOCK)
          .name("&aMożesz zmienić swoją rasę.")
          .lore("&7Po wybraniu rasy, nie będziesz mógł")
          .lore("&7jej zmienić przez następne &f24h")
          .build()));
    }

    contents.set(2, 1, build(player, Race.HUMAN, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNjMzdiZmM0ZDFhYzJmZmU1M2ZkNGFkMjRhYTI3OWQ2NzE2NDJhNWEyYWEzMWYyZWRhMDFhMThhOTZjIn19fQ=="));
    contents.set(3, 2, build(player, Race.ELF, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzVhZjI3NTAxZmE4MTFlMjM4NTNkOTBhYzQzODNmODc2NTU0MjJmZTVhZTg0MjRlMmNjNDkzNTA3MzJkZmMifX19"));
    contents.set(2, 3, build(player, Race.DWARF, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA0ODFlNTJkYWYwYjUzNjM0NjljMGYyY2M5YzhiYTIyYzZiYjgyNWUxMjYxNzhlOTkyNGI0Mjg0OTk3NTYifX19"));
    contents.set(3, 4, build(player, Race.GOBLIN, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGJmNTZjNjU0MWMxMjY2ZmZjNDYzNTEwYmRiNTVhZWY5MzE1YWY1NDg4OThjZjVkM2NiYTFiNWI0YzAxIn19fQ=="));
    contents.set(2, 5, build(player, Race.ORC, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmI5MDI2M2Q2ODc4YTgyNTVkZTlhZThjNWI3NTU5YmJkMTU4NjBjZGE1MzliZWM1ZTM4Y2UzNTdhYzBlZGU3OCJ9fX0="));
    contents.set(3, 5, build(player, Race.MORG, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWI3M2NkNDEzZDgxZThjM2NlZTQ2ZmU4YTgzMjI1MjY1MmRjMzM1ODRkZGU0ZGRkZjNjYTgzNmRjZDE3NGUifX19"));
    contents.set(2, 7, build(player, Race.NONE, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzM4YWIxNDU3NDdiNGJkMDljZTAzNTQzNTQ5NDhjZTY5ZmY2ZjQxZDllMDk4YzY4NDhiODBlMTg3ZTkxOSJ9fX0="));
  }

  @Override
  public void update(Player player, InventoryContents contents) {}

  private ClickableItem build(Player player, Race race, String skullTexture) {
    return ClickableItem.of(ItemBuilder.skull(skullTexture)
        .name(race.title())
        .lore(race.description())
        .build(), event -> switchRace(race, player));
  }
  
  private void switchRace(Race race, Player player) {
    player.closeInventory();
    if (SakuraGuildsPlugin.get().cooldownManager().hasCooldown(player, "race_change")) {
      ChatUtils.sendMessage(player, "&cRasę możesz zmienić dopiero za: &6" + SakuraGuildsPlugin.get().cooldownManager().getRemaining(player, "race_change"));
      VisualUtils.sound(player, Sound.ENTITY_VILLAGER_NO);
      return;
    }

    if (race != Race.NONE) {
      SakuraGuildsPlugin.get().cooldownManager().add(player, "race_change", 1, TimeUnit.SECONDS);
    }

    VisualUtils.sound(player, Sound.ENTITY_WITHER_DEATH);
    ChatUtils.sendMessage(player, "&7Pomyślnie ustawiono twoją rasę na " + race.title());
    ChatUtils.sendTitle(player, race.title(), "&7Wybór twojej rasy został zaakceptowany...");

    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sk modifier remove " + player.getName() + " race");
    User user = SakuraGuildsPlugin.get().userManager().user(player);
    user.race(race);

    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
        ChatUtils.format("sk modifier add {0} {1} race {2}", player.getName(), race.stat().name(), race.multiplier()));
  }
}