package me.xneox.guilds.command.admin;

import me.xneox.guilds.command.PermissibleCommand;
import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Arena;
import me.xneox.guilds.manager.ArenaManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ServiceUtils;
import org.bukkit.entity.Player;

@PermissibleCommand("guilds.admin")
public class ArenaCreateCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        if (args.length < 2) {
            ChatUtils.sendMessage(player, "&cMusisz podać nazwę areny.");
            return;
        }

        ArenaManager arenaManager = ServiceUtils.INSTANCE.getArenaManager();
        if (arenaManager.getArena(args[1]) != null) {
            ChatUtils.sendMessage(player, "&cTaka arena już istnieje.");
            return;
        }

        arenaManager.getArenaMap().put(args[1], new Arena(args[1]));
        ChatUtils.sendMessage(player, "&7Stworzono pomyślnie arenę &6" + args[1]);
    }
}
