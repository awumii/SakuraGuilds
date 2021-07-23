package me.xneox.guilds.listener;

import me.xneox.guilds.NeonGuilds;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public record PlayerJoinLeaveListener(NeonGuilds plugin) implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.plugin.userManager().getUser(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.plugin.userManager().removeUser(event.getPlayer().getName());
    }
}
