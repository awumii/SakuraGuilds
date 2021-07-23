package me.xneox.guilds.task;

import me.xneox.guilds.NeonGuilds;

public record DataSaveTask(NeonGuilds plugin) implements Runnable {

    @Override
    public void run() {
        this.plugin.userManager().save();
        this.plugin.guildManager().save();
    }
}
