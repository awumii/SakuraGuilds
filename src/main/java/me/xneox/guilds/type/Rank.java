package me.xneox.guilds.type;

import me.xneox.guilds.util.Colors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.xneox.guilds.type.Permission.*;

public enum Rank {
    LEADER(5, "Lider", Colors.CRIMSON + "★★★★", Permission.values()),
    GENERAL(4, "Generał", "&d★★★", Permission.values()),
    OFICER(3, "Oficer", "&b★★", SET_HOME, CLAIM, BUILDINGS, ALLIES, PUBLIC),
    KAPRAL(2, "Kapral", "&6★", SET_HOME, CLAIM),
    REKRUT(1, "Rekrut", Colors.BRONZE + "♧");

    private final int priority;
    private final String name;
    private final String icon;
    private final List<Permission> defaultPermissions;

    Rank(int priority, String name, String icon, Permission... defaultPermissions) {
        this.priority = priority;
        this.name = name;
        this.icon = icon;
        this.defaultPermissions = new ArrayList<>(Arrays.asList(defaultPermissions));
    }

    public boolean isHigher(Rank compareTo) {
        return this.priority > compareTo.getPriority();
    }

    public List<Permission> getDefaultPermissions() {
        return defaultPermissions;
    }

    public String getDisplay() {
        return icon + " " + name;
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }
}
