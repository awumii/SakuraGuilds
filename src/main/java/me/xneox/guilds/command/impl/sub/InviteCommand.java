package me.xneox.guilds.command.impl.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.HookUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class InviteCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        if (args.length < 2) {
            ChatUtils.sendMessage(player, "&cPodaj nick gracza.");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            ChatUtils.sendMessage(player, "&cTen gracz nie jest online.");
            return;
        }

        if (manager.getGuild(args[1]) != null) {
            ChatUtils.sendMessage(player, "&cTen gracz już posiada gildię.");
            return;
        }

        Guild guild = manager.getGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        if (guild.getMembers().size() >= guild.maxSlots()) {
            ChatUtils.sendMessage(player, "&cOsiągnięto limit graczy dodanych do gildii. Zakup ulepszenie gildii!");
            return;
        }

        if (guild.isMember(args[1])) {
            ChatUtils.sendMessage(player, "&cTen gracz jest już dodany do gildii.");
            return;
        }

        if (HookUtils.INSTANCE.getCooldownManager().hasCooldown(player, "invite-" + target.getName())) {
            ChatUtils.sendMessage(player, "&cMusisz poczekać &6"
                    + HookUtils.INSTANCE.getCooldownManager().getRemaining(player, "invite-" + target.getName()) + " &cprzed wysłaniem zaproszenia.");
            return;
        }

        guild.getInvitations().add(args[1]);
        ChatUtils.sendMessage(player, "Zaproszono gracza &6" + args[1] + " &7do twojej gildii.");

        ChatUtils.sendRaw(target, "");
        ChatUtils.sendRaw(target, "  &7Otrzymano zaproszenie do gildii &6" + manager.getGuild(player.getName()).getName());
        ChatUtils.sendClickableMessage(target, "  &aKliknij, aby zaakceptować.",
                "&aPo kliknięciu dołączysz do gildii!", "/g join " + guild.getName());
        ChatUtils.sendRaw(target, "");

        HookUtils.INSTANCE.getCooldownManager().add(player, "invite-" + target.getName(), 10, TimeUnit.MINUTES);
    }
}
