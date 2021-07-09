package me.xneox.guilds;

import me.xneox.guilds.command.GuildCommand;
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
import me.xneox.guilds.war.ArenaControllerTask;
import me.xneox.guilds.war.WarListener;
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

    @Override
    public void onEnable() {
        this.guildManager = new GuildManager();
        this.arenaManager = new ArenaManager();
        this.userManager = new UserManager();
        this.cooldownManager = new CooldownManager();
        this.inventoryManager = new InventoryManager(this);

        inventoryManager.register("management", new ManagementGui(this));
        inventoryManager.register("claim", new ClaimGui(this));
        inventoryManager.register("members", new MembersGui(this));
        inventoryManager.register("rank_editor", new RankEditorGui(this));
        inventoryManager.register("allies", new AlliesGui(this));
        inventoryManager.register("upgrades", new UpgradesGui(this));
        inventoryManager.register("leaderboards", new LeaderboardsGui(this));
        inventoryManager.register("war", new WarGui(this));
        inventoryManager.register("browse", new BrowseGui(this));
        inventoryManager.register("newbie", new NewbieGui(this));

        GuildCommand command = new GuildCommand(this);
        registerCommand("guild", command, command.getCommandCompleter());
        registerCommand("live", new LiveCommand(this), null);
        registerCommand("help", new GlobalHelpCommand(), null);

        registerListener(new GuildProtectionListener(this));
        registerListener(new PlayerDeathListener(this));
        registerListener(new PlayerDamageListener(this));
        registerListener(new PlayerChatListener(this));
        registerListener(new GuildAttackListener(this));
        registerListener(new ItemCooldownListener(this));
        registerListener(new PlayerPortalListener());
        registerListener(new PlayerJoinLeaveListener(this));
        registerListener(new WarListener(this));

        // To remove
        registerListener(new CompatibilityListener());

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new GuildNotifierTask(this), 0L, 40L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new DataSaveTask(this), 0L, 20 * 60L);

        Bukkit.getScheduler().runTaskTimer(this, new HoloRefreshTask(this), 0L,  120 * 20L);
        Bukkit.getScheduler().runTaskTimer(this, new PlayerTeleportTask(this), 0L, 20L);
        Bukkit.getScheduler().runTaskTimer(this, new ArenaControllerTask(this), 0L, 20L);

        new PlaceholderApiHook(this).register();
    }

    @Override
    public void onDisable() {
        this.guildManager.save();
        this.arenaManager.save();
        this.userManager.saveAll();
    }

    public GuildManager guildManager() {
        return this.guildManager;
    }

    public ArenaManager arenaManager() {
        return this.arenaManager;
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
