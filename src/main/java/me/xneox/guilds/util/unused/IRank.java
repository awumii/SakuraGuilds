package me.xneox.guilds.util.unused;

import java.util.Set;
import org.jetbrains.annotations.NotNull;

public interface IRank {

  /**
   * @return The rank ID, stored in the database.
   */
  @NotNull
  String id();

  /**
   * @return Displayed name of this rank.
   */
  @NotNull
  String title();

  /**
   * @return Short icon for this rank.
   */
  @NotNull
  String icon();

  /**
   * @return Permissions given by default to the members acquiring this rank.
   */
  @NotNull
  Set<IPermission> defaultPermissions();

  /**
   * @return Weight of this rank, the bigger the value, the higher the role is in hierarchy.
   */
  int weight();
}
