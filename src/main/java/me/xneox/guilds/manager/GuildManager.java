package me.xneox.guilds.manager;

import co.aikar.idb.DB;
import co.aikar.idb.DbRow;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.util.DatabaseUtils;
import me.xneox.guilds.util.ItemSerialization;
import me.xneox.guilds.util.LocationUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class GuildManager {
    private final Map<String, Guild> guildMap = new HashMap<>();

    public GuildManager() throws SQLException {
        DB.executeUpdate("CREATE TABLE IF NOT EXISTS guilds(" +
                "`Name` TEXT NOT NULL PRIMARY KEY, " +
                "`Members` TEXT NOT NULL, " +
                "`Allies` TEXT NOT NULL, " +
                "`Home` TEXT NOT NULL, " +
                "`Nexus` TEXT NOT NULL, " +
                "`Chunks` TEXT NOT NULL, " +
                "`Kills` INT NOT NULL, " +
                "`Deaths` INT NOT NULL, " +
                "`Storage` TEXT NOT NULL, " +
                "`Money` INT NOT NULL, " +
                "`MaxSlots` INT NOT NULL, " +
                "`MaxChunks` INT NOT NULL, " +
                "`MaxStorage` INT NOT NULL, " +
                "`Health` INT NOT NULL, " +
                "`Shield` BIGINT NOT NULL, " +
                "`Creation` BIGINT NOT NULL" +
                ")");

        for (DbRow row : DB.getResults("SELECT * FROM guilds")) {
            String name = row.getString("Name");
            int kills = row.getInt("Kills");
            int deaths = row.getInt("Deaths");
            int money = row.getInt("Money");
            int health = row.getInt("Health");
            int maxSlots = row.getInt("MaxSlots");
            int maxChunks = row.getInt("MaxChunks");
            int maxStorage = row.getInt("MaxStorage");
            long shield = row.getLong("Shield");
            long creation = row.getLong("Creation");

            List<Member> members = DatabaseUtils.serializeList(row.getString("Members")).stream().map(Member::serialize).collect(Collectors.toList());
            List<String> chunks = DatabaseUtils.serializeList(row.getString("Chunks"));
            List<String> allies = DatabaseUtils.serializeList(row.getString("Allies"));
            Location home = LocationUtils.serialize(row.getString("Home"));
            Location nexusLocation = LocationUtils.serialize(row.getString("Nexus"));
            ItemStack[] storage = ItemSerialization.deserializeInventory(row.getString("Storage"));

            this.guildMap.put(name, new Guild(name, members, nexusLocation, creation, allies,
                    home, chunks, shield, health, kills, deaths, money, maxSlots, maxChunks, maxStorage, storage));
        }
    }

    public void save() {
        for (Guild guild : this.guildMap.values()) {
            DB.executeUpdateAsync("INSERT OR REPLACE INTO guilds(Name, Members, Allies, Home, Nexus," +
                            " Chunks, Kills, Deaths, Storage, Money, MaxSlots, MaxChunks, MaxStorage, Health, Shield, Creation) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",

                    guild.name(),
                    DatabaseUtils.deserializeList(guild.members().stream().map(Member::toString).collect(Collectors.toList())),
                    DatabaseUtils.deserializeList(guild.allies()),
                    LocationUtils.deserialize(guild.homeLocation()),
                    LocationUtils.deserialize(guild.nexusLocation()),
                    DatabaseUtils.deserializeList(guild.claims()),
                    guild.kills(),
                    guild.deaths(),
                    ItemSerialization.serializeInventory(guild.storage()),
                    guild.money(),
                    guild.maxSlots(),
                    guild.maxChunks(),
                    guild.maxStorage(),
                    guild.health(),
                    guild.shieldDuration(),
                    guild.creationLong());
        }
    }

    public void delete(Guild guild) {
        for (Guild value : this.guildMap.values()) {
            value.allies().remove(guild.name());
        }

        guild.nexusLocation().getBlock().setType(Material.AIR);
        guild.nexusLocation().createExplosion(5, true, true);

        DB.executeUpdateAsync("DELETE FROM guilds WHERE name=?", guild.name());
        this.guildMap.remove(guild.name());
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

    public List<Guild> leaderboard() {
        List<Guild> copy = new ArrayList<>(this.guildMap.values());
        Collections.sort(copy);
        return copy;
    }

    public Map<String, Guild> guildMap() {
        return this.guildMap;
    }
}
