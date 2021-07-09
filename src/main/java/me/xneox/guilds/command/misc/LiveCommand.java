package me.xneox.guilds.command.misc;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class LiveCommand implements CommandExecutor {
    private final NeonGuilds plugin;

    public LiveCommand(NeonGuilds plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        if (args.length != 1) {
            ChatUtils.sendRaw(player, "&cPodaj link do swojego live!");
            return true;
        }
        if (this.plugin.cooldownManager().hasCooldown(player, "live")) {
            ChatUtils.sendRaw(player, "&cPoczekaj &4" + this.plugin.cooldownManager().getRemaining(player, "live") + " &cprzed zareklamowaniem.");
            return true;
        }
        for (Player target : Bukkit.getOnlinePlayers()) {
            ChatUtils.sendCenteredMessage(target, "");
            ChatUtils.sendCenteredMessage(target, "&7Gracz &a" + player.getName() + " &7prowadzi live z serwera!");
            ChatUtils.sendCenteredMessage(target, "&f&n" + args[0]);
            ChatUtils.sendCenteredMessage(target, "");
        }
        this.plugin.cooldownManager().add(player, "live", 5, TimeUnit.MINUTES);
        return true;
    }
}
