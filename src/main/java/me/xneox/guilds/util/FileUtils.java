package me.xneox.guilds.util;

import java.io.File;
import java.io.IOException;
import org.apache.commons.lang.Validate;
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
    Validate.notNull(file, "Can't create null file!");

    try {
      if (file.createNewFile()) {
        LogUtils.debug("Created new file: " + file.getPath());
      }
    } catch (IOException e) {
      LogUtils.catchException("Can't create database file", e);
    }

    return file;
  }
}
