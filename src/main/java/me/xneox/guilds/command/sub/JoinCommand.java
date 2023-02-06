package me.xneox.guilds.command.sub;

import java.util.List;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.enums.Rank;
import me.xneox.guilds.gui.ManagementGui;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class JoinCommand implements SubCommand {

  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
    if (args.length < 2) {
      ChatUtils.sendMessage(player, ConfigManager.messages().commands().noGuildSpecified());
      return;
    }

    if (manager.playerGuild(player.getName()) != null) {
      ChatUtils.sendMessage(player, ConfigManager.messages().commands().youAreInGuild());
      return;
    }

    Guild guild = manager.get(args[1]);
    if (guild == null) {
      ChatUtils.sendMessage(player, ConfigManager.messages().commands().unknownGuild());
      return;
    }

    // Check if the player was invited, operators skip this check.
    if (!guild.playerInvitations().contains(player.getUniqueId()) && !player.isOp()) {
      ChatUtils.sendMessage(player, ConfigManager.messages().commands().notInvited());
      return;
    }

    if (guild.members().size() >= guild.maxSlots()) {
      ChatUtils.sendMessage(player, ConfigManager.messages().commands().cantJoinGuildFull());
      return;
    }

    // Add member with the newbie rank to the guild.
    guild.members().add(Member.create(player.getUniqueId(), Rank.NEWBIE, System.currentTimeMillis()));

    ManagementGui.INVENTORY.open(player);
    ChatUtils.broadcast(ConfigManager.messages().commands().newMemberJoined()
        .replace("{PLAYER}", player.getName())
        .replace("{GUILD}", guild.name()));
  }

  @Override
  public List<String> suggest(String[] args) {
    return SakuraGuildsPlugin.get().guildManager().guildNames();
  }
}
