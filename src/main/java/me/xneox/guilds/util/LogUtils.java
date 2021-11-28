package me.xneox.guilds.util;

import me.xneox.guilds.SakuraGuildsPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * This util helps with various logging operations.
 */
public final class LogUtils {
  public static final Logger LOGGER = SakuraGuildsPlugin.get().getSLF4JLogger();

  /**
   * Catches a Throwable and prints a detailed error message.
   *
   * @param details details about where the error has occurred
   * @param throwable the caught exception
   */
  public static void catchException(@NotNull String details, @NotNull Throwable throwable) {
    LOGGER.error("An error occurred in SakuraGuilds v" + SakuraGuildsPlugin.get().getDescription().getVersion());
    LOGGER.error(" > Details: " + details);
    LOGGER.error(" > Stacktrace: ");
    LOGGER.error("", throwable);
  }

  /**
   * Logs a message if debug is enabled in the configuration.
   *
   * @param message message to be logged
   */
  public static void debug(@NotNull String message) {
    // todo toggling debug in config
    LOGGER.info("(Debug) " + message);
  }
}
