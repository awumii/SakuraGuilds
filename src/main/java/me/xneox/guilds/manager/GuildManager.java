package me.xneox.guilds.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.util.ChunkUtils;
import me.xneox.guilds.util.LocationUtils;
import me.xneox.guilds.util.StorageService;
import me.xneox.guilds.util.inventory.ItemSerialization;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GuildManager extends StorageService {
  private final Map<String, Guild> guildMap = new ConcurrentHashMap<>();

  public GuildManager(@NotNull DatabaseManager database) {
    super(database);
  }

  @Override
  public void load() throws SQLException {
    this.database.executeUpdate("CREATE TABLE IF NOT EXISTS guilds("
        + "`Name` TEXT NOT NULL PRIMARY KEY, "
        + "`Members` TEXT NOT NULL, "
        + "`Allies` TEXT NOT NULL, "
        + "`Home` TEXT NOT NULL, "
        + "`Nexus` TEXT NOT NULL, "
        + "`Chunks` TEXT NOT NULL, "
        + "`Storage` TEXT NOT NULL, "
        + "`Money` INT NOT NULL, "
        + "`MaxSlots` INT NOT NULL, "
        + "`MaxChunks` INT NOT NULL, "
        + "`MaxStorage` INT NOT NULL, "
        + "`Health` INT NOT NULL, "
        + "`MaxHealth` INT NOT NULL, "
        + "`Shield` BIGINT NOT NULL, "
        + "`Creation` BIGINT NOT NULL)");

    var rs = database.executeQuery("SELECT * FROM guilds");
    while (rs.next()) {
      String name = rs.getString("Name");
      int money = rs.getInt("Money");
      int health = rs.getInt("Health");
      int maxHealth = rs.getInt("MaxHealth");
      int maxSlots = rs.getInt("MaxSlots");
      int maxChunks = rs.getInt("MaxChunks");
      int maxStorage = rs.getInt("MaxStorage");
      long shield = rs.getLong("Shield");
      long creation = rs.getLong("Creation");

      var members = DatabaseManager.stringToList(rs.getString("Members"))
          .stream()
          .map(Member::serialize)
          .collect(Collectors.toList());

      var chunksRaw = DatabaseManager.stringToList(rs.getString("Chunks"));
      var chunks = chunksRaw.stream().map(ChunkUtils::serialize).collect(Collectors.toList());

      var alliesRaw = DatabaseManager.stringToList(rs.getString("Allies"));
      var allies = alliesRaw.stream().map(ally -> SakuraGuildsPlugin.get().guildManager().get(ally)).collect(Collectors.toList());

      var homeLocation = LocationUtils.serialize(rs.getString("Home"));
      var nexusLocation = LocationUtils.serialize(rs.getString("Nexus"));

      var storage = ItemSerialization.deserializeInventory(rs.getString("Storage"));

      this.guildMap.put(name, new Guild(name, members, nexusLocation, allies, homeLocation, chunks, storage,
          creation, shield, health, maxHealth, money, maxSlots, maxChunks, maxStorage));
    }
  }

  @Override
  public void save() throws SQLException {
    for (Guild guild : this.guildMap.values()) {
      this.database.executeUpdate("INSERT OR REPLACE INTO guilds(Name, Members, Allies, Home, Nexus,"
              + " Chunks, Storage, Money, MaxSlots, MaxChunks, MaxStorage, Health, MaxHealth, Shield, Creation) "
              + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",

          guild.name(),
          DatabaseManager.listToString(guild.members().stream().map(Member::toString).collect(Collectors.toList())),
          DatabaseManager.listToString(guild.allies().stream().map(Guild::name).toList()),
          LocationUtils.deserialize(guild.homeLocation()),
          LocationUtils.deserialize(guild.nexusLocation()),
          DatabaseManager.listToString(guild.claims().stream().map(ChunkUtils::deserialize).toList()),
          ItemSerialization.serializeInventory(guild.storage()),
          guild.money(),
          guild.maxSlots(),
          guild.maxChunks(),
          guild.maxStorage(),
          guild.health(),
          guild.maxHealth(),
          guild.shieldDuration(),
          guild.creationLong());
    }
  }

  /**
   * Deletes the specified guild and its data, and explodes the nexus.
   *
   * @param guild guild to be deleted.
   */
  public void delete(@NotNull Guild guild) {
    for (Guild value : this.guildMap.values()) {
      value.allies().remove(guild);
    }

    guild.nexusLocation().getBlock().setType(Material.AIR);
    guild.nexusLocation().createExplosion(5, true, true);

    this.database.executeUpdateAsync("DELETE FROM guilds WHERE name=?", guild.name());
    this.guildMap.remove(guild.name());
  }

  /**
   * Find a guild at the specified location
   *
   * @param location location to search for guild terrain.
   * @return guild that is located at the specified location, or null.
   */
  @Nullable
  public Guild findAt(@NotNull Location location) {
    for (Guild guild : this.guildMap.values()) {
      if (guild.inside(location)) {
        return guild;
      }
    }
    return null;
  }

  /**
   * Returns the guild that the specified player is in.
   *
   * @param player the player.
   * @return player's guild or null.
   */
  @Nullable
  public Guild playerGuild(@NotNull Player player) {
    return this.playerGuild(player.getName());
  }

  /**
   * Returns the guild that the specified player is in,
   * but the player is found by their nickname.
   *
   * @param player the player's nickname.
   * @return guild the player is in,
   */
  @Nullable
  public Guild playerGuild(@NotNull String player) {
    for (Guild guild : this.guildMap.values()) {
      if (guild.isMember(player)) {
        return guild;
      }
    }
    return null;
  }

  /**
   * Get the guild by its name.
   *
   * @param name name of the guild.
   * @return guild found by the specified name, or null.
   */
  @Nullable
  public Guild get(@NotNull String name) {
    return this.guildMap.get(name);
  }

  /**
   * @return List of guilds sorted by their points value.
   */
  @NotNull
  public List<Guild> leaderboard() {
    var copy = new ArrayList<>(this.guildMap.values());
    Collections.sort(copy);
    return copy;
  }

  /**
   * Return list of all guild's names, useful for argument auto-completions.
   *
   * @return List of names of all guilds.
   */
  public List<String> guildNames() {
    return this.guildMap.values().stream()
        .map(Guild::name)
        .collect(Collectors.toList());
  }

  @NotNull
  public Map<String, Guild> guildMap() {
    return this.guildMap;
  }
}
