package me.xneox.guilds.integration.placeholderapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.xneox.guilds.SakuraGuildsPlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainPlaceholderExpansion extends PlaceholderExpansion {
  private final SakuraGuildsPlugin plugin;

  public MainPlaceholderExpansion(@NotNull SakuraGuildsPlugin plugin) {
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
    var guild = this.plugin.guildManager().playerGuild(player);
    var user = this.plugin.userManager().user(player);

    if (params.startsWith("guild_") && guild == null) {
      return "-/-";
    }

    return switch (params) {
      // User placeholders
      case "kills" -> String.valueOf(user.kills());
      case "deaths" -> String.valueOf(user.deaths());
      case "channel" -> user.chatChannel().getName();
      case "trophies" -> String.valueOf(user.trophies());

      // Guild placeholders
      case "guild_icon" -> guild.member(player).rank().icon();
      case "guild_name" -> guild.name();
      case "guild_trophies" -> String.valueOf(guild.trophies());
      case "guild_division" -> guild.division().getName();
      case "guild_rank" -> guild.member(player).rank().title();
      case "guild_position" -> "#" + this.plugin.guildManager().leaderboard().indexOf(guild) + 1;

      default -> "<unknown>";
    };
  }
}
