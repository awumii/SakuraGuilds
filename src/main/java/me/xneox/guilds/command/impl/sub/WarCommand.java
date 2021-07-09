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

        Guild guild = manager.playerGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        Guild target = manager.get(args[1]);
        if (target == null) {
            ChatUtils.sendMessage(player, "&cTaka gildia nie istnieje.");
            return;
        }

        if (target.equals(guild)) {
            ChatUtils.sendMessage(player, "&cCos cie chyba boli xdddDdD.");
            return;
        }

        if (target.warEnemy() != null || guild.warEnemy() != null) {
            ChatUtils.sendMessage(player, "&cJedna ze stron już jest w trakcie wojny!");
            return;
        }

        if (HookUtils.INSTANCE.cooldownManager().hasCooldown(player, "war-" + target.name())) {
            ChatUtils.sendMessage(player, "&cMusisz poczekać &6" + HookUtils.INSTANCE.cooldownManager().getRemaining(player, "war-" + target.name()) + " &cprzed wypowiedzeniem wojny.");
            return;
        }

        ChatUtils.sendMessage(player, "&7Wysłano zaproszenie pojedynku do &6" + target.name());


        ChatUtils.guildAlertRaw(target, " ");
        ChatUtils.guildAlertRaw(target, "  &6&lOtrzymano zaproszenie do POJEDYNKU od gildii" + guild.name());
        ChatUtils.guildAlertRaw(target, " &fW pojedunku uczestniczą obie gildie, odbywa się on na specjalnej arenie.");
        ChatUtils.guildAlertRaw(target, " &fNikt nie traci przedmiotów, a obie strony dostają nagrodę za uczestnictwo.");
        ChatUtils.guildAlertRaw(target, " ");

        target.members().stream().map(Member::nickname).map(Bukkit::getPlayerExact).filter(Objects::nonNull).forEach(member -> {
            ChatUtils.sendClickableMessage(member, "  &aKliknij, aby zaakceptować.",
                    "&aPo kliknięciu wojna się rozpocznie!", "/g acceptwar IJAD98jdksldM " + guild.name());
            ChatUtils.sendClickableMessage(member, "  &cKliknij, aby odrzucić.",
                    "&cOdrzuca zaproszenie do wojny.", "/g acceptwar dh98jadOAKD " + guild.name());
        });

        ChatUtils.guildAlertRaw(target, " ");
        HookUtils.INSTANCE.cooldownManager().add(player, "war-" + target.name(), 1, TimeUnit.HOURS);
    }
}
