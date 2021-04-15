package me.xneox.guilds.util;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ChunkUtils {
    public static final World WORLD = Objects.requireNonNull(Bukkit.getWorld("world"));

    public static String toString(Chunk chunk) {
        return chunk.getX() + ", " + chunk.getZ();
    }

    public static Chunk toChunk(String chunk) {
        String[] split = chunk.split(", ");
        return WORLD.getChunkAt(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    public static List<Player> getPlayersAt(Chunk chunk) {
        return Arrays.stream(chunk.getEntities())
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity).collect(Collectors.toList());
    }

    public static Location getCenter(String location) {
        Chunk chunk = ChunkUtils.toChunk(location);
        Block block = chunk.getBlock(8, 0, 8);

        Location loc = block.getLocation();
        loc.setY(WORLD.getHighestBlockYAt(loc) + 1);
        return loc;
    }

    public static boolean isEqual(Chunk original, String target) {
        return toString(original).equals(target);
    }

    public static boolean canBeClaimed(Player player) {
        if (ServiceUtils.INSTANCE.getGuildManager().getGuildAt(player.getLocation()) != null) {
            ChatUtils.sendMessage(player, "&cTen teren jest zajęty.");
            return false;
        }

        if (!player.getWorld().getName().equals("world")) {
            ChatUtils.sendMessage(player, "&cNa tym świecie nie można zakładać gildii.");
            return false;
        }

        if (player.getWorld().getSpawnLocation().distance(player.getLocation()) < 200) {
            ChatUtils.sendMessage(player, "&cNie możesz zakładać gildii blisko spawna.");
            return false;
        }
        return true;
    }

    private ChunkUtils() {}
}
