package me.xneox.guilds.command.impl.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.type.Permission;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ChunkUtils;
import org.bukkit.entity.Player;

public class UnClaimCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        Guild guild = manager.playerGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        if (!guild.member(player.getName()).hasPermission(Permission.CLAIM)) {
            ChatUtils.sendMessage(player, "&cNie posiadasz uprawnień do zajmowania terenu.");
            return;
        }

        String chunk = ChunkUtils.toString(player.getChunk());
        if (!guild.claims().contains(chunk)) {
            ChatUtils.sendMessage(player, "&cTen chunk nie został zajęty.");
            return;
        }

        if (guild.isNexusChunk(player.getChunk())) {
            ChatUtils.sendMessage(player, "&cTen chunk zawiera nexusa i nie może być porzucony.");
            return;
        }

        guild.claims().remove(chunk);
        ChatUtils.guildAlert(guild, guild.member(player).displayName() + " &cporzuca chunk: &6" + chunk);
    }
}
