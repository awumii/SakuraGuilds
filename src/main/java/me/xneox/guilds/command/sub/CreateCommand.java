package me.xneox.guilds.command.sub;

import java.time.Duration;
import java.util.ArrayList;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.enums.Rank;
import me.xneox.guilds.gui.ManagementGui;
import me.xneox.guilds.hook.HookUtils;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChunkUtils;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.inventory.InventoryUtils;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CreateCommand implements SubCommand {

  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
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

    if (HookUtils.aureliumSkillsLevel(player) < 29) {
      ChatUtils.sendMessage(player, "&cMusisz mieć przynajmniej &630 poziom &caby odblokować funkcję tworzenia gildii.");
      return;
    }

    if (!player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 1)) {
      ChatUtils.sendMessage(player, "&cCena za założenie gildii wynosi: &61x Diament &c(nie posiadasz)");
      return;
    }

    if (ChunkUtils.isProtected(player)) {
      return;
    }

    Location nexusLoc = ChunkUtils.getCenter(ChunkUtils.deserialize(player.getChunk()));
    if (nexusLoc.getY() > 90) {
      ChatUtils.sendMessage(player, "&cNie możesz zakładać gildii powyżej Y=90");
      return;
    }

    // todo different methods of guild creation
    nexusLoc.setY(nexusLoc.getY() + 2);
    if (!HookUtils.pasteSchematic("nexus.schematic", nexusLoc)) {
      ChatUtils.sendMessage(player, "&cWystąpił błąd uniemożliwiający na wygenerowanie nexusa. Skontaktuj sie z administracja.");
    }

    Guild guild = new Guild(
            args[1],
            new ArrayList<>(),
            nexusLoc,
            new ArrayList<>(),
            player.getLocation(),
            new ArrayList<>(),
            new ItemStack[0],
            System.currentTimeMillis(),
            0,
            3,
            0,
            6,
            6,
            9);

    guild.shieldDuration(Duration.ofDays(1));

    guild.members().add(Member.create(player.getUniqueId(), Rank.LEADER, System.currentTimeMillis()));
    guild.claims().add(ChunkUtils.deserialize(player.getLocation().getChunk()));

    manager.guildMap().put(args[1], guild);

    InventoryUtils.removeItems(player.getInventory(), Material.DIAMOND, 1);

    player.getWorld().getBlockAt(nexusLoc).setType(Material.END_PORTAL_FRAME);
    player.teleportAsync(nexusLoc);

    VisualUtils.createGuildInfo(guild);

    ChatUtils.broadcast("&e" + player.getName() + " &7zakłada gildię &6" + args[1]);
    ManagementGui.INVENTORY.open(player);
  }
}
