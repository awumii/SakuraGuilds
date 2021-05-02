package me.xneox.guilds.task;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.RankedUtils;
import me.xneox.guilds.util.VisualUtils;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class HoloRefreshTask implements Runnable {
    private final NeonGuilds plugin;

    public HoloRefreshTask(NeonGuilds plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        HologramsAPI.getHolograms(this.plugin).forEach(Hologram::delete);

        this.plugin.getGuildManager().getGuildMap().values().forEach(guild -> {
            guild.getInvitations().clear();
            guild.setDeleteConfirm(false);

            VisualUtils.createGuildInfo(guild);
            guild.getBuildings().forEach(building -> building.refresh(guild));
        });

        createRankTop();
    }

    private void createRankTop() {
        List<Guild> leaderboard = RankedUtils.getLeaderboard(this.plugin.getGuildManager().getGuildMap().values());
        List<String> lines = new ArrayList<>();
        lines.add("&6&l10 Najlepszych gildii");
        lines.add("&7Ilość pucharków");
        lines.add("");

        for (int i = 0; i < 10; i++) {
            if (i >= leaderboard.size()) {
                lines.add(" &e" + (i + 1) + ". &b-/-");
                continue;
            }

            Guild guild = leaderboard.get(i);
            lines.add(" &e" + (i + 1) + ". &b" + guild.getName() + " &7- &e" + guild.getTrophies() + "★");
        }

        VisualUtils.createHologram(this.plugin.getArenaManager().getLeaderboard(), Material.GOLD_BLOCK, lines.toArray(new String[0]));
    }
}
