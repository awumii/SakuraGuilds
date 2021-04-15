package me.xneox.guilds.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class InventoryUtils {
    public static void reduceHandItem(Player player) {
        ItemStack stack = player.getInventory().getItemInMainHand();
        if (stack.getAmount() == 1) {
            player.getInventory().setItemInMainHand(null);
        } else {
            stack.setAmount(stack.getAmount() - 1);
        }
    }

    public static void drawBorder(Inventory inventory) {
        ItemStack black = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("&r").build();
        ItemStack other = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("&r").build();
        inventory.setItem(0, black);
        inventory.setItem(1, black);
        inventory.setItem(2, black);

        inventory.setItem(3, other);
        inventory.setItem(4, other);
        inventory.setItem(5, other);

        inventory.setItem(6, black);
        inventory.setItem(7, black);
        inventory.setItem(8, black);
        inventory.setItem(9, black);

        inventory.setItem(17, black);

        inventory.setItem(18, other);
        inventory.setItem(27, other);
        inventory.setItem(26, other);
        inventory.setItem(35, other);

        inventory.setItem(36, black);
        inventory.setItem(44, black);

        inventory.setItem(45, black);
        inventory.setItem(46, black);

        inventory.setItem(52, black);
        inventory.setItem(53, black);

        inventory.setItem(47, black);

        inventory.setItem(48, other);
        inventory.setItem(49, other);
        inventory.setItem(50, other);

        inventory.setItem(51, black);
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
        if (type == null || inventory == null || amount <= 0) {
            return;
        }

        if (amount == Integer.MAX_VALUE) {
            inventory.remove(type);
            return;
        }

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
