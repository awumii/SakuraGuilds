package me.xneox.guilds.task;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.text.ChatUtils;
import me.xneox.guilds.util.LocationUtils;
import me.xneox.guilds.util.VisualUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public final class PlayerTeleportTask implements Runnable {
  private final SakuraGuildsPlugin plugin;

  public PlayerTeleportTask(SakuraGuildsPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void run() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      User user = this.plugin.userManager().user(player);
      if (user.teleportTarget() == null) {
        continue;
      }

      if (!LocationUtils.equalsSoft(player.getLocation(), user.startLocation())) {
        ChatUtils.sendTitle(player, "&b&lTELEPORTACJA ➥", "&cPoruszyłeś się w trakcie teleportacji!");

        user.clearTeleport();
        VisualUtils.sound(player, Sound.BLOCK_ANVIL_DESTROY);
        continue;
      }

      if (user.teleportCountdown() > 0) {
        user.teleportCountdown(user.teleportCountdown() - 1);
        ChatUtils.sendTitle(player, "&b&lTELEPORTACJA ➥", "&7Zostaniesz przeteleportowany za &e" + user.teleportCountdown() + " sekund...");

        VisualUtils.sound(player, Sound.BLOCK_NOTE_BLOCK_GUITAR);
        continue;
      }

      ChatUtils.sendTitle(player, "&b&lTELEPORTACJA ➥", "&6Rozpoczynanie &7teleportacji...");
      player.teleportAsync(user.teleportTarget()).thenAccept(bool -> {
        ChatUtils.sendTitle(player, "&b&lTELEPORTACJA ➥", "&7Zostałeś przeteleportowany &apomyślnie!");
        VisualUtils.sound(player, Sound.BLOCK_PISTON_EXTEND);
        user.clearTeleport();
      });
    }
  }
}
