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

import me.xneox.guilds.util.gui.api.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private final Map<String, InventoryProvider> inventories = new HashMap<>();

    public InventoryManager(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new InventoryListener(this), plugin);
    }

    public void register(@Nonnull String id, @Nonnull InventoryProvider inventory) {
        this.inventories.put(id, inventory);
    }

    public void open(@Nonnull String id, @Nonnull Player player) {
        InventoryProvider inventory = this.inventories.get(id);
        if (inventory != null) {
            Inventory bukkitInventory = Bukkit.createInventory(player, inventory.size(), inventory.title());
            player.openInventory(bukkitInventory);

            inventory.open(player, bukkitInventory);
        }
    }

    @Nullable
    public InventoryProvider findByName(String name) {
        return this.inventories.values().stream()
                .filter(inventory -> inventory.title().equals(name))
                .findFirst()
                .orElse(null);
    }
}
