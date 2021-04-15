package me.xneox.guilds.command.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.RankedUtils;
import me.xneox.guilds.util.ServiceUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TopCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        ServiceUtils.INSTANCE.getInventoryManager().open("leaderboards", player);
    }
}
