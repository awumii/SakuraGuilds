package me.xneox.guilds.util;

import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public final class LocationUtils {
  @NotNull
  public static String deserialize(@NotNull Location location) {
    return ChatUtils.join(':',
        location.getWorld().getName(),
        location.getX(),
        location.getY(),
        location.getZ(),
        location.getYaw(),
        location.getPitch());
  }

  @NotNull
  public static String legacyDeserialize(@NotNull Location location) {
    return ChatUtils.join(',',
        location.getWorld().getName(),
        (int) location.getX(),
        (int) location.getY(),
        (int) location.getZ());
  }

  @NotNull
  public static Location serialize(@NotNull String string) {
    String[] split = string.split(":");
    String world = split[0];
    double x = Double.parseDouble(split[1]);
    double y = Double.parseDouble(split[2]);
    double z = Double.parseDouble(split[3]);
    float yaw = Float.parseFloat(split[4]);
    float pitch = Float.parseFloat(split[5]);

    return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
  }

  public static boolean equalsSoft(@NotNull Location first, @NotNull Location second) {
    return first.getBlockX() == second.getBlockX()
        && first.getBlockZ() == second.getBlockZ()
        && first.getBlockY() == second.getBlockY();
  }

  // todo needs config
  @Deprecated
  public static boolean isWorldNotAllowed(@NotNull Location location) {
    return !location.getWorld().getName().startsWith("world");
  }
}
