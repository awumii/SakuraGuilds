package me.xneox.guilds.enums;

import me.xneox.guilds.element.Guild;

public enum Upgrade {
    SLOTS("Zwiększenie Slotów", 20, 6, 4),
    CHUNKS("Więcej Terenu", 64, 2, 6),
    STORAGE("Zwiększony Magazyn", 54, 6, 9);

    private final String title;
    private final int maxValue;   // Maximum value of the upgrade.
    private final int exponent;   // How much will be divided from upgrade cost.
    private final int multiplier; // How much the upgrade level will increase.

    Upgrade(String title, int maxValue, int exponent, int multiplier) {
        this.title = title;
        this.maxValue = maxValue;
        this.exponent = exponent;
        this.multiplier = multiplier;
    }

    public String title() {
        return this.title;
    }

    public int maxValue() {
        return this.maxValue;
    }

    public int multiplier() {
        return this.multiplier;
    }

    public void performUpgrade(Guild guild) {
        switch (this) {
            case SLOTS -> guild.maxSlots(guild.maxSlots() + this.multiplier);
            case CHUNKS -> guild.maxChunks(guild.maxChunks() + this.multiplier);
            case STORAGE -> guild.maxStorage(guild.maxStorage() + this.multiplier);
        };
    }

    public int currentValue(Guild guild) {
        return switch (this) {
            case SLOTS -> guild.maxSlots();
            case CHUNKS -> guild.maxChunks();
            case STORAGE -> guild.maxStorage();
        };
    }

    public int cost(Guild guild) {
        return currentValue(guild) * 200 / this.exponent;
    }
}
