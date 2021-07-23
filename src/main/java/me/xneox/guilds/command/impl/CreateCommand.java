package me.xneox.guilds.command.impl;

import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.type.Rank;
import me.xneox.guilds.util.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

public class CreateCommand implements SubCommand {

    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        if (args.length < 2) {
            ChatUtils.sendMessage(player, "&cMusisz podać nazwę gildii.");
            return;
        }

        if (!args[1].matches("^[a-zA-Z0-9]+$")) {
            ChatUtils.sendMessage(player, "&cNazwa gildii zawiera niedozwolone znaki.");
            return;
        }

        if (args[1].length() > 16) {
            ChatUtils.sendMessage(player, "&cNazwa gildii przekracza 12 znaków.");
            return;
        }

        if (manager.guildMap().containsKey(args[1])) {
            ChatUtils.sendMessage(player, "&cGildia o takiej nazwie już istnieje!");
            return;
        }

        if (manager.playerGuild(player.getName()) != null) {
            ChatUtils.sendMessage(player, "&cJuż posiadasz gildię!");
            return;
        }

        if (ChunkUtils.isProtected(player)) {
            return;
        }

        HookUtils.INSTANCE.userManager().getUser(player).setJoinDate();

        Location nexusLoc = ChunkUtils.getCenter(ChunkUtils.toString(player.getChunk()));
        nexusLoc.setY(30);

        Guild guild = new Guild(args[1], new ArrayList<>(), nexusLoc, new Date().getTime(), new ArrayList<>(), player.getLocation(), new ArrayList<>(),
                0, 3, 100, 0, 0, 0, 6, 6, 9, new ItemStack[0]);

        manager.guildMap().put(args[1], guild);

        guild.shieldDuration(Duration.ofDays(1));
        guild.members().add(new Member(player.getName(), Rank.LEADER, Rank.LEADER.defaultPermissions()));
        guild.claims().add(ChunkUtils.toString(player.getLocation().getChunk()));

        ChatUtils.broadcast("&e" + player.getName() + " &7zakłada gildię &6" + args[1]);

        // USTAWIANIE NEXUSA

        for (Location sphere : SpaceUtils.sphere(nexusLoc, 4, 4, false, true, 3)) {
            if (sphere.getBlock().getType() != Material.BEDROCK) {
                sphere.getBlock().setType(Material.AIR);
            }
        }

        player.getWorld().getBlockAt(nexusLoc).setType(Material.END_PORTAL_FRAME);
        player.teleport(nexusLoc);

        HookUtils.INSTANCE.inventoryManager().open("management", player);

        Location light;

        light = nexusLoc.clone();
        light.setY(nexusLoc.getY() - 1);
        light.setX(nexusLoc.getX() + 1);
        player.getWorld().getBlockAt(light).setType(Material.SEA_LANTERN);

        light = nexusLoc.clone();
        light.setY(nexusLoc.getY() - 1);
        light.setX(nexusLoc.getX() - 1);
        player.getWorld().getBlockAt(light).setType(Material.SEA_LANTERN);

        light = nexusLoc.clone();
        light.setY(nexusLoc.getY() - 1);
        light.setZ(nexusLoc.getZ() + 1);
        player.getWorld().getBlockAt(light).setType(Material.SEA_LANTERN);

        light = nexusLoc.clone();
        light.setY(nexusLoc.getY() - 1);
        light.setZ(nexusLoc.getZ() - 1);
        player.getWorld().getBlockAt(light).setType(Material.SEA_LANTERN);

        VisualUtils.createGuildInfo(guild);
    }
}
