package me.xneox.guilds.command.sub;

import java.util.List;
import java.util.stream.Collectors;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Member;
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

    var guild = manager.get(args[1]);
    if (guild == null) {
      ChatUtils.sendMessage(player, config.noGuild());
      return;
    }

    for (String line : ConfigManager.messages().commands().guildInfoCommand()) {
      ChatUtils.sendNoPrefix(player, line
          .replace("{NAME}", guild.name())
          .replace("{LEADER}", guild.leader().nickname())
          .replace("{CHUNKS}", String.valueOf(guild.claims().size()))
          .replace("{MAX-CHUNKS}", String.valueOf(guild.maxChunks()))
          .replace("{MEMBER-AMOUNT}", String.valueOf(guild.members().size()))
          .replace("{MAX-MEMBERS}", String.valueOf(guild.maxSlots())
          .replace("{MEMBER-LIST", guild.members().stream().map(Member::displayName).collect(Collectors.joining())))
      );
    }
  }

  @Override
  public List<String> suggest(String[] args) {
    return SakuraGuildsPlugin.get().guildManager().guildNames();
  }
}
