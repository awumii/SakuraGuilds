package me.xneox.guilds.hook;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.skills.Skills;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import java.io.File;
import java.time.Duration;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.hook.placeholderapi.MainPlaceholderExpansion;
import me.xneox.guilds.hook.placeholderapi.TopPlaceholderExpansion;
import me.xneox.guilds.hook.worldedit.FAWEAdapter;
import me.xneox.guilds.hook.worldedit.WE7Adapter;
import me.xneox.guilds.util.LogUtils;
import me.xneox.guilds.util.text.ChatUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// This class manages compatibility for different plugins.
public final class HookUtils {
  // Directory of the plugin.
  public static final String DIRECTORY = "plugins/SakuraGuilds";

  // Vault economy manager.
  private static @Nullable Economy VAULT_ECONOMY = null;

  // WorldEdit adapter for pasting schematics.
  private static @Nullable WorldEditAdapter WORLD_EDIT_ADAPTER = null;

  // Simple availability checks
  private static final boolean AURELIUM_SKILLS_AVAILABLE = Bukkit.getPluginManager().isPluginEnabled("AureliumSkills");
  private static final boolean HOLOGRAMS_AVAILABLE = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");

  // Register hooks for supported plugins.
  public static void register(@NotNull SakuraGuildsPlugin plugin) {
    // PlaceholderAPI hook
    if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
      new MainPlaceholderExpansion(plugin).register();
      new TopPlaceholderExpansion(plugin).register();
    }

    // Vault hook
    var economyService = Bukkit.getServicesManager().getRegistration(Economy.class);
    if (economyService != null) {
      VAULT_ECONOMY = economyService.getProvider();
    } else {
      LogUtils.LOGGER.error("Vault is not installed. Economy is not available.");
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

  // VaultAPI: taxes >:) (there are no taxes in this plugin... yet)
  public static void ecoWithdraw(@NotNull Player player, double amount) {
    if (VAULT_ECONOMY != null) {
      VAULT_ECONOMY.withdrawPlayer(player, amount);
    }
  }

  // VaultAPI: checking player's balance.
  public static boolean ecoHasAtLeast(@NotNull Player player, double amount) {
    if (VAULT_ECONOMY == null) {
      return false;
    }

    return VAULT_ECONOMY.has(player, amount);
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

  // Using HolographicDisplays to create a hologram.
  @Nullable
  public static Hologram createHologram(@NotNull Location baseLocation, Material icon, String... text) {
    if (!HOLOGRAMS_AVAILABLE) {
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
  public static void createTimedHologram(@NotNull Location location, @NotNull Duration duration, Material icon, String... text) {
    var hologram = createHologram(location, icon, text);
    if (hologram != null) {
      Bukkit.getScheduler().runTaskLater(SakuraGuildsPlugin.get(), hologram::delete, duration.getSeconds() * 20);
    }
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
