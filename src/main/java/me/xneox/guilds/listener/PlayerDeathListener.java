package me.xneox.guilds.listener;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.Colors;
import me.xneox.guilds.util.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.Nullable;

import static org.bukkit.event.entity.EntityDamageEvent.*;

public class PlayerDeathListener implements Listener {
    private final NeonGuilds plugin;

    public PlayerDeathListener(NeonGuilds plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player attacker = event.getEntity().getKiller();

        victim.getWorld().strikeLightningEffect(victim.getLocation());

        if (attacker != null) {
            User victimUser = this.plugin.getUserManager().getUser(victim);
            User attackerUser = this.plugin.getUserManager().getUser(attacker);

            victimUser.setDeaths(victimUser.getDeaths() + 1);
            attackerUser.setKills(attackerUser.getKills() + 1);
        }
    }
}
