package me.xneox.guilds.command.sub;

import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HelpCommand implements SubCommand {
    @Override
    public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
        ConfigManager.messages().commands().helpCommand().forEach(line -> ChatUtils.sendNoPrefix(player, line));
    }
}
