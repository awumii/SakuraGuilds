package me.xneox.guilds.task;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.LocationUtils;
import me.xneox.guilds.util.VisualUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public record PlayerTeleportTask(NeonGuilds plugin) implements Runnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            User user = this.plugin.userManager().getUser(player);
            if (user.getTeleportTarget() != null) {
                if (!LocationUtils.equalsSoft(player.getLocation(), user.getStartLocation())) {
                    ChatUtils.sendTitle(player, "&b&lTELEPORTACJA ➥",
                            "&cPoruszyłeś się w trakcie teleportacji!");
                    user.clearTeleport();

                    VisualUtils.sound(player, Sound.BLOCK_ANVIL_DESTROY);
                    continue;
                }

                if (user.getTeleportCountdown() > 0) {
                    user.setTeleportCountdown(user.getTeleportCountdown() - 1);
                    ChatUtils.sendTitle(player, "&b&lTELEPORTACJA ➥",
                            "&7Zostaniesz przeteleportowany za &e" + user.getTeleportCountdown() + " sekund...");

                    VisualUtils.sound(player, Sound.BLOCK_NOTE_BLOCK_GUITAR);
                } else {
                    ChatUtils.sendTitle(player, "&b&lTELEPORTACJA ➥",
                            "&7Zostałeś przeteleportowany &apomyślnie!");
                    player.teleport(user.getTeleportTarget());
                    user.clearTeleport();

                    VisualUtils.sound(player, Sound.BLOCK_PISTON_EXTEND);
                }
            }
        }
    }
}
