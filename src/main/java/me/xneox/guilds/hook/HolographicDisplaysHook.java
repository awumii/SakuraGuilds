package me.xneox.guilds.hook;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.util.text.ChatUtils;
import me.xneox.guilds.util.text.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HolographicDisplaysHook {

  public static void clearHolograms() {
    HologramsAPI.getHolograms(SakuraGuildsPlugin.get()).forEach(Hologram::delete);
  }

  // Using HolographicDisplays to create a hologram.
  @Nullable
  public static Hologram createHologram(@NotNull Location baseLocation, Material icon, List<String> text) {
    if (!HookUtils.HOLOGRAMS_AVAILABLE) {
      return null;
    }

    var location = baseLocation.clone();
    location.setX(location.getX() + 0.5);
    location.setZ(location.getZ() + 0.5);

    var hologram = HologramsAPI.createHologram(SakuraGuildsPlugin.get(), location);
    hologram.appendItemLine(new ItemStack(icon));

    // Append and color all lines
    for (String line : text) {
      hologram.appendTextLine(ChatUtils.legacyColor(line));
    }
    return hologram;
  }

  // Creates hologram, and schedules a task to remove it later.
  public static void createTimedHologram(@NotNull Location location, @NotNull Duration duration, Material icon, List<String> text) {
    var hologram = createHologram(location, icon, text);
    if (hologram != null) {
      Bukkit.getScheduler().runTaskLater(SakuraGuildsPlugin.get(), hologram::delete, duration.getSeconds() * 20);
    }
  }

  // Creates a hologram above the nexus block.
  public static void createGuildInfo(Guild guild) {
    if (!ConfigManager.holograms().nexus().enabled()) {
      return;
    }

    var location = guild.nexusLocation().clone();
    location.setY(location.getY() + ConfigManager.holograms().nexus().heightAboveGround());

    List<String> text = new ArrayList<>();
    for (String line : ConfigManager.holograms().nexus().text()) {
      text.add(line
          .replace("{GUILD}", guild.name())
          .replace("{HP}", String.valueOf(guild.health()))
          .replace("{MAX-HP}", String.valueOf(guild.maxHealth()))
          .replace("{SHIELD}", TimeUtils.futureMillisToTime(guild.shieldDuration())));
    }

    createHologram(location, Material.ENDER_EYE, text);
  }
}
