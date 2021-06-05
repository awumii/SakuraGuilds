package me.xneox.guilds.war;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArenaControllerTask implements Runnable {
    private final NeonGuilds plugin;

    public ArenaControllerTask(NeonGuilds plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Arena arena : this.plugin.getArenaManager().getArenaMap().values()) {
            if (arena.getState() == ArenaState.FREE) {
                continue;
            }

            WarParticipant firstGuild = arena.getFirstGuild();
            WarParticipant secondGuild = arena.getSecondGuild();

             if (arena.getState() == ArenaState.PREPARING) {
                 if (arena.getTime() == 0) {
                     ChatUtils.broadcastCenteredMessage("");
                     ChatUtils.broadcastCenteredMessage("&6&l⚔ " + firstGuild.getGuild().getName() + " vs " + secondGuild.getGuild().getName() + " ⚔");
                     ChatUtils.broadcastCenteredMessage("&fRozpoczyna się pojedynek pomiędzy tymi gildiami!");
                     ChatUtils.broadcastCenteredMessage("");

                     // Teleporting players and sending title
                     arena.getFirstGuild().getMembers().forEach(player -> handleArenaStart(player, arena, arena.getFirstSpawn()));
                     arena.getSecondGuild().getMembers().forEach(player -> handleArenaStart(player, arena, arena.getSecondSpawn()));

                     arena.setTime(360);
                     arena.setState(ArenaState.INGAME);
                 } else {
                     arena.setTime(arena.getTime() - 1);

                     arena.getFirstGuild().getMembers().forEach(player -> handleArenaCountdown(player, arena));
                     arena.getSecondGuild().getMembers().forEach(player -> handleArenaCountdown(player, arena));
                 }
            } else if (arena.getState() == ArenaState.INGAME) {
                if (arena.getTime() == 0 || firstGuild.getPoints() > 100 || secondGuild.getPoints() > 100) {
                    // Handling teleport and backup for players.
                    arena.getFirstGuild().getMembers().forEach(this::handleArenaEnd);
                    arena.getSecondGuild().getMembers().forEach(this::handleArenaEnd);

                    // Ranking system.
                    ChatUtils.broadcastCenteredMessage("");
                    ChatUtils.broadcastCenteredMessage("&6&l⚔ " + firstGuild.getGuild().getName() + " vs " + secondGuild.getGuild().getName() + " ⚔");
                    ChatUtils.broadcastCenteredMessage("&fWygrała gildia: &e" + arena.getWinner().getGuild().getName());
                    ChatUtils.broadcastCenteredMessage("&fIlość punktów:");
                    ChatUtils.broadcastCenteredMessage("&8▸ &6" + firstGuild.getGuild().getName() + "&7: &e" + firstGuild.getPoints() + "★");
                    ChatUtils.broadcastCenteredMessage("&8▸ &6" + secondGuild.getGuild().getName() + "&7: &e" + secondGuild.getPoints() + "★");
                    ChatUtils.broadcastCenteredMessage("");

                    arena.getWinner().getGuild().addTrophies(arena.getWinner().getPoints());

                    // Clearing guild war locks.
                    arena.getFirstGuild().getGuild().setWarEnemy(null);
                    arena.getSecondGuild().getGuild().setWarEnemy(null);

                    arena.setFirstGuild(null);
                    arena.setSecondGuild(null);

                    // Resetting arena state.
                    arena.getBossBar().removeAll();
                    arena.setState(ArenaState.FREE);

                    generateRewards(firstGuild.getGuild());
                    generateRewards(secondGuild.getGuild());
                } else {
                    arena.setTime(arena.getTime() - 1);
                    arena.getBossBar().setTitle(ChatUtils.colored("&e⊙ Czas: &f" + TimeUtils.secondsToTime(arena.getTime())
                            + " &9➙ " + arena.getFirstGuild().getGuild().getName() + ": &f" + arena.getFirstGuild().getPoints() + "/100 ☆" +
                            " &c➙ " + arena.getSecondGuild().getGuild().getName() + ": &f" + arena.getSecondGuild().getPoints() + "/100 ☆"));
                }
            }
        }
    }

    private void handleArenaEnd(Player player) {
        player.teleport(ChunkUtils.WORLD.getSpawnLocation());
        this.plugin.getArenaManager().loadBackup(player);
    }

    private void handleArenaStart(Player player, Arena arena, Location spawn) {
        arena.getBossBar().addPlayer(player);
        player.teleport(spawn);
        this.plugin.getArenaManager().createBackup(player);

        ChatUtils.sendTitle(player, "&6&l⚔ " + arena.getFirstGuild().getGuild().getName() + " vs " + arena.getSecondGuild().getGuild().getName() + " ⚔",
                "&fRozpoczyna się pojedynek, niech wygra najlepsza gildia!");
    }

    private void handleArenaCountdown(Player player, Arena arena) {
        VisualUtils.playSound(player, Sound.ENTITY_EVOKER_CAST_SPELL);
        ChatUtils.sendTitle(player, "&6&l⚔ " + arena.getFirstGuild().getGuild().getName() + " vs " + arena.getSecondGuild().getGuild().getName() + " ⚔",
                "&fPojedynek rozpocznie się za &e" + arena.getTime() + " sekund!");
    }

    private void generateRewards(Guild guild) {
        int expAmount = RandomUtils.getInt(16);
        int diamondAmount = RandomUtils.getInt(6);
        int emeraldAmount = RandomUtils.getInt(4, 8);

        guild.getStorage().addItem(new ItemStack(Material.EXPERIENCE_BOTTLE, expAmount));
        guild.getStorage().addItem(new ItemStack(Material.DIAMOND, diamondAmount));
        guild.getStorage().addItem(new ItemStack(Material.EMERALD, emeraldAmount));
    }
}
