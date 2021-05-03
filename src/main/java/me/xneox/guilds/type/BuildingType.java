package me.xneox.guilds.type;

public enum BuildingType {
    TOWNHALL("Ratusz", 30),
    BARRACK("Koszary", 60),
    ALCHEMY("Labolatorium", 60),
    STORAGE("Magazyn", 20);

    private final String name;
    private final int time;

    BuildingType(String name, int baseProcessTime) {
        this.name = name;
        this.time = baseProcessTime;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public int getProcessTimeForLevel(int level) {
        return this.time * level;
    }
}
