package me.xneox.guilds.element;

import me.xneox.guilds.enums.ChatChannel;
import me.xneox.guilds.enums.Race;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User implements Comparable<User> {
    private int trophies;
    private int kills;
    private int deaths;
    private long joinDate;
    private Race race;

    private String editorSubject;
    private ChatChannel chatChannel;

    private Location startLocation;
    private Location teleportTarget;
    private int teleportCountdown;

    public User(int trophies, int kills, int deaths, long joinDate, Race race) {
        this.trophies = trophies;
        this.kills = kills;
        this.deaths = deaths;
        this.joinDate = joinDate;
        this.race = race;
        this.chatChannel = ChatChannel.GLOBAL;
    }

    public int trophies() {
        return this.trophies;
    }

    public void trophies(int trophies) {
        this.trophies = trophies;
    }

    public String joinDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm");
        return sdf.format(new Date(this.joinDate));
    }

    public long joinLong() {
        return this.joinDate;
    }

    public Race race() {
        return this.race;
    }

    public void race(Race race) {
        this.race = race;
    }

    public int kills() {
        return this.kills;
    }

    public void kills(int kills) {
        this.kills = kills;
    }

    public int deaths() {
        return this.deaths;
    }

    public void deaths(int deaths) {
        this.deaths = deaths;
    }

    public void joinDate(long joinDate) {
        this.joinDate = joinDate;
    }

    public String editorSubject() {
        return this.editorSubject;
    }

    public void editorSubject(String editorSubject) {
        this.editorSubject = editorSubject;
    }

    public ChatChannel chatChannel() {
        return this.chatChannel;
    }

    public void chatChannel(ChatChannel chatChannel) {
        this.chatChannel = chatChannel;
    }

    public Location startLocation() {
        return this.startLocation;
    }

    public Location teleportTarget() {
        return this.teleportTarget;
    }

    public int teleportCountdown() {
        return this.teleportCountdown;
    }

    public void teleportCountdown(int teleportCountdown) {
        this.teleportCountdown = teleportCountdown;
    }

    public void clearTeleport() {
        this.teleportTarget = null;
        this.startLocation = null;
    }

    public void beginTeleport(Location startLocation, Location teleportTarget) {
        this.teleportCountdown = 6;
        this.startLocation = startLocation;
        this.teleportTarget = teleportTarget;
    }

    @Override
    public int compareTo(@NotNull User user) {
        return Integer.compare(user.trophies(), this.trophies());
    }
}
