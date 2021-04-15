package me.xneox.guilds.features.amongus;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class ConcludeCommand implements CommandExecutor {
    private final NeonGuilds plugin;

    public ConcludeCommand(NeonGuilds plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) {
            return true;
        }
        if (args[0].equals("start")) {
            ChatUtils.broadcastCenteredMessage("");
            ChatUtils.broadcastCenteredMessage("&c&l☢ GŁOSOWANIE ☢");
            ChatUtils.broadcastCenteredMessage("&7Zagłosuj na podejrzaną osobę komendą &6/sus <nick>");
            ChatUtils.broadcastCenteredMessage("&7Głosowanie zakończy się za &c30 sekund.");
            ChatUtils.broadcastCenteredMessage("");
            this.plugin.setAmongus(true);
            return true;
        } else {
            Player sus = Collections.max(this.plugin.getVoteMap().entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
            ChatUtils.broadcastCenteredMessage("");
            ChatUtils.broadcastCenteredMessage("&c&l☢ GŁOSOWANIE ☢");
            ChatUtils.broadcastCenteredMessage("&7Impostorem okazał się gracz &6" + sus.getName());
            ChatUtils.broadcastCenteredMessage("&7Ilość głosów: &c" + this.plugin.getVoteMap().get(sus));
            ChatUtils.broadcastCenteredMessage("");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kick " + sus.getName() + " byłeś impostorem.");
            this.plugin.getVoteMap().clear();
            this.plugin.setAmongus(false);
        }
        return true;
    }
}
