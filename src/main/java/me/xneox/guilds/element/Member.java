package me.xneox.guilds.element;

import me.xneox.guilds.type.Permission;
import me.xneox.guilds.type.Rank;
import org.bukkit.Bukkit;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Member {
    private final UUID uuid;

    private EnumSet<Permission> permissions;
    private Rank rank;

    private Member(UUID uuid, Rank rank, EnumSet<Permission> permissions) {
        this.uuid = uuid;
        this.rank = rank;
        this.permissions = permissions;
    }

    public static Member create(UUID uuid, Rank rank) {
        return new Member(uuid, rank, rank.defaultPermissions());
    }

    public static Member serialize(String string) {
        String[] split = string.split(";");

        return new Member(UUID.fromString(split[0]), Rank.valueOf(split[1]),
                IntStream.range(2, split.length)
                        .mapToObj(i -> Permission.valueOf(split[i]))
                        .collect(Collectors.toCollection(() -> EnumSet.noneOf(Permission.class))));
    }

    public UUID uuid() {
        return this.uuid;
    }

    public String nickname() {
        return Bukkit.getOfflinePlayer(uuid).getName();
    }

    public Rank rank() {
        return this.rank;
    }

    public void rank(Rank rank) {
        this.rank = rank;
        this.permissions = rank.defaultPermissions();
    }

    public boolean hasPermission(Permission permission) {
        return this.permissions.contains(permission);
    }

    public Set<Permission> permissions() {
        return this.permissions;
    }

    public String displayName() {
        return this.rank.icon() + " " + nickname();
    }

    @Override
    public String toString() {
        return this.uuid + ";" + this.rank.name() + ";" + this.permissions.stream().map(Enum::name).collect(Collectors.joining(";"));
    }
}
