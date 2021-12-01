package me.xneox.guilds.config;

import java.util.List;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@SuppressWarnings("ALL") // make intellij shut up about using final fields that would break the config loader.
@ConfigSerializable
public class MessagesConfiguration {

  private Chat chat = new Chat();
  private Divisions divisions = new Divisions();
  private Commands commands = new Commands();

  @ConfigSerializable
  public static class Commands {
    // General
    private String unknownCommand = "&cNie odnaleziono takiej komendy.";
    private String noPermission = "&cNie posiadasz uprawnień do tej komendy.";
    private String internalError = "&cWystąpił błąd podczas wykonywania komendy: &4{ERR}";
    private String noGuildSpecified = "&cPodaj nazwę gildii.";
    private String noGuild = "&cNie posiadasz gildii.";
    private String unknownGuild = "&cPodana gildia nie istnieje.";
    private String noGuildPermission = "&cTwoja ranga w gildii jest zbyt niska!";
    private String cooldown = "&cMusisz poczekać jeszcze &6{TIME}";

    // Ally
    private String allyAlready = "&cSojusz już został zawarty!";
    private String allyNoInvitation = "&cTa gildia nie wysłała wam zaproszenia, lub zaproszenie wygasło.";
    private String allySuccessBroadcast = "&7Gildie &6{1} &7oraz &6{2} &7zawarły &aSOJUSZ!";
    private String allyDeniedSelf = "&e{PLAYER} &7odrzucił zaproszenie sojuszu od &6{GUILD}";
    private String allyDeniedOther = "&e{PLAYER} &7o&7z gildii &6{GUILD} &7odrzucił wasze zaproszenie do sojuszu.";
    private String allyCantSelf = "&cNie możesz zawrzeć sojuszu ze sobą.";
    private String allyInvitationSent = "&7Wysłano zaproszenie sojuszu do &6{GUILD}";
    private List<String> allyInvitationPrefix = List.of(
        " ",
        "  &7Otrzymano zaproszenie do sojuszu od &6{GUILD}",
        " ");
    private String allyInvitationButtonAccept = "  &aKliknij, aby zaakceptować.";
    private String allyInvitationButtonAcceptHover = "&aPo kliknięciu zostaniecie sojusznikami.";
    private String allyInvitationButtonDeny = "  &cKliknij, aby odrzucić.";
    private String allyInvitationButtonDenyHover = "&aPo kliknięciu zaproszenie zostanie odrzucone.";
    private List<String> allyInvitationSuffix = List.of(" ", " ");

    // Creation
    private String createAnnoucement = "&e{PLAYER} &7zakłada gildię &6{GUILD}";
    private String createInvalidName = "&cNazwa gildii zawiera niedozwolone znaki.";
    private String createNameTooLong = "&cNazwa gildii zbyt długa.";
    private String createNameTooShort = "&cNazwa gildii zbyt krótka.";
    private String createExists = "&cGildia o takiej nazwie już istnieje!";
    private String createHasGuild = "&cJuż posiadasz gildię!";
    private String createAureliumSkillsLevel = "&cMusisz mieć przynajmniej &6{REQUIRED} poziom &caby odblokować funkcję tworzenia gildii.";

    // Deleting
    private String deleteNoPermission = "&cMusisz być liderem gildii.";
    private String deleteConfirm = "&7Użyj komendy ponownie aby potwierdzić usunięcie gildii. &cTA AKCJA JEST NIEODWRACALNA!";
    private String deleteAnnoucement = "&e{PLAYER} &7rozwiązuje gildię &c{GUILD}";

    private String donateAmount = "&cPodaj ilość do wpłacenia.";
    private String donateNoEconomy = "&cSerwer nie posiada ekonomii. Skontaktuj się z administracją.";
    private String donateYouArePoor = "&cNie posiadasz tyle pieniędzy!";
    private String donateGuildNotify = "&e{MEMBER} &7wpłacił &6{AMOUNT}$ &7do banku gildii";

