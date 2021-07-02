package me.xneox.guilds.command.impl.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.HookUtils;
import me.xneox.guilds.util.VisualUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class DonateCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        if (args.length < 2) {
            ChatUtils.sendMessage(player, "&cPodaj ilość do wpłacenia.");
            return;
        }

        Guild guild = manager.getGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        int money = Integer.parseInt(args[1]);
        if (HookUtils.ECONOMY.has(player, money)) {
            VisualUtils.sound(player, Sound.ENTITY_WITHER_BREAK_BLOCK);
            HookUtils.ECONOMY.withdrawPlayer(player, money);

            guild.money(guild.money() + money);
            ChatUtils.guildAlert(guild, guild.getDisplayName(player) + " &7wpłacił &6" + money + "$ &7do banku gildii.");
        } else {
            ChatUtils.sendMessage(player, "&cNie posiadasz tyle pieniędzy!");
        }
    }
}