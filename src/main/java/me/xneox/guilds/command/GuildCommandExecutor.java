package me.xneox.guilds.command;

import me.xneox.guilds.command.internal.AdminOnly;
import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class GuildCommandExecutor implements CommandExecutor {
    private final CommandManager commandManager;

    public GuildCommandExecutor(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;

        // Default behaviour when no argument is specified.
        if (args.length < 1) {
            if (this.commandManager.plugin().guildManager().playerGuild(player) != null) {
                this.commandManager.plugin().inventoryManager().open("management", player);
            } else {
                this.commandManager.plugin().inventoryManager().open("newbie", player);
            }
            return true;
        }

        // Checking if the command exists
        SubCommand subCommand = this.commandManager.commandMap().get(args[0]);
        if (subCommand == null) {
            ChatUtils.sendMessage(player, "&cNie odnaleziono takiej komendy.");
            return true;
        }

        // Checking if the command has a permission and handles the check.
        AdminOnly permissible = subCommand.getClass().getAnnotation(AdminOnly.class);
        if (permissible != null && !player.isOp()) {
            ChatUtils.sendMessage(player, "&cNie posiadasz uprawnień do tej komendy.");
        }

        try {
            subCommand.handle(this.commandManager.plugin().guildManager(), player, args);
        } catch (Exception e) {
            ChatUtils.sendMessage(player, "&cWystąpił błąd podczas wykonywania komendy: &4" + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }
}
