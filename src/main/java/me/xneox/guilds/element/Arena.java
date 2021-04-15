package me.xneox.guilds.element;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;

public class Arena {
    // ARENA DATA
    private final String name;
    private Location firstSpawn;
    private Location secondSpawn;

    // ARENA CONTROLLERS
    private Map<Guild, Player> playerMap;
    private int countdown;
    private boolean occupied;
    private boolean firstGuildTeleported;

    public Arena(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFirstSpawn(Location firstSpawn) {
        this.firstSpawn = firstSpawn;
    }

    public Location getFirstSpawn() {
        return firstSpawn;
    }

    public Location getSecondSpawn() {
        return secondSpawn;
    }

    public void setSecondSpawn(Location secondSpawn) {
        this.secondSpawn = secondSpawn;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public int getCountdown() {
        return countdown;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean isFirstGuildTeleported() {
        return firstGuildTeleported;
    }

    public void setFirstGuildTeleported(boolean firstGuildTeleported) {
        this.firstGuildTeleported = firstGuildTeleported;
    }

    public Map<Guild, Player> getPlayerMap() {
        return playerMap;
    }
}
