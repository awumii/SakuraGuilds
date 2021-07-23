package me.xneox.guilds.listener;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public record PlayerDeathListener(NeonGuilds plugin) implements Listener {
    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player attacker = event.getEntity().getKiller();

        victim.getWorld().strikeLightningEffect(victim.getLocation());

        if (attacker != null) {
            User victimUser = this.plugin.userManager().getUser(victim);
            User attackerUser = this.plugin.userManager().getUser(attacker);

            victimUser.setDeaths(victimUser.getDeaths() + 1);
            attackerUser.setKills(attackerUser.getKills() + 1);
        }
    }
}
