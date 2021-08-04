package me.xneox.guilds.util;

import com.SirBlobman.combatlogx.api.ICombatLogX;
import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.skills.Skills;
import me.xneox.guilds.SakuraGuildsPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

// This class manages compatibility for different plugins.
public final class HookUtils {
    public static final Economy ECONOMY = Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(Economy.class)).getProvider();
    public static final SakuraGuildsPlugin INSTANCE = SakuraGuildsPlugin.getPlugin(SakuraGuildsPlugin.class);
    public static final String DIRECTORY = "plugins/SakuraGuilds";

    public static boolean hasCombatTag(Player player) {
        ICombatLogX plugin = (ICombatLogX) Bukkit.getPluginManager().getPlugin("CombatLogX");
        if (plugin != null) {
            return plugin.getCombatManager().isInCombat(player);
        }
        return false;
    }

    public static int getAureliumLevel(Player player) {
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
