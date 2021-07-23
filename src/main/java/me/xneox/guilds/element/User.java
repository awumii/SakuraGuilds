package me.xneox.guilds.element;

import de.leonhard.storage.Json;
import me.xneox.guilds.type.ChatChannel;
import org.bukkit.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private final Json data;
    private final String name;

    private int kills;
    private int deaths;
    private long joinDate;

    private String editorSubject;
    private ChatChannel chatChannel;

    private Location startLocation;
    private Location teleportTarget;
    private int teleportCountdown;

    public User(String name) {
        this.data = new Json(name, "plugins/NeonGuilds/users");
        this.name = name;

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

    public String getJoinDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm");
        return sdf.format(new Date(this.joinDate));
    }

    public void setJoinDate() {
        this.joinDate = new Date().getTime();
    }

    public String getName() {
        return name;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getEditorSubject() {
        return editorSubject;
    }

    public void setEditorSubject(String editorSubject) {
        this.editorSubject = editorSubject;
    }

    public ChatChannel getChatChannel() {
        return chatChannel;
    }

    public void setChatChannel(ChatChannel chatChannel) {
        this.chatChannel = chatChannel;
    }

    public int getTeleportCountdown() {
        return teleportCountdown;
    }

    public void setTeleportCountdown(int teleportCountdown) {
        this.teleportCountdown = teleportCountdown;
    }

    public Location getTeleportTarget() {
        return teleportTarget;
    }

    public Location getStartLocation() {
        return startLocation;
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
