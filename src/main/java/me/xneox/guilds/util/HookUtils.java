package me.xneox.guilds.util;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.xneox.guilds.NeonGuilds;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

// This class manages compatibility for different plugins.
public final class HookUtils {
    public static final Economy ECONOMY = Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(Economy.class)).getProvider();
    public static final NeonGuilds INSTANCE = NeonGuilds.getPlugin(NeonGuilds.class);
    public static final String VERSION = INSTANCE.getDescription().getVersion();

    @Nullable
    public static String getWorldGuardRegion(@Nonnull Location location) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager manager = container.get(new BukkitWorld(location.getWorld()));
        if (manager == null) {
            return null;
        }

        Set<ProtectedRegion> regions = manager.getApplicableRegions(locationToVector(location)).getRegions();
        return regions.stream()
                .findAny()
                .map(ProtectedRegion::getId)
                .orElse(null);
    }

    public static void pasteSchematic(@Nonnull Location location, @Nonnull String schematic) {
        try {
            File file = new File("plugins/FastAsyncWorldEdit/schematics", schematic + ".schematic");

            ClipboardFormats.findByFile(file)
                    .load(file)
                    .paste(new BukkitWorld(location.getWorld()), locationToVector(location))
                    .close();
        } catch (IOException e) {
            System.err.println("[FAWE-API] Could not paste the schematic " + schematic);
            e.printStackTrace();
        }
    }

    @Nonnull
    public static BlockVector3 locationToVector(@Nonnull Location location) {
        return BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
