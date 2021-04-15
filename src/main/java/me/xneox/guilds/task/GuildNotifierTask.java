package me.xneox.guilds.task;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ChunkUtils;
import me.xneox.guilds.util.LocationUtils;
import me.xneox.guilds.util.VisualUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuildNotifierTask implements Runnable {
    private final NeonGuilds plugin;
    private final Map<UUID, String> areaMap = new HashMap<>();

    public GuildNotifierTask(NeonGuilds plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Guild guild = this.plugin.getGuildManager().getGuildAt(player.getLocation());
            if (guild != null) {
                if (!this.areaMap.containsKey(player.getUniqueId())) {
                    this.areaMap.put(player.getUniqueId(), guild.getName());

                    if (guild.getMembers().containsKey(player.getName())) {
                        ChatUtils.sendTitle(player, "&6&l" + guild.getName(), "Witaj w domu! To bezpieczny teren twojej gildii...");
                    } else if (guild.isNexusChunk(player.getChunk())) {
                        ChatUtils.sendTitle(player, "&c&l" + guild.getName() + " (NEXUS)", "Wkraczasz na teren nexusa wrogiej gildii!");
                    } else {
                        ChatUtils.sendTitle(player, "&c&l" + guild.getName(), "Wkraczasz na teren wrogiej gildii!");
                    }

                    VisualUtils.playSound(player, Sound.BLOCK_NOTE_BLOCK_XYLOPHONE);
                    for (String chunk : guild.getChunks()) {
                        VisualUtils.drawBorderAtChunk(ChunkUtils.toChunk(chunk), player);
                    }

                    if (!guild.getMembers().containsKey(player.getName())) {
                        ChatUtils.forGuildMembers(guild, member -> {
                            VisualUtils.playSound(member, Sound.ENTITY_ELDER_GUARDIAN_CURSE);

                            String sub = " &7wkroczył na teren: &6" + guild.getChunks().indexOf(ChunkUtils.toString(player.getChunk())) +
                                    " (" + LocationUtils.toSimpleString(player.getLocation()) + ")";

                            ChatUtils.sendBossBar(member, BarColor.RED, "&4&l⚠ &c" + player.getName() + sub);
                            ChatUtils.sendTitle(member, "&4&l⚠", "&c" + player.getName() + sub);
                        });
                    }
                }
            } else if (this.areaMap.containsKey(player.getUniqueId())) {
                ChatUtils.sendTitle(player, "&2&lWILDERNESS", "&fWolny, ale niebezpieczny teren...");
                this.areaMap.remove(player.getUniqueId());
                VisualUtils.playSound(player, Sound.BLOCK_NOTE_BLOCK_BASS);
            }
        }
    }
}
