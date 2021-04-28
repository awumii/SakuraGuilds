package me.xneox.guilds.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class TimeUtils {
    public static String secondsToTime(int seconds) {
        return millisToTime(TimeUnit.SECONDS.toMillis(seconds));
    }

    public static String millisToTime(long millis) {
        int totalSeconds = (int) Math.floor(millis / 1000);
        int totalMinutes = (int) Math.floor(totalSeconds / 60);
        int totalHours = (int) Math.floor(totalMinutes / 60);
        int days = (int) Math.floor(totalHours / 24);

        long seconds = totalSeconds % 60;
        long minutes = totalMinutes % 60;
        long hours = totalHours % 24;

        if (days > 0) {
            return days + "d, " + hours + "h, " + minutes + "m, " + seconds + "s";
        } else if (hours > 0) {
            return hours + "h, " + minutes + "m, " + seconds + "s";
        } else if (minutes > 0) {
            return minutes + "m, " + seconds + "s";
        } else {
            return seconds + "s";
        }
    }

    public static String futureMillisToTime(long milis) {
        long time = milis - System.currentTimeMillis();
        if (time <= 0) {
            return "brak";
        }

        return TimeUtils.millisToTime(time);
    }

    public static String formatDate(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(new Date(millis));
    }

    private TimeUtils() {}
}
