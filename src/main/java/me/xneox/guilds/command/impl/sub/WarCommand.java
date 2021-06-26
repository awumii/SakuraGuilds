package me.xneox.guilds.command.impl.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.HookUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class WarCommand implements SubCommand {

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

        Guild target = manager.getGuildExact(args[1]);
        if (target == null) {
            ChatUtils.sendMessage(player, "&cTaka gildia nie istnieje.");
            return;
        }

        if (target.equals(guild)) {
            ChatUtils.sendMessage(player, "&cCos cie chyba boli xdddDdD.");
            return;
        }

        if (target.getWarEnemy() != null || guild.getWarEnemy() != null) {
            ChatUtils.sendMessage(player, "&cJedna ze stron już jest w trakcie wojny!");
            return;
        }

        /*
        if (guild.isShieldActive()) {
            ChatUtils.sendMessage(player, "&cTwoja gildia posiada tarczę wojenną przez: &6" + TimeUtils.futureMillisToTime(guild.getShield()));
        }

        if (target.isShieldActive()) {
            ChatUtils.sendMessage(player, "&cTa gildia posiada tarczę wojenną przez: &6" + TimeUtils.futureMillisToTime(target.getShield()));
            return;
        }
         */

        if (HookUtils.INSTANCE.getCooldownManager().hasCooldown(player, "war-" + target.getName())) {
            ChatUtils.sendMessage(player, "&cMusisz poczekać &6" + HookUtils.INSTANCE.getCooldownManager().getRemaining(player, "war-" + target.getName()) + " &cprzed wypowiedzeniem wojny.");
            return;
        }

        ChatUtils.sendMessage(player, "&7Wysłano zaproszenie pojedynku do &6" + target.getName());


        ChatUtils.guildAlertRaw(target, " ");
        ChatUtils.guildAlertRaw(target, "  &6&lOtrzymano zaproszenie do POJEDYNKU od gildii" + guild.getName());
        ChatUtils.guildAlertRaw(target, " &fW pojedunku uczestniczą obie gildie, odbywa się on na specjalnej arenie.");
        ChatUtils.guildAlertRaw(target, " &fNikt nie traci przedmiotów, a obie strony dostają nagrodę za uczestnictwo.");
        ChatUtils.guildAlertRaw(target, " ");

        target.getMembers().stream().map(Member::nickname).map(Bukkit::getPlayerExact).filter(Objects::nonNull).forEach(member -> {
            ChatUtils.sendClickableMessage(member, "  &aKliknij, aby zaakceptować.",
                    "&aPo kliknięciu wojna się rozpocznie!", "/g acceptwar IJAD98jdksldM " + guild.getName());
            ChatUtils.sendClickableMessage(member, "  &cKliknij, aby odrzucić.",
                    "&cOdrzuca zaproszenie do wojny.", "/g acceptwar dh98jadOAKD " + guild.getName());
        });

        ChatUtils.guildAlertRaw(target, " ");
        HookUtils.INSTANCE.getCooldownManager().add(player, "war-" + target.getName(), 1, TimeUnit.HOURS);
    }
}
