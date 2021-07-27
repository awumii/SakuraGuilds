package me.xneox.guilds.manager;

import de.leonhard.storage.Json;
import me.xneox.guilds.util.HookUtils;
import me.xneox.guilds.util.LocationUtils;
import org.bukkit.Location;

public class DataManager {
    private final Json data;
    private Location leaderboard;

    public DataManager() {
        this.data = new Json("data", HookUtils.DIRECTORY);
        this.leaderboard = LocationUtils.fromString(data.getOrSetDefault("leaderboards", LocationUtils.EMPTY));
    }

    public void save() {
        this.data.set("leaderboards", LocationUtils.toString(this.leaderboard));
    }

    public Location leaderboardLocation() {
        return leaderboard;
    }

    public void leaderboardLocation(Location leaderboard) {
        this.leaderboard = leaderboard;
    }
}
