package me.xneox.guilds.manager;

import co.aikar.idb.DB;
import co.aikar.idb.DbRow;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.*;

public class UserManager {
    private final Map<UUID, User> userMap = new HashMap<>();

    public UserManager() throws SQLException {
        DB.executeUpdate("CREATE TABLE IF NOT EXISTS users(" +
                "`UUID` VARCHAR(40) PRIMARY KEY, " +
                "`Trophies` INT NOT NULL, " +
                "`Kills` INT NOT NULL, " +
                "`Deaths` INT NOT NULL, " +
                "`JoinDate` BIGINT NOT NULL" +
                ")");

        for (DbRow row : DB.getResults("SELECT * FROM users")) {
            UUID uuid = UUID.fromString(row.getString("UUID"));
            int trophies = row.getInt("Trophies");
            int kills = row.getInt("Kills");
            int deaths = row.getInt("Deaths");
            long joinDate = row.getLong("JoinDate");

            this.userMap.put(uuid, new User(trophies, kills, deaths, joinDate));
        }
    }

    public void save() {
        this.userMap.forEach((uuid, user) -> DB.executeUpdateAsync(
                "INSERT OR REPLACE INTO users(UUID, Trophies, Kills, Deaths, JoinDate) VALUES(?, ?, ?, ?, ?)",
                uuid,
                user.trophies(),
                user.kills(),
                user.deaths(),
                user.joinLong()));
    }

    @NotNull
    public User getUser(Player player) {
        return this.getUser(player.getUniqueId());
    }

    @NotNull
    public User getUser(UUID uuid) {
        return this.userMap.computeIfAbsent(uuid, u -> new User(500, 0, 0, new Date().getTime()));
    }
}
