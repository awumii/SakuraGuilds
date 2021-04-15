package me.xneox.guilds.command.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.type.Permission;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class AllyCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        if (args.length < 2) {
            ChatUtils.sendMessage(player, "&cPodaj nazwę gildii.");
            return;
        }

        Guild guild = manager.getGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        if (!guild.getPlayerRank(player).hasPermission(Permission.ALLIES)) {
            ChatUtils.sendMessage(player, "&cTwoja ranga w gildii jest zbyt niska!");
            return;
        }

        Guild otherGuild = manager.getGuildExact(args[1]);
        if (otherGuild == null) {
            ChatUtils.sendMessage(player, "&cPodana gildia nie istnieje.");
            return;
        }

        if (guild.getName().equals(otherGuild.getName())) {
            ChatUtils.sendMessage(player, "&cNie możesz zawrzeć sojuszu ze sobą XDDXDxdDXdx.");
            return;
        }

        if (guild.getAllies().contains(otherGuild.getName())) {
            ChatUtils.sendMessage(player, "&cJuż posiadacie sojusz.");
            return;
        }

        ChatUtils.sendMessage(player, "&7Wysłano zaproszenie sojuszu do &6" + otherGuild.getName());
        guild.log(player.getName() + " wysyła zaproszenie sojuszu do " + otherGuild.getName());


        ChatUtils.guildAlertRaw(otherGuild, " ");
        ChatUtils.guildAlertRaw(otherGuild, "  &7Otrzymano zaproszenie do sojuszu od &6" + guild.getName());
        ChatUtils.guildAlertRaw(otherGuild, " ");

        otherGuild.getMembers().keySet().stream().map(Bukkit::getPlayerExact).filter(Objects::nonNull).forEach(member -> {
            ChatUtils.sendClickableMessage(member, "  &aKliknij, aby zaakceptować.",
                    "&aPo kliknięciu zostaniecie sojusznikami!", "/g x_acceptally IJAD98jdksldM " + guild.getName());
            ChatUtils.sendClickableMessage(member, "  &cKliknij, aby odrzucić.",
                    "&cOdrzuca zaproszenie.", "/g x_acceptally dh98jadOAKD " + guild.getName());
        });

        ChatUtils.guildAlertRaw(otherGuild, " ");
    }
}
