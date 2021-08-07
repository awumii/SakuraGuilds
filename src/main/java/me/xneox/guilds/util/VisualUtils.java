package me.xneox.guilds.util;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import java.time.Duration;
import java.util.Arrays;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.text.ChatUtils;
import me.xneox.guilds.util.text.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class VisualUtils {
  public static void sound(Player player, Sound sound) {
    player.playSound(player.getLocation(), sound, 1f, 1f);
  }

  public static void click(Player player) {
    sound(player, Sound.BLOCK_WOODEN_BUTTON_CLICK_ON);
  }

  public static void drawBorderAtChunk(Chunk chunk, Player player) {
    int minX = chunk.getX() * 16;
    int minZ = chunk.getZ() * 16;
    int minY = player.getLocation().getBlockY();

    for (int x = minX; x < minX + 17; x++) {
      for (int y = minY; y < minY + 1; y++) {
        for (int z = minZ; z < minZ + 17; z++) {
          player.spawnParticle(
              Particle.REDSTONE, minX, y, z, 1, new Particle.DustOptions(Color.RED, 1));
          player.spawnParticle(
              Particle.REDSTONE, x, y, minZ, 1, new Particle.DustOptions(Color.RED, 1));
          player.spawnParticle(
              Particle.REDSTONE, minX + 16, y, z, 1, new Particle.DustOptions(Color.RED, 1));
          player.spawnParticle(
              Particle.REDSTONE, x, y, minZ + 17, 1, new Particle.DustOptions(Color.RED, 1));
        }
      }
    }
  }

  public static void createGuildInfo(Guild guild) {
    Location location = guild.nexusLocation().clone();
    location.setY(location.getY() + 3);

    createHologram(location, Material.ENDER_EYE,
        "&6&lNEXUS " + guild.name(),
        "&7Ilość żyć: &c" + guild.health() + "/3",
        "&7Tarcza: &c" + TimeUtils.futureMillisToTime(guild.shieldDuration()));
  }

  public static Hologram createHologram(Location baseLocation, Material icon, String... text) {
    Location location = baseLocation.clone();
    location.setX(location.getX() + 0.5);
    location.setZ(location.getZ() + 0.5);

    Hologram hologram = HologramsAPI.createHologram(HookUtils.INSTANCE, location);
    hologram.appendItemLine(new ItemStack(icon));
    Arrays.stream(text).map(ChatUtils::legacyColor).forEach(hologram::appendTextLine);
    return hologram;
  }

  public static void createTimedHologram(Location location, Duration duration, Material icon, String... text) {
    Hologram hologram = createHologram(location, icon, text);
    Bukkit.getScheduler()
        .runTaskLater(HookUtils.INSTANCE, hologram::delete, duration.getSeconds() * 20);
  }
}
