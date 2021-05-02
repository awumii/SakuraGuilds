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

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.util.gui.basic.Clickable;
import me.xneox.guilds.util.gui.basic.CustomInventory;
import org.bukkit.ChatColor;

public abstract class ClickableInventory implements CustomInventory, Clickable {
    protected final NeonGuilds plugin;
    private final String title;
    private final int size;

    public ClickableInventory(NeonGuilds plugin, String title, InventorySize size) {
        this.plugin = plugin;
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.size = size.getSize();
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getSize() {
        return this.size;
    }
}
