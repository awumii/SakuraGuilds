package me.xneox.guilds.xdronizja;

import me.badbones69.crazyauctions.api.events.AuctionListEvent;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CrazyAuctionsListener implements Listener {

    @EventHandler
    public void onAuctionList(AuctionListEvent event) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            ChatUtils.sendAction(onlinePlayer, "&a" + event.getPlayer().getName() + " &7wystawił na /ah - &6"
                    + event.getItem().getI18NDisplayName() + " x" + event.getItem().getAmount() + " &7w cenie &a" + event.getPrice() + "$");
        }
    }
}