package me.xneox.guilds.enums;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public enum Permission {
    BUILD("Budowanie oraz interakcje na terenie gildii."),
    KICK("Wyrzucanie członków"),
    SET_HOME("Zmiana bazy"),
    CLAIM("Zajmowanie ziem"),
    RANKS("Zarządzanie uprawnieniami"),
    UPGRADES("Ulepszenia gildyjne"),
    ALLIES("Dyplomacja"),
    WAR("Wypowiadanie wojen");

    private final String description;

    Permission(String description) {
        this.description = description;
    }

    @Nullable
    public static Permission find(String description) {
        return Arrays.stream(Permission.values())
                .filter(permission -> description.contains(permission.getDescription()))
                .findFirst()
                .orElse(null);
    }

    public String getDescription() {
        return description;
    }
}
