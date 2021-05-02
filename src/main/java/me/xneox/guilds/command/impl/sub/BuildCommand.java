package me.xneox.guilds.command.impl.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Building;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.type.Permission;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ChunkUtils;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class BuildCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        Guild guild = manager.getGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        if (!guild.getPlayerRank(player).hasPermission(Permission.SET_HOME)) {
            ChatUtils.sendMessage(player, "&cTwoja pozycja jest zbyt niska.");
            return;
        }

        if (!guild.getChunks().contains(ChunkUtils.toString(player.getChunk()))) {
            ChatUtils.sendMessage(player, "&cBudynki można stawiać tylko na zajętym terenie.");
            return;
        }

        guild.getBuildingAt(player.getChunk()).ifPresentOrElse(building -> {
            building.setState(Building.State.INBUILT);
            building.setCompleteTime(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1));
            ChatUtils.sendMessage(player, "&7Rozpoczęto ulepszanie budowli: &6" + building.getType().getName());
        }, () -> {
            Building building = new Building(ChunkUtils.getCenter(player.getChunk()), Building.Type.BARRACK, Building.State.INBUILT, 0);
            building.setCompleteTime(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1));

            guild.getBuildings().add(building);
            ChatUtils.sendMessage(player, "&7Rozpoczeto budowę nowej budowli!");
        });
    }
}
