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
            Guild victimGuild = this.plugin.getGuildManager().getGuild(victim.getName());
            Guild attackerGuild = this.plugin.getGuildManager().getGuild(attacker.getName());

            User victimUser = this.plugin.getUserManager().getUser(victim);
            User attackerUser = this.plugin.getUserManager().getUser(attacker);

            victimUser.setDeaths(victimUser.getDeaths() + 1);
            attackerUser.setKills(attackerUser.getKills() + 1);

            if (attackerGuild == null) {
                ChatUtils.sendMessage(victim, "&7Zabójca nie posiadał gildii, punkty nie zostały odjęte!");
                return;
            }

            attackerGuild.log(attacker.getName() + " zabija " + victim.getName());

            if (victimGuild == null) {
                ChatUtils.sendMessage(attacker, "&7Zabity gracz nie posiadał gildii, punkty nie zostały naliczone!");
                return;
            }

            victimGuild.log(victim.getName() + " zostaje zabity przez " + attacker.getName());

            int points = RandomUtils.getInt(50);

            ChatUtils.broadcastCenteredMessage("");
            ChatUtils.broadcastCenteredMessage("&6&l⚔ WOJNY GILDII ⚔");
            ChatUtils.broadcastCenteredMessage("&8✠ &7Zabójca: " + "&8[&4" + attackerGuild.getName() + "&8] &c" + attacker.getName());
            ChatUtils.broadcastCenteredMessage("&8☠ &7Ofiara: &8[&2" + victimGuild.getName() + "&8] &a" + victim.getName());
            ChatUtils.broadcastCenteredMessage("&8♤ &7Wartość rankingowa: &6" + points + "⭐");
            ChatUtils.broadcastCenteredMessage("");

            if (victimGuild.getTrophies() == 0) {
                ChatUtils.sendMessage(attacker, "&cGildia przeciwnika nie posiada pucharków, ranking nie uległ zmianie!");
                ChatUtils.sendMessage(victim, "&cTwoja gildia nie posiada pucharków, ranking nie uległ zmianie!");
                return;
            }

            victimGuild.setDeaths(victimGuild.getDeaths() + 1);
            victimGuild.setTrophies(victimGuild.getTrophies() - points);

            attackerGuild.setKills(attackerGuild.getKills() + 1);
            attackerGuild.setTrophies(attackerGuild.getTrophies() + points);

            ChatUtils.guildAlert(attackerGuild, "Twoja gildia otrzymuje " + Colors.BRONZE + points + "★");
            ChatUtils.guildAlert(victimGuild, "Twoja gildia traci " + Colors.BRONZE + points + "★");
        }
    }
}
