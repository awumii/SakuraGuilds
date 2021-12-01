package me.xneox.guilds.util;

import java.util.ArrayList;
import java.util.List;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ChunkUtils {

  // Returns location of chunk: worldname/x/z
  @NotNull
  public static String deserialize(@NotNull Chunk chunk) {
    return ChatUtils.join('/',
        chunk.getWorld().getName(),
        chunk.getX(),
        chunk.getZ());
  }

  // Returns chunk at the specified location. Location is in the format worldname/x/z
  @NotNull
  public static Chunk serialize(@NotNull String chunk) {
    String[] split = chunk.split("/");
    //noinspection ConstantConditions
    return Bukkit.getWorld(split[0])
        .getChunkAt(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
  }

  @NotNull
  public static List<Player> getPlayersAt(@NotNull Chunk chunk) {
    List<Player> list = new ArrayList<>();
    for (Entity entity : chunk.getEntities()) {
      if (entity instanceof Player player) {
        list.add(player);
      }
    }
    return list;
  }

  @NotNull
  public static Location getCenter(@NotNull String chunk) {
    return getCenter(serialize(chunk));
  }

  @NotNull
  public static Location getCenter(@NotNull Chunk chunk) {
    Block block = chunk.getBlock(8, 0, 8);

    Location loc = block.getLocation();
    loc.setY(chunk.getWorld().getHighestBlockYAt(loc) + 1);
    return loc;
  }

  public static boolean isProtected(@NotNull Player player) {
    if (SakuraGuildsPlugin.get().guildManager().findAt(player.getLocation()) != null) {
      ChatUtils.sendMessage(player, "&cTen teren jest zajęty.");
      return true;
    }

    if (!player.getWorld().getName().equals("world")) {
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
