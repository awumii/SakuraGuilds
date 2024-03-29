package me.xneox.guilds.listener;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.util.text.ChatUtils;
import me.xneox.guilds.util.text.TimeUtils;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * This listener handles player pvp.
 */
public record PlayerDamageListener(SakuraGuildsPlugin plugin) implements Listener {

  @EventHandler
  public void onDamage(EntityDamageByEntityEvent event) {
    if (event.getEntity() instanceof Player damaged && event.getDamager() instanceof Arrow arrow) {
      var source = arrow.getShooter();
      if (source instanceof Player damager && this.isProtected(damaged, damager)) {
        event.setCancelled(true);
      }
    }

    if (event.getEntity() instanceof Player damaged && event.getDamager() instanceof Player damager
        && this.isProtected(damaged, damager)) {
      event.setCancelled(true);
    }
  }

  /**
   * @param damaged  Attacked player.
   * @param attacker The player who attacked.
   * @return whenever the event should be cancelled.
   */
  private boolean isProtected(Player damaged, Player attacker) {
    if (damaged.equals(attacker)) {
      return false;
    }

    var guild = this.plugin.guildManager().findAt(damaged.getLocation());
    if (guild != null && guild.isShieldActive()) {
      ChatUtils.sendTitle(attacker, "", "&cTarcza wojenna blokuje atak! &8(&6"
          + TimeUtils.futureMillisToTime(guild.shieldDuration()) + "&8)");
      return true;
    }

    var victimGuild = this.plugin.guildManager().playerGuild(damaged.getName());
    var attackerGuild = this.plugin.guildManager().playerGuild(attacker.getName());

    if (victimGuild == null || attackerGuild == null) {
      return false;
    }

    if (victimGuild.name().equals(attackerGuild.name())
        || victimGuild.allies().contains(attackerGuild)) {
      ChatUtils.sendTitle(attacker, "", "&cNie możesz atakować członków/sojuszników gildii!");
      return true;
    }
    return false;
  }
}
