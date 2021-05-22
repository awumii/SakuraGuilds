package me.xneox.guilds.command.impl.admin;

import me.xneox.guilds.command.annotations.AdminOnly;
import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.war.Arena;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.HookUtils;
import org.bukkit.entity.Player;

@AdminOnly
public class SetFirstSpawnCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        if (args.length < 2) {
            ChatUtils.sendMessage(player, "&cMusisz podać nazwę areny.");
            return;
        }

        Arena arena = HookUtils.INSTANCE.getArenaManager().getArena(args[1]);
        if (arena == null) {
            ChatUtils.sendMessage(player, "&cTaka arena nie istnieje.");
            return;
        }

        arena.setFirstSpawn(player.getLocation());
        ChatUtils.sendMessage(player, "&7Pomyślnie ustawiono pierwszy spawn areny &6" + args[1]);
    }
}
