package me.xneox.guilds.util;

import me.xneox.guilds.element.Guild;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public final class RankedUtils {
    public static List<Guild> getLeaderboard(Collection<Guild> guilds) {
        List<Guild> copy = new ArrayList<>(guilds);
        copy.sort(new TrophyComparator());
        return copy;
    }

    public static class TrophyComparator implements Comparator<Guild> {
        @Override
        public int compare(Guild o1, Guild o2) {
            return Integer.compare(o2.trophies(), o1.trophies());
        }
    }

    private RankedUtils() {}
}
