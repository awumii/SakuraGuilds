package me.xneox.guilds.manager;

import co.aikar.idb.*;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.HookUtils;
import me.xneox.guilds.util.ItemSerialization;
import me.xneox.guilds.util.LocationUtils;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SQLManager {
    private final SakuraGuildsPlugin plugin;

    public SQLManager(SakuraGuildsPlugin plugin) {
        this.plugin = plugin;
        this.connect();
    }

    private void connect() {
        DatabaseOptions.DatabaseOptionsBuilder builder = DatabaseOptions.builder()
                .sqlite(HookUtils.DIRECTORY + "/guilds.db");

        Database database = PooledDatabaseOptions.builder().options(builder.build()).createHikariDatabase();
        DB.setGlobalDatabase(database);
    }

    public void loadGuilds() throws SQLException {
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

            List<Member> members = serializeList(row.getString("Members")).stream().map(Member::parse).collect(Collectors.toList());
            List<String> chunks = serializeList(row.getString("Chunks"));
            List<String> allies = serializeList(row.getString("Allies"));
            Location home = LocationUtils.serialize(row.getString("Home"));
            Location nexusLocation = LocationUtils.serialize(row.getString("Nexus"));
            ItemStack[] storage = ItemSerialization.deserializeInventory(row.getString("Storage"));

            this.plugin.guildManager().guildMap().put(name, new Guild(name, members, nexusLocation, creation, allies,
                    home, chunks, shield, health, kills, deaths, money, maxSlots, maxChunks, maxStorage, storage));
        }
    }

    public void saveGuilds() {
        for (Guild guild : this.plugin.guildManager().guildMap().values()) {
            try {
                DB.executeInsert("INSERT OR REPLACE INTO guilds(Name, Members, Allies, Home, Nexus," +
                        " Chunks, Kills, Deaths, Storage, Money, MaxSlots, MaxChunks, MaxStorage, Health, Shield, Creation) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",

                        guild.name(),
                        deserializeList(guild.members().stream().map(Member::toString).collect(Collectors.toList())),
                        deserializeList(guild.allies()),
                        LocationUtils.deserialize(guild.homeLocation()),
                        LocationUtils.deserialize(guild.nexusLocation()),
                        deserializeList(guild.claims()),
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
            } catch (SQLException e) {
                ChatUtils.broadcast("&cWystąpił krytyczny błąd podczas zapisywania gildii &6" + guild.name() + " &cdo bazy danych: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void loadUsers() throws SQLException {
        DB.executeUpdate("CREATE TABLE IF NOT EXISTS users(" +
                "`Name` TEXT NOT NULL PRIMARY KEY, " +
                "`Trophies` INT NOT NULL, " +
                "`Kills` INT NOT NULL, " +
                "`Deaths` INT NOT NULL, " +
                "`JoinDate` BIGINT NOT NULL" +
                ")");

        for (DbRow row : DB.getResults("SELECT * FROM users")) {
            String name = row.getString("Name");
            int trophies = row.getInt("Trophies");
            int kills = row.getInt("Kills");
            int deaths = row.getInt("Deaths");
            long joinDate = row.getLong("JoinDate");

            this.plugin.userManager().userMap().put(name, new User(trophies, kills, deaths, joinDate));
        }
    }

    public void saveUsers() {
        this.plugin.userManager().userMap().forEach((name, user) -> {
            try {
                DB.executeInsert("INSERT OR REPLACE INTO users(Name, Trophies, Kills, Deaths, JoinDate) VALUES(?, ?, ?, ?, ?)",
                        name,
                        user.trophies(),
                        user.kills(),
                        user.deaths(),
                        user.joinLong());
            } catch (SQLException e) {
                ChatUtils.broadcast("&cNie można zapisać gracza &6" + name + "&c: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private List<String> serializeList(String result) {
        return new ArrayList<>(Arrays.asList(result.split(",")));
    }

    private String deserializeList(List<String> list) {
        return String.join(",", list);
    }
}
