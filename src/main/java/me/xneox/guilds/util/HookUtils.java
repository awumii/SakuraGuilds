package me.xneox.guilds.util;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.skills.Skills;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import java.io.File;
import java.io.IOException;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

// This class manages compatibility for different plugins.
public final class HookUtils {
  public static final String DIRECTORY = "plugins/SakuraGuilds";

  // It's not possible for these variables to be ever null...
  @SuppressWarnings("ConstantConditions")
  public static final Economy ECONOMY = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();

  public static void pasteSchematic(@NotNull String schematic, @NotNull Location location) throws IOException {
    File file = new File("plugins/FastAsyncWorldEdit/schematics", schematic);

    World world = new BukkitWorld(location.getWorld());
    BlockVector3 vector = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());

    ClipboardFormats.findByFile(file)
        .load(file)
        .paste(world, vector)
        .close();
  }

  public static int getAureliumLevel(@NotNull Player player) {
    int sum = 0;
    for (Skills skill : Skills.values()) {
      int skillLevel = AureliumAPI.getSkillLevel(player, skill);
      if (skillLevel > 1) {
        sum += skillLevel;
      }
    }
    return sum;
  }
}
