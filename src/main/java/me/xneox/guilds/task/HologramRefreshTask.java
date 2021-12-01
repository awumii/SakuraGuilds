package me.xneox.guilds.task;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.VisualUtils;

/**
 * This task removes all holograms created by this plugin, and refreshes the valid holograms.
 */
public record HologramRefreshTask(SakuraGuildsPlugin plugin) implements Runnable {

  @Override
  public void run() {
    HologramsAPI.getHolograms(this.plugin).forEach(Hologram::delete);

    for (Guild guild : this.plugin.guildManager().guildMap().values()) {
      VisualUtils.createGuildInfo(guild);

      // Expire sent invitations while we're at it
      guild.playerInvitations().clear();
      guild.allyInvitations().clear();
      guild.deleteConfirmation(false);
    }
  }
}
