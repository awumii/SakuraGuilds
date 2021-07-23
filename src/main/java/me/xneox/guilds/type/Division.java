package me.xneox.guilds.type;

import me.xneox.guilds.util.Colors;

public enum Division {
    CHAMPION(1500, Colors.ALIZARIN_RED + "Czempion"),
    ADAMANT(1000, "&2Adamantyt"),
    RUBY(800, "&cRubin"),
    CRYSTAL(600, "&bKryształ"),
    GOLDEN(400, "&6Złoto"),
    STEEL(200, "&fStal"),
    BRONZE(0, Colors.BRONZE + "Brąz");

    private final int minPoints;
    private final String name;

    Division(int minPoints, String name) {
        this.minPoints = minPoints;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getMinPoints() {
        return minPoints;
    }
}
