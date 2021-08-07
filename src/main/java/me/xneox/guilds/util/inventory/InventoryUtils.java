package me.xneox.guilds.util.inventory;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class InventoryUtils {
  public static final ItemStack BLACK_GLASS = ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).name("&r").build();
  public static final ClickableItem GLASS = ClickableItem.empty(BLACK_GLASS);

  /**
   * Inserts a back button to an inventory, after clicking it the specified
   * inventory will be shown to the player, or it will be closed if it is null.
   *
   * @param row Vertical position of the button.
   * @param column Horizontal position of the button.
   * @param contents The inventory to insert the button to.
   * @param switchTo Which inventory should be opened after interaction.
   */
  public static void insertBackButton(int row, int column, @NotNull InventoryContents contents, @Nullable SmartInventory switchTo) {
    ItemStack close = ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
        .name("&cPowrót")
        .lore("&7Cofnij do menu gildii.")
        .build();

    contents.set(row, column, ClickableItem.of(close, event -> {
      if (switchTo != null) {
        switchTo.open((Player) event.getWhoClicked());
      } else {
        event.getWhoClicked().closeInventory();
      }
    }));
  }

  /**
   * Removes a specified amount of a certain material from the inventory.
   *
   * @param inventory Inventory from which the items will be removed.
   * @param type Type of the removed item.
   * @param amount Amount of the items to remove.
   */
  public static void removeItems(@NotNull Inventory inventory, Material type, int amount) {
    inventory.removeItem(new ItemStack(type, amount));
  }
}
