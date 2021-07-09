package me.xneox.guilds.war;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ChunkUtils;
import me.xneox.guilds.util.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class WarListener implements Listener {
    private final NeonGuilds plugin;

    public WarListener(NeonGuilds plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Arena arena = this.getArena(player);
        if (arena != null) {
            arena.getSecondGuild().getMembers().remove(player);
            arena.getFirstGuild().getMembers().remove(player);

            this.plugin.arenaManager().loadBackup(player);
            player.teleport(ChunkUtils.WORLD.getSpawnLocation());
            ChatUtils.broadcast("&7Gracz &e" + player.getName() + " &7opuścił wojnę!");
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (this.inGame(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (this.inGame(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp() && this.inGame(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Arena arena = this.getArena(player);
        if (arena != null) {
            int points = RandomUtils.getInt(5, 20);

            if (arena.getFirstGuild().getMembers().contains(player)) {
                this.handleRespawn(event, arena.getFirstSpawn());

                player.teleport(arena.getFirstSpawn());
                arena.getSecondGuild().setPoints(arena.getSecondGuild().getPoints() + points);
                arena.forPlayers(p -> ChatUtils.sendTitle(p, "&6&lZabójstwo!",
                        "&fGildia &e" + arena.getSecondGuild().getGuild().name() + " &fzdobywa &e" + points + "★"));
            } else if (arena.getSecondGuild().getMembers().contains(player)) {
                this.handleRespawn(event, arena.getSecondSpawn());

                player.teleport(arena.getSecondSpawn());
                arena.getFirstGuild().setPoints(arena.getFirstGuild().getPoints() + points);
                arena.forPlayers(p -> ChatUtils.sendTitle(p, "&6&lZabójstwo!",
                        "&fGildia &e" + arena.getFirstGuild().getGuild().name() + " &fzdobywa &e" + points + "★"));
            }
        }
    }

    private Arena getArena(Player player) {
        for (Arena arena : this.plugin.arenaManager().arenaMap().values()) {
            if (arena.getState() != ArenaState.INGAME) {
                continue;
            }

            if (arena.getFirstGuild().getMembers().contains(player) || arena.getSecondGuild().getMembers().contains(player)) {
                return arena;
            }
        }
        return null;
    }

    private void handleRespawn(PlayerDeathEvent event, Location spawn) {
        Player player = event.getEntity();
        event.getDrops().clear();

        Bukkit.getScheduler().runTaskLater(this.plugin, () -> player.spigot().respawn(), 2L);
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            plugin.arenaManager().loadBackup(player);
            player.teleport(spawn);
        }, 4L);
    }

    private boolean inGame(Player player) {
        return getArena(player) != null;
    }
}
