package me.xneox.guilds.manager;

import de.leonhard.storage.Json;
import me.xneox.guilds.element.Arena;
import me.xneox.guilds.util.LocationUtils;
import org.bukkit.Location;

import javax.annotation.Nullable;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ArenaManager {
    private final Map<String, Arena> arenaMap = new HashMap<>();

    public void load() {
        File dir = new File("plugins/NeonGuilds/arenas");
        dir.mkdirs();

        for (File file : dir.listFiles()) {
            Json json = new Json(file);
            String name = file.getName().replace(".json", "");

            Location firstSpawn = LocationUtils.fromString(json.getString("FirstSpawn"));
            Location secondSpawn = LocationUtils.fromString(json.getString("SecondSpawn"));

            Arena arena = new Arena(name);
            arena.setFirstSpawn(firstSpawn);
            arena.setSecondSpawn(secondSpawn);

            this.arenaMap.put(name, new Arena(name));
        }
    }

    public void save() {
        this.arenaMap.forEach((name, arena) -> {
            Json json = new Json(name, "plugins/NeonGuilds/arenas");

            json.set("FirstSpawn", LocationUtils.toString(arena.getFirstSpawn()));
            json.set("SecondSpawn", LocationUtils.toString(arena.getSecondSpawn()));
        });
    }

    @Nullable
    public Arena getArena(String name) {
        return this.arenaMap.get(name);
    }

    @Nullable
    public Arena getFreeArena() {
        return this.arenaMap.values().stream().filter(value -> !value.isOccupied()).findFirst().orElse(null);
    }

    public Map<String, Arena> getArenaMap() {
        return arenaMap;
    }
}
