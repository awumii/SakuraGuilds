package me.xneox.guilds.integration.worldedit;

import java.io.File;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public interface WorldEditAdapter {
  /**
   * Paste the schematic using WorldEdit.
   *
   * @param schematic the schematic file.
   * @param location location where to paste.
   * @return true if operation was successful, false if there was an error.
   */
  boolean pasteSchematic(@NotNull File schematic, @NotNull Location location);
}
