package me.xneox.guilds;

import me.xneox.guilds.command.GuildCommand;
import me.xneox.guilds.features.LiveCommand;
import me.xneox.guilds.features.RestrictionFeature;
import me.xneox.guilds.gui.*;
import me.xneox.guilds.listener.*;
import me.xneox.guilds.manager.*;
import me.xneox.guilds.task.GuildNotifierTask;
import me.xneox.guilds.task.DataSaveTask;
import me.xneox.guilds.task.PlayerTeleportTask;
import me.xneox.guilds.util.ChatUtils;
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
    private KitManager kitManager;

    @Override
    public void onEnable() {
        long ms = System.currentTimeMillis();
        this.guildManager = new GuildManager();
        this.arenaManager = new ArenaManager();
        this.userManager = new UserManager();
        this.cooldownManager = new CooldownManager();
        this.kitManager = new KitManager();
        this.inventoryManager = new InventoryManager(this);

        inventoryManager.addInventory(new ManagementGui(this));
        inventoryManager.addInventory(new ClaimGui(this));
        inventoryManager.addInventory(new MembersGui(this));
        inventoryManager.addInventory(new RankEditorGui(this));
        inventoryManager.addInventory(new AlliesGui(this));
        inventoryManager.addInventory(new UpgradesGui(this));
        inventoryManager.addInventory(new LeaderboardsGui(this));
        inventoryManager.addInventory(new WarGui(this));

        GuildCommand command = new GuildCommand(this);
        registerCommand("guild", command, command.getCommandCompleter());
        registerCommand("live", new LiveCommand(this), null);

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
        Bukkit.getScheduler().runTaskTimer(this, new DataSaveTask(this), 0L, 20 * 60L);
        Bukkit.getScheduler().runTaskTimer(this, new PlayerTeleportTask(this), 0L, 20L);
        Bukkit.getScheduler().runTaskTimer(this, new ArenaControllerTask(this), 0L, 20L);

        new PlaceholderApiHook(this).register();
        ChatUtils.broadcast("Zrestartowano w &6" + (System.currentTimeMillis() - ms) + "ms");
    }

    @Override
    public void onDisable() {
        this.guildManager.save();
        this.arenaManager.save();
        this.kitManager.save();
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

    public KitManager getKitManager() {
        return kitManager;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
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
