package me.xneox.guilds.enums;

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

  public String getDescription() {
    return description;
  }
}
