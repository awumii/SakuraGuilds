package me.xneox.guilds.manager;

import de.leonhard.storage.Yaml;
import me.xneox.guilds.type.BuildingType;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigManager {
    private final Yaml buildingConfig;

    public ConfigManager() {
        this.buildingConfig = new Yaml("building-materials.yml", "plugins/NeonGuilds");
    }

    public List<Pair<Material, Integer>> getMaterialsFor(BuildingType building, int level) {
        Map<String, String> materialMap = this.buildingConfig.getMapParameterized(building.name() + "." + level);
        return materialMap.entrySet().stream()
                .map(entry -> Pair.of(Material.valueOf(entry.getKey()), Integer.parseInt(entry.getValue())))
                .collect(Collectors.toList());
    }
}
