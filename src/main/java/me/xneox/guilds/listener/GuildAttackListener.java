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
        GuildManager manager = this.plugin.getGuildManager();

        if (this.plugin.getCooldownManager().hasCooldown(player, "nexus")) {
            return;
        }

        this.plugin.getCooldownManager().add(player, "nexus", 1, TimeUnit.SECONDS);
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.END_PORTAL_FRAME
                && (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {

            Guild guild = manager.getGuildAt(player.getLocation());
            if (guild == null) {
                return;
            }

            if (!guild.getNexusLocation().equals(event.getClickedBlock().getLocation())) {
                return;
            }

            if (guild.getNexusLocation().distance(player.getLocation()) > 5) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + player.getName() + " 24h Próba wykorzystania exploitu w celu ataku nexusa.");
                return;
            }

            if (guild.isMember(player.getName())) {
                this.plugin.getInventoryManager().open("management", player);
                return;
            }

            if (guild.isShieldActive()) {
                ChatUtils.sendMessage(player, "&cTa gildia posiada ochronę przez " + TimeUtils.futureMillisToTime(guild.getShield()));
                return;
            }

            ChatUtils.broadcast("&c[" + manager.getGuild(player).getName() +  "] " + player.getName() + " &7zaatakował nexusa &6" + guild.getName() + "!");

            if (guild.getHealth() <= 1) {
                ChatUtils.broadcastCenteredMessage(" ");
                ChatUtils.broadcastCenteredMessage("&6&l⚔️ WOJNA   ⚔️");
                ChatUtils.broadcastCenteredMessage("&7Gildia &6" + guild.getName() + " &7została przebita przez &c" + manager.getGuild(player).getName());
                ChatUtils.broadcastCenteredMessage("&7Nexusa zniszczył: " + manager.getGuild(player).getDisplayName(player));
                ChatUtils.broadcastCenteredMessage(" ");

                manager.deleteGuild(guild);

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    VisualUtils.playSound(onlinePlayer, Sound.ENTITY_ENDER_DRAGON_DEATH);
                }
            } else {
                guild.setShield(Duration.ofDays(1));
                guild.setHealth(guild.getHealth() - 1);
                ChatUtils.broadcast("&7Gildia &6" + guild.getName() + " &7posiada jeszcze &c" + guild.getHealth() + " żyć!");
            }
        }
    }
}
