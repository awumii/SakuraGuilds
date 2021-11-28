package me.xneox.guilds.command.annotations;

import java.util.List;
import me.xneox.guilds.manager.GuildManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A subcommand of the /guild command.
 */
public interface SubCommand {
  /**
   * Handles the execution of this subcommand
   *
   * @param manager the GuildManager
   * @param player executor if this commands
   * @param args arguments provided by the executor
   */
  void handle(@NotNull GuildManager manager, @NotNull Player player, @NotNull String[] args);

  /**
   * Handles the tab-completion of this subcommand.
   * Returns available suggestions if possible.
   *
   * @param args arguments provided by the executor
   * @return available suggestions, or null
   */
  default @Nullable List<String> suggest(@NotNull String[] args) {
    return null;
  }
}
