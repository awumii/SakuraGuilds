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
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.gui.api.InventoryProvider;
import me.xneox.guilds.util.gui.api.InventorySize;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class InventoryProviderImpl implements InventoryProvider {
    protected final NeonGuilds plugin;
    private final TextComponent title;
    private final int size;

    public InventoryProviderImpl(NeonGuilds plugin, String title, InventorySize size) {
        this.plugin = plugin;
        this.title = LegacyComponentSerializer.legacyAmpersand().deserialize(title);
        this.size = size.slots();
    }

    @Override
    public @NotNull TextComponent title() {
        return this.title;
    }

    @Override
    public int size() {
        return this.size;
    }

    public boolean isBackButton(ItemStack stack) {
        return stack.getType() == Material.PLAYER_HEAD && ChatUtils.plainString(stack.getItemMeta().displayName()).contains("Powrót");
    }
}
