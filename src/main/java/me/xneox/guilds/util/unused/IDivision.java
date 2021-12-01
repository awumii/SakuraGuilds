package me.xneox.guilds.util.unused;

import org.jetbrains.annotations.NotNull;

public interface IDivision {

  /**
   * @return The division ID, stored in the database.
   */
  @NotNull
  String id();

  /**
   * @return Displayed name of the division
   */
  @NotNull
  String title();

  /**
   * @return Minimum points required to be in this division
   */
  int minPoints();
}
