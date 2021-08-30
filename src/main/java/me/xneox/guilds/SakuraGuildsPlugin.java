package me.xneox.guilds;

import java.sql.SQLException;
import me.xneox.guilds.command.CommandManager;
import me.xneox.guilds.command.misc.GlobalHelpCommand;
import me.xneox.guilds.command.misc.LiveCommand;
import me.xneox.guilds.command.misc.SendMessageCommand;
import me.xneox.guilds.listener.GuildAttackListener;
import me.xneox.guilds.listener.GuildProtectionListener;
import me.xneox.guilds.listener.ItemCooldownListener;
import me.xneox.guilds.listener.PlayerChatListener;
import me.xneox.guilds.listener.PlayerDamageListener;
import me.xneox.guilds.listener.PlayerDeathListener;
import me.xneox.guilds.manager.CooldownManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.manager.UserManager;
import me.xneox.guilds.placeholder.MainPlaceholderExpansion;
import me.xneox.guilds.placeholder.TopPlaceholderExpansion;
import me.xneox.guilds.task.DataSaveTask;
import me.xneox.guilds.task.GuildNotificatorTask;
import me.xneox.guilds.task.HologramRefreshTask;
import me.xneox.guilds.task.PlayerTeleportTask;
import me.xneox.guilds.util.DatabaseUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SakuraGuildsPlugin extends JavaPlugin {
  private GuildManager guildManager;
  private UserManager userManager;
  private CooldownManager cooldownManager;

  @Override
  public void onEnable() {
    try {
      DatabaseUtils.connect();
      this.userManager = new UserManager();
      this.guildManager = new GuildManager();
      this.cooldownManager = new CooldownManager();
    } catch (SQLException ex) {
      ex.printStackTrace();
      Bukkit.getPluginManager().disablePlugin(this);
    }

    CommandManager commandManager = new CommandManager(this);
    registerCommand("guild", commandManager.executor(), commandManager.completer());

    // Other commands that are not needed here but anyway
    registerCommand("sendraw", new SendMessageCommand(), null);
    registerCommand("live", new LiveCommand(this), null);
    registerCommand("help", new GlobalHelpCommand(), null);

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

    //Bukkit.getScheduler().runTaskTimer(this,
    //    () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "su reloadplugin LevelledMobs"), 20L, 20L * 1500L);

    new MainPlaceholderExpansion(this).register();
    new TopPlaceholderExpansion(this).register();
  }

  @Override
  public void onDisable() {
    this.guildManager.save();
    this.userManager.save();
    this.cooldownManager.save();
  }

  public GuildManager guildManager() {
    return this.guildManager;
  }

  public UserManager userManager() {
    return this.userManager;
  }

  public CooldownManager cooldownManager() {
    return this.cooldownManager;
  }

  private void registerCommand(String name, CommandExecutor executor, TabCompleter tabCompleter) {
    PluginCommand command = this.getCommand(name);
    if (command == null) {
      this.getLogger().severe("Could not register command " + name);
      return;
    }

    command.setExecutor(executor);
    if (tabCompleter != null) {
      command.setTabCompleter(tabCompleter);
    }
  }

  private void registerListener(Listener listener) {
    Bukkit.getPluginManager().registerEvents(listener, this);
  }

  public static SakuraGuildsPlugin get() {
    return SakuraGuildsPlugin.getPlugin(SakuraGuildsPlugin.class);
  }
}
