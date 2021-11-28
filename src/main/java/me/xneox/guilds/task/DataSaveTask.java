package me.xneox.guilds.task;

import java.sql.SQLException;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.util.LogUtils;

/**
 * This task is saves data to the database asynchronously, every 10 minutes.
 */
public record DataSaveTask(SakuraGuildsPlugin plugin) implements Runnable {

  @Override
  public void run() {
    try {
      this.plugin.guildManager().save();
      this.plugin.userManager().save();
      this.plugin.cooldownManager().save();
    } catch (SQLException exception) {
      LogUtils.catchException("[auto-save] Could not save date to the database.", exception);
    }
  }
}
