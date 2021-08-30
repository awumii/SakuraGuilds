package me.xneox.guilds.task;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.util.VisualUtils;

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
  }
}
