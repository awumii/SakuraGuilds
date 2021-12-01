package me.xneox.guilds.command;

import me.xneox.guilds.command.annotations.AdminOnly;
import me.xneox.guilds.gui.ManagementGui;
import me.xneox.guilds.gui.NewbieGui;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.util.LogUtils;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation of the Bukkit's CommandExecutor that manages execution of the /guild command and subcommands.
 */
public record GuildCommandExecutor(@NotNull CommandManager commandManager) implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    var player = (Player) sender;

    // Default behaviour when no argument is specified. Opening the GUI.
    if (args.length < 1) {
      if (this.commandManager.plugin().guildManager().playerGuild(player) != null) {
        // Player has a guild, open the guild menu.
        ManagementGui.INVENTORY.open(player);
      } else {
        // Player has no guild, open the newbie gui.
        NewbieGui.INVENTORY.open(player);
      }
      return true;
    }

    // Checking if the command exists
    var subCommand = this.commandManager.commandMap().get(args[0]);
    if (subCommand == null) {
      ChatUtils.sendMessage(player, ConfigManager.messages().commands().unknownCommand());
      return true;
    }

    // Checking if the command has a permission and handles the check.
    var adminAnnotation = subCommand.getClass().getAnnotation(AdminOnly.class);
    if (adminAnnotation != null && !player.hasPermission("sakuraguilds.admin")) {
      ChatUtils.sendMessage(player, ConfigManager.messages().commands().noPermission());
    }

    try {
      subCommand.handle(this.commandManager.plugin().guildManager(), player, args);
    } catch (Exception exception) {
      ChatUtils.sendMessage(player, ConfigManager.messages().commands().internalError()
          .replace("{ERR}", exception.getMessage()));
      LogUtils.catchException("An exception ocured while executing the command: " + args[0], exception);
    }
    return true;
  }
}
