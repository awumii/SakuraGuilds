package me.xneox.guilds;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.HookUtils;
import me.xneox.guilds.util.RankedUtils;
import org.apache.commons.lang.WordUtils;
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
        User user = this.plugin.getUserManager().getUser(player);

        if (guild == null && !params.equals("location")) {
            return "-/-";
        }

        return switch (params) {
            case "kills" -> String.valueOf(user.getKills());
            case "deaths" -> String.valueOf(user.getDeaths());
            case "channel" -> user.getChatChannel().getName();
            case "icon" -> guild.getPlayerRank(player).getIcon();
            case "guild" -> guild.getName();
            case "trophies" -> String.valueOf(guild.getTrophies());
            case "division" -> guild.getDivision().getName();
            case "rank" -> guild.getPlayerRank(player).getDisplay();
            case "rankedposition" -> "#" + RankedUtils.getLeaderboard(this.plugin.getGuildManager().getGuildMap().values()).indexOf(guild);
            default -> "<unknown>";
        };
    }
}
