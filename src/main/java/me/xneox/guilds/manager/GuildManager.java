package me.xneox.guilds.manager;

import me.xneox.epicguard.libs.storage.Json;
import me.xneox.guilds.element.Building;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ItemSerialization;
import me.xneox.guilds.util.LocationUtils;
import org.apache.commons.io.FileUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GuildManager {
    private final Map<String, Guild> guildMap = new HashMap<>();

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public GuildManager() {
        File dir = new File("plugins/NeonGuilds/guilds");
        dir.mkdirs();

        for (File file : dir.listFiles()) {
            try {
                Json json = new Json(file);

                // Parsing members
                List<Member> members = json.getStringList("Members").stream().map(Member::parse).collect(Collectors.toList());

                // Parsing buildings
                List<Building> buildings = json.getStringList("Buildings")
                        .stream()
                        .map(Building::parse)
                        .collect(Collectors.toList());

                Location home = LocationUtils.fromString(json.getString("Home"));
                Location nexusLocation = LocationUtils.fromString(json.getString("Nexus"));
                List<String> chunks = json.getStringList("Chunks");
                List<String> allies = json.getStringList("Allies");
                ItemStack[] storage = ItemSerialization.deserializeInventory(json.getString("Storage"));

                int kills = json.getInt("Kills");
                int deaths = json.getInt("Deaths");
                int trophies = json.getInt("Trophies");
                int health = json.getInt("Health");
                long shield = json.getLong("Shield");
                long creation = json.getLong("Creation");
                boolean isPublic = json.getBoolean("Public");

                String name = file.getName().replace(".json", "");
                this.guildMap.put(name, new Guild(name, members, nexusLocation, creation, allies, home, chunks,
                        shield, health, trophies, kills, deaths, isPublic, storage, buildings));
            } catch (Exception e) {
                ChatUtils.broadcast("&cWystąpił błąd podczas wczytywania danych gildii: &4" + file.getName());
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void save() {
        File backupDir = new File("plugins/NeonGuilds/backup");
        backupDir.mkdirs();

        this.guildMap.forEach((name, guild) -> {
            try {
                File guildFile = new File("plugins/NeonGuilds/guilds", name + ".json");
                if (guildFile.exists()) {
                    FileUtils.copyFile(guildFile, new File(backupDir, name + ".backup"));
                }

                Json json = new Json(guildFile);

                json.set("Members", guild.getMembers().stream().map(Member::toString).collect(Collectors.toList()));
                json.set("Allies", guild.getAllies());
                json.set("Home", LocationUtils.toString(guild.getHome()));
                json.set("Nexus", LocationUtils.toString(guild.getNexusLocation()));
                json.set("Chunks", guild.getChunks());
                json.set("Kills", guild.getKills());
                json.set("Deaths", guild.getDeaths());
                json.set("Trophies", guild.getTrophies());
                json.set("Shield", guild.getShield());
                json.set("Health", guild.getHealth());
                json.set("Creation", guild.getCreationLong());
                json.set("Public", guild.isPublic());
                json.set("Storage", ItemSerialization.serializeInventory(guild.getStorage()));
                json.set("Buildings", guild.getBuildings().stream().map(Building::toString).collect(Collectors.toList()));
            } catch (Exception e) {
                ChatUtils.broadcast("&cNie udało się zapisać danych gildii &4" + name + "&c, kopia zapasowa utworzona.");
                e.printStackTrace();
            }
        });
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void deleteGuild(Guild guild) {
        for (Guild value : this.guildMap.values()) {
            value.getAllies().remove(guild.getName());
        }

        guild.getNexusLocation().getBlock().setType(Material.AIR);
        guild.getNexusLocation().createExplosion(5, true, true);

        this.getGuildMap().remove(guild.getName());
        new File("plugins/NeonGuilds/guilds", guild.getName() + ".json").delete();
    }

    public Guild getGuildAt(Location location) {
        for (Guild guild : this.guildMap.values()) {
            if (guild.inside(location)) {
                return guild;
            }
        }
        return null;
    }

    public Guild getGuild(Player player) {
        return this.getGuild(player.getName());
    }

    public Guild getGuild(String player) {
        return this.guildMap.values().stream()
                .filter(guild -> guild.isMember(player))
                .findFirst()
                .orElse(null);
    }

    public Guild getGuildExact(String name) {
        return this.guildMap.get(name);
    }

    public Map<String, Guild> getGuildMap() {
        return guildMap;
    }
}
