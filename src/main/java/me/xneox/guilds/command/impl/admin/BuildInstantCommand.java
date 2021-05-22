package me.xneox.guilds.command.impl.admin;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.command.annotations.AdminOnly;
import me.xneox.guilds.element.Building;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.type.BuildingType;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.entity.Player;

@AdminOnly
public class BuildInstantCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        if (args.length < 3) {
            ChatUtils.sendMessage(player, "&cPodaj nazwę gildii i budynku.");
            return;
        }

        Guild guild = manager.getGuildExact(args[1]);
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie odnaleziono takiej gildii.");
            return;
        }

        BuildingType type = BuildingType.valueOf(args[2]);
        Building building = guild.getBuildingOfType(type);
        if (building != null && building.getState() == Building.State.INBUILT) {
            building.setCompleteTime(System.currentTimeMillis());
            ChatUtils.sendMessage(player, "&7Wymuszono budowę budynku &6" + type);
        }
    }
}
