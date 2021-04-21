package me.xneox.guilds.type;

import me.xneox.guilds.util.Colors;

import java.util.Arrays;
import java.util.List;

import static me.xneox.guilds.type.Permission.*;

public enum Rank {
    LEADER(5, "Lider", Colors.CRIMSON + "⭐⭐⭐⭐", Permission.values()),
    GENERAL(4, "Generał", "&d★★★", Permission.values()),
    OFICER(3, "Oficer", "&b★★", SET_HOME, CLAIM, UPGRADES, ALLIES, PUBLIC),
    KAPRAL(2, "Kapral", "&6★", SET_HOME, CLAIM),
    REKRUT(1, "Rekrut", Colors.BRONZE + "♧");

    private final int priority;
    private final String name;
    private final String icon;
    private final List<Permission> permissions;

    Rank(int priority, String name, String icon, Permission... permissions) {
        this.priority = priority;
        this.name = name;
        this.icon = icon;
        this.permissions = Arrays.asList(permissions);
    }

    public boolean isHigher(Rank compareTo) {
        return this.priority > compareTo.getPriority();
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
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
