package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.entity.Player;

public class HelpCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        if (args.length < 2) {
            player.performCommand("g help 1");
            return;
        }

        switch (args[1]) {
            case "1" -> {
                ChatUtils.sendRaw(player, "&8&m----------------(&r &6&lGILDIE &8&m)----------------");
                ChatUtils.sendRaw(player, "   &e/g create <nazwa> &8- &7Tworzy nową gildię.");
                ChatUtils.sendRaw(player, "   &e/g browse &8- &7Otwiera wyszukiwarkę gildii.");
                ChatUtils.sendRaw(player, "   &e/g join <gildia> &8- &7Dołącza do podanej gildii.");
                ChatUtils.sendRaw(player, "   &e/g leave &8- &7Opuszcza gildię w której się znajdujesz.");
                ChatUtils.sendRaw(player, "   &e/g disband &8- &7Usuwa twoją gildię.");
                ChatUtils.sendRaw(player, "   &e/g claim &8- &7Zajmuje chunk na którym stoisz.");
                ChatUtils.sendRaw(player, "   &e/g unclaim &8- &7Porzuca chunk na którym stoisz.");
                ChatUtils.sendRaw(player, "&8&m--------&r &7Użyj &6/g help 2 &7aby przejść dalej. &8&m-------");
            }
            case "2" -> {
                ChatUtils.sendRaw(player, "&8&m----------------(&r &6&lGILDIE &8&m)----------------");
                ChatUtils.sendRaw(player, "   &e/g invite <nick> &8- &7Zaprasza gracza do gildii.");
                ChatUtils.sendRaw(player, "   &e/g kick <nick> &8- &7Wyrzuca gracza z gildii.");
                ChatUtils.sendRaw(player, "   &e/g chat &8- &7Przełącza kanał chatu (sojuszniczy/gildyjny).");
                ChatUtils.sendRaw(player, "   &e/g home &8- &7Teleportuje do bazy gildii.");
                ChatUtils.sendRaw(player, "   &e/g sethome &8- &7Ustawia nową bazę gildii.");
                ChatUtils.sendRaw(player, "   &e/g public &8- &7Przełącza tryb publicznej/prywatnej gildii.");
                ChatUtils.sendRaw(player, "&8&m--------&r &7Użyj &6/g help 3 &7aby przejść dalej. &8&m-------");
            }
            case "3" -> {
                ChatUtils.sendRaw(player, "&8&m----------------(&r &6&lGILDIE &8&m)----------------");
                ChatUtils.sendRaw(player, "   &e/g top &8- &7Wyświetla najlepsze gildie.");
                ChatUtils.sendRaw(player, "   &e/g info <gildia> &8- &7Wyświetla informacje o gildii.");
                ChatUtils.sendRaw(player, "   &e/g ally <gildia> &8- &7Zawiera sojusz z gildią.");
                ChatUtils.sendRaw(player, "   &e/g donate <ilość> &8- &7Wpłaca pieniądze do banku gildii.");
                ChatUtils.sendRaw(player, "   &e/g war <gildia> &8- &7Wypowiadanie wojny gildii.");
                ChatUtils.sendRaw(player, "&8&m--------------------------------------------");
            }
            default -> ChatUtils.sendMessage(player, "&cNie odnaleziono takiej strony.");
        }
    }
}
