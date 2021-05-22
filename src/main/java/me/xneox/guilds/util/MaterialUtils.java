package me.xneox.guilds.util;

import org.bukkit.Material;

public final class MaterialUtils {
    public static boolean isWood(Material material) {
        return material.name().endsWith("_WOOD") || material.name().endsWith("_LOG");
    }

    public static boolean isStone(Material material) {
        return material == Material.STONE || material == Material.COBBLESTONE;
    }
}
