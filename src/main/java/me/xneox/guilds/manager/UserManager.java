package me.xneox.guilds.manager;

import co.aikar.idb.DB;
import co.aikar.idb.DbRow;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import me.xneox.guilds.element.User;
import me.xneox.guilds.enums.Race;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UserManager {
  private final Map<UUID, User> userMap = new ConcurrentHashMap<>();

  public UserManager() throws SQLException {
    DB.executeUpdate(
        "CREATE TABLE IF NOT EXISTS users("
            + "`UUID` VARCHAR(40) PRIMARY KEY, "
            + "`Trophies` INT NOT NULL, "
            + "`Kills` INT NOT NULL, "
            + "`Deaths` INT NOT NULL, "
            + "`JoinDate` BIGINT NOT NULL,"
            + "`Race` TEXT NOT NULL"
            + ")");

    for (DbRow row : DB.getResults("SELECT * FROM users")) {
      UUID uuid = UUID.fromString(row.getString("UUID"));
      int trophies = row.getInt("Trophies");
      int kills = row.getInt("Kills");
      int deaths = row.getInt("Deaths");
      long joinDate = row.getLong("JoinDate");
      Race race = Race.valueOf(row.getString("Race"));

      this.userMap.put(uuid, new User(trophies, kills, deaths, joinDate, race));
    }
  }

  public void save() {
    this.userMap.forEach(
        (uuid, user) -> {
          try {
            DB.executeUpdate("INSERT OR REPLACE INTO users(UUID, Trophies, Kills, Deaths, JoinDate, Race) VALUES(?, ?, ?, ?, ?, ?)",
                uuid,
                user.trophies(),
                user.kills(),
                user.deaths(),
                user.joinLong(),
                user.race());
          } catch (SQLException e) {
            e.printStackTrace();
          }
        });
  }

  @NotNull
  public User user(@NotNull Player player) {
    return this.user(player.getUniqueId());
  }

  @NotNull
  public User user(@NotNull UUID uuid) {
    return this.userMap.computeIfAbsent(
        uuid, u -> new User(500, 0, 0, new Date().getTime(), Race.NONE));
  }

  public Map<UUID, User> userMap() {
    return this.userMap;
  }
}
