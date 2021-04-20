package me.xneox.guilds.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class LocationUtils {
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

    private LocationUtils() {}
}
