package me.xneox.guilds.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class CommandCompleter implements TabCompleter {
    private final GuildCommand guildCommand;

    public CommandCompleter(GuildCommand guildCommand) {
        this.guildCommand = guildCommand;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return this.guildCommand.getCommandMap().keySet()
                    .stream()
                    .filter(cmd -> !cmd.getClass().isAnnotationPresent(Hidden.class))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
