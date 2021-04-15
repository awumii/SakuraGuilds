package me.xneox.guilds.command.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class WarCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        if (!player.isOp()) {
            ChatUtils.sendMessage(player, "&cWojny gildii jeszcze nie są gotowe.");
        }

        if (args.length < 2) {
            ChatUtils.sendMessage(player, "&cPodaj nazwę gildii.");
            return;
        }

        Guild guild = manager.getGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        Guild target = manager.getGuildExact(args[1]);
        if (target == null) {
            ChatUtils.sendMessage(player, "&cTaka gildia nie istnieje.");
            return;
        }

        if (target.getWarEnemy() != null || guild.getWarEnemy() != null) {
            ChatUtils.sendMessage(player, "&cJedna ze stron już jest w trakcie wojny!");
            return;
        }

        ChatUtils.sendMessage(player, "&7Wysłano zaproszenie wojny do &6" + target.getName());


        ChatUtils.guildAlertRaw(target, " ");
        ChatUtils.guildAlertRaw(target, "  &7Otrzymano zaproszenie do WOJNY od &6" + guild.getName());
        ChatUtils.guildAlertRaw(target, " ");

        target.getMembers().keySet().stream().map(Bukkit::getPlayerExact).filter(Objects::nonNull).forEach(member -> {
            ChatUtils.sendClickableMessage(member, "  &aKliknij, aby zaakceptować.",
                    "&aPo kliknięciu wojna się rozpocznie!", "/g akceptujwojne IJAD98jdksldM " + guild.getName());
            ChatUtils.sendClickableMessage(member, "  &cKliknij, aby odrzucić.",
                    "&cOdrzuca zaproszenie do wojny.", "/g akceptujwojne dh98jadOAKD " + guild.getName());
        });

        ChatUtils.guildAlertRaw(target, " ");
    }
}
