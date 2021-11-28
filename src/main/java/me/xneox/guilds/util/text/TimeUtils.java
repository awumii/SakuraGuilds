package me.xneox.guilds.util.text;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import me.clip.placeholderapi.util.TimeUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Todo: use methods from PlaceholderAPI's TimeUtil instead. or not. just rewrite this
 */
public final class TimeUtils {

  @NotNull
  public static String secondsToTime(int seconds) {
    return millisToTime(TimeUnit.SECONDS.toMillis(seconds));
  }

  @NotNull
  public static String millisToTime(long millis) {
    return TimeUtil.getTime(Duration.ofMillis(millis));
  }

  @NotNull
  public static String futureMillisToTime(long milis) {
    long time = milis - System.currentTimeMillis();
    if (time <= 0) {
      return "brak"; // todo: localize this string
    }

    return TimeUtils.millisToTime(time);
  }

  @NotNull
  public static String timeSince(long milis) {
    return millisToTime(System.currentTimeMillis() - milis);
  }

  @NotNull
  public static String formatDate(long millis) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    return sdf.format(new Date(millis));
  }
}
