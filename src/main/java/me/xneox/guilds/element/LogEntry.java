package me.xneox.guilds.element;

import me.xneox.guilds.util.TimeUtils;

public class LogEntry {
    private final long time;
    private final String value;

    public LogEntry(long time, String value) {
        this.time = time;
        this.value = value;
    }

    public String getHowMuchElapsed() {
        return TimeUtils.millisToTime(System.currentTimeMillis() - this.time);
    }

    public long getTime() {
        return time;
    }

    public String getValue() {
        return value;
    }
}
