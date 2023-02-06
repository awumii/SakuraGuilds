package me.xneox.guilds.integration;

import java.io.File;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.integration.placeholderapi.MainPlaceholderExpansion;
import me.xneox.guilds.integration.placeholderapi.TopPlaceholderExpansion;
import me.xneox.guilds.integration.worldedit.FAWEAdapter;
import me.xneox.guilds.integration.worldedit.WE7Adapter;
import me.xneox.guilds.integration.worldedit.WorldEditAdapter;
import me.xneox.guilds.util.LogUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// This class manages compatibility for different plugins.
public final class Integrations {
  // Directory of the plugin.
  public static final String DIRECTORY = "plugins/SakuraGuilds";

  // WorldEdit adapter for pasting schematics.
  private static @Nullable WorldEditAdapter WORLD_EDIT_ADAPTER = null;

  // Simple availability checks
  public static final boolean HOLOGRAMS_AVAILABLE = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");
  public static final boolean VAULT_AVAILABLE = Bukkit.getPluginManager().isPluginEnabled("Vault");

  // Register hooks for supported plugins.
  public static void register(@NotNull SakuraGuildsPlugin plugin) {
    // PlaceholderAPI hook
    if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
      new MainPlaceholderExpansion(plugin).register();
      new TopPlaceholderExpansion(plugin).register();
    }

    // WorldEdit hook
    if (Bukkit.getPluginManager().isPluginEnabled("FastAsyncWorldEdit")) {
      WORLD_EDIT_ADAPTER = new FAWEAdapter();
    } else if (Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {
      WORLD_EDIT_ADAPTER = new WE7Adapter();
    } else {
      LogUtils.LOGGER.error("WorldEdit is not installed. Schematic pasting not available.");
    }
  }

  // Redirect to the WORLD_EDIT_ADAPTER to paste a schematic using WorldEdit/FAWE.
  public static boolean pasteSchematic(@NotNull String schematic, @NotNull Location location) {
    if (WORLD_EDIT_ADAPTER == null) {
      LogUtils.LOGGER.error("Attempted to paste a schematic, but WorldEdit is not installed.");
      return false;
    }

    var file = new File(DIRECTORY, schematic);
    return WORLD_EDIT_ADAPTER.pasteSchematic(file, location);
  }
}
