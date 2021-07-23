package me.xneox.guilds.util.gui.api;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class ClickEvent {
    private final ItemStack item;
    private final int slot;
    private final ClickType clickType;

    public ClickEvent(ItemStack item, int slot, ClickType clickType) {
        this.item = item;
        this.slot = slot;
        this.clickType = clickType;
    }

    @Nonnull
    public ItemStack item() {
        return this.item;
    }

    public int slot() {
        return this.slot;
    }

    public ClickType type() {
        return this.clickType;
    }
}
