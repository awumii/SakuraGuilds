package me.xneox.guilds.manager;

import me.xneox.epicguard.libs.storage.Json;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ItemSerialization;
import me.xneox.guilds.util.LocationUtils;
import me.xneox.guilds.war.Arena;
import me.xneox.guilds.war.ArenaState;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ArenaManager {
    private final Map<UUID, Pair<String, Float>> backupMap = new HashMap<>();
    private final Map<String, Arena> arenaMap = new HashMap<>();
    private final Json data;

    private Location leaderboard;

    public ArenaManager() {
        this.data = new Json("arenas", "plugins/NeonGuilds");
        for (String name : data.getSection("arenas").singleLayerKeySet()) {
            Location firstSpawn = LocationUtils.fromString(data.getString("arenas." + name + ".FirstSpawn"));
            Location secondSpawn = LocationUtils.fromString(data.getString("arenas." + name + ".SecondSpawn"));

            Arena arena = new Arena(name);
            arena.setFirstSpawn(firstSpawn);
            arena.setSecondSpawn(secondSpawn);
            this.arenaMap.put(name, arena);
        }

        this.leaderboard = LocationUtils.fromString(data.getOrSetDefault("leaderboards", LocationUtils.EMPTY));
    }

    public void save() {
        this.arenaMap.forEach((name, arena) -> {
            data.set("arenas." + name + ".FirstSpawn", LocationUtils.toString(arena.getFirstSpawn()));
            data.set("arenas." + name + ".SecondSpawn", LocationUtils.toString(arena.getSecondSpawn()));
            data.set("leaderboards", LocationUtils.toString(this.leaderboard));
        });
    }

    @Nullable
    public Arena getArena(String name) {
        return this.arenaMap.get(name);
    }

    @Nullable
    public Arena getFreeArena() {
        return this.arenaMap.values().stream().filter(arena -> arena.getState() == ArenaState.FREE).findFirst().orElse(null);
    }

    public void createBackup(Player player) {
        this.backupMap.put(player.getUniqueId(), Pair.of(ItemSerialization.serializeInventory(player.getInventory()), player.getExp()));
    }

    public void loadBackup(Player player) {
        Pair<String, Float> data = this.backupMap.get(player.getUniqueId());
        if (data == null) {
            ChatUtils.sendMessage(player, "&cNie można wczytać ekwipunku, nie został zapisany backup!");
            return;
        }

        player.getInventory().setContents(ItemSerialization.deserializeInventory(data.getLeft()));
        player.setExp(data.getRight());
    }

    public Map<String, Arena> getArenaMap() {
        return arenaMap;
    }

    public Location getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(Location leaderboard) {
        this.leaderboard = leaderboard;
    }
}
