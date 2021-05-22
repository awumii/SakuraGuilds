package me.xneox.guilds.util;

import com.destroystokyo.paper.Title;
import me.xneox.guilds.element.Guild;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ChatUtils {
    public static final String PREFIX = "&6&lGILDIE &8➥ &7";
    private final static int CENTER_PX = 154;

    @Nonnull
    public static String colored(@Nonnull String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void sendMessage(@Nonnull Player sender, @Nonnull String message) {
        sendRaw(sender, PREFIX + message);
    }

    public static void sendRaw(@Nonnull Player sender, @Nonnull String message) {
        sender.sendMessage(colored(message));
    }

    @ParametersAreNonnullByDefault
    public static void sendClickableMessage(Player player, String message, String hover, String runCommand) {
        TextComponent component = new TextComponent(colored(message));
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, runCommand));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(colored(hover))));

        player.sendMessage(component);
    }

    @ParametersAreNonnullByDefault
    public static void sendTitle(Player player, String title, String subtitle) {
        player.sendTitle(Title.builder()
                .title(colored(title))
                .subtitle(colored(subtitle))
                .fadeIn(20)
                .stay(80)
                .fadeOut(20)
                .build());
    }

    @ParametersAreNonnullByDefault
    public static void sendBossBar(Player player, BarColor color, String message) {
        BossBar bossBar = Bukkit.createBossBar(colored(message), color, BarStyle.SOLID);
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

    @Nonnull
    public static String formatPlayers(@Nonnull List<Player> list) {
        StringBuilder builder = new StringBuilder();
        for (Player player : list) {
            builder.append(player.getName()).append(", ");
        }
        return builder.toString();
    }

    public static int percentage(double i, double of) {
        return (int) ((i * 100) / of);
    }

    @Nonnull
    public static String progressBar(int done, int total) {
        int size = 10;
        int donePercents = (100 * done) / total;
        int doneLength = size * donePercents / 100;
        return IntStream.range(0, size).mapToObj(i -> i < doneLength ? "&6⬛" : "&6⬜").collect(Collectors.joining());
    }

    public static void sendAction(@Nonnull Player player, @Nonnull String message) {
        player.sendActionBar(colored(message));
    }

    public static void broadcast(@Nonnull String message) {
        broadcastRaw(PREFIX + message);
    }

    public static void broadcastRaw(@Nonnull String message) {
        Bukkit.broadcastMessage(colored(message));
    }

    public static void guildAlert(@Nonnull Guild guild, @Nonnull String message) {
        guildAlertRaw(guild, PREFIX + message);
    }

    public static void guildAlertRaw(@Nonnull Guild guild, @Nonnull String message) {
        forGuildMembers(guild, player -> sendRaw(player, message));
    }

    public static void forGuildMembers(@Nonnull Guild guild, @Nonnull Consumer<Player> action) {
        guild.getOnlineMembers().forEach(action);
    }

    @Nonnull
    public static String buildString(@Nonnull String[] args, int index) {
        return IntStream.range(index, args.length).mapToObj(i -> args[i] + " ").collect(Collectors.joining());
    }

    public static void broadcastCenteredMessage(@Nonnull String message) {
        Bukkit.getOnlinePlayers().forEach(player -> sendCenteredMessage(player, message));
    }

    public static void sendCenteredMessage(@Nonnull Player player, @Nonnull String message){
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
