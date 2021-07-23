package me.xneox.guilds.manager;

import de.leonhard.storage.Json;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ItemSerialization;
import me.xneox.guilds.util.LocationUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataManager {
    private final Map<UUID, Pair<String, Integer>> backupMap = new HashMap<>();
    private final Json data;

    private Location leaderboard;

    public DataManager() {
        this.data = new Json("data", "plugins/NeonGuilds");
        this.leaderboard = LocationUtils.fromString(data.getOrSetDefault("leaderboards", LocationUtils.EMPTY));
    }

    public void save() {
        this.data.set("leaderboards", LocationUtils.toString(this.leaderboard));
    }

    public void createBackup(Player player) {
        this.backupMap.put(player.getUniqueId(), Pair.of(ItemSerialization.serializeInventory(player.getInventory()), player.getLevel()));
    }

    public void loadBackup(Player player) {
        Pair<String, Integer> data = this.backupMap.get(player.getUniqueId());
        if (data == null) {
            ChatUtils.sendMessage(player, "&cNie można wczytać ekwipunku, nie został zapisany backup!");
            return;
        }

        player.getInventory().setContents(ItemSerialization.deserializeInventory(data.getLeft()));
        player.setLevel(data.getRight());
    }

    public Location leaderboardLocation() {
        return leaderboard;
    }

    public void leaderboardLocation(Location leaderboard) {
        this.leaderboard = leaderboard;
    }
}