    // Misc
    private String channelSwitched = "&7Przełączono na kanał {CHANNEL}";
    private String claimLimit = "&cPrzekroczono limit chunków. Zakup ulepszenie gildii!";
    private String claimGuildNotify = "&e{PLAYER} &7zajmuje chunk: &6{LOCATION}";

    public String claimLimit() {
      return this.claimLimit;
    }

    public String createAnnoucement() {
      return this.createAnnoucement;
    }

    public String donateAmount() {
      return this.donateAmount;
    }

    public String donateNoEconomy() {
      return this.donateNoEconomy;
    }

    public String donateYouArePoor() {
      return this.donateYouArePoor;
    }

    public String donateGuildNotify() {
      return this.donateGuildNotify;
    }

    public String deleteNoPermission() {
      return this.deleteNoPermission;
    }

    public String deleteConfirm() {
      return this.deleteConfirm;
    }

    public String deleteAnnoucement() {
      return this.deleteAnnoucement;
    }

    public String createInvalidName() {
      return this.createInvalidName;
    }

    public String createNameTooLong() {
      return this.createNameTooLong;
    }

    public String createNameTooShort() {
      return this.createNameTooShort;
    }

    public String createExists() {
      return this.createExists;
    }

    public String createHasGuild() {
      return this.createHasGuild;
    }

    public String createAureliumSkillsLevel() {
      return this.createAureliumSkillsLevel;
    }

    public String claimGuildNotify() {
      return this.claimGuildNotify;
    }

    public String cooldown() {
      return this.cooldown;
    }

    public String allyCantSelf() {
      return this.allyCantSelf;
    }

    public String unknownCommand() {
      return this.unknownCommand;
    }

    public String noPermission() {
      return this.noPermission;
    }

    public String internalError() {
      return this.internalError;
    }

    public String noGuildSpecified() {
      return this.noGuildSpecified;
    }

    public String noGuild() {
      return this.noGuild;
    }

    public String unknownGuild() {
      return this.unknownGuild;
    }

    public String noGuildPermission() {
      return this.noGuildPermission;
    }

    public String allyAlready() {
      return this.allyAlready;
    }

    public String allyNoInvitation() {
      return this.allyNoInvitation;
    }

    public String allySuccessBroadcast() {
      return this.allySuccessBroadcast;
    }

    public String allyDeniedSelf() {
      return this.allyDeniedSelf;
    }

    public String allyDeniedOther() {
      return this.allyDeniedOther;
    }

    public String allyInvitationSent() {
      return this.allyInvitationSent;
    }

    public List<String> allyInvitationPrefix() {
      return this.allyInvitationPrefix;
    }

    public String allyInvitationButtonAccept() {
      return this.allyInvitationButtonAccept;
    }

    public String allyInvitationButtonAcceptHover() {
      return this.allyInvitationButtonAcceptHover;
    }

    public String allyInvitationButtonDeny() {
      return this.allyInvitationButtonDeny;
    }

    public String allyInvitationButtonDenyHover() {
      return this.allyInvitationButtonDenyHover;
    }

    public List<String> allyInvitationSuffix() {
      return this.allyInvitationSuffix;
    }

    public String channelSwitched() {
      return this.channelSwitched;
    }
  }

  @ConfigSerializable
  public static class Chat {
    private String guildPlaceholder = "&8[&#E74C3C{GUILD}&8] ";
    private String noGuildPlaceholder = "&8[No Guild]";
    private String guildChatFormat = " &8[&aGUILD&8] {MEMBER}&8: &a{MESSAGE}";
    private String allyChatFormat = " &8[&bALLY&8] &7(&d{GUILD}&7) {MEMBER}&8: &d{MESSAGE}";
    private String channelNameGlobal = "Globalny";
    private String channelNameGuild = "&aGildyjny";
    private String channelNameAlly = "&6Sojuszniczy";

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

    public String channelNameGlobal() {
      return this.channelNameGlobal;
    }

    public String channelNameGuild() {
      return this.channelNameGuild;
    }

    public String channelNameAlly() {
      return this.channelNameAlly;
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

  public Commands commands() {
    return this.commands;
  }
}
