package me.xneox.guilds.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class InventoryUtils {
    public static final ItemStack BLACK_GLASS = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("&r").build();

    public static void reduceHandItem(Player player) {
        ItemStack stack = player.getInventory().getItemInMainHand();
        if (stack.getAmount() == 1) {
            player.getInventory().setItemInMainHand(null);
        } else {
            stack.setAmount(stack.getAmount() - 1);
        }
    }

    public static void drawBorder(Inventory inventory) {
        inventory.setItem(0, BLACK_GLASS);
        inventory.setItem(1, BLACK_GLASS);
        inventory.setItem(2, BLACK_GLASS);

        inventory.setItem(3, BLACK_GLASS);
        inventory.setItem(4, BLACK_GLASS);
        inventory.setItem(5, BLACK_GLASS);

        inventory.setItem(6, BLACK_GLASS);
        inventory.setItem(7, BLACK_GLASS);
        inventory.setItem(8, BLACK_GLASS);
        inventory.setItem(9, BLACK_GLASS);

        inventory.setItem(17, BLACK_GLASS);

        inventory.setItem(18, BLACK_GLASS);
        inventory.setItem(27, BLACK_GLASS);
        inventory.setItem(26, BLACK_GLASS);
        inventory.setItem(35, BLACK_GLASS);

        inventory.setItem(36, BLACK_GLASS);
        inventory.setItem(44, BLACK_GLASS);

        inventory.setItem(45, BLACK_GLASS);
        inventory.setItem(46, BLACK_GLASS);

        inventory.setItem(52, BLACK_GLASS);
        inventory.setItem(53, BLACK_GLASS);

        inventory.setItem(47, BLACK_GLASS);

        inventory.setItem(48, BLACK_GLASS);
        inventory.setItem(49, BLACK_GLASS);
        inventory.setItem(50, BLACK_GLASS);

        inventory.setItem(51, BLACK_GLASS);
    }

    public static void fillInventory(Inventory inventory, Material material) {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilder(material).setName("&8 ").build());
        }
    }

    public static void removeItems(Player player, Material material, int amount) {
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack.getType() == material) {
                if (itemStack.getAmount() < amount) {
                    player.getInventory().removeItem(itemStack);
                } else {
                    itemStack.setAmount(itemStack.getAmount() - amount);
                }
            }
        }
    }

    public static void removeItems(Inventory inventory, Material type, int amount) {
        inventory.removeItem(new ItemStack(type, amount));
    }

    public static int getAmount(Player player, Material material, short durability) {
        int sum = 0;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack.getType() == material && itemStack.getDurability() == durability) {
                int amount = itemStack.getAmount();
                sum += amount;
            }
        }
        return sum;
    }

    public static void giveOrDropItem(Player player, ItemStack stack) {
        giveOrDropItem(player, stack, 1);
    }

    public static void giveOrDropItem(Player player, ItemStack stack, int amount) {
        for (int i = 0; i < amount; i++) {
            if (isFull(player.getInventory())) {
                player.getWorld().dropItem(player.getLocation(), stack);
            } else {
                player.getInventory().addItem(stack);
            }
        }
    }

    public static boolean isFull(Inventory inventory) {
        return inventory.firstEmpty() == -1;
    }

    private InventoryUtils() {}
}
