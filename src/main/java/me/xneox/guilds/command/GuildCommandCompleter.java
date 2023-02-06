package me.xneox.guilds.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Bukkit's TabCompleter that manages suggestions for the /guild command and subcommands.
 */
public record GuildCommandCompleter(@NotNull CommandManager commandManager) implements TabCompleter {

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
    if (args.length == 1) {
      return new ArrayList<>(this.commandManager.commandMap().keySet());
    }

    var subCommand = this.commandManager.commandMap().get(args[0]);
    if (subCommand != null) {
      return subCommand.suggest(args);
    }
    return null;
  }
}
