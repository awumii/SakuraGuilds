package me.xneox.guilds;

import me.xneox.guilds.command.CommandManager;
import me.xneox.guilds.command.misc.GlobalHelpCommand;
import me.xneox.guilds.command.misc.LiveCommand;
import me.xneox.guilds.gui.*;
import me.xneox.guilds.listener.*;
import me.xneox.guilds.manager.*;
import me.xneox.guilds.task.DataSaveTask;
import me.xneox.guilds.task.GuildNotifierTask;
import me.xneox.guilds.task.HoloRefreshTask;
import me.xneox.guilds.task.PlayerTeleportTask;
import me.xneox.guilds.util.gui.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class NeonGuilds extends JavaPlugin {
    private GuildManager guildManager;
    private UserManager userManager;
    private InventoryManager inventoryManager;
    private CooldownManager cooldownManager;
    private DataManager dataManager;

    @Override
    public void onEnable() {
        this.guildManager = new GuildManager();
        this.userManager = new UserManager();
        this.cooldownManager = new CooldownManager();
        this.dataManager = new DataManager();
        this.inventoryManager = new InventoryManager(this);

        // Registering inventories
        inventoryManager.register("management", new ManagementGui(this));
        inventoryManager.register("claim", new ClaimGui(this));
        inventoryManager.register("members", new MembersGui(this));
        inventoryManager.register("rank_editor", new RankEditorGui(this));
        inventoryManager.register("allies", new AlliesGui(this));
        inventoryManager.register("upgrades", new UpgradesGui(this));
        inventoryManager.register("leaderboards", new LeaderboardsGui(this));
        inventoryManager.register("newbie", new NewbieGui(this));
        inventoryManager.register("profile", new HelpProfileGui(this));

        CommandManager commandManager = new CommandManager(this);
        registerCommand("guild", commandManager.executor(), commandManager.completer());

        // Other commands that are not needed here but anyway
        registerCommand("live", new LiveCommand(this), null);
        registerCommand("help", new GlobalHelpCommand(), null);

        // Registering listeners
        registerListener(new GuildProtectionListener(this));
        registerListener(new PlayerDeathListener(this));
        registerListener(new PlayerDamageListener(this));
        registerListener(new PlayerChatListener(this));
        registerListener(new GuildAttackListener(this));
        registerListener(new ItemCooldownListener(this));
        registerListener(new PlayerJoinLeaveListener(this));
        registerListener(new PlayerMenuListener(this));
        registerListener(new CompatibilityListener());
        registerListener(new PlayerPortalListener());

        // Registering tasks
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new GuildNotifierTask(this), 0L, 40L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new DataSaveTask(this), 0L, 20 * 60L);

        Bukkit.getScheduler().runTaskTimer(this, new HoloRefreshTask(this), 0L,  120 * 20L);
        Bukkit.getScheduler().runTaskTimer(this, new PlayerTeleportTask(this), 0L, 20L);

        new PlaceholderApiHook(this).register();
    }

    @Override
    public void onDisable() {
        this.guildManager.save();
        this.userManager.save();
        this.dataManager.save();
    }

    public GuildManager guildManager() {
        return this.guildManager;
    }

    public UserManager userManager() {
        return this.userManager;
    }

    public InventoryManager inventoryManager() {
        return this.inventoryManager;
    }

    public CooldownManager cooldownManager() {
        return this.cooldownManager;
    }

    public DataManager dataManager() {
        return this.dataManager;
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
