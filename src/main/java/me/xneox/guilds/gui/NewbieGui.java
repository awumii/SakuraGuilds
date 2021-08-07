package me.xneox.guilds.gui;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.inventory.InventoryUtils;
import me.xneox.guilds.util.inventory.ItemBuilder;
import me.xneox.guilds.util.inventory.InventorySize;
import org.bukkit.entity.Player;

public class NewbieGui implements InventoryProvider {
  public static final SmartInventory INVENTORY = SmartInventory.builder()
      .title("Menu Nowicjusza")
      .size(InventorySize.MEDIUM.rows(), 9)
      .build();

  @Override
  public void init(Player player, InventoryContents contents) {
    contents.fill(InventoryUtils.GLASS);

    User user = SakuraGuildsPlugin.get().userManager().user(player);
    contents.set(1, 2, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWI3M2NkNDEzZDgxZThjM2NlZTQ2ZmU4YTgzMjI1MjY1MmRjMzM1ODRkZGU0ZGRkZjNjYTgzNmRjZDE3NGUifX19")
        .name("&aWybrana rasa: " + user.race().title())
        .lore("")
        .lore("&7Kliknij, zmienić rasę.")
        .build(), event -> player.performCommand("g race")));

    contents.set(1, 3, ClickableItem.empty(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTJlYzlhOGE5MGQ5MGM3ODY1MWFmMmY5NjIwMTUxMTFmY2JmMTFhYzg3MzhmNTJiOGUyNGRhODYyYTM4NzFiYSJ9fX0=")
        .name("&cNie posiadasz gildii!")
        .lore("")
        .lore("&7Dołącz do gildii aby wyświetlić panel.")
        .build()));

    contents.set(1, 5, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzEwYTI2YTg3NTQ5ZTA2ZTA2NjlmNmM0OGY2MTQ3OGQyMzAyNWI5MWQ5MGVhMmM0YjlmODA0YjlmNzk2ZDA0YyJ9fX0=")
        .name("&6Lista komend")
        .lore("")
        .lore("&7Kliknij, aby wyświetlić pomoc.")
        .build(), event -> {
          player.closeInventory();
          player.performCommand("g help");
        }));
  }

  @Override
  public void update(Player player, InventoryContents contents) {}
}
