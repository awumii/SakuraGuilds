package me.xneox.guilds.util;

import com.SirBlobman.combatlogx.api.ICombatLogX;
import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.skills.Skills;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
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

  @SuppressWarnings("ConstantConditions")
  public static final Permission PERMISSION = Bukkit.getServicesManager().getRegistration(Permission.class).getProvider();

  public static void pasteSchematic(@NotNull String schematic, @NotNull Location location) throws WorldEditException, IOException {
    File file = new File("plugins/WorldEdit/schematics", schematic);

    ClipboardFormat format = ClipboardFormats.findByFile(file);
    if (format == null) {
      throw new IOException("Could not find the clipboard format for " + schematic);
    }

    try (ClipboardReader reader = format.getReader(new FileInputStream(file));
        EditSession editSession = WorldEdit.getInstance().newEditSession(new BukkitWorld(location.getWorld()))) {

      Operation operation = new ClipboardHolder(reader.read())
          .createPaste(editSession)
          .to(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
          .build();

      Operations.complete(operation);
    }
  }

  public static boolean hasCombatTag(@NotNull Player player) {
    ICombatLogX plugin = (ICombatLogX) Bukkit.getPluginManager().getPlugin("CombatLogX");
    if (plugin != null) {
      return plugin.getCombatManager().isInCombat(player);
    }
    return false;
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
