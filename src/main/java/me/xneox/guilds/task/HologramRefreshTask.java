package me.xneox.guilds.task;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.integration.HolographicDisplaysHook;
import me.xneox.guilds.integration.Integrations;

/**
 * This task removes all holograms created by this plugin, and refreshes the valid holograms.
 */
public record HologramRefreshTask(SakuraGuildsPlugin plugin) implements Runnable {

  @Override
  public void run() {
    // Remove all HolographicDisplays holograms created by this plugin.
    if (Integrations.HOLOGRAMS_AVAILABLE) {
      HolographicDisplaysHook.clearHolograms();
    }

    // Update guild holograms and clear cache
    for (Guild guild : this.plugin.guildManager().guildMap().values()) {
      if (Integrations.HOLOGRAMS_AVAILABLE) {
        HolographicDisplaysHook.createGuildInfo(guild);
      }

      // Expire sent invitations while we're at it
      guild.playerInvitations().clear();
      guild.allyInvitations().clear();
      guild.deleteConfirmation(false);
    }
  }
}
