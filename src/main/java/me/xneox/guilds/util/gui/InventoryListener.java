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

import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.api.InventoryProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {
    private final InventoryManager manager;

    public InventoryListener(InventoryManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = ChatUtils.plainString(event.getView().title());
        ItemStack item = event.getCurrentItem();

        if (event.getClickedInventory() == null || item == null || item.getItemMeta() == null) {
            return;
        }

        InventoryProvider inventory = this.manager.findByName(title);
        if (inventory != null) {
            ClickEvent clickEvent = new ClickEvent(item, event.getSlot(), event.getClick());
            inventory.event(clickEvent, (Player) event.getWhoClicked());

            event.setCancelled(true);
        }
    }
}
