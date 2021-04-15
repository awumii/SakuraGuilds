package me.xneox.guilds.command.admin;

import me.xneox.guilds.command.PermissibleCommand;
import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Arena;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ServiceUtils;
import org.bukkit.entity.Player;

@PermissibleCommand("guilds.admin")
public class SetFirstSpawnCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        if (args.length < 2) {
            ChatUtils.sendMessage(player, "&cMusisz podać nazwę areny.");
            return;
        }

        Arena arena = ServiceUtils.INSTANCE.getArenaManager().getArena(args[1]);
        if (arena == null) {
            ChatUtils.sendMessage(player, "&cTaka arena nie istnieje.");
            return;
        }

        arena.setFirstSpawn(player.getLocation());
        ChatUtils.sendMessage(player, "&7Pomyślnie ustawiono pierwszy spawn areny &6" + args[1]);
    }
}
