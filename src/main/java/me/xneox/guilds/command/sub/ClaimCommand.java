package me.xneox.guilds.command.sub;

import java.time.Duration;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.enums.Permission;
import me.xneox.guilds.hook.HookUtils;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChunkUtils;
import me.xneox.guilds.util.LocationUtils;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ClaimCommand implements SubCommand {

  @Override
  public void handle(GuildManager manager, Player player, String[] args) {
    Guild guild = manager.playerGuild(player.getName());
    if (guild == null) {
      ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
      return;
    }

    Member member = guild.member(player);
    if (!member.hasPermission(Permission.CLAIM)) {
      ChatUtils.sendMessage(player, "&cNie posiadasz uprawnień do zajmowania terenu.");
      return;
    }

    if (guild.claims().size() >= guild.maxChunks()) {
      ChatUtils.sendMessage(player, "&cPrzekroczono limit chunków. Zakup ulepszenie gildii!");
      return;
    }

    if (ChunkUtils.isProtected(player)) {
      return;
    }

    String chunk = ChunkUtils.deserialize(player.getLocation().getChunk());
    guild.claims().add(player.getLocation().getChunk());
    ChatUtils.guildAlert(guild, guild.member(player).displayName()
        + " &7zajmuje chunk: &6"
        + LocationUtils.legacyDeserialize(player.getLocation()));

    Location hologramLoc = ChunkUtils.getCenter(chunk);
    hologramLoc.setY(hologramLoc.getY() + 3);

    HookUtils.createTimedHologram(
        hologramLoc,
        Duration.ofSeconds(20),
        Material.DIAMOND_SHOVEL,
        "&6&lGILDIA " + guild.name() + " ZAJMUJE TEN TEREN",
        "&7Lokalizacja: &f" + chunk,
        "&7Przez: &f" + player.getName());
  }
}
