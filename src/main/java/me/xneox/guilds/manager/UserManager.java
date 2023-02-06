package me.xneox.guilds.manager;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.StorageService;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UserManager extends StorageService {
  private final Map<UUID, User> userMap = new ConcurrentHashMap<>();

  public UserManager(@NotNull DatabaseManager database) {
    super(database);
  }

  @Override
  public void load() throws SQLException {
    this.database.executeUpdate("CREATE TABLE IF NOT EXISTS users("
        + "`UUID` VARCHAR(40) PRIMARY KEY, "
        + "`Trophies` INT NOT NULL, "
        + "`Kills` INT NOT NULL, "
        + "`Deaths` INT NOT NULL)");

    try (var rs = database.executeQuery("SELECT * FROM users")) {
      while (rs.next()) {
        UUID uuid = UUID.fromString(rs.getString("UUID"));
        int trophies = rs.getInt("Trophies");
        int kills = rs.getInt("Kills");
        int deaths = rs.getInt("Deaths");

        this.userMap.put(uuid, new User(trophies, kills, deaths));
      }
    }
  }

  @Override
  public void save() throws SQLException {
    for (var entry : this.userMap.entrySet()) {
      var user = entry.getValue();
      this.database.executeUpdate("INSERT OR REPLACE INTO users(UUID, Trophies, Kills, Deaths) VALUES(?, ?, ?, ?)",
          entry.getKey(),
          user.trophies(),
          user.kills(),
          user.deaths());
    }
  }

  /**
   * Returns the user object for the specified player.
   * Creates new user if it does not exist.
   *
   * @param player player to get the user object for.
   * @return user object for this player.
   */
  @NotNull
  public User user(@NotNull Player player) {
    return this.user(player.getUniqueId());
  }

  /**
   * Returns the user object for the specified UUID.
   * Creates new user if it does not exist.
   *
   * @param uuid uuid of the user.
   * @return user object for the specified UUID.
   */
  @NotNull
  public User user(@NotNull UUID uuid) {
    return this.userMap.computeIfAbsent(uuid, u -> new User(500, 0, 0));
  }
}
