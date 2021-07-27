package me.xneox.guilds;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.HookUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderApiHook extends PlaceholderExpansion {
    private final SakuraGuildsPlugin plugin;

    public PlaceholderApiHook(SakuraGuildsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "sakuraguilds";
    }

    @Override
    public @NotNull String getAuthor() {
        return "SakuraDevelopment";
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
        Guild guild = this.plugin.guildManager().playerGuild(player);
        User user = this.plugin.userManager().getUser(player);

        if (guild == null && !params.equals("level")) {
            return "-/-";
        }

        return switch (params) {
            case "kills" -> String.valueOf(user.getKills());
            case "deaths" -> String.valueOf(user.getDeaths());
            case "channel" -> user.getChatChannel().getName();
            case "icon" -> guild.member(player).rank().icon();
            case "guild" -> guild.name();
            case "trophies" -> String.valueOf(guild.trophies());
            case "division" -> guild.division().getName();
            case "rank" -> guild.member(player).rank().title();
            case "level" -> String.valueOf(HookUtils.getAureliumLevel(player));
            case "rankedposition" -> "#" + this.plugin.guildManager().leaderboard().indexOf(guild);
            default -> "<unknown>";
        };
    }
}
