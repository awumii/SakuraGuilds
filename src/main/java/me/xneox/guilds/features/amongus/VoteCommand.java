package me.xneox.guilds.features.amongus;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VoteCommand implements CommandExecutor {
    private final NeonGuilds plugin;

    public VoteCommand(NeonGuilds plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        if (args.length != 1) {
            ChatUtils.sendMessage(player, "&7Musisz podać nazwę podejrzanego gracza!");
            return true;
        }

        if (!this.plugin.isAmongus()) {
            ChatUtils.sendMessage(player, "&cRunda amongus nie trwa teraz!");
            return true;
        }

        Player sus = Bukkit.getPlayer(args[0]);
        if (sus == null) {
            ChatUtils.sendMessage(player, "&cNie ma takiego gracza!");
            return true;
        }

        ChatUtils.broadcastRaw(" &4☢ " + player.getName() + " uważa, że " + args[0] + " jest podejrzany...");
        this.plugin.getVoteMap().put(sus, this.plugin.getVoteMap().getOrDefault(sus, 0) + 1);
        return true;
    }
}
