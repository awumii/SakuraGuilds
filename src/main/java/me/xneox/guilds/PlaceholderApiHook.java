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

        switch (params) {
            case "kills":
                return String.valueOf(user.getKills());
            case "deaths":
                return String.valueOf(user.getDeaths());
            case "channel":
                return user.getChatChannel().getName();
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
            case "location":
                // WHAT THE FUCK IS THIS
                // Explanation so i won't forget it later:
                // If region is present at player's location, display it (capitalized since worldguard forces lowercase)
                // If region is not present, searches for guild at player's location, and formats it whether it is ally or enemy.
                // If both conditions fail, it will display "Wilderness".

                Guild at = this.plugin.getGuildManager().getGuildAt(player.getLocation());
                String region = HookUtils.getWorldGuardRegion(player.getLocation());
                return region != null ? WordUtils.capitalize(region) : at != null ? at.equals(guild) ? "&a" + at.getName() + " ☮" : "&c" + at.getName() + " ⚠" : "&2Świat";
        }
        return "<unknown_placeholder>";
    }
}
