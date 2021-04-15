package me.xneox.guilds.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandCompleter implements TabCompleter {
    private final GuildCommand guildCommand;

    public CommandCompleter(GuildCommand guildCommand) {
        this.guildCommand = guildCommand;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(this.guildCommand.getCommandMap().keySet());
        }
        return null;
    }
}
