package me.xneox.guilds.hook;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.skills.Skills;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AureliumSkillsHook {

  // Get player's AureliumSkills level (sum of levels of all skills).
  public static int aureliumSkillsLevel(@NotNull Player player) {
    if (!HookUtils.AURELIUM_SKILLS_AVAILABLE) {
      return -1;
    }

    int sum = 0;
    for (Skills skill : Skills.values()) {
      int skillLevel = AureliumAPI.getSkillLevel(player, skill);
      if (skillLevel > 1) {
        sum += skillLevel;
      }
    }
    return sum;
  }
}
