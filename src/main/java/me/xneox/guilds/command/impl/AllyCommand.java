package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.enums.Permission;
import me.xneox.guilds.util.text.ChatUtils;
import me.xneox.guilds.util.HookUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AllyCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        if (args.length < 2) {
            ChatUtils.sendMessage(player, "&cPodaj nazwę gildii.");
            return;
        }

        Guild guild = manager.playerGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        if (!guild.member(player.getName()).hasPermission(Permission.ALLIES)) {
            ChatUtils.sendMessage(player, "&cTwoja ranga w gildii jest zbyt niska!");
            return;
        }

        Guild otherGuild = manager.get(args[1]);
        if (otherGuild == null) {
            ChatUtils.sendMessage(player, "&cPodana gildia nie istnieje.");
            return;
        }

        if (guild.name().equals(otherGuild.name())) {
            ChatUtils.sendMessage(player, "&cNie możesz zawrzeć sojuszu ze sobą XDDXDxdDXdx.");
            return;
        }

        if (guild.allies().contains(otherGuild.name())) {
            ChatUtils.sendMessage(player, "&cJuż posiadacie sojusz.");
            return;
        }

        if (HookUtils.INSTANCE.cooldownManager().hasCooldown(player, "ally-" + otherGuild.name())) {
            ChatUtils.sendMessage(player, "&cMusisz poczekać &6"
                    + HookUtils.INSTANCE.cooldownManager().getRemaining(player, "ally-" + otherGuild.name()) + " &cprzed wysłaniem zaproszenia.");
            return;
        }

        ChatUtils.sendMessage(player, "&7Wysłano zaproszenie sojuszu do &6" + otherGuild.name());

        ChatUtils.guildAlertRaw(otherGuild, " ");
        ChatUtils.guildAlertRaw(otherGuild, "  &7Otrzymano zaproszenie do sojuszu od &6" + guild.name());
        ChatUtils.guildAlertRaw(otherGuild, " ");

        otherGuild.members().stream().map(Member::nickname).map(Bukkit::getPlayerExact).filter(Objects::nonNull).forEach(member -> {
            ChatUtils.sendClickableMessage(member, "  &aKliknij, aby zaakceptować.",
                    "&aPo kliknięciu zostaniecie sojusznikami!", "/g acceptally IJAD98jdksldM " + guild.name());
            ChatUtils.sendClickableMessage(member, "  &cKliknij, aby odrzucić.",
                    "&cOdrzuca zaproszenie.", "/g acceptally dh98jadOAKD " + guild.name());
        });

        ChatUtils.guildAlertRaw(otherGuild, " ");
        HookUtils.INSTANCE.cooldownManager().add(player, "ally-" + otherGuild.name(), 10, TimeUnit.MINUTES);
    }
}
