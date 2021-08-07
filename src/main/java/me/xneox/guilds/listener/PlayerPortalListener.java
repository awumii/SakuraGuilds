package me.xneox.guilds.listener;

import java.util.Date;
import me.xneox.guilds.util.text.ChatUtils;
import me.xneox.guilds.util.text.TimeUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerPortalListener implements Listener {
  private final Date END_OPEN = TimeUtils.parseDate("11/08/2021 19:00");

  @EventHandler
  public void onPortal(PlayerPortalEvent event) {
    Player player = event.getPlayer();

    if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL && new Date().before(END_OPEN)) {
      ChatUtils.sendMessage(player, "&7Portale do endu są jeszcze wyłączone.");
      ChatUtils.sendMessage(player, "&7Zostaną włączone za: &c" + TimeUtils.futureMillisToTime(END_OPEN.getTime()));
      event.setCancelled(true);
    }
  }
}
