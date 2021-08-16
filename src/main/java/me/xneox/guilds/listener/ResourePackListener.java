package me.xneox.guilds.listener;

import fr.xephi.authme.events.LoginEvent;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class ResourePackListener implements Listener {

  /*
  @EventHandler
  public void onAuthLogin(LoginEvent event) {
    Player player = event.getPlayer();

    if (!player.hasResourcePack()) {
      player.setResourcePack("http://dronizja.pl/uploads/slimefun.zip"); //b06604a8577c9e4d282e5826df1d3667aa8a38e2
    }
  }

  @EventHandler
  public void onResourcePack(PlayerResourcePackStatusEvent event) {
    Player player = event.getPlayer();
    switch (event.getStatus()) {
      case DECLINED -> {
        ChatUtils.sendCenteredMessage(player, "");
        ChatUtils.sendCenteredMessage(player, "&4&lODRZUCONO PACZKĘ TEKSTUR");
        ChatUtils.sendCenteredMessage(player, "&cNiektóre funkcje serwera nie będą działać poprawnie.");
        ChatUtils.sendCenteredMessage(player, "&cAby to naprawić, usuń serwer ze swojej listy, dodaj");
        ChatUtils.sendCenteredMessage(player, "&cgo ponownie i zaakceptuj paczkę zasobów.");
        ChatUtils.sendCenteredMessage(player, "");
        VisualUtils.sound(player, Sound.BLOCK_ANVIL_DESTROY);
      }
      case ACCEPTED -> ChatUtils.sendNoPrefix(player, "&#3BB273Paczka zasobów zaakceptowana, przesyłanie...");
      case SUCCESSFULLY_LOADED -> ChatUtils.sendNoPrefix(player, "&#3BB273Załadowano, tekstury powinny teraz działać poprawnie");
      case FAILED_DOWNLOAD -> {
        ChatUtils.sendNoPrefix(player, "&#BC412BWystąpił błąd podczas ładowania tekstur.");
        VisualUtils.sound(player, Sound.BLOCK_ANVIL_DESTROY);
      }
    }

    for (Player o : Bukkit.getOnlinePlayers()) {
      if (o.hasPermission("sakuraguilds.resourcepack")) {
        ChatUtils.sendNoPrefix(o, "&c[TXT] " + player.getName() + " -> " + event.getStatus().name());
      }
    }
  }

   */
}
