package me.xneox.guilds.command.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ServiceUtils;
import org.bukkit.entity.Player;

public class BrowseCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        ServiceUtils.INSTANCE.getInventoryManager().open("browse", player);
    }
}
