package me.xneox.guilds.util;

import java.io.File;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public final class FileUtils {
  /**
   * Creates new file, catches eventual exception and logs to debug.
   *
   * @param file The file to be created.
   * @return The created file.
   */
  @NotNull
  public static File create(@NotNull File file) {
    try {
      if (file.createNewFile()) {
        LogUtils.debug("Created new file: " + file.getPath());
      }
    } catch (IOException exception) {
      LogUtils.catchException("Can't create database file", exception);
    }

    return file;
  }
}
