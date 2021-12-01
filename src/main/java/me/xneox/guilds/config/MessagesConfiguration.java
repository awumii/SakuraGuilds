package me.xneox.guilds.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@SuppressWarnings("ALL") // make intellij shut up about using final fields that would break the config loader.
@ConfigSerializable
public class MessagesConfiguration {

  private Chat chat = new Chat();
  private Divisions divisions = new Divisions();

  @ConfigSerializable
  public static class Chat {
    private String guildPlaceholder = "&8[&#E74C3C{GUILD}&8] ";
    private String noGuildPlaceholder = "&8[No Guild]";
    private String guildChatFormat = " &8[&aGUILD&8] {MEMBER}&8: &a{MESSAGE}";
    private String allyChatFormat = " &8[&bALLY&8] &7(&d{GUILD}&7) {MEMBER}&8: &d{MESSAGE}";

    public String guildPlaceholder() {
      return this.guildPlaceholder;
    }

    public String noGuildPlaceholder() {
      return this.noGuildPlaceholder;
    }

    public String guildChatFormat() {
      return this.guildChatFormat;
    }

    public String allyChatFormat() {
      return this.allyChatFormat;
    }
  }

  @ConfigSerializable
  public static class Divisions {
    private String champion = "&6&#E74C3CCzempion";
    private String adamant = "&2Adamantyt";
    private String ruby = "&cRubin";
    private String crystal = "&bKryształ";
    private String golden = "&6Złoto";
    private String steel = "&fStal";
    private String bronze = "&#cd7f32Brąz";

    public String champion() {
      return this.champion;
    }

    public String adamant() {
      return this.adamant;
    }

    public String ruby() {
      return this.ruby;
    }

    public String crystal() {
      return this.crystal;
    }

    public String golden() {
      return this.golden;
    }

    public String steel() {
      return this.steel;
    }

    public String bronze() {
      return this.bronze;
    }
  }

  public Divisions divisions() {
    return this.divisions;
  }

  public Chat chat() {
    return this.chat;
  }
}
