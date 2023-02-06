package me.xneox.guilds.integration.worldedit;

import java.io.File;
import me.xneox.guilds.util.LogUtils;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * WorldEditAdapter implementation for WorldEdit v7
 */
public class WE7Adapter implements WorldEditAdapter {

  @Override
  public boolean pasteSchematic(@NotNull File schematic, @NotNull Location location) {
    // TODO implementation
    LogUtils.LOGGER.error("WorldEdit is not supported yet. Use FastAsyncWorldEdit.");
    return false;
  }
}
