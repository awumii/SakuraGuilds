package me.xneox.guilds.command.impl.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.type.Rank;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.HookUtils;
import org.bukkit.entity.Player;

public class JoinCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        if (args.length < 2) {
            ChatUtils.sendMessage(player, "&cMusisz podać nazwę gildii!");
            return;
        }

        if (manager.getGuild(player.getName()) != null) {
            ChatUtils.sendMessage(player, "&cJuż posiadasz gildię.");
            return;
        }

        Guild guild = manager.getGuildExact(args[1]);
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cTaka gildia nie istnieje.");
            return;
        }

        if (!guild.isPublic() && !guild.getInvitations().contains(player.getName()) && !player.isOp()) {
            ChatUtils.sendMessage(player, "&cNie zostałeś zaproszony do tej gildii.");
            return;
        }

        if (guild.getMembers().size() >= guild.getMaxMembers()) {
            ChatUtils.sendMessage(player, "&cTa gildia osiągnęła limit członków!");
            return;
        }

        guild.getMembers().add(new Member(player.getName(), Rank.REKRUT, Rank.REKRUT.getDefaultPermissions()));
        HookUtils.INSTANCE.getUserManager().getUser(player).setJoinDate();
        HookUtils.INSTANCE.getInventoryManager().open("management", player);
        ChatUtils.broadcast("&e" + player.getName() + " &7dołącza do gildii &6" + guild.getName());
    }
}
