package me.xneox.guilds.listener;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.text.ChatUtils;
import me.xneox.guilds.util.text.TimeUtils;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public final class PlayerDamageListener implements Listener {
  private final SakuraGuildsPlugin plugin;

  public PlayerDamageListener(SakuraGuildsPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onDamage(EntityDamageByEntityEvent event) {
    if (!event.getEntity().getWorld().getName().startsWith("world")) {
      return;
    }

    if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow) {
      ProjectileSource source = ((Arrow) event.getDamager()).getShooter();
      if (source instanceof Player) {
        if (this.isProtected((Player) event.getEntity(), (Player) source)) {
          event.setCancelled(true);
        }
      }
    }

    if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
      if (this.isProtected((Player) event.getEntity(), (Player) event.getDamager())) {
        event.setCancelled(true);
      }
    }
  }

  /**
   * @param victim Attacked player.
   * @param attacker The player who attacked.
   * @return whenever the event should be cancelled.
   */
  private boolean isProtected(Player victim, Player attacker) {
    if (victim.equals(attacker)) {
      return false;
    }

    Guild guild = this.plugin.guildManager().findAt(victim.getLocation());
    if (guild != null && guild.isShieldActive()) {
      ChatUtils.showActionBar(attacker, "&cTa gildia posiada tarczę wojenną przez: &6"
              + TimeUtils.futureMillisToTime(guild.shieldDuration()));
      return true;
    }

    Guild victimGuild = this.plugin.guildManager().playerGuild(victim.getName());
    Guild attackerGuild = this.plugin.guildManager().playerGuild(attacker.getName());

    if (victimGuild == null || attackerGuild == null) {
      return false;
    }

    if (victimGuild.name().equals(attackerGuild.name())
        || victimGuild.allies().contains(attackerGuild.name())) {
      ChatUtils.showActionBar(attacker, "&cNie możesz atakować członków/sojuszników gildii!");
      return true;
    }
    return false;
  }
}
