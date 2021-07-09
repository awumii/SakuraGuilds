package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.SubCommand;
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

        if (manager.playerGuild(args[1]) != null) {
            ChatUtils.sendMessage(player, "&cTen gracz już posiada gildię.");
            return;
        }

        Guild guild = manager.playerGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        if (guild.members().size() >= guild.maxSlots()) {
            ChatUtils.sendMessage(player, "&cOsiągnięto limit graczy dodanych do gildii. Zakup ulepszenie gildii!");
            return;
        }

        if (guild.isMember(args[1])) {
            ChatUtils.sendMessage(player, "&cTen gracz jest już dodany do gildii.");
            return;
        }

        if (HookUtils.INSTANCE.cooldownManager().hasCooldown(player, "invite-" + target.getName())) {
            ChatUtils.sendMessage(player, "&cMusisz poczekać &6"
                    + HookUtils.INSTANCE.cooldownManager().getRemaining(player, "invite-" + target.getName()) + " &cprzed wysłaniem zaproszenia.");
            return;
        }

        guild.invitations().add(args[1]);
        ChatUtils.sendMessage(player, "Zaproszono gracza &6" + args[1] + " &7do twojej gildii.");

        ChatUtils.sendRaw(target, "");
        ChatUtils.sendRaw(target, "  &7Otrzymano zaproszenie do gildii &6" + manager.playerGuild(player.getName()).name());
        ChatUtils.sendClickableMessage(target, "  &aKliknij, aby zaakceptować.",
                "&aPo kliknięciu dołączysz do gildii!", "/g join " + guild.name());
        ChatUtils.sendRaw(target, "");

        HookUtils.INSTANCE.cooldownManager().add(player, "invite-" + target.getName(), 10, TimeUnit.MINUTES);
    }
}
