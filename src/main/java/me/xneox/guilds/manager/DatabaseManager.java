package me.xneox.guilds.manager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.util.FileUtils;
import me.xneox.guilds.integration.Integrations;
import me.xneox.guilds.util.LogUtils;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DatabaseManager {
  private final SakuraGuildsPlugin plugin;
  private HikariDataSource source;

  public DatabaseManager(@NotNull SakuraGuildsPlugin plugin) {
    this.plugin = plugin;
    this.connect();
  }

  // Initial connection to the database, obtaining HikariDataSource.
  public void connect() {
    var config = ConfigManager.config().storage();
    var hikariConfig = new HikariConfig();

    if (config.useMySQL()) {
      hikariConfig.setJdbcUrl("jdbc:mysql://" + config.host() + ":" + config.port() + "/" + config.database());
      hikariConfig.setUsername(config.user());
      hikariConfig.setPassword(config.password());

      hikariConfig.addDataSourceProperty("cachePrepStmts", true);
      hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
      hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
      hikariConfig.addDataSourceProperty("useServerPrepStmts", true);
    } else {
      var file = FileUtils.create(new File(Integrations.DIRECTORY, "database.db"));
      hikariConfig.setJdbcUrl("jdbc:sqlite:" + file.getPath());
    }

    this.source = new HikariDataSource(hikariConfig);
  }

  @NotNull
  public ResultSet executeQuery(@NotNull String query) throws SQLException {
    try (var connection = this.source.getConnection();
        var statement = connection.prepareStatement(query);
        var rs = statement.executeQuery()) {
      return rs;
    }
  }

  public void executeUpdate(@NotNull String update, @Nullable Object... params) throws SQLException {
    try (var connection = this.source.getConnection();
        var statement = connection.prepareStatement(update)) {

      for (int i = 0; i < params.length; i++) {
        statement.setObject(i + 1, params[i]);
      }
      statement.executeUpdate();
    }
  }

  public void executeUpdateAsync(@NotNull String update, @Nullable Object... params) {
    Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
      try {
        executeUpdate(update, params);
      } catch (SQLException exception) {
        LogUtils.catchException("Could not execute asynchronous update to the database.", exception);
      }
    });
  }

  // Shut down the Hikari connection pool.
  public void shutdown() {
    this.source.close();
  }

  // Utility methods for lists because im fucking stupid

  @NotNull
  public static List<String> stringToList(@NotNull String result) {
    return new ArrayList<>(Arrays.asList(result.split(",")));
  }

  @NotNull
  public static String listToString(@NotNull List<String> list) {
    return String.join(",", list);
  }
}
