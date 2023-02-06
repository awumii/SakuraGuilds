package me.xneox.guilds.listener;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.util.text.ChatUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * This listener handles player deaths, and ranking point changes
 */
public record PlayerDeathListener(SakuraGuildsPlugin plugin) implements Listener {

  @EventHandler
  public void onPlayerKill(PlayerDeathEvent event) {
    var victim = event.getEntity();
    var attacker = event.getEntity().getKiller();

    if (attacker != null) {
      var victimUser = this.plugin.userManager().user(victim);
      var attackerUser = this.plugin.userManager().user(attacker);

      victimUser.deaths(victimUser.deaths() + 1);
      attackerUser.kills(attackerUser.kills() + 1);

      int rating = RandomUtils.nextInt(80);
      if (victimUser.trophies() - rating < 0) {
        ChatUtils.sendMessage(attacker, "&cNie otrzymano trofeów ponieważ zabity gracz nie posiadał ani jednego.");
        return;
      }

      victimUser.trophies(victimUser.trophies() - rating);
      attackerUser.trophies(attackerUser.trophies() + rating);

      var victimGuild = this.plugin.guildManager().playerGuild(victim);
      var attackerGuild = this.plugin.guildManager().playerGuild(attacker);

      ChatUtils.broadcastRaw(" &8» &f{0} &8(&c-{1}&8) &7został zabity przez &f{2} &8(&a+{1}&8)",
          (victimGuild != null ? "&8[&c" + victimGuild.name() + "&8] &f" : "") + victim.getName(),
          rating,
          (attackerGuild != null ? "&8[&c" + attackerGuild.name() + "&8] &f" : "")
              + attacker.getName());
    }
  }
}
