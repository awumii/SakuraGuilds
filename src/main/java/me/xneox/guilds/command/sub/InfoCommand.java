package me.xneox.guilds.command.sub;

import java.util.List;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InfoCommand implements SubCommand {

  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
    var config = ConfigManager.messages().commands();

    if (args.length < 2) {
      ChatUtils.sendMessage(player, config.noGuildSpecified());
      return;
    }

    Guild guild = manager.get(args[1]);
    if (guild == null) {
      ChatUtils.sendMessage(player, config.noGuild());
      return;
    }

    ChatUtils.sendNoPrefix(player, "&3&m--&8&m------------------------------------------&3&m--");
    ChatUtils.sendNoPrefix(player, "&6&lInformacje o gildii:");
    ChatUtils.sendNoPrefix(player, "&eNazwa: &6" + guild.name());
    ChatUtils.sendNoPrefix(player, "&eLider: &6" + guild.leader().nickname());
    ChatUtils.sendNoPrefix(player, "&eLimit chunków: &6" + guild.maxChunks());
    ChatUtils.sendNoPrefix(player, "&eLimit członków: &6" + guild.maxSlots());
    ChatUtils.sendNoPrefix(player, "");
    ChatUtils.sendNoPrefix(player, "&eCzłonkowie: &7");
    guild
        .members()
        .forEach(member -> ChatUtils.sendNoPrefix(player, " &8- " + member.displayName()));
    ChatUtils.sendNoPrefix(player, "");
    ChatUtils.sendNoPrefix(player, "&eZajęte Chunki: &7" + guild.claims().size());
    ChatUtils.sendNoPrefix(player, "&3&m--&8&m------------------------------------------&3&m--");
  }

  @Override
  public List<String> suggest(String[] args) {
    return SakuraGuildsPlugin.get().guildManager().guildNames();
  }
}
