package me.xneox.guilds.type;

public enum ChatChannel {
    GLOBAL("Globalny"),
    GUILD("&aGildyjny"),
    ALLY("&6Sojuszniczy");

    private final String name;

    ChatChannel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
