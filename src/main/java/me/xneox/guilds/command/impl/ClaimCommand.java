package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.enums.Permission;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ChunkUtils;
import me.xneox.guilds.util.LocationUtils;
import me.xneox.guilds.util.VisualUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.time.Duration;

public class ClaimCommand implements SubCommand {

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

        if (guild.claims().size() >= guild.maxChunks()) {
            ChatUtils.sendMessage(player, "&cPrzekroczono limit chunków. Zakup ulepszenie gildii!");
            return;
        }

        if (ChunkUtils.isProtected(player)) {
            return;
        }

        String chunk = ChunkUtils.deserialize(player.getLocation().getChunk());
        guild.claims().add(chunk);
        ChatUtils.guildAlert(guild, guild.member(player).displayName() + " &7zajmuje chunk: &6" + LocationUtils.legacyDeserialize(player.getLocation()));

        Location hologramLoc = ChunkUtils.getCenter(chunk);
        hologramLoc.setY(hologramLoc.getY() + 3);

        VisualUtils.createTimedHologram(hologramLoc, Duration.ofSeconds(20), Material.DIAMOND_SHOVEL,
                "&6&lGILDIA " + guild.name() + " ZAJMUJE TEN TEREN", "&7Lokalizacja: &f" + chunk, "&7Przez: &f" + player.getName());
    }
}
