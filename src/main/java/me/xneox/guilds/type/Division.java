package me.xneox.guilds.type;

import me.xneox.guilds.util.Colors;

public enum Division {
    CHAMPION(500, Colors.ALIZARIN_RED + "Czempion"),
    ADAMANT(300, "&2Adamantyt"),
    RUBY(250, "&cRubin"),
    CRYSTAL(200, "&bKryształ"),
    GOLDEN(150, "&6Złoto"),
    STEEL(100, "&fStal"),
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
