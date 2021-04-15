package me.xneox.guilds.command.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ServiceUtils;
import org.bukkit.entity.Player;

public class HelpCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        ChatUtils.sendRaw(player, " &8*------------------------------------------&8*");
        ChatUtils.sendRaw(player, "   &f&lNeonGuilds &7" + ServiceUtils.VERSION);
        ChatUtils.sendRaw(player, "   &7Stworzone przez &fxNeox");
        ChatUtils.sendRaw(player, "");
        ChatUtils.sendRaw(player, "   &e/g top &b- &7Wyświetla najlepsze gildie.");
        ChatUtils.sendRaw(player, "   &e/g create <nazwa> &b- &7Tworzy nową gildię.");
        ChatUtils.sendRaw(player, "   &e/g join <gildia> &b- &7Dołącza do podanej gildii.");
        ChatUtils.sendRaw(player, "   &e/g disband &b- &7Usuwa twoją gildię.");
        ChatUtils.sendRaw(player, "   &e/g public &b- &7Przełącza tryb publicznej/prywatnej gildii.");
        ChatUtils.sendRaw(player, "   &e/g leave &b- &7Opuszcza gildię w której się znajdujesz.");
        ChatUtils.sendRaw(player, "   &e/g claim &b- &7Zajmuje chunk na którym stoisz.");
        ChatUtils.sendRaw(player, "   &e/g unclaim &b- &7Porzuca chunk na którym stoisz.");
        ChatUtils.sendRaw(player, "   &e/g invite <nick> &b- &7Zaprasza gracza do gildii.");
        ChatUtils.sendRaw(player, "   &e/g kick <nick> &b- &7Wyrzuca gracza z gildii.");
        ChatUtils.sendRaw(player, "   &e/g chat &b- &7Przełącza kanał chatu (sojuszniczy/gildyjny).");
        ChatUtils.sendRaw(player, "   &e/g home &b- &7Teleportuje do bazy gildii.");
        ChatUtils.sendRaw(player, "   &e/g sethome &b- &7Ustawia nową bazę gildii.");
        ChatUtils.sendRaw(player, "   &e/g info <gildia> &b- &7Wyświetla informacje o gildii.");
        ChatUtils.sendRaw(player, "   &e/g ally <gildia> &b- &7Zawiera sojusz z gildią.");
        ChatUtils.sendRaw(player, "   &e/g donate <ilość> &b- &7Wpłaca pieniądze do banku gildii.");
        ChatUtils.sendRaw(player, "");
        ChatUtils.sendRaw(player, " &8*------------------------------------------&8*");
    }
}
