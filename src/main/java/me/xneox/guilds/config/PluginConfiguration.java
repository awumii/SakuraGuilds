package me.xneox.guilds.config;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@SuppressWarnings("ALL") // make intellij shut up about using final fields that would break the config loader.
@ConfigSerializable
public class PluginConfiguration {
  private Storage storage = new Storage();

  @Comment("Enabling debug will show additional information in the logs.")
  private boolean debug = false;

  @ConfigSerializable
  public static class Storage {

    @Comment("""
        false - use SQLite for storage
        true - use MYSQL for storage
        (!) This option requires a restart. Changing storage type will reset your current data.""")
    private boolean useMysql = false;

    private String host = "127.0.0.1";
    private int port = 3306;
    private String database = "database!";
    private String user = "username!";
    private String password = "password!";

    public boolean useMySQL() {
      return this.useMysql;
    }

    public String host() {
      return this.host;
    }

    public int port() {
      return this.port;
    }

    public String database() {
      return this.database;
    }

    public String user() {
      return this.user;
    }

    public String password() {
      return this.password;
    }
  }

  @NotNull
  public Storage storage() {
    return this.storage;
  }

  public boolean debug() {
    return this.debug;
  }
}
