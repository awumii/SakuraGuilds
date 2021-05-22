package me.xneox.guilds.command.impl.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.HookUtils;
import org.bukkit.entity.Player;

public class BrowseCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        HookUtils.INSTANCE.getInventoryManager().open("browse", player);
    }
}
