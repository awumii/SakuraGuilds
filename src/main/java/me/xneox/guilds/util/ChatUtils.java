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
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ChatUtils {
    public static final String PREFIX = "&6&lGILDIE &8➥ &7";
    private final static int CENTER_PX = 154;

    public static String colored(@Nonnull String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void sendMessage(@Nonnull Player sender, @Nonnull String message) {
        sendRaw(sender, PREFIX + message);
    }

    public static void sendRaw(@Nonnull Player sender, @Nonnull String message) {
        sender.sendMessage(colored(message));
    }

    public static void sendClickableMessage(Player player, String message, String hover, String runCommand) {
        TextComponent component = new TextComponent(colored(message));
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, runCommand));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(colored(hover))));

        player.sendMessage(component);
    }

    public static void sendTitle(@Nonnull Player player, @Nonnull String title, @Nonnull String subtitle) {
        player.sendTitle(Title.builder()
                .title(colored(title))
                .subtitle(colored(subtitle))
                .fadeIn(20)
                .stay(80)
                .fadeOut(20)
                .build());
    }

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
        }.runTaskTimer(ServiceUtils.INSTANCE, 0, 20);

        bossBar.setVisible(true);
        bossBar.addPlayer(player);
    }

    public static String formatPlayers(List<Player> list) {
        StringBuilder builder = new StringBuilder();
        for (Player player : list) {
            builder.append(player.getName()).append(", ");
        }
        return builder.toString();
    }

    public static void sendAction(Player player, String message) {
        player.sendActionBar(colored(message));
    }

    public static void broadcast(String message) {
        broadcastRaw(PREFIX + message);
    }

    public static void broadcastRaw(String message) {
        Bukkit.broadcastMessage(colored(message));
    }

    public static void guildAlert(Guild guild, String message) {
        guildAlertRaw(guild, PREFIX + message);
    }

    public static void guildAlertRaw(Guild guild, String message) {
        forGuildMembers(guild, player -> sendRaw(player, message));
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
        player.sendMessage(sb.toString() + message);
    }

    private ChatUtils() {}
}
