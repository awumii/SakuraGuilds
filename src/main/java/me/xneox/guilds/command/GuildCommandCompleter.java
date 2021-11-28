package me.xneox.guilds.command;

import java.util.ArrayList;
import java.util.List;
import me.xneox.guilds.command.annotations.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Implementation of the Bukkit's TabCompleter that manages suggestions for the /guild command and subcommands.
 */
public record GuildCommandCompleter(@NotNull CommandManager commandManager) implements TabCompleter {

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
    if (args.length == 1) {
      return new ArrayList<>(this.commandManager.commandMap().keySet());
    }

    SubCommand subCommand = this.commandManager.commandMap().get(args[0]);
    if (subCommand != null) {
      return subCommand.suggest(args);
    }
    return null;
  }
}
