package me.xneox.guilds.type;

public enum BuildingType {
    TOWNHALL("Ratusz", 60, 6),
    BARRACK("Koszary", 120, 5),
    ALCHEMY("Labolatorium", 320, 3),
    STORAGE("Magazyn", 120, 6);

    private final String name;
    private final int time;
    private final int maxLevel;

    BuildingType(String name, int baseProcessTime, int maxLevel) {
        this.name = name;
        this.time = baseProcessTime;
        this.maxLevel = maxLevel;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getProcessTimeForLevel(int level) {
        return this.time * level;
    }
}
