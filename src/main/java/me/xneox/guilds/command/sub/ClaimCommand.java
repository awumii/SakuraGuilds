package me.xneox.guilds.command.sub;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.enums.Permission;
import me.xneox.guilds.hook.HolographicDisplaysHook;
import me.xneox.guilds.hook.HookUtils;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChunkUtils;
import me.xneox.guilds.util.LocationUtils;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;

public class ClaimCommand implements SubCommand {

  @Override
  public void handle(GuildManager manager, Player player, String[] args) {
    var config = ConfigManager.messages().commands();

    var guild = manager.playerGuild(player.getName());
    if (guild == null) {
      ChatUtils.sendMessage(player, config.noGuild());
      return;
    }

    Member member = guild.member(player);
    if (!member.hasPermission(Permission.CLAIM)) {
      ChatUtils.sendMessage(player, config.noGuildPermission());
      return;
    }

    if (guild.claims().size() >= guild.maxChunks()) {
      ChatUtils.sendMessage(player, config.claimLimit());
      return;
    }

    if (ChunkUtils.isProtected(player)) {
      return;
    }

    // Add chunk to claims.
    guild.claims().add(player.getLocation().getChunk());

    // Notify guild members about this interesting situation.
    ChatUtils.guildAlert(guild, config.claimGuildNotify()
        .replace("{PLAYER}", guild.member(player).displayName())
        .replace("{LOCATION}", LocationUtils.legacyDeserialize(player.getLocation())));

    // Create hologram
    if (HookUtils.HOLOGRAMS_AVAILABLE) {
      var holoCfg = ConfigManager.holograms().claim();

      var location = ChunkUtils.getCenter(player.getChunk());
      location.setY(location.getY() + holoCfg.heightAboveGround());

      List<String> text = new ArrayList<>();
      for (String line : holoCfg.text()) {
        text.add(line
            .replace("{GUILD}", guild.name())
            .replace("{LOCATION}", ChunkUtils.deserialize(player.getChunk()))
            .replace("{CLAIMER}", player.getName()));
      }

      HolographicDisplaysHook.createTimedHologram(
          location,
          Duration.ofSeconds(holoCfg.displayDurationSeconds()),
          holoCfg.icon(),
          text);
    }
  }
}
