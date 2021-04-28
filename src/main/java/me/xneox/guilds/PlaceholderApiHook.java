package me.xneox.guilds;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.RankedUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderApiHook extends PlaceholderExpansion {
    private final NeonGuilds plugin;

    public PlaceholderApiHook(NeonGuilds plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "neonguilds";
    }

    @Override
    public @NotNull String getAuthor() {
        return "xNeox";
    }

    @Override
    public @NotNull String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        Guild guild = this.plugin.getGuildManager().getGuild(player);
        if (guild == null) {
            return "-/-";
        }

        switch (params) {
            case "icon":
                return guild.getPlayerRank(player).getIcon();
            case "guild":
                return guild.getName();
            case "trophies":
                return String.valueOf(guild.getTrophies());
            case "division":
                return guild.getDivision().getName();
            case "rank":
                return guild.getPlayerRank(player).getDisplay();
            case "rankedposition":
                return "#" + RankedUtils.getLeaderboard(this.plugin.getGuildManager().getGuildMap().values()).indexOf(guild);
        }
        return "<unknown_placeholder>";
    }
}
