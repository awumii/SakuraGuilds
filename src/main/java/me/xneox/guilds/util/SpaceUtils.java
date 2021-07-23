package me.xneox.guilds.util;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public final class SpaceUtils {
    private SpaceUtils() {
    }

    public static List<Location> sphere(Location sphereCenter, int radius, int height, boolean hollow, boolean sphere, int plusY) {
        return mapSphereCoordinates(sphereCenter, radius, height, plusY, hollow, sphere, ArrayList::new, Location::new);
    }

    private static <T, C extends Collection<T>> C mapSphereCoordinates(Location sphereCenter,
                                                                       int radius, int height, int plusY,
                                                                       boolean hollow, boolean sphere,
                                                                       Supplier<C> collectionSupplier,
                                                                       QuadFunction<World, Integer, Integer, Integer, T> coordinateMapper) {

        C result = collectionSupplier.get();

        World world = sphereCenter.getWorld();
        int centerX = sphereCenter.getBlockX();
        int centerY = sphereCenter.getBlockY();
        int centerZ = sphereCenter.getBlockZ();

        for (int x = centerX - radius; x <= centerX + radius; x++) {
            for (int z = centerZ - radius; z <= centerZ + radius; z++) {
                for (int y = (sphere ? centerY - radius : centerY); y < (sphere ? centerY + radius : centerY + height); y++) {
                    double dist = (centerX - x) * (centerX - x) + (centerZ - z) * (centerZ - z) + (sphere ? (centerY - y) * (centerY - y) : 0);

                    if (dist < radius * radius && !(hollow && dist < (radius - 1) * (radius - 1))) {
                        result.add(coordinateMapper.apply(world, x, y + plusY, z));
                    }
                }
            }
        }

        return result;
    }
}
