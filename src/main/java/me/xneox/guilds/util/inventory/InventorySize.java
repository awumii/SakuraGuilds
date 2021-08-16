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

package me.xneox.guilds.util.inventory;

/** TODO dont use this shit xd */
@Deprecated
public enum InventorySize {
    SMALLEST(1),
    SMALL(2),
    MEDIUM(3),
    BIG(4),
    HUGE(5),
    BIGGEST(6);

    private final int rows;

    InventorySize(int slots) {
        this.rows = slots;
    }

    /**
     * @return Amount of the columns in this size type.
     */
    public int rows() {
        return this.rows;
    }
}
