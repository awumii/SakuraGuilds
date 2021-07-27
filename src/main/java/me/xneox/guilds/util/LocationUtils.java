package me.xneox.guilds.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public final class LocationUtils {
    public static final String EMPTY = "world:0:0:0:0:0";

    private LocationUtils() {
    }

    public static String toString(Location location) {
        return location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ() + ":" + location.getYaw() + ":" + location.getPitch();
    }

    public static String toSimpleString(Location location) {
        // Te (int) są po to, żeby nie było 10 liczb po kropce :p
        return "X: " + (int) location.getX() + ", Y: " + (int) location.getY() + ", Z: " + (int) location.getZ();
    }

    public static Location fromString(String string) {
        String[] split = string.split(":");
        String world = split[0];
        double x = Double.parseDouble(split[1]);
        double y = Double.parseDouble(split[2]);
        double z = Double.parseDouble(split[3]);
        float yaw = Float.parseFloat(split[4]);
        float pitch = Float.parseFloat(split[5]);

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public static boolean equalsSoft(Location first, Location second) {
        return first.getBlockX() == second.getBlockX()
                && first.getBlockZ() == second.getBlockZ()
                && first.getBlockY() == second.getBlockY();
    }

    public static List<Location> sphere(Location sphereCenter, int radius, int height, boolean hollow, boolean sphere, int plusY) {
        int centerX = sphereCenter.getBlockX();
        int centerY = sphereCenter.getBlockY();
        int centerZ = sphereCenter.getBlockZ();

        List<Location> blocks = new ArrayList<>();
        for (int x = centerX - radius; x <= centerX + radius; x++) {
            for (int z = centerZ - radius; z <= centerZ + radius; z++) {
                for (int y = (sphere ? centerY - radius : centerY); y < (sphere ? centerY + radius : centerY + height); y++) {
                    double dist = (centerX - x) * (centerX - x) + (centerZ - z) * (centerZ - z) + (sphere ? (centerY - y) * (centerY - y) : 0);

                    if (dist < radius * radius && !(hollow && dist < (radius - 1) * (radius - 1))) {
                        blocks.add(new Location(sphereCenter.getWorld(), x, y + plusY, z));
                    }
                }
            }
        }
        return blocks;
    }
}
