package me.xneox.guilds.command.impl.hidden;

import me.xneox.guilds.command.annotations.Hidden;
import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.type.Permission;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.HookUtils;
import me.xneox.guilds.war.Arena;
import me.xneox.guilds.war.ArenaState;
import me.xneox.guilds.war.WarParticipant;
import org.bukkit.entity.Player;

@Hidden
public class WarAcceptCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        Guild guild = manager.playerGuild(player);
        Guild other = manager.get(args[2]);

        if (other.warEnemy() != null || guild.warEnemy() != null) {
            ChatUtils.sendMessage(player, "&cJedna ze stron już jest w trakcie wojny!");
            return;
        }

        if (!guild.member(player.getName()).hasPermission(Permission.WAR)) {
            ChatUtils.sendMessage(player, "&cTwoja ranga w gildii jest zbyt niska!");
            return;
        }

        if (args[1].equals("IJAD98jdksldM")) {
            Arena arena = HookUtils.INSTANCE.arenaManager().findFree();
            if (arena == null) {
                ChatUtils.broadcast("&cBrakuje wolnych aren, wojna została anulowana!");
                return;
            }

            // Enabling the arena for war.
            arena.setTime(30);
            arena.setState(ArenaState.PREPARING);
            arena.setFirstGuild(new WarParticipant(other));
            arena.setSecondGuild(new WarParticipant(guild));

            // Setting war enemies
            guild.warEnemy(other);
            other.warEnemy(guild);

            ChatUtils.broadcast("&7Gildie &6" + guild.name() + " &7oraz &6" + other.name() + " &7rozpoczęły &4&lPOJEDYNEK!");
        } else if (args[1].equals("dh98jadOAKD")) {
            ChatUtils.guildAlert(guild, guild.member(player).displayName() + " &7odrzucił zaproszenie pojedynku od &6" + other.name());
            ChatUtils.guildAlert(other, guild.member(player).displayName() + " &7z gildii &6" + guild.name() + " &7odrzucił wasze zaproszenie do pojedynku.");
        }
    }
}
