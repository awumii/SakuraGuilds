package me.xneox.guilds.task;

import me.xneox.guilds.NeonGuilds;

public class DataSaveTask implements Runnable {
    private final NeonGuilds plugin;

    public DataSaveTask(NeonGuilds plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        this.plugin.userManager().save();
        this.plugin.guildManager().save();
    }
}
