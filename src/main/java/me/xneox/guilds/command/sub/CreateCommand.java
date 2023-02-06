package me.xneox.guilds.command.sub;

import java.time.Duration;
import java.util.ArrayList;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.enums.Rank;
import me.xneox.guilds.gui.ManagementGui;
import me.xneox.guilds.integration.HolographicDisplaysHook;
import me.xneox.guilds.integration.Integrations;
import me.xneox.guilds.manager.ConfigManager;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChunkUtils;
import me.xneox.guilds.util.NexusBuilder;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CreateCommand implements SubCommand {

  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
    var config = ConfigManager.messages().commands();

    if (args.length < 2) {
      ChatUtils.sendMessage(player, config.noGuildSpecified());
      return;
    }

    if (!args[1].matches(ConfigManager.config().guildCreation().allowedNameRegex())) {
      ChatUtils.sendMessage(player, config.createInvalidName());
      return;
    }

    if (args[1].length() > ConfigManager.config().guildCreation().maximumNameLenght()) {
      ChatUtils.sendMessage(player, config.createNameTooLong());
      return;
    }

    if (args[1].length() < ConfigManager.config().guildCreation().minimumNameLenght()) {
      ChatUtils.sendMessage(player, config.createNameTooShort());
      return;
    }

    if (manager.guildMap().containsKey(args[1])) {
      ChatUtils.sendMessage(player, config.createExists());
      return;
    }

    if (manager.playerGuild(player.getName()) != null) {
      ChatUtils.sendMessage(player, config.createHasGuild());
      return;
    }

    if (ChunkUtils.isProtected(player)) {
      return;
    }

    // Build the nexus!
    var nexusLoc = ChunkUtils.getCenter(ChunkUtils.deserialize(player.getChunk()));
    NexusBuilder.buildNexus(nexusLoc, player);

    // Default guild settings
    var def = ConfigManager.config().defaultGuildSettings();

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
        def.health(),
        def.maxHealth(),
        def.money(),
        def.slots(),
        def.maxChunks(),
        def.storage());

    // Manually set the starting shield duration.
    guild.shieldDuration(Duration.of(def.shieldDuration(), def.shieldDurationUnit()));

    // Add player to the member list as the leader.
    guild.members().add(Member.create(player.getUniqueId(), Rank.LEADER, System.currentTimeMillis()));

    // Claim chunk the player is standing in.
    if (def.claimFirstChunk()) {
      guild.claims().add(player.getLocation().getChunk());
    }

    // Add the guild to the GuildManager.
    manager.guildMap().put(args[1], guild);

    // Place nexus block (end portal is unbreakable and can't be moved by pistons, so it's hardcoded for now)
    player.getWorld().getBlockAt(nexusLoc).setType(Material.END_PORTAL_FRAME);

    player.teleportAsync(nexusLoc);

    // Create hologram above the nexus.
    if (Integrations.HOLOGRAMS_AVAILABLE) {
      HolographicDisplaysHook.createGuildInfo(guild);
    }

    ChatUtils.broadcast(config.createAnnoucement()
        .replace("{PLAYER}", player.getName())
        .replace("{GUILD}", guild.name()));

    ManagementGui.INVENTORY.open(player);
  }
}
