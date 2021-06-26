package me.xneox.guilds.command.misc;

import me.xneox.guilds.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GlobalHelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        if (args.length < 1) {
            player.performCommand("help 1");
            return false;
        }

        switch (args[0]) {
            case "1" -> {
                ChatUtils.sendRaw(player, "&8&m----------------(&r &6&lPOMOC &8&m)----------------");
                ChatUtils.sendRaw(player, "  &e/g help &8- &7Wyświetla komendy gildyjne.");
                ChatUtils.sendRaw(player, "  &e/praca &8- &7Wyświetla menu zatrudnienia.");
                ChatUtils.sendRaw(player, "  &e/warp (nazwa) &8- &7Wyświetla warpy lub teleportuje na podany.");
                ChatUtils.sendRaw(player, "  &e/home &8- &7Teleportacja do ustawionego domu.");
                ChatUtils.sendRaw(player, "  &e/sethome &8- &7Ustawia lokalizację domu.");
                ChatUtils.sendRaw(player, "  &e/rtp &8- &7Teleportacja na losowe koordynaty.");
                ChatUtils.sendRaw(player, "  &e/bal (gracz) &8- &7Wyświetla stan konta.");
                ChatUtils.sendRaw(player, "  &e/baltop &8- &7Wyświetla najbogatszych graczy.");
                ChatUtils.sendRaw(player, "&8&m--------&r &7Użyj &6/pomoc 2 &7aby przejść dalej. &8&m-------");
            }
            case "2" -> {
                ChatUtils.sendRaw(player, "&8&m----------------(&r &6&lPOMOC &8&m)----------------");
                ChatUtils.sendRaw(player, "  &e/ah &8- &7Otwiera menu rynku społeczności.");
                ChatUtils.sendRaw(player, "  &e/ah sell <cena> &8- &7Sprzedaje trzymany przedmiot na rynku.");
                ChatUtils.sendRaw(player, "  &e/helpop <wiadomość> &8- &7Wysyła wiadomość do administracji.");
                ChatUtils.sendRaw(player, "  &e/kit (nazwa) &8- &7Wyświetla zestawy lub odbiera podany zestaw.");
                ChatUtils.sendRaw(player, "  &e/chestsort &8- &7Ustawienia sortowania skrzynek.");
                ChatUtils.sendRaw(player, "  &e/skin <nick> &8- &7Ustawia skina podanego gracza premium.");
                ChatUtils.sendRaw(player, "  &e/suicide &8- &7Samobójstwo.");
                ChatUtils.sendRaw(player, "&8&m--------&r &7Użyj &6/pomoc 3 &7aby przejść dalej. &8&m-------");
            }
            case "3" -> {
                ChatUtils.sendRaw(player, "&8&m----------------(&r &6&lPOMOC &8&m)----------------");
                ChatUtils.sendRaw(player, "  &e/mcmmo &8- &7Wyświetla pomoc od pluginu do umiejętności.");
                ChatUtils.sendRaw(player, "  &e/mctop &8- &7Wyświetla graczy z najwyższym poziomem umiejętności.");
                ChatUtils.sendRaw(player, "  &e/mcstats &8- &7Wyświetla własne statystyki umiejętności.");
                ChatUtils.sendRaw(player, "  &e/mcability &8- &7Wyłącza system umiejętności na narzędziach.");
                ChatUtils.sendRaw(player, "  &e/inspect <nick> &8- &7Wyświetla statystyki podanego gracza.");
                ChatUtils.sendRaw(player, "  &e/pay <nick> <ilość> &8- &7Wysyła donejta graczowi.");
                ChatUtils.sendRaw(player, "  &e/graves &8- &7Wyświetla twoje groby (teleportacja za 400$).");
                ChatUtils.sendRaw(player, "&8&m--------&r &7Użyj &6/pomoc 4 &7aby przejść dalej. &8&m-------");
            }
            case "4" -> {
                ChatUtils.sendRaw(player, "&8&m----------------(&r &6&lPOMOC &8&m)----------------");
                ChatUtils.sendRaw(player, "  &e/quests &8- &7Otwiera menu zadań.");
                ChatUtils.sendRaw(player, "  &e/msgtoggle &8- &7Wyłącza otrzymywanie prywatnych wiadomości.");
                ChatUtils.sendRaw(player, "  &e/tpatoggle &8- &7Wyłącza otrzymywanie zapytań o teleportację.");
                ChatUtils.sendRaw(player, "  &e/ignore <nick> &8- &7Ukrywa podanego gracza na chacie.");
                ChatUtils.sendRaw(player, "  &e/hat &8- &7Zakłada trzymany przedmiot na głowę.");
                ChatUtils.sendRaw(player, "  &e/ec &8- &7Otwiera enderchest (&6VIP&7).");
                ChatUtils.sendRaw(player, "  &e/pp &8- &7Otwiera menu cząsteczek (&dSVIP&7).");
                ChatUtils.sendRaw(player, "&8&m--------------------------------------------");
            }
            default -> ChatUtils.sendMessage(player, "&cNie odnaleziono takiej strony.");
        }
        return true;
    }
}
