package me.xneox.guilds.hook.placeholderapi;

import java.util.List;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TopPlaceholderExpansion extends PlaceholderExpansion {
  private final SakuraGuildsPlugin plugin;

  public TopPlaceholderExpansion(@NotNull SakuraGuildsPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public @NotNull String getIdentifier() {
    return "guildtop";
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
    if (params.startsWith("gtop_")) {
      List<Guild> leaderboard = this.plugin.guildManager().leaderboard();

      int position = Integer.parseInt(params.replace("gtop_", ""));
      if (position >= leaderboard.size()) {
        return "-";
      }

      Guild guild = leaderboard.get(position);
      return guild.name() + " &8[&a" + guild.trophies() + "♠&8]";
    }
    return "-/-";
  }
}
