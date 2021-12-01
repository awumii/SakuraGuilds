package me.xneox.guilds.util;

import me.xneox.guilds.hook.HookUtils;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NexusBuilder {

  public static void buildNexus(@NotNull Location location, @NotNull Player player) {
    switch (ConfigManager.config().guildCreation().method()) {
      case SCHEMATIC -> buildSchematic(location, player);
      case UNDERGROUND_SPHERE_HOLLOW -> buildUndergroundHollowSphere(location);
    }
  }

  private static void buildSchematic(@NotNull Location location, @NotNull Player player) {
    if (location.getY() > ConfigManager.config().guildCreation().maxSchematicHeight()) {
      ChatUtils.sendMessage(player, "&cNie możesz zakładać gildii powyżej Y=90");
      return;
    }

    location.setY(location.getY() + ConfigManager.config().guildCreation().placeOffset());
    if (!HookUtils.pasteSchematic("nexus.schematic", location)) {
      ChatUtils.sendMessage(player, "&cWystąpił błąd uniemożliwiający na wygenerowanie nexusa. Skontaktuj sie z administracja.");
    }
  }

  private static void buildUndergroundHollowSphere(@NotNull Location location) {
    int radius = ConfigManager.config().guildCreation().sphereRadius();
    int centerX = location.getBlockX();
    int centerY = location.getBlockY();
    int centerZ = location.getBlockZ();

    for (int x = centerX - radius; x <= centerX + radius; x++) {
      for (int z = centerZ - radius; z <= centerZ + radius; z++) {
        for (int y = centerY - radius; y < centerY + radius; y++) {
          double dist = (centerX - x) * (centerX - x) + (centerZ - z) * (centerZ - z) + (centerY - y) * (centerY - y);
          if (dist < radius * radius && !(dist < (radius - 1) * (radius - 1))) {
            var block = location.getWorld().getBlockAt(x, y, z);
            block.setType(Material.AIR);
          }
        }
      }
    }
  }

  public enum Method {
    SCHEMATIC, UNDERGROUND_SPHERE_HOLLOW
  }
}
