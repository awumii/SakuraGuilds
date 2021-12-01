package me.xneox.guilds.util.text;

import com.google.common.base.Joiner;
import java.text.MessageFormat;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.LocationUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public final class ChatUtils {
  // TODO: un-hardcode
  public static final String PREFIX = "&#D75524&lGILDIE &8» &7";

  public static final String BRONZE = "&#cd7f32";
  public static final String CRIMSON = "&#dc143c";

  private static final int CENTER_PX = 154;
  private static final LegacyComponentSerializer SERIALIZER =
      LegacyComponentSerializer.builder()
          .character('&')
          .hexCharacter('#')
          .hexColors()
          .extractUrls()
          .build();

  /**
   * Returns a formatted TextComponent, colors are replaced
   * using the & symbol. Hex colors are supported (ex. &#cd7f32).
   *
   * @param message Raw string to be formatted.
   * @return A formatted TextComponent.
   */
  @NotNull
  public static TextComponent color(@NotNull String message) {
    return SERIALIZER.deserialize(message);
  }

  /**
   * Sends a formatted message to the player, includes a prefix.
   */
  public static void sendMessage(@NotNull Player sender, String message, Object... objects) {
    sendNoPrefix(sender, PREFIX + message, objects);
  }

  /**
   * Sends a formatted message to the player, without a prefix.
   */
  public static void sendNoPrefix(@NotNull Player sender, String message, Object... objects) {
    sender.sendMessage(color(format(message, objects)));
  }

  /**
   * Converts a Component to a plain string.
   *
   * @param component Component to be converted.
   * @return The converted string.
   */
  @NotNull
  public static String plainString(Component component) {
    return PlainTextComponentSerializer.plainText().serialize(component);
  }

  /**
   * Formats all {0}, {1}... etc in the string with provided objects.
   *
   * @param text Text to be formatted.
   * @param objects Objects to insert in the placeholders.
   * @return A formatted string.
   */
  public static String format(String text, Object... objects) {
    return new MessageFormat(text).format(objects);
  }

  public static void sendClickableMessage(@NotNull Player player, @NotNull String message, @NotNull String hover, @NotNull String runCommand) {
    TextComponent component = color(message)
        .hoverEvent(color(hover))
        .clickEvent(ClickEvent.runCommand(runCommand));

    player.sendMessage(component);
  }

  public static void sendTitle(Player player, String title, String subtitle) {
    player.showTitle(Title.title(color(title), color(subtitle)));
  }

  @NotNull
  public static String join(char separator, Object... objects) {
    return Joiner.on(separator).join(objects);
  }

  /** Only used when interacting with plugins not supporting Adventure. */
  @NotNull
  public static String legacyColor(String string) {
    return ChatColor.translateAlternateColorCodes('&', string);
  }

  public static void sendBossBar(@NotNull Player player, BarColor color, String message) {
    BossBar bossBar = Bukkit.createBossBar(legacyColor(message), color, BarStyle.SOLID);
    new BukkitRunnable() {
      int remaining = 10;

      @Override
      public void run() {
        if ((remaining -= 1) == 0) {
          bossBar.removeAll();
          cancel();
        } else {
          bossBar.setProgress(remaining / 10D);
        }
      }
    }.runTaskTimer(SakuraGuildsPlugin.get(), 0, 20);

    bossBar.setVisible(true);
    bossBar.addPlayer(player);
  }

  public static String formatPlayerList(List<Player> list) {
    StringBuilder builder = new StringBuilder();
    for (Player player : list) {
      builder.append(player.getName()).append(", ");
    }
    return builder.toString();
  }

  public static int percentageOf(double i, double of) {
    return (int) ((i * 100) / of);
  }

  public static String progressBarOf(int done, int total) {
    int size = 10;
    int donePercents = (100 * done) / total;
    int doneLength = size * donePercents / 100;
    return IntStream.range(0, size)
        .mapToObj(i -> i < doneLength ? "&6⬛" : "&6⬜")
        .collect(Collectors.joining());
  }

  public static void showActionBar(Player player, String message) {
    player.sendActionBar(color(message));
  }

  public static void broadcast(String message, Object... objects) {
    broadcastRaw(PREFIX + message, objects);
  }

  public static void broadcastRaw(String message, Object... objects) {
    Bukkit.broadcast(color(format(message, objects)));
  }

  public static void guildAlert(Guild guild, String message) {
    guildAlertRaw(guild, PREFIX + message);
  }

  public static void guildAlertRaw(Guild guild, String message, Object... objects) {
    forGuildMembers(guild, player -> sendNoPrefix(player, message, objects));
  }

  public static void forGuildMembers(Guild guild, Consumer<Player> action) {
    guild.getOnlineMembers().stream()
        .filter(player -> !LocationUtils.isWorldNotAllowed(player.getLocation()))
        .forEach(action);
  }

  public static String buildString(String[] args, int index) {
    return IntStream.range(index, args.length)
        .mapToObj(i -> args[i] + " ")
        .collect(Collectors.joining());
  }

  public static void broadcastCenteredMessage(String message, Object... objects) {
    Bukkit.getOnlinePlayers().forEach(player -> sendCenteredMessage(player, message, objects));
  }

  public static void sendCenteredMessage(Player player, String message, Object... objects) {
    message = legacyColor(format(message, objects));

    int messagePxSize = 0;
    boolean previousCode = false;
    boolean isBold = false;

    for (char c : message.toCharArray()) {
      if (c == '§') {
        previousCode = true;
      } else if (previousCode) {
        previousCode = false;
        isBold = c == 'l' || c == 'L';
      } else {
        DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
        messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
        messagePxSize++;
      }
    }

    int halvedMessageSize = messagePxSize / 2;
    int toCompensate = CENTER_PX - halvedMessageSize;
    int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
    int compensated = 0;
    StringBuilder sb = new StringBuilder();
    while (compensated < toCompensate) {
      sb.append(" ");
      compensated += spaceLength;
    }
    player.sendMessage(sb + message);
  }
}
