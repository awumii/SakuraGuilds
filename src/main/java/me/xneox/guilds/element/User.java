package me.xneox.guilds.element;

import me.xneox.guilds.enums.ChatChannel;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class User implements Comparable<User> {
  private int trophies;
  private int kills;
  private int deaths;

  private String editorSubject;
  private ChatChannel chatChannel;

  // todo objectify
  private Location startLocation;
  private Location teleportTarget;
  private int teleportCountdown;

  public User(int trophies, int kills, int deaths) {
    this.trophies = trophies;
    this.kills = kills;
    this.deaths = deaths;
    this.chatChannel = ChatChannel.GLOBAL;
  }

  public int trophies() {
    return this.trophies;
  }

  public void trophies(int trophies) {
    this.trophies = trophies;
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

  /**
   * @return nickname of the member this user is currently managing (changing permissions in guild).
   */
  @Nullable
  public String editorSubject() {
    return this.editorSubject;
  }

  public void editorSubject(@Nullable String editorSubject) {
    this.editorSubject = editorSubject;
  }

  @NotNull
  public ChatChannel chatChannel() {
    return this.chatChannel;
  }

  public void chatChannel(ChatChannel chatChannel) {
    this.chatChannel = chatChannel;
  }

  @Nullable
  public Location startLocation() {
    return this.startLocation;
  }

  @Nullable
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

  public void beginTeleport(@NotNull Location startLocation, @NotNull Location teleportTarget) {
    this.teleportCountdown = 6;
    this.startLocation = startLocation;
    this.teleportTarget = teleportTarget;
  }

  @Override
  public int compareTo(@NotNull User user) {
    return Integer.compare(user.trophies(), this.trophies());
  }

  @Override
  public String toString() {
    return "User{" +
        "trophies=" + trophies +
        ", kills=" + kills +
        ", deaths=" + deaths +
        ", editorSubject='" + editorSubject + '\'' +
        ", chatChannel=" + chatChannel +
        ", startLocation=" + startLocation +
        ", teleportTarget=" + teleportTarget +
        ", teleportCountdown=" + teleportCountdown +
        '}';
  }
}
