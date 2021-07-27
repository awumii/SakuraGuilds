package me.xneox.guilds.type;

import com.google.common.collect.Sets;
import me.xneox.guilds.util.ChatUtils;

import java.util.Arrays;
import java.util.EnumSet;

import static me.xneox.guilds.type.Permission.*;

public enum Rank {
    LEADER(5, "Lider", ChatUtils.CRIMSON + "★★★★", Permission.values()),
    GENERAL(4, "Generał", "&d★★★", Permission.values()),
    OFICER(3, "Oficer", "&b★★", SET_HOME, CLAIM, BUILDINGS, ALLIES, PUBLIC),
    KAPRAL(2, "Kapral", "&6★", SET_HOME, CLAIM),
    REKRUT(1, "Rekrut", ChatUtils.BRONZE + "♧");

    private final int weight;
    private final String name;
    private final String icon;
    private final EnumSet<Permission> defaultPermissions;

    Rank(int weight, String name, String icon, Permission... defaultPermissions) {
        this.weight = weight;
        this.name = name;
        this.icon = icon;
        this.defaultPermissions = Sets.newEnumSet(Arrays.asList(defaultPermissions), Permission.class);
    }

    public boolean isHigher(Rank compareTo) {
        return this.weight > compareTo.weight();
    }

    public String title() {
        return icon + " " + name;
    }

    public int weight() {
        return weight;
    }

    public String icon() {
        return icon;
    }

    public EnumSet<Permission> defaultPermissions() {
        return this.defaultPermissions;
    }
}
