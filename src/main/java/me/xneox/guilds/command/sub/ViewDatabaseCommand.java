package me.xneox.guilds.command.sub;

import java.util.Arrays;
import java.util.List;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.annotations.AdminOnly;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.manager.CooldownManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.manager.UserManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AdminOnly
public class ViewDatabaseCommand implements SubCommand {

  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
    GuildManager guildManager = SakuraGuildsPlugin.get().guildManager();
    UserManager userManager = SakuraGuildsPlugin.get().userManager();
    CooldownManager cooldownManager = SakuraGuildsPlugin.get().cooldownManager();

    if (args.length < 2) {
      ChatUtils.sendMessage(player, "&7Liczba gildii: " + guildManager.guildMap().size());
      ChatUtils.sendMessage(player, "&7Liczba kont: " + userManager.userMap().size());
      ChatUtils.sendMessage(player, "&7Liczba cooldownów: " + cooldownManager.cooldownMap().size());
      return;
    }

    switch (args[1]) {
      case "guilds" -> guildManager.guildMap().values()
          .forEach(guild -> ChatUtils.sendNoPrefix(player, " &8- &f" + guild.toString()));
      case "users" -> userManager.userMap().forEach((uuid, user) ->
          ChatUtils.sendNoPrefix(player, " &8- &f{0}: {1}", uuid.toString(), user.toString()));
      case "cooldowns" -> cooldownManager.cooldownMap().forEach((name, pair) ->
          ChatUtils.sendNoPrefix(player, " &8- {0}: dur={1}, time={2}", name, pair.getLeft(), pair.getRight()));
    }
  }

  @Override
  public @Nullable List<String> suggest(String[] args) {
    return Arrays.asList("guilds", "users", "cooldowns");
  }
}
