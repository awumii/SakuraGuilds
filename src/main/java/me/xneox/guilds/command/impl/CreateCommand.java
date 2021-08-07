package me.xneox.guilds.command.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.internal.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.enums.Rank;
import me.xneox.guilds.gui.ManagementGui;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChunkUtils;
import me.xneox.guilds.util.HookUtils;
import me.xneox.guilds.util.LocationUtils;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.inventory.InventoryUtils;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
      ChatUtils.sendMessage(player, "&cNazwa gildii przekracza 16 znaków.");
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

    if (HookUtils.getAureliumLevel(player) < 29) {
      ChatUtils.sendMessage(
          player,
          "&cMusisz mieć przynajmniej &630 poziom &caby odblokować funkcję tworzenia gildii.");
      return;
    }

    if (!player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 1)) {
      ChatUtils.sendMessage(
          player, "&cCena za założenie gildii wynosi: &61x Diament &c(nie posiadasz)");
      return;
    }

    if (ChunkUtils.isProtected(player)) {
      return;
    }

    SakuraGuildsPlugin.get().userManager().user(player).joinDate(new Date().getTime());

    Location nexusLoc = ChunkUtils.getCenter(ChunkUtils.deserialize(player.getChunk()));
    nexusLoc.setY(30);

    Guild guild =
        new Guild(
            args[1],
            new ArrayList<>(),
            nexusLoc,
            new ArrayList<>(),
            player.getLocation(),
            new ArrayList<>(),
            new ItemStack[0],
            new Date().getTime(),
            0,
            3,
            0,
            6,
            6,
            9);

    manager.guildMap().put(args[1], guild);

    guild.shieldDuration(Duration.ofDays(1));
    guild.members().add(Member.create(player.getUniqueId(), Rank.LEADER));
    guild.claims().add(ChunkUtils.deserialize(player.getLocation().getChunk()));

    ChatUtils.broadcast("&e" + player.getName() + " &7zakłada gildię &6" + args[1]);

    // USTAWIANIE NEXUSA

    for (Location sphere : LocationUtils.sphere(nexusLoc, 5, 4, false, true, 3)) {
      if (sphere.getBlock().getType() != Material.BEDROCK) {
        sphere.getBlock().setType(Material.AIR);
      }
    }

    InventoryUtils.removeItems(player.getInventory(), Material.DIAMOND, 1);
    player.getWorld().getBlockAt(nexusLoc).setType(Material.END_PORTAL_FRAME);
    player.teleportAsync(nexusLoc);

    ManagementGui.INVENTORY.open(player);

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
