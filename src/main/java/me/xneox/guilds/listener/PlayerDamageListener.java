package me.xneox.guilds.listener;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.TimeUtils;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class PlayerDamageListener implements Listener {
    private final NeonGuilds plugin;

    public PlayerDamageListener(NeonGuilds plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
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
        Guild guild = this.plugin.getGuildManager().getGuildAt(victim.getLocation());
        if (guild != null && guild.isShieldActive()) {
            ChatUtils.sendMessage(attacker, "&cTa gildia posiada tarczę wojenną przez: &6" + TimeUtils.futureMillisToTime(guild.getShield()));
            return true;
        }

        Guild victimGuild = this.plugin.getGuildManager().getGuild(victim.getName());
        Guild attackerGuild = this.plugin.getGuildManager().getGuild(attacker.getName());

        if (victimGuild == null || attackerGuild == null) {
            return false;
        }

        if (victimGuild.getName().equals(attackerGuild.getName()) || victimGuild.getAllies().contains(attackerGuild.getName())) {
            ChatUtils.sendAction(attacker, "&cNie możesz atakować członków/sojuszników gildii!");
            return true;
        }
        return false;
    }
}
