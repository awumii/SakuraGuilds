package me.xneox.guilds.command;

import me.xneox.guilds.command.internal.Hidden;
import me.xneox.guilds.command.internal.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class GuildCommandCompleter implements TabCompleter {
    private final CommandManager commandManager;

    public GuildCommandCompleter(@NotNull CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return this.commandManager.commandMap().keySet()
                    .stream()
                    .filter(cmd -> !cmd.getClass().isAnnotationPresent(Hidden.class))
                    .collect(Collectors.toList());
        } else {
            SubCommand subCommand = this.commandManager.commandMap().get(args[0]);
            if (subCommand != null) {
                return subCommand.suggest(args);
            }
        }
        return null;
    }
}
