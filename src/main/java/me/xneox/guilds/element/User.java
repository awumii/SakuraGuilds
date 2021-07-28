package me.xneox.guilds.element;

import de.leonhard.storage.Json;
import me.xneox.guilds.type.ChatChannel;
import me.xneox.guilds.util.HookUtils;
import org.bukkit.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private final Json data;
    private final String name;

    private int trophies;
    private int kills;
    private int deaths;
    private long joinDate;

    private String editorSubject;
    private ChatChannel chatChannel;

    private Location startLocation;
    private Location teleportTarget;
    private int teleportCountdown;

    public User(String name) {
        this.data = new Json(name, HookUtils.directory("users"));
        this.name = name;

        this.trophies = data.getOrSetDefault("Trophies", 500);
        this.kills = data.getInt("Kills");
        this.deaths = data.getInt("Deaths");
        this.joinDate = data.getLong("JoinDate");
        this.chatChannel = ChatChannel.GLOBAL;
    }

    public void save() {
        data.set("Kills", this.kills);
        data.set("Deaths", this.deaths);
        data.set("JoinDate", this.joinDate);
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

    public void startLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Location teleportTarget() {
        return this.teleportTarget;
    }

    public void teleportTarget(Location teleportTarget) {
        this.teleportTarget = teleportTarget;
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
}
