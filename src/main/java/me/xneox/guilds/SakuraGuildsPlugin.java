package me.xneox.guilds;

import fr.minuskube.inv.InventoryManager;
import java.sql.SQLException;
import me.xneox.guilds.command.CommandManager;
import me.xneox.guilds.hook.HookUtils;
import me.xneox.guilds.listener.GuildAttackListener;
import me.xneox.guilds.listener.GuildProtectionListener;
import me.xneox.guilds.listener.ItemCooldownListener;
import me.xneox.guilds.listener.PlayerChatListener;
import me.xneox.guilds.listener.PlayerDamageListener;
import me.xneox.guilds.listener.PlayerDeathListener;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.manager.CooldownManager;
import me.xneox.guilds.manager.DatabaseManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.manager.UserManager;
import me.xneox.guilds.task.DataSaveTask;
import me.xneox.guilds.task.GuildNotificatorTask;
import me.xneox.guilds.task.HologramRefreshTask;
import me.xneox.guilds.task.PlayerTeleportTask;
import me.xneox.guilds.util.LogUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class SakuraGuildsPlugin extends JavaPlugin {
  private ConfigManager configManager;
  private DatabaseManager databaseManager;
  private GuildManager guildManager;
  private UserManager userManager;
  private CooldownManager cooldownManager;
  private InventoryManager inventoryManager; // SmartInvs

  @Override
  public void onEnable() {
    this.configManager = new ConfigManager();
    this.configManager.loadConfigurations();

    this.databaseManager = new DatabaseManager(this);
    this.userManager = new UserManager(this.databaseManager);
    this.guildManager = new GuildManager(this.databaseManager);
    this.cooldownManager = new CooldownManager(this.databaseManager);

    // Setup SmartInvs
    this.inventoryManager = new InventoryManager(this);
    this.inventoryManager.init();

    // Setup the CommandManager
    var bukkitCommand = this.getCommand("guild");
    if (bukkitCommand != null) {
      var commandManager = new CommandManager(this);

      bukkitCommand.setExecutor(commandManager.executor());
      bukkitCommand.setTabCompleter(commandManager.completer());
    }

    // Registering listeners
    registerListener(new GuildProtectionListener(this));
    registerListener(new PlayerDeathListener(this));
    registerListener(new PlayerDamageListener(this));
    registerListener(new PlayerChatListener(this));
    registerListener(new GuildAttackListener(this));
    registerListener(new ItemCooldownListener(this));

    // Registering tasks
    Bukkit.getScheduler().runTaskTimerAsynchronously(this, new GuildNotificatorTask(this), 0L, 40L);
    Bukkit.getScheduler().runTaskTimerAsynchronously(this, new DataSaveTask(this), 0L, 120 * 20L);

    Bukkit.getScheduler().runTaskTimer(this, new HologramRefreshTask(this), 0L, 60 * 20L);
    Bukkit.getScheduler().runTaskTimer(this, new PlayerTeleportTask(this), 0L, 20L);

    HookUtils.register(this);
  }

  @Override
  public void onDisable() {
    try {
      this.guildManager.save();
      this.userManager.save();
      this.cooldownManager.save();
    } catch (SQLException exception) {
      LogUtils.catchException("Could not save date to the database.", exception);
    }

    this.databaseManager.shutdown();
  }

  @NotNull
  public InventoryManager inventoryManager() {
    return this.inventoryManager;
  }

  @NotNull
  public ConfigManager configManager() {
    return this.configManager;
  }

  @NotNull
  public GuildManager guildManager() {
    return this.guildManager;
  }

  @NotNull
  public UserManager userManager() {
    return this.userManager;
  }

  @NotNull
  public CooldownManager cooldownManager() {
    return this.cooldownManager;
  }

  // Utility methods

  private void registerListener(@NotNull Listener listener) {
    Bukkit.getPluginManager().registerEvents(listener, this);
  }

  @NotNull
  public static SakuraGuildsPlugin get() {
    return SakuraGuildsPlugin.getPlugin(SakuraGuildsPlugin.class);
  }
}
