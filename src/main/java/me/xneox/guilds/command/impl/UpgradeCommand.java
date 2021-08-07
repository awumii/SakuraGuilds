package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import me.xneox.guilds.util.HookUtils;
import org.bukkit.entity.Player;

public class UpgradeCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        Guild guild = manager.playerGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        HookUtils.INSTANCE.inventoryManager().open("upgrades", player);
    }
}
