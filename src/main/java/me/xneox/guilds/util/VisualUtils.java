package me.xneox.guilds.util;

import org.bukkit.Chunk;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public final class VisualUtils {
  public static void sound(Player player, Sound sound) {
    player.playSound(player.getLocation(), sound, 1f, 1f);
  }

  public static void drawBorderAtChunk(Chunk chunk, Player player) {
    int minX = chunk.getX() * 16;
    int minZ = chunk.getZ() * 16;
    int minY = player.getLocation().getBlockY();

    for (int x = minX; x < minX + 17; x++) {
      for (int y = minY; y < minY + 1; y++) {
        for (int z = minZ; z < minZ + 17; z++) {
          player.spawnParticle(Particle.VILLAGER_HAPPY, minX, y, z, 1);
          player.spawnParticle(Particle.VILLAGER_HAPPY, x, y, minZ, 1);
          player.spawnParticle(Particle.VILLAGER_HAPPY, minX + 16, y, z, 1);
          player.spawnParticle(Particle.VILLAGER_HAPPY, x, y, minZ + 17, 1);
        }
      }
    }
  }
}
