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
                ChatUtils.sendNoPrefix(player, "&8&m----------------(&r &6&lPOMOC &8&m)----------------");
                ChatUtils.sendNoPrefix(player, "  &e/gildie &8- &7Wyświetla komendy gildyjne.");
                ChatUtils.sendNoPrefix(player, "  &e/skille &8- &7Wyświetla menu twoich umiejętności.");
                ChatUtils.sendNoPrefix(player, "  &e/staty &8- &7Wyświetla menu twoich statystyk.");
                ChatUtils.sendNoPrefix(player, "  &e/rybactwo &8- &7Wyświetla dziennik rybacki.");
                ChatUtils.sendNoPrefix(player, "  &e/enchanty &8- &7Wyświetla wszystkie nowe enchanty.");
                ChatUtils.sendNoPrefix(player, "  &e/groby &8- &7Wyświetla twoje groby.");
                ChatUtils.sendNoPrefix(player, "  &e/rynek &8- &7Otwiera menu rynku społeczności.");
                ChatUtils.sendNoPrefix(player, "  &e/rynek sell <cena> &8- &7Sprzedaje trzymany przedmiot na rynku.");
                ChatUtils.sendNoPrefix(player, "  &e/rtp &8- &7Teleportacja na losowe koordynaty.");
                ChatUtils.sendNoPrefix(player, "&8&m--------&r &7Użyj &6/pomoc 2 &7aby przejść dalej. &8&m-------");
            }
            case "2" -> {
                ChatUtils.sendNoPrefix(player, "&8&m----------------(&r &6&lPOMOC &8&m)----------------");
                ChatUtils.sendNoPrefix(player, "  &e/warp (nazwa) &8- &7Wyświetla warpy lub teleportuje na podany.");
                ChatUtils.sendNoPrefix(player, "  &e/home &8- &7Teleportacja do ustawionego domu.");
                ChatUtils.sendNoPrefix(player, "  &e/sethome &8- &7Ustawia lokalizację domu.");
                ChatUtils.sendNoPrefix(player, "  &e/bal (gracz) &8- &7Wyświetla stan konta.");
                ChatUtils.sendNoPrefix(player, "  &e/baltop &8- &7Wyświetla najbogatszych graczy.");
                ChatUtils.sendNoPrefix(player, "  &e/pay <nick> <ilość> &8- &7Wysyła donejta graczowi.");
                ChatUtils.sendNoPrefix(player, "  &e/kit (nazwa) &8- &7Wyświetla zestawy lub odbiera podany zestaw.");
                ChatUtils.sendNoPrefix(player, "&8&m--------&r &7Użyj &6/pomoc 3 &7aby przejść dalej. &8&m-------");
            }
            case "3" -> {
                ChatUtils.sendNoPrefix(player, "&8&m----------------(&r &6&lPOMOC &8&m)----------------");
                ChatUtils.sendNoPrefix(player, "  &e/helpop <wiadomość> &8- &7Wysyła wiadomość do administracji.");
                ChatUtils.sendNoPrefix(player, "  &e/skiny &8- &7Wyświetla menu dostępnych skinów.");
                ChatUtils.sendNoPrefix(player, "  &e/skin <nick> &8- &7Ustawia skina podanego gracza premium.");
                ChatUtils.sendNoPrefix(player, "  &e/msgtoggle &8- &7Wyłącza otrzymywanie prywatnych wiadomości.");
                ChatUtils.sendNoPrefix(player, "  &e/tpatoggle &8- &7Wyłącza otrzymywanie zapytań o teleportację.");
                ChatUtils.sendNoPrefix(player, "  &e/ignore <nick> &8- &7Ukrywa podanego gracza na chacie.");
                ChatUtils.sendNoPrefix(player, "  &e/hat &8- &7Zakłada trzymany przedmiot na głowę.");
                ChatUtils.sendNoPrefix(player, "  &e/suicide &8- &7Samobójstwo.");
                ChatUtils.sendNoPrefix(player, "&8&m--------------------------------------------");
            }
            default -> ChatUtils.sendMessage(player, "&cNie odnaleziono takiej strony.");
        }
        return true;
    }
}
