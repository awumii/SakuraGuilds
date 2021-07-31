package me.xneox.guilds.task;

import me.xneox.guilds.SakuraGuildsPlugin;

public final class DataSaveTask implements Runnable {
    private final SakuraGuildsPlugin plugin;

    public DataSaveTask(SakuraGuildsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        this.plugin.onDisable();
    }
}
