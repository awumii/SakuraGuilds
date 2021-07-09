package me.xneox.guilds.listener;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.TimeUtils;
import me.xneox.guilds.util.VisualUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class GuildAttackListener implements Listener {
    private final NeonGuilds plugin;

    public GuildAttackListener(NeonGuilds plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        GuildManager manager = this.plugin.guildManager();

        if (this.plugin.cooldownManager().hasCooldown(player, "nexus")) {
            return;
        }

        this.plugin.cooldownManager().add(player, "nexus", 1, TimeUnit.SECONDS);
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.END_PORTAL_FRAME
                && (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {

            Guild guild = manager.findAt(player.getLocation());
            if (guild == null) {
                return;
            }

            if (!guild.nexusLocation().equals(event.getClickedBlock().getLocation())) {
                return;
            }

            double distance = guild.nexusLocation().distance(player.getLocation());
            if (distance > 4.5) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + player.getName() + " 30m Zaatakowanie nexusa z dystansu " + distance + "m (exploit?)");
                return;
            }

            if (guild.isMember(player.getName())) {
                this.plugin.inventoryManager().open("management", player);
                return;
            }

            if (guild.isShieldActive()) {
                ChatUtils.sendMessage(player, "&cTa gildia posiada ochronę przez " + TimeUtils.futureMillisToTime(guild.shieldDuration()));
                return;
            }

            Guild attackerGuild = manager.playerGuild(player);
            ChatUtils.broadcast("&c[" + attackerGuild.name() +  "] " + player.getName() + " &7zaatakował nexusa &6" + guild.name() + "!");

            if (guild.health() <= 1) {
                ChatUtils.broadcastCenteredMessage(" ");
                ChatUtils.broadcastCenteredMessage("&6&lZNISZCZENIE NEXUSA GILDII");
                ChatUtils.broadcastCenteredMessage(" ");
                ChatUtils.broadcastCenteredMessage("&7Gildia &6" + guild.name() + " &7została przebita przez &c" + manager.playerGuild(player).name());
                ChatUtils.broadcastCenteredMessage("&7Nexusa zniszczył: " + player.getName());
                ChatUtils.broadcastCenteredMessage(" ");

                manager.delete(guild);

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    VisualUtils.sound(onlinePlayer, Sound.ENTITY_ENDER_DRAGON_DEATH);
                }
            } else {
                guild.shieldDuration(Duration.ofDays(1));
                guild.health(guild.health() - 1);
                ChatUtils.broadcast("&7Gildia &6" + guild.name() + " &7posiada jeszcze &c" + guild.health() + " żyć!");
            }
        }
    }
}
