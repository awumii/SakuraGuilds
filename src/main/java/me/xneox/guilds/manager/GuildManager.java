package me.xneox.guilds.manager;

import me.xneox.epicguard.libs.storage.Json;
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
import java.util.Objects;
import java.util.stream.Collectors;

public class GuildManager {
    private final Map<String, Guild> guildMap = new HashMap<>();

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public GuildManager() {
        File dir = new File("plugins/NeonGuilds/guilds");
        dir.mkdirs();

        for (File file : Objects.requireNonNull(dir.listFiles())) {
            try {
                Json json = new Json(file);

                // Parsing members
                List<Member> members = json.getStringList("Members").stream().map(Member::parse).collect(Collectors.toList());

                Location home = LocationUtils.fromString(json.getString("Home"));
                Location nexusLocation = LocationUtils.fromString(json.getString("Nexus"));
                List<String> chunks = json.getStringList("Chunks");
                List<String> allies = json.getStringList("Allies");
                ItemStack[] storage = ItemSerialization.deserializeInventory(json.getString("Storage"));

                int kills = json.getInt("Kills");
                int deaths = json.getInt("Deaths");
                int money = json.getInt("Money");
                int trophies = json.getInt("Trophies");
                int health = json.getInt("Health");
                int maxSlots = json.getInt("MaxSlots");
                int maxChunks = json.getInt("MaxChunks");
                int maxStorage = json.getInt("MaxStorage");
                long shield = json.getLong("Shield");
                long creation = json.getLong("Creation");

                String name = file.getName().replace(".json", "");
                this.guildMap.put(name, new Guild(name, members, nexusLocation, creation, allies, home, chunks,
                        shield, health, trophies, kills, deaths, money, maxSlots, maxChunks, maxStorage, storage));
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

                json.set("Members", guild.members().stream().map(Member::toString).collect(Collectors.toList()));
                json.set("Allies", guild.allies());
                json.set("Home", LocationUtils.toString(guild.homeLocation()));
                json.set("Nexus", LocationUtils.toString(guild.nexusLocation()));
                json.set("Chunks", guild.claims());
                json.set("Money", guild.money());
                json.set("Kills", guild.kills());
                json.set("Deaths", guild.deaths());
                json.set("MaxSlots", guild.maxSlots());
                json.set("MaxChunks", guild.maxChunks());
                json.set("MaxStorage", guild.maxStorage());
                json.set("Trophies", guild.trophies());
                json.set("Shield", guild.shieldDuration());
                json.set("Health", guild.health());
                json.set("Creation", guild.creationLong());
                json.set("Storage", ItemSerialization.serializeInventory(guild.storage()));
            } catch (Exception e) {
                ChatUtils.broadcast("&cNie udało się zapisać danych gildii &4" + name + "&c, kopia zapasowa utworzona.");
                e.printStackTrace();
            }
        });
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void delete(Guild guild) {
        for (Guild value : this.guildMap.values()) {
            value.allies().remove(guild.name());
        }

        guild.nexusLocation().getBlock().setType(Material.AIR);
        guild.nexusLocation().createExplosion(5, true, true);

        this.guildMap.remove(guild.name());
        new File("plugins/NeonGuilds/guilds", guild.name() + ".json").delete();
    }

    public Guild findAt(Location location) {
        for (Guild guild : this.guildMap.values()) {
            if (guild.inside(location)) {
                return guild;
            }
        }
        return null;
    }

    public Guild playerGuild(Player player) {
        return this.playerGuild(player.getName());
    }

    public Guild playerGuild(String player) {
        return this.guildMap.values().stream()
                .filter(guild -> guild.isMember(player))
                .findFirst()
                .orElse(null);
    }

    public Guild get(String name) {
        return this.guildMap.get(name);
    }

    public Map<String, Guild> guildMap() {
        return guildMap;
    }
}
