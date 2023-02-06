package me.xneox.guilds.manager;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import me.xneox.guilds.util.StorageService;
import me.xneox.guilds.util.text.TimeUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

// TODO: rewrite this class because it's pain to use.
public class CooldownManager extends StorageService {
  private final Map<String, Pair<Long, Long>> cooldownMap = new ConcurrentHashMap<>();

  public CooldownManager(@NotNull DatabaseManager database) {
    super(database);
  }

  @Override
  public void load() throws SQLException {
    this.database.executeUpdate("CREATE TABLE IF NOT EXISTS cooldowns("
        + "`ID` TEXT NOT NULL PRIMARY KEY, "
        + "`Duration` INT NOT NULL, "
        + "`TimeScheduled` BIGINT NOT NULL"
        + ")");

    try (var rs = database.executeQuery("SELECT * FROM cooldowns")) {
      while (rs.next()) {
        String id = rs.getString("ID");
        long duration = rs.getInt("Duration");
        long timeScheduled = rs.getLong("TimeScheduled");

        this.cooldownMap.put(id, Pair.of(duration, timeScheduled));
      }
    }
  }

  @Override
  public void save() throws SQLException{
    // clear the whole table, removing expired cooldowns.
    this.database.executeUpdate("DELETE FROM cooldowns");

    for (var entry : this.cooldownMap.entrySet()) {
      var pair = entry.getValue();
      this.database.executeUpdate("INSERT OR REPLACE INTO cooldowns(ID, Duration, TimeScheduled) VALUES(?, ?, ?)",
          entry.getKey(),
          pair.getLeft(),
          pair.getRight());
    }
  }

  public void add(@NotNull Player player, @NotNull String id, int duration, TimeUnit durationUnit) {
    this.add(player.getUniqueId() + id, duration, durationUnit);
  }

  public void add(@NotNull String id, int duration, TimeUnit durationUnit) {
    this.cooldownMap.putIfAbsent(id, Pair.of(durationUnit.toMillis(duration), System.currentTimeMillis()));
  }

  public boolean hasCooldown(@NotNull Player player, String id) {
    return this.hasCooldown(player.getUniqueId() + id);
  }

  public boolean hasCooldown(@NotNull String id) {
    Pair<Long, Long> cooldown = this.cooldownMap.get(id);
    if (cooldown == null) {
      return false;
    }

    if (System.currentTimeMillis() > cooldown.getRight() + cooldown.getLeft()) {
      this.cooldownMap.remove(id);
      return false;
    }
    return true;
  }

  @NotNull
  public String getRemaining(@NotNull Player player, @NotNull String id) {
    return this.getRemaining(player.getUniqueId() + id);
  }

  @NotNull
  public String getRemaining(@NotNull String id) {
    if (this.hasCooldown(id)) {
      Pair<Long, Long> cooldown = this.cooldownMap.get(id);
      return TimeUtils.millisToTime(cooldown.getRight() + cooldown.getLeft() - System.currentTimeMillis());
    }
    return "teraz"; // todo: localize this string
  }
}
