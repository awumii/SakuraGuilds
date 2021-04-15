package me.xneox.guilds.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class TimeUtils {
    public static String secondsToTime(int seconds) {
        return millisToTime(TimeUnit.SECONDS.toMillis(seconds));
    }

    public static String millisToTime(long millis) {
        long seconds = (millis/1000) % 60;
        long minutes = ((millis/1000) / 60) % 60;
        long hours = ((millis/1000) / 60) / 60;

        if (hours != 0) {
            return hours + "h, " + minutes + "m, " + seconds + "s";
        } else if (minutes != 0) {
            return minutes + "m, " + seconds + "s";
        } else {
            return TimeUnit.MILLISECONDS.toSeconds(millis) + " sekund";
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
