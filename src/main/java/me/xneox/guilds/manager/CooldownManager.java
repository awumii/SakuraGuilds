package me.xneox.guilds.manager;

import co.aikar.idb.DB;
import co.aikar.idb.DbRow;
import me.xneox.guilds.util.text.TimeUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CooldownManager {
    private final Map<String, Pair<Long, Long>> cooldownMap = new HashMap<>();

    public CooldownManager() throws SQLException {
        DB.executeUpdate("CREATE TABLE IF NOT EXISTS cooldowns(" +
                "`ID` TEXT NOT NULL PRIMARY KEY, " +
                "`Duration` INT NOT NULL, " +
                "`TimeScheduled` BIGINT NOT NULL" +
                ")");

        for (DbRow row : DB.getResults("SELECT * FROM cooldowns")) {
            String id = row.getString("ID");
            long duration = row.getInt("Duration");
            long timeScheduled = row.getLong("TimeScheduled");

            this.cooldownMap.put(id, Pair.of(duration, timeScheduled));
        }
    }

    public void save() {
        try {
            DB.executeUpdate("DELETE FROM cooldowns");
            for (Map.Entry<String, Pair<Long, Long>> entry : this.cooldownMap.entrySet()) {
                String id = entry.getKey();
                Pair<Long, Long> pair = entry.getValue();
                DB.executeUpdate("INSERT OR REPLACE INTO cooldowns(ID, Duration, TimeScheduled) VALUES(?, ?, ?)",
                        id,
                        pair.getLeft(),
                        pair.getRight());
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return "teraz";
    }
}
