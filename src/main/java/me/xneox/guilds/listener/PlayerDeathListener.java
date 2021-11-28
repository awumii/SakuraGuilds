package me.xneox.guilds.listener;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.LocationUtils;
import me.xneox.guilds.util.text.ChatUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * This listener handles player deaths, and ranking point changes
 */
public record PlayerDeathListener(SakuraGuildsPlugin plugin) implements Listener {

  @EventHandler
  public void onPlayerKill(PlayerDeathEvent event) {
    Player victim = event.getEntity();
    Player attacker = event.getEntity().getKiller();

    victim.getWorld().strikeLightningEffect(victim.getLocation());
    if (LocationUtils.isWorldNotAllowed(victim.getLocation())) {
      return;
    }

    if (attacker != null) {
      User victimUser = this.plugin.userManager().user(victim);
      User attackerUser = this.plugin.userManager().user(attacker);

      victimUser.deaths(victimUser.deaths() + 1);
      attackerUser.kills(attackerUser.kills() + 1);

      int rating = RandomUtils.nextInt(80);
      if (victimUser.trophies() - rating < 0) {
        ChatUtils.sendMessage(attacker, "&cNie otrzymano trofeów ponieważ zabity gracz nie posiadał ani jednego.");
        return;
      }

      victimUser.trophies(victimUser.trophies() - rating);
      attackerUser.trophies(attackerUser.trophies() + rating);

      Guild victimGuild = this.plugin.guildManager().playerGuild(victim);
      Guild attackerGuild = this.plugin.guildManager().playerGuild(attacker);

      ChatUtils.broadcastRaw(" &8» &f{0} &8(&c-{1}&8) &7został zabity przez &f{2} &8(&a+{1}&8)",
          (victimGuild != null ? "&8[&c" + victimGuild.name() + "&8] &f" : "") + victim.getName(),
          rating,
          (attackerGuild != null ? "&8[&c" + attackerGuild.name() + "&8] &f" : "")
              + attacker.getName());
    }
  }
}
