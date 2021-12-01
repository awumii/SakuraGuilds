package me.xneox.guilds.util.unused;

import org.jetbrains.annotations.NotNull;

public interface IPermission {

  /**
   * @return The permission ID, stored in the database.
   */
  @NotNull
  String id();

  /**
   * @return Description of this permission, displayed in the GUI.
   */
  @NotNull
  String description();
}
