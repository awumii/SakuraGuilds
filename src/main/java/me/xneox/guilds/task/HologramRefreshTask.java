package me.xneox.guilds.task;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import java.util.ArrayList;
import java.util.List;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.enums.Race;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public final class HologramRefreshTask implements Runnable {
  private final SakuraGuildsPlugin plugin;

  public HologramRefreshTask(SakuraGuildsPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void run() {
    HologramsAPI.getHolograms(this.plugin).forEach(Hologram::delete);

    this.plugin.guildManager().guildMap().values().forEach(guild -> {
      guild.invitations().clear();
      guild.deleteConfirmation(false);
      VisualUtils.createGuildInfo(guild);
    });

    createRankTop();
  }

  private void createRankTop() {
    List<String> lines = new ArrayList<>();
    lines.add("&6&lTOP 10 Gildii na serwerze");
    lines.add("&7Ilość pucharków");
    lines.add("");

    for (int i = 0; i < 10; i++) {
      if (i >= this.plugin.guildManager().leaderboard().size()) {
        lines.add(" &e" + (i + 1) + ". &b-/-");
        continue;
      }

      Guild guild = this.plugin.guildManager().leaderboard().get(i);
      lines.add(" &e" + (i + 1) + ". &b" + guild.name() + " &7- &e" + guild.trophies() + "★");
    }

    VisualUtils.createHologram(
        new Location(Bukkit.getWorld("world"), 0, 60, 0),
        Material.GOLD_BLOCK,
        lines.toArray(new String[0]));
  }
}
