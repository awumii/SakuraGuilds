package me.xneox.guilds.command.admin;

import me.xneox.guilds.command.PermissibleCommand;
import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ServiceUtils;
import org.bukkit.entity.Player;

@PermissibleCommand("guilds.admin")
public class CreateWarKitCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        ServiceUtils.INSTANCE.getKitManager().setDefaultWarKit(player);
        ChatUtils.sendMessage(player, "Pomyślnie zapisano domyślny zestaw wojenny.");
    }
}
