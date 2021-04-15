package me.xneox.guilds.manager;

import me.xneox.guilds.util.TimeUtils;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CooldownManager {
    private final Map<String, Cooldown> cooldowns = new HashMap<>();

    public void add(Player player, String id, int duration, TimeUnit durationUnit) {
        Cooldown cooldown = new Cooldown(duration, durationUnit);
        this.cooldowns.put(player + id, cooldown);
    }

    public boolean hasCooldown(Player player, String id) {
        Cooldown cooldown = this.cooldowns.get(player + id);
        if (cooldown == null) {
            return false;
        }
        if (cooldown.hasExpired()) {
            this.cooldowns.remove(id);
            return false;
        }
        return true;
    }

    public String getRemaining(Player player, String id) {
        if (hasCooldown(player, id)) {
            Cooldown cooldown = this.cooldowns.get(player + id);
            return TimeUtils.millisToTime(cooldown.getRemaining());
        }
        return "teraz";
    }

    public static class Cooldown {
        private final long duration;
        private final long time;

        public Cooldown(long duration, TimeUnit durationUnit) {
            this.duration = durationUnit.toMillis(duration);
            this.time = System.currentTimeMillis();
        }

        public boolean hasExpired() {
            return System.currentTimeMillis() > this.time + this.duration;
        }

        public long getRemaining() {
            return this.time + this.duration - System.currentTimeMillis();
        }
    }
}
