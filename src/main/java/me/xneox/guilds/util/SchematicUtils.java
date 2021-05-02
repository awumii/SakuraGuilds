package me.xneox.guilds.util;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;

public final class SchematicUtils {
    public static void pasteSchematic(Location location, String schematic) {
        try {
            File file = new File("plugins/FastAsyncWorldEdit/schematics", schematic + ".schematic");

            World world = new BukkitWorld(location.getWorld());
            BlockVector3 vector = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());

            ClipboardFormats.findByFile(file)
                    .load(file)
                    .paste(world, vector)
                    .close();
        } catch (IOException e) {
            System.err.println("[FAWE-API] Could not paste the schematic " + schematic);
            e.printStackTrace();
        }
    }
}
