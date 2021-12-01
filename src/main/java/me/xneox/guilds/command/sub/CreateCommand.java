package me.xneox.guilds.command.sub;

import java.time.Duration;
import java.util.ArrayList;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.enums.Rank;
import me.xneox.guilds.gui.ManagementGui;
import me.xneox.guilds.hook.HookUtils;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChunkUtils;
import me.xneox.guilds.util.NexusBuilder;
import me.xneox.guilds.util.VisualUtils;
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

    if (HookUtils.aureliumSkillsLevel(player) >= ConfigManager.config().guildCreation().aureliumSkillsRequiredLevel()) {
      ChatUtils.sendMessage(player, "&cMusisz mieć przynajmniej &630 poziom &caby odblokować funkcję tworzenia gildii.");
      return;
    }

    if (ChunkUtils.isProtected(player)) {
      return;
    }

    Location nexusLoc = ChunkUtils.getCenter(ChunkUtils.deserialize(player.getChunk()));
    NexusBuilder.buildNexus(nexusLoc, player);

    var config = ConfigManager.config().defaultGuildSettings();

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
            config.health(),
            config.money(),
            config.slots(),
            config.maxChunks(),
            config.storage());

    // Manually set the starting shield duration.
    guild.shieldDuration(Duration.of(config.shieldDuration(), config.shieldDurationUnit()));

    // Add player to the member list as the leader.
    guild.members().add(Member.create(player.getUniqueId(), Rank.LEADER, System.currentTimeMillis()));

    // Claim chunk the player is standing in.
    if (config.claimFirstChunk()) {
      guild.claims().add(player.getLocation().getChunk());
    }

    // Add the guild to the GuildManager.
    manager.guildMap().put(args[1], guild);

    // Place nexus block (end portal is unbreakable and can't be moved by pistons, so it's hardcoded for now)
    player.getWorld().getBlockAt(nexusLoc).setType(Material.END_PORTAL_FRAME);

    player.teleportAsync(nexusLoc);

    // Create hologram above the nexus.
    VisualUtils.createGuildInfo(guild);

    ChatUtils.broadcast("&e" + player.getName() + " &7zakłada gildię &6" + args[1]);
    ManagementGui.INVENTORY.open(player);
  }
}
