package me.xneox.guilds.util;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.skills.Skills;
import me.xneox.guilds.NeonGuilds;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Objects;

// This class manages compatibility for different plugins.
public final class HookUtils {
    public static final Economy ECONOMY = Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(Economy.class)).getProvider();
    public static final NeonGuilds INSTANCE = NeonGuilds.getPlugin(NeonGuilds.class);
    public static final String VERSION = INSTANCE.getDescription().getVersion();

    public static int getAureliumLevel(Player player) {
        return Arrays.stream(Skills.values())
                .mapToInt(s -> AureliumAPI.getSkillLevel(player, s))
                .sum();
    }
}
