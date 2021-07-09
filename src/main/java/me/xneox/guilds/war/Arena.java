package me.xneox.guilds.war;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Deprecated
public class Arena {
    // ARENA DATA
    private final String name;
    private Location firstSpawn;
    private Location secondSpawn;

    // ARENA CONTROLLERS
    private final BossBar bossBar;
    private WarParticipant firstGuild;
    private WarParticipant secondGuild;

    private int time;
    private ArenaState state = ArenaState.FREE;

    public Arena(String name) {
        this.name = name;
        this.bossBar = Bukkit.createBossBar("", BarColor.RED, BarStyle.SOLID);
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

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public ArenaState getState() {
        return state;
    }

    public void setState(ArenaState state) {
        this.state = state;
    }

    public WarParticipant getFirstGuild() {
        return firstGuild;
    }

    public WarParticipant getSecondGuild() {
        return secondGuild;
    }

    public void setFirstGuild(WarParticipant firstGuild) {
        this.firstGuild = firstGuild;
    }

    public void setSecondGuild(WarParticipant secondGuild) {
        this.secondGuild = secondGuild;
    }

    public WarParticipant getWinner() {
        return this.firstGuild.getPoints() > this.secondGuild.getPoints() ? this.firstGuild : this.secondGuild;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public void forPlayers(Consumer<Player> action) {
        List<Player> list = new ArrayList<>();
        list.addAll(this.firstGuild.getMembers());
        list.addAll(this.secondGuild.getMembers());
        list.stream().filter(Objects::nonNull).forEach(action);
    }
}
