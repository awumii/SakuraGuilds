package me.xneox.guilds.util;

import me.xneox.guilds.element.Guild;
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

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ChatUtils {
    public static final String PREFIX = " &6&lGILDIE &8▶ &7";
    private static final int CENTER_PX = 154;
    private static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.builder()
            .character('&')
            .hexCharacter('#')
            .hexColors()
            .extractUrls()
            .build();

    public static TextComponent color(String message) {
        return SERIALIZER.deserialize(message);
    }

    public static void sendMessage(Player sender, String message) {
        sendNoPrefix(sender, PREFIX + message);
    }

    public static void sendNoPrefix(Player sender, String message) {
        sender.sendMessage(color(message));
    }

    public static String plainString(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    public static void sendClickableMessage(Player player, String message, String hover, String runCommand) {
        TextComponent component = color(message)
                .hoverEvent(color(hover))
                .clickEvent(ClickEvent.runCommand(runCommand));

        player.sendMessage(component);
    }

    public static void sendTitle(Player player, String title, String subtitle) {
        player.showTitle(Title.title(color(title), color(subtitle)));
    }

    /**
     * Only used when interacting with plugins not supporting Adventure.
     */
    @Deprecated
    public static String legacyColor(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void sendBossBar(Player player, BarColor color, String message) {
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
        }.runTaskTimer(HookUtils.INSTANCE, 0, 20);

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
        return IntStream.range(0, size).mapToObj(i -> i < doneLength ? "&6⬛" : "&6⬜").collect(Collectors.joining());
    }

    public static void showActionBar(Player player, String message) {
        player.sendActionBar(color(message));
    }

    public static void broadcast(String message) {
        broadcastRaw(PREFIX + message);
    }

    public static void broadcastRaw(String message) {
        Bukkit.broadcast(color(message));
    }

    public static void guildAlert(Guild guild, String message) {
        guildAlertRaw(guild, PREFIX + message);
    }

    public static void guildAlertRaw(Guild guild, String message) {
        forGuildMembers(guild, player -> sendNoPrefix(player, message));
    }

    public static void forGuildMembers(Guild guild, Consumer<Player> action) {
        guild.getOnlineMembers().forEach(action);
    }

    public static String buildString(String[] args, int index) {
        return IntStream.range(index, args.length).mapToObj(i -> args[i] + " ").collect(Collectors.joining());
    }

    public static void broadcastCenteredMessage(String message) {
        Bukkit.getOnlinePlayers().forEach(player -> sendCenteredMessage(player, message));
    }

    public static void sendCenteredMessage(Player player, String message){
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '§'){
                previousCode = true;
            } else if(previousCode){
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            }else{
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
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        player.sendMessage(sb + message);
    }

    private ChatUtils() {}
}
