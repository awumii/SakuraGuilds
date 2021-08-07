package me.xneox.guilds.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.HookUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainPlaceholderExpansion extends PlaceholderExpansion {
    private final SakuraGuildsPlugin plugin;

    public MainPlaceholderExpansion(SakuraGuildsPlugin plugin) {
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

        if (params.startsWith("guild_") && guild == null) {
            return "-/-";
        }

        return switch (params) {
            // User placeholders
            case "kills" -> String.valueOf(user.kills());
            case "deaths" -> String.valueOf(user.deaths());
            case "channel" -> user.chatChannel().getName();
            case "trophies" -> String.valueOf(user.trophies());
            case "level" -> String.valueOf(HookUtils.getAureliumLevel(player));

            // Guild placeholders
            case "guild_icon" -> guild.member(player).rank().icon();
            case "guild_name" -> guild.name();
            case "guild_trophies" -> String.valueOf(guild.trophies());
            case "guild_division" -> guild.division().getName();
            case "guild_rank" -> guild.member(player).rank().title();

            case "title" -> "SURVIVAL 4.0";
            default -> "<unknown>";
        };
    }
}
