package me.xneox.guilds.listener;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.text.ChatUtils;
import me.xneox.guilds.util.RandomUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public final class PlayerDeathListener implements Listener {
    private final SakuraGuildsPlugin plugin;

    public PlayerDeathListener(SakuraGuildsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player attacker = event.getEntity().getKiller();

        victim.getWorld().strikeLightningEffect(victim.getLocation());

        if (attacker != null) {
            User victimUser = this.plugin.userManager().getUser(victim);
            User attackerUser = this.plugin.userManager().getUser(attacker);

            victimUser.deaths(victimUser.deaths() + 1);
            attackerUser.kills(attackerUser.kills() + 1);

            int rating = RandomUtils.getInt(80);
            victimUser.trophies(victimUser.trophies() - rating);
            attackerUser.trophies(attackerUser.trophies() + rating);

            ChatUtils.broadcastRaw(buildKillMessage(victim, attacker, rating));
        }
    }

    private String buildKillMessage(Player victim, Player attacker, int rating) {
        StringBuilder builder = new StringBuilder();

        Guild victimGuild = this.plugin.guildManager().playerGuild(victim);
        Guild attackerGuild = this.plugin.guildManager().playerGuild(attacker);
        if (victimGuild != null) {
            builder.append("&8[&a")
                    .append(victimGuild.name())
                    .append("&8] ");
        }

        builder.append("&f")
                .append(victim.getName())
                .append(" &8(&e-")
                .append(rating)
                .append("&8) &7został zabity przez ");

        if (attackerGuild != null) {
            builder.append("&8[&c")
                    .append(attackerGuild.name())
                    .append("&8] ");
        }

        builder.append("&f")
                .append(attacker.getName())
                .append(" &8(&e+")
                .append(rating)
                .append("&8)");

        return " " + builder;
    }
}
