package me.xneox.guilds.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

// Copied from gist i don't remember lol
public final class ItemSerialization {
    /**
     * Converts the player inventory to a Base64 encoded string.
     *
     * @param playerInventory to turn into an array of strings.
     * @return string with serialized Inventory
     */
    public static String serializeInventory(Inventory playerInventory) {
        // This contains contents, armor and offhand (contents are indexes 0 - 35, armor 36 - 39, offhand - 40)
        return serializeStack(playerInventory.getContents());
    }

    /**
     * A method to serialize an {@link ItemStack} array to Base64 String.
     *
     * @param items to turn into a Base64 String.
     * @return Base64 string of the items.
     */
    public static String serializeStack(ItemStack[] items) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(items.length);

            for (ItemStack item : items) {
                if (item != null) {
                    dataOutput.writeObject(item.serializeAsBytes());
                } else {
                    dataOutput.writeObject(null);
                }
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    /**
     * Gets an array of ItemStacks from Base64 string.
     *
     * @param data Base64 string to convert to ItemStack array.
     * @return ItemStack array created from the Base64 string.
     */
    public static ItemStack[] deserializeInventory(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            for (int Index = 0; Index < items.length; Index++) {
                byte[] stack = (byte[]) dataInput.readObject();

                if (stack != null) {
                    items[Index] = ItemStack.deserializeBytes(stack);
                } else {
                    items[Index] = null;
                }
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
