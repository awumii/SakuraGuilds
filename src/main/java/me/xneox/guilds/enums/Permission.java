package me.xneox.guilds.enums;

import org.jetbrains.annotations.NotNull;

/**
 * Permissions manage the priveleges of guild members and default guild ranks.
 * TODO replace with object
 */
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

  Permission(@NotNull String description) {
    this.description = description;
  }

  @NotNull
  public String getDescription() {
    return description;
  }
}
