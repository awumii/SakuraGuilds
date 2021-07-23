package me.xneox.guilds.element;

import me.xneox.guilds.type.Permission;
import me.xneox.guilds.type.Rank;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Member {
    private final String nickname;

    private EnumSet<Permission> permissions;
    private Rank rank;

    public Member(String nickname, Rank rank, EnumSet<Permission> permissions) {
        this.nickname = nickname;
        this.rank = rank;
        this.permissions = permissions;
    }

    public String nickname() {
        return this.nickname;
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
        return this.rank.icon() + " " + nickname;
    }

    public static Member parse(String string) {
        String[] split = string.split(";");

        return new Member(split[0], Rank.valueOf(split[1]),
                IntStream.range(2, split.length)
                        .mapToObj(i -> Permission.valueOf(split[i]))
                        .collect(Collectors.toCollection(() -> EnumSet.noneOf(Permission.class))));
    }

    @Override
    public String toString() {
        return this.nickname + ";" + this.rank.name() + ";" + this.permissions.stream().map(Enum::name).collect(Collectors.joining(";"));
    }
}
