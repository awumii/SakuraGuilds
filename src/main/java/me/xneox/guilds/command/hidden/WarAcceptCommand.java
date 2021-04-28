package me.xneox.guilds.command.hidden;

import me.xneox.guilds.command.Hidden;
import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.type.Permission;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ServiceUtils;
import me.xneox.guilds.war.Arena;
import me.xneox.guilds.war.ArenaState;
import me.xneox.guilds.war.WarGuild;
import org.bukkit.entity.Player;

@Hidden
public class WarAcceptCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        Guild guild = manager.getGuild(player);
        Guild other = manager.getGuildExact(args[2]);

        if (other.getWarEnemy() != null || guild.getWarEnemy() != null) {
            ChatUtils.sendMessage(player, "&cJedna ze stron już jest w trakcie wojny!");
            return;
        }

        if (!guild.getPlayerRank(player).hasPermission(Permission.WAR)) {
            ChatUtils.sendMessage(player, "&cTwoja ranga w gildii jest zbyt niska!");
            return;
        }

        if (args[1].equals("IJAD98jdksldM")) {
            Arena arena = ServiceUtils.INSTANCE.getArenaManager().getFreeArena();
            if (arena == null) {
                ChatUtils.broadcast("&cBrakuje wolnych aren, wojna została anulowana!");
                return;
            }

            // Enabling the arena for war.
            arena.setTime(30);
            arena.setState(ArenaState.PREPARING);
            arena.setFirstGuild(new WarGuild(other));
            arena.setSecondGuild(new WarGuild(guild));

            // Setting war enemies
            guild.setWarEnemy(other);
            other.setWarEnemy(guild);

            ChatUtils.broadcast("&7Gildie &6" + guild.getName() + " &7oraz &6" + other.getName() + " &7wypowidziały &4&lWOJNĘ!");
        } else if (args[1].equals("dh98jadOAKD")) {
            ChatUtils.guildAlert(guild, guild.getDisplayName(player) + " &7odrzucił zaproszenie wojny od &6" + other.getName());
            ChatUtils.guildAlert(other, guild.getDisplayName(player) + " &7z gildii &6" + guild.getName() + " &7odrzucił wasze zaproszenie do wojny.");
        }
    }
}
