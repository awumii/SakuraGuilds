package me.xneox.guilds.task;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Arena;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.entity.Player;

public class ArenaControllerTask implements Runnable {
    private final NeonGuilds plugin;

    public ArenaControllerTask(NeonGuilds plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Guild value : this.plugin.getGuildManager().getGuildMap().values()) {
            if (value.getWarEnemy() == null) {
                continue;
            }

            if (value.getWarCountdown() != 0) {
                value.setWarCountdown(value.getWarCountdown() - 1);
            } else {
                Arena arena = this.plugin.getArenaManager().getFreeArena();
                if (arena == null) {
                    ChatUtils.guildAlert(value, "&7Nie znaleziono wolnej areny. Dodano czas do oczekiwania...");
                    value.setWarCountdown(60);
                    continue;
                }

                for (Player warMember : value.getWarMembers()) {
                    if (arena.isFirstGuildTeleported()) {
                        warMember.teleport(arena.getSecondSpawn());
                    } else {
                        warMember.teleport(arena.getFirstSpawn());
                    }
                }

                arena.setFirstGuildTeleported(true);
                value.setWarEnemy(null);
                arena.setOccupied(true);
                arena.setCountdown(300);
            }
        }

        for (Arena arena : this.plugin.getArenaManager().getArenaMap().values()) {
            if (arena.isOccupied()) {
                if (arena.getCountdown() != 0) {
                    arena.setCountdown(arena.getCountdown() - 1);
                } else {
                    arena.setOccupied(false);
                    for (Player value : arena.getPlayerMap().values()) {
                    }
                }
            }
        }
    }
}
