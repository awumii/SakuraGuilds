package me.xneox.guilds.hook;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.skills.Skills;
import java.io.File;
import java.util.Objects;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.hook.placeholderapi.MainPlaceholderExpansion;
import me.xneox.guilds.hook.placeholderapi.TopPlaceholderExpansion;
import me.xneox.guilds.hook.worldedit.FAWEAdapter;
import me.xneox.guilds.hook.worldedit.WE7Adapter;
import me.xneox.guilds.util.LogUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// This class manages compatibility for different plugins.
public final class HookUtils {
  // Directory of the plugin.
  public static final String DIRECTORY = "plugins/SakuraGuilds";

  // Vault economy manager.
  public static final Economy ECONOMY = Objects.requireNonNull(
      Bukkit.getServicesManager().getRegistration(Economy.class)).getProvider();

  // WorldEdit adapter for pasting schematics.
  private static @Nullable WorldEditAdapter WORLD_EDIT_ADAPTER = null;

  private static final boolean AURELIUM_SKILLS_AVAILABLE = Bukkit.getPluginManager().isPluginEnabled("AureliumSkills");

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

  public static boolean pasteSchematic(@NotNull String schematic, @NotNull Location location) {
    var file = new File(DIRECTORY + "/schematics", schematic);

    if (WORLD_EDIT_ADAPTER == null) {
      LogUtils.LOGGER.error("Attempted to paste a schematic, but WorldEdit is not installed.");
      return false;
    }

    return WORLD_EDIT_ADAPTER.pasteSchematic(file, location);
  }

  // Get player's AureliumSkills level (sum of levels of all skills).
  public static int aureliumSkillsLevel(@NotNull Player player) {
    if (!AURELIUM_SKILLS_AVAILABLE) {
      return -1;
    }

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
