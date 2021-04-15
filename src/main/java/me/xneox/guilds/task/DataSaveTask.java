package me.xneox.guilds.task;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.util.VisualUtils;

public class DataSaveTask implements Runnable {
    private final NeonGuilds plugin;

    public DataSaveTask(NeonGuilds plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        HologramsAPI.getHolograms(this.plugin).forEach(Hologram::delete);

        this.plugin.getGuildManager().getGuildMap().values().forEach(guild -> {
            guild.getInvitations().clear();
            guild.setDeleteConfirm(false);

            VisualUtils.createGuildInfo(guild);
        });

        this.plugin.getGuildManager().save();
    }
}
