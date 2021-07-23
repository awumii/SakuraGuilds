package me.xneox.guilds.type;

import javax.annotation.Nullable;
import java.util.Arrays;

public enum Permission {
    KICK("Wyrzucanie członków"),
    SET_HOME("Zmiana bazy"),
    CLAIM("Zajmowanie ziem"),
    RANKS("Zarządzanie uprawnieniami"),
    BUILDINGS("Tworzenie i ulepszenie budynków"),
    ALLIES("Dyplomacja"),
    WAR("Wypowiadanie wojen"),
    PUBLIC("Zmiana statusu publiczny/prywatny");

    private final String description;

    Permission(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Nullable
    public static Permission find(String description) {
        return Arrays.stream(Permission.values())
                .filter(permission -> description.contains(permission.getDescription()))
                .findFirst()
                .orElse(null);
    }
}
