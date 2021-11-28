package me.xneox.guilds.hook.worldedit;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.math.BlockVector3;
import java.io.File;
import java.io.IOException;
import me.xneox.guilds.hook.WorldEditAdapter;
import me.xneox.guilds.util.LogUtils;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class FAWEAdapter implements WorldEditAdapter {

  @Override
  public boolean pasteSchematic(@NotNull File schematic, @NotNull Location location) {
    var world = new BukkitWorld(location.getWorld());
    var vector = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());

    var format = ClipboardFormats.findByFile(schematic);
    if (format == null) {
      LogUtils.LOGGER.error("The schematic file '" + schematic.getName() + "' does not exist.");
      return false;
    }

    try {
      format.load(schematic)
          .paste(world, vector)
          .close();
    } catch (IOException exception) {
      LogUtils.catchException("[FAWEAdapter] Could not paste schematic", exception);
      return false;
    }
    return true;
  }
}
