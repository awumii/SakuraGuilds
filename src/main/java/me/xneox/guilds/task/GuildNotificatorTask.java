package me.xneox.guilds.task;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.ChunkUtils;
import me.xneox.guilds.util.HookUtils;
import me.xneox.guilds.util.LocationUtils;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;

public class GuildNotificatorTask implements Runnable {
  private final SakuraGuildsPlugin plugin;
  private final Map<UUID, String> areaMap = new HashMap<>();

  public GuildNotificatorTask(SakuraGuildsPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void run() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      Guild guild = this.plugin.guildManager().findAt(player.getLocation());
      if (guild != null) {
        if (this.areaMap.containsKey(player.getUniqueId())) {
          continue;
        }

        this.areaMap.put(player.getUniqueId(), guild.name());
        if (guild.isMember(player.getName())) {
          ChatUtils.sendTitle(player, "&6&l&n" + guild.name() + "&6 ☮",
              "&f&oWitaj w domu! To bezpieczny teren twojej gildii...");
        } else if (guild.isNexusChunk(player.getChunk())) {
          ChatUtils.sendTitle(player, "&c&l&n" + guild.name() + "&c ⚠ (NEXUS)",
              "&f&oWkraczasz na teren nexusa wrogiej gildii!");
        } else {
          ChatUtils.sendTitle(player, "&c&l&n" + guild.name() + "&c ⚠",
              "&f&oWkraczasz na teren wrogiej gildii!");
        }

        VisualUtils.sound(player, Sound.BLOCK_NOTE_BLOCK_XYLOPHONE);
        for (String chunk : guild.claims()) {
          VisualUtils.drawBorderAtChunk(ChunkUtils.serialize(chunk), player);
        }

        if (!HookUtils.isVanished(player) && !guild.isMember(player.getName())) {
          ChatUtils.forGuildMembers(guild, member -> {
            if (LocationUtils.isWorldNotAllowed(member.getLocation())) {
              VisualUtils.sound(member, Sound.ENTITY_ELDER_GUARDIAN_CURSE);
              ChatUtils.sendBossBar(member, BarColor.RED, ChatUtils.format(
                  "&4&l⚠ &c{0} &7wkroczył na teren: &6{1} ({2})",
                  player.getName(),
                  guild.claims().indexOf(ChunkUtils.deserialize(player.getChunk())),
                  LocationUtils.legacyDeserialize(player.getLocation())));
            }
          });
        }
      } else if (this.areaMap.containsKey(player.getUniqueId())) {
        ChatUtils.sendTitle(player, "&2&l&nŚwiat&2 ❆", "&f&oPowodzenia w eksploracji, uważaj na siebie!");
        VisualUtils.sound(player, Sound.BLOCK_NOTE_BLOCK_BASS);

        this.areaMap.remove(player.getUniqueId());
      }
    }
  }
}
