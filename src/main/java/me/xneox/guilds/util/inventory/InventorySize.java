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

/** Multiplies of 9 that can be used as an inventory row size. */
public enum InventorySize {
    SMALLEST(9),
    SMALL(18),
    MEDIUM(27),
    BIG(36),
    HUGE(45),
    BIGGEST(54);

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
