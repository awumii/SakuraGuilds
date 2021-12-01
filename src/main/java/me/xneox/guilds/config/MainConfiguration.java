package me.xneox.guilds.config;

import java.time.temporal.ChronoUnit;
import me.xneox.guilds.util.NexusBuilder;
import me.xneox.guilds.util.NexusBuilder.Method;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@SuppressWarnings("ALL") // make intellij shut up about using final fields that would break the config loader.
@ConfigSerializable
public class MainConfiguration {
  private Storage storage = new Storage();
  private GuildCreation guildCreation = new GuildCreation();
  private DefaultGuildSettings defaultGuildSettings = new DefaultGuildSettings();

  @Comment("Enabling debug will show additional information in the logs.")
  private boolean debug = false;

  @ConfigSerializable
  public static class DefaultGuildSettings {
    @Comment("Example units: WEEKS, DAYS, HOURS, SECONDS")
    private ChronoUnit shieldDurationUnit = ChronoUnit.DAYS;

    @Comment("Starting duration of the shield, measured in the 'shield-duration-unit'")
    private int shieldDuration = 3;

    @Comment("If enabled, the chunk that the player is standing when creating a guild, will be claimed.")
    private boolean claimFirstChunk = true;

    @Comment("Starting amount of health.")
    private int health = 3;

    @Comment("Starting amount of money.")
    private int money = 0;

    @Comment("Starting amount of member slots.")
    private int slots = 6;

    @Comment("Starting amount of maximum claims.")
    private int maxChunks = 6;

    @Comment("""
        Starting size of the storage.
        Acceptable values: 9, 18, 27, 36, 45, 54
        """)
    private int storage = 9;

    public ChronoUnit shieldDurationUnit() {
      return this.shieldDurationUnit;
    }

    public int shieldDuration() {
      return this.shieldDuration;
    }

    public boolean claimFirstChunk() {
      return this.claimFirstChunk;
    }

    public int health() {
      return this.health;
    }

    public int money() {
      return this.money;
    }

    public int slots() {
      return this.slots;
    }

    public int maxChunks() {
      return this.maxChunks;
    }

    public int storage() {
      return this.storage;
    }
  }

  @ConfigSerializable
  public static class GuildCreation {
    @Comment("""
        SCHEMATIC - pastes schematic from plugins/SakuraGuilds/nexus.schematic
        UNDERGROUND_SPHERE_HOLLOW - create a sphere underground and place nexus there""")
    private NexusBuilder.Method method = Method.UNDERGROUND_SPHERE_HOLLOW;

    @Comment("""
        When method is SCHEMATIC - places schematic above ground by the specified amount of blocks.
        When method is UNDERGROUND_SPHERE_HOLLOW - creates sphere at the specified Y coordinate.
        """)
    private int placeOffset = 60;

    @Comment("Maximum allowed height when placing nexus schematic.")
    private int maxSchematicHeight = 120;

    @Comment("Radius of the underground hollow sphere.")
    private int sphereRadius = 5;

    @Comment("""
        If AureliumSkills is installed, you can specify the minimum level required to create a guild.
        Player level is calculated by summing up the levels of all skills.
        """)
    private int aureliumSkillsRequiredLevel = 0;

    @Comment("If Vault and an economy plugin (such as Essentials) is installed, you can specify the cost of creating a guild")
    private int economyCost = 0;

    public Method method() {
      return this.method;
    }

    public int placeOffset() {
      return this.placeOffset;
    }

    public int maxSchematicHeight() {
      return this.maxSchematicHeight;
    }

    public int sphereRadius() {
      return this.sphereRadius;
    }

    public int aureliumSkillsRequiredLevel() {
      return this.aureliumSkillsRequiredLevel;
    }

    public int economyCost() {
      return this.economyCost;
    }
  }

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

  public Storage storage() {
    return this.storage;
  }

  public GuildCreation guildCreation() {
    return this.guildCreation;
  }

  public DefaultGuildSettings defaultGuildSettings() {
    return this.defaultGuildSettings;
  }

  public boolean debug() {
    return this.debug;
  }
}
