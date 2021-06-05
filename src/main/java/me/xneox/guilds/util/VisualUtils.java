package me.xneox.guilds.util;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.xneox.guilds.element.Building;
import me.xneox.guilds.element.Guild;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.Arrays;

public final class VisualUtils {
    public static void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1f, 1f);
    }

    public static void click(Player player) {
        playSound(player, Sound.BLOCK_WOODEN_BUTTON_CLICK_ON);
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

    public static void createGuildInfo(Guild guild) {
        Location location = guild.getNexusLocation().clone();
        location.setY(location.getY() + 3);

        createHologram(location, Material.ENDER_EYE,
                "&6&lNEXUS GILDII " + guild.getName(),
                "&7Ilość żyć: &c" + guild.getHealth() + "/3",
                "&7Tarcza: &c" + TimeUtils.futureMillisToTime(guild.getShield()));
    }

    public static void createBuildingInfo(Guild guild, Building building) {
        Location location = building.getLocation().clone();
        location.setY(location.getY() + 3);

        switch (building.getState()) {
            case INBUILT:
                createHologram(location, Material.CLOCK,
                        "&6&l" + building.getType().getName() + " gildii " + guild.getName(),
                        "&7W trakcie budowy...",
                        "&c" + TimeUtils.futureMillisToTime(building.getCompleteTime()));
                break;
            case BUILT:
                createHologram(location, Material.EMERALD_BLOCK,
                        "&6&l" + building.getType().getName() + " gildii " + guild.getName(),
                        "&7Poziom: " + building.getLevel());
                break;
        }
    }

    public static Hologram createHologram(Location baseLocation, Material icon, String... text) {
        Location location = baseLocation.clone();
        location.setX(location.getX() + 0.5);
        location.setZ(location.getZ() + 0.5);

        Hologram hologram = HologramsAPI.createHologram(HookUtils.INSTANCE, location);
        hologram.appendItemLine(new ItemStack(icon));
        Arrays.stream(text).map(ChatUtils::colored).forEach(hologram::appendTextLine);
        return hologram;
    }

    public static void createTimedHologram(Location location, Duration duration, Material icon, String... text) {
        Hologram hologram = createHologram(location, icon, text);
        Bukkit.getScheduler().runTaskLater(HookUtils.INSTANCE, hologram::delete, duration.getSeconds() * 20);
    }


    private VisualUtils() {}
}
