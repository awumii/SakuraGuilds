package me.xneox.guilds;

import me.xneox.guilds.command.GuildCommand;
import me.xneox.guilds.features.LiveCommand;
import me.xneox.guilds.features.RestrictionFeature;
import me.xneox.guilds.features.amongus.ConcludeCommand;
import me.xneox.guilds.features.amongus.VoteCommand;
import me.xneox.guilds.gui.*;
import me.xneox.guilds.listener.*;
import me.xneox.guilds.manager.*;
import me.xneox.guilds.task.GuildNotifierTask;
import me.xneox.guilds.task.DataSaveTask;
import me.xneox.guilds.task.PlayerTeleportTask;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.gui.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class NeonGuilds extends JavaPlugin {
    private GuildManager guildManager;
    private ArenaManager arenaManager;
    private UserManager userManager;
    private InventoryManager inventoryManager;
    private CooldownManager cooldownManager;

    private final Map<Player, Integer> voteMap = new HashMap<>();
    private boolean amongus;

    @Override
    public void onEnable() {
        long ms = System.currentTimeMillis();
        this.guildManager = new GuildManager();
        this.arenaManager = new ArenaManager();
        this.userManager = new UserManager();
        this.cooldownManager = new CooldownManager();
        this.inventoryManager = new InventoryManager(this);

        inventoryManager.addInventory(new ManagementGui(this));
        inventoryManager.addInventory(new ClaimGui(this));
        inventoryManager.addInventory(new MembersGui(this));
        inventoryManager.addInventory(new RankEditorGui(this));
        inventoryManager.addInventory(new AlliesGui(this));
        inventoryManager.addInventory(new UpgradesGui(this));
        inventoryManager.addInventory(new LeaderboardsGui(this));

        GuildCommand command = new GuildCommand(this);
        registerCommand("guild", command, command.getCommandCompleter());
        registerCommand("live", new LiveCommand(this), null);
        registerCommand("sus", new VoteCommand(this), null);
        registerCommand("amongus", new ConcludeCommand(this), null);

        registerListener(new GuildProtectionListener(this));
        registerListener(new PlayerDeathListener(this));
        registerListener(new PlayerDamageListener(this));
        registerListener(new PlayerChatListener(this));
        registerListener(new GuildAttackListener(this));
        registerListener(new ItemCooldownListener(this));
        registerListener(new RestrictionFeature(this));
        registerListener(new PlayerJoinLeaveListener(this));
        registerListener(new CrazyAuctionsListener());

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new GuildNotifierTask(this), 0L, 10L);
        Bukkit.getScheduler().runTaskTimer(this, new DataSaveTask(this), 0L, 20 * 30L);
        Bukkit.getScheduler().runTaskTimer(this, new PlayerTeleportTask(this), 0L, 20L);

        new PlaceholderApiHook(this).register();
        ChatUtils.broadcast("Restart took &6" + (System.currentTimeMillis() - ms) + "ms");
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

    public Map<Player, Integer> getVoteMap() {
        return voteMap;
    }

    public void setAmongus(boolean amongus) {
        this.amongus = amongus;
    }

    public boolean isAmongus() {
        return amongus;
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
