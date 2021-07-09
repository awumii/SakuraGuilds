package me.xneox.guilds.util;

import me.xneox.guilds.NeonGuilds;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;

import java.util.Objects;

// This class manages compatibility for different plugins.
public final class HookUtils {
    public static final Economy ECONOMY = Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(Economy.class)).getProvider();
    public static final NeonGuilds INSTANCE = NeonGuilds.getPlugin(NeonGuilds.class);
    public static final String VERSION = INSTANCE.getDescription().getVersion();
}
