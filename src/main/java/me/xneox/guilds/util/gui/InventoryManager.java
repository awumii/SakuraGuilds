/*
 * InventoryAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * InventoryAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.xneox.guilds.util.gui;

import me.xneox.guilds.util.gui.basic.CustomInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private final Map<String, CustomInventory> inventories = new HashMap<>();

    public InventoryManager(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new InventoryListener(this), plugin);
        Bukkit.getScheduler().runTaskTimer(plugin, new InventoryTask(this), 20L, 40L);
    }

    /**
     * Registering a new Inventory.
     *
     * @param inventory Implementation of the {@link CustomInventory}.
     */
    public void register(String id, CustomInventory inventory) {
        this.inventories.put(id, inventory);
    }

    /**
     * Opens the specified inventory.
     * Throws {@link IllegalArgumentException} if the inventory with the provided ID does not exist.
     *
     * @param id String ID of the inventory.
     * @param player The player to open the GUI.
     */
    public void open(String id, Player player) {
        CustomInventory inventory = this.inventories.get(id);
        if (inventory == null) {
            throw new IllegalArgumentException("Can't find an Inventory with the provided ID '" + id + "'");
        }

        this.open(inventory, player);
    }

    public void open(CustomInventory inventory, Player player) {
        Inventory bukkitInventory = Bukkit.createInventory(player, inventory.getSize(), inventory.getTitle());
        player.openInventory(bukkitInventory);
        inventory.onOpen(player, bukkitInventory);
        player.updateInventory();
    }

    /**
     * Searches for the {@link CustomInventory} from the item name.
     *
     * @param name Inventory title of the searched GUI.
     * @return The searched {@link CustomInventory}, or null if not found.
     */
    public CustomInventory findByName(String name) {
        return this.inventories.values().stream()
                .filter(inventory -> inventory.getTitle().equals(name))
                .findFirst()
                .orElse(null);
    }
}
