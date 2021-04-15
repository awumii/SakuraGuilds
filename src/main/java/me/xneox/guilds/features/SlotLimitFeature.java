package me.xneox.guilds.features;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;

public class SlotLimitFeature implements Listener {
    private int slots = 50;

    @EventHandler
    public void onPreLogin(PlayerLoginEvent event) {
        if (Bukkit.getOnlinePlayers().size() > this.slots && !event.getPlayer().hasPermission("neonguilds.reservation")) {
            event.setResult(PlayerLoginEvent.Result.KICK_FULL);
        }
    }

    public static class SetSlotsCommand implements CommandExecutor {
        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            if (args.length != 1) {
                return false;
            }

            int slots = Integer.parseInt(args[0]);
            return true;
        }
    }
}
