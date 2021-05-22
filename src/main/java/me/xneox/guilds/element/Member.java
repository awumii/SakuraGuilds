package me.xneox.guilds.element;

import me.xneox.guilds.type.Permission;
import me.xneox.guilds.type.Rank;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Member {
    private final String name;
    private final List<Permission> permissions;
    private Rank rank;

    public Member(String name, Rank rank, List<Permission> permissions) {
        this.name = name;
        this.rank = rank;
        this.permissions = permissions;
    }

    public static Member parse(String string) {
        String[] split = string.split(";");
        return new Member(split[0], Rank.valueOf(split[1]), IntStream.range(2, split.length).mapToObj(i -> Permission.valueOf(split[i])).collect(Collectors.toList()));
    }

    public String getName() {
        return name;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public boolean hasPermission(Permission permission) {
        return this.permissions.contains(permission);
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    @Override
    public String toString() {
        return this.name + ";" + this.rank.name() + ";" + this.permissions.stream().map(Enum::name).collect(Collectors.joining(";"));
    }
}
