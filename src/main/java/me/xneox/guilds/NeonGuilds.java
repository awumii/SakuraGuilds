package me.xneox.guilds;

import me.xneox.guilds.command.GuildCommand;
import me.xneox.guilds.gui.*;
import me.xneox.guilds.listener.*;
import me.xneox.guilds.manager.*;
import me.xneox.guilds.task.DataSaveTask;
import me.xneox.guilds.task.GuildNotifierTask;
import me.xneox.guilds.task.HoloRefreshTask;
import me.xneox.guilds.task.PlayerTeleportTask;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.gui.InventoryManager;
import me.xneox.guilds.war.ArenaControllerTask;
import me.xneox.guilds.war.WarListener;
import me.xneox.guilds.xdronizja.CrazyAuctionsListener;
import me.xneox.guilds.xdronizja.HelpCommand;
import me.xneox.guilds.xdronizja.LiveCommand;
import me.xneox.guilds.xdronizja.RestrictionFeature;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class NeonGuilds extends JavaPlugin {
    private GuildManager guildManager;
    private ArenaManager arenaManager;
    private UserManager userManager;
    private InventoryManager inventoryManager;
    private CooldownManager cooldownManager;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        long ms = System.currentTimeMillis();
        this.guildManager = new GuildManager();
        this.arenaManager = new ArenaManager();
        this.userManager = new UserManager();
        this.cooldownManager = new CooldownManager();
        this.configManager = new ConfigManager();
        this.inventoryManager = new InventoryManager(this);


        inventoryManager.register("management", new ManagementGui(this));
        inventoryManager.register("claim", new ClaimGui(this));
        inventoryManager.register("members", new MembersGui(this));
        inventoryManager.register("rank_editor", new RankEditorGui(this));
        inventoryManager.register("allies", new AlliesGui(this));
        inventoryManager.register("buildings", new BuildingsGui(this));
        inventoryManager.register("leaderboards", new LeaderboardsGui(this));
        inventoryManager.register("war", new WarGui(this));
        inventoryManager.register("browse", new BrowseGui(this));
        inventoryManager.register("newbie", new NewbieGui(this));

        GuildCommand command = new GuildCommand(this);
        registerCommand("guild", command, command.getCommandCompleter());
        registerCommand("live", new LiveCommand(this), null);
        registerCommand("help", new HelpCommand(), null);

        registerListener(new GuildProtectionListener(this));
        registerListener(new PlayerDeathListener(this));
        registerListener(new PlayerDamageListener(this));
        registerListener(new PlayerChatListener(this));
        registerListener(new GuildAttackListener(this));
        registerListener(new ItemCooldownListener(this));
        registerListener(new RestrictionFeature(this));
        registerListener(new PlayerJoinLeaveListener(this));
        registerListener(new CrazyAuctionsListener());
        registerListener(new WarListener(this));

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new GuildNotifierTask(this), 0L, 40L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new DataSaveTask(this), 0L, 20 * 60L);

        Bukkit.getScheduler().runTaskTimer(this, new HoloRefreshTask(this), 0L,  30 * 20L);
        Bukkit.getScheduler().runTaskTimer(this, new PlayerTeleportTask(this), 0L, 20L);
        Bukkit.getScheduler().runTaskTimer(this, new ArenaControllerTask(this), 0L, 20L);

        new PlaceholderApiHook(this).register();
        ChatUtils.broadcast("Zrestartowano w &6" + (System.currentTimeMillis() - ms) + "ms");
    }

    @Override
    public void onDisable() {
        this.guildManager.save();
        this.arenaManager.save();
        this.userManager.saveAll();
    }

    public GuildManager getGuildManager() {
        return guildManager;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
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
}
