package me.xneox.guilds.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public final class ChunkUtils {
  private ChunkUtils() {}

  public static String deserialize(Chunk chunk) {
    return ChatUtils.join('/', chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
  }

  public static Chunk serialize(String chunk) {
    String[] split = chunk.split("/");
    //noinspection ConstantConditions
    return Bukkit.getWorld(split[0])
        .getChunkAt(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
  }

  public static List<Player> getPlayersAt(Chunk chunk) {
    return Arrays.stream(chunk.getEntities())
        .filter(entity -> entity instanceof Player)
        .map(entity -> (Player) entity)
        .collect(Collectors.toList());
  }

  public static Location getCenter(String chunk) {
    return getCenter(serialize(chunk));
  }

  public static Location getCenter(Chunk chunk) {
    Block block = chunk.getBlock(8, 0, 8);

    Location loc = block.getLocation();
    loc.setY(chunk.getWorld().getHighestBlockYAt(loc) + 1);
    return loc;
  }

  public static boolean isEqual(Chunk original, String target) {
    return deserialize(original).equals(target);
  }

  public static boolean isProtected(Player player) {
    if (SakuraGuildsPlugin.get().guildManager().findAt(player.getLocation()) != null) {
      ChatUtils.sendMessage(player, "&cTen teren jest zajęty.");
      return true;
    }

    if (player.getWorld().getName().endsWith("end")) {
      ChatUtils.sendMessage(player, "&cNa tym świecie nie można zakładać gildii.");
      return true;
    }

    if (player.getWorld().getSpawnLocation().distance(player.getLocation()) < 200) {
      ChatUtils.sendMessage(player, "&cNie możesz zakładać gildii blisko spawna.");
      return true;
    }
    return false;
  }
}
