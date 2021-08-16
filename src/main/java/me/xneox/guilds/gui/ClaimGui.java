package me.xneox.guilds.gui;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import java.util.List;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.ChunkUtils;
import me.xneox.guilds.util.LocationUtils;
import me.xneox.guilds.util.inventory.InventorySize;
import me.xneox.guilds.util.inventory.InventoryUtils;
import me.xneox.guilds.util.inventory.ItemBuilder;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClaimGui implements InventoryProvider {
  public static final SmartInventory INVENTORY = SmartInventory.builder()
      .title("Zarządzanie Zajętymi Terenami")
      .size(6, 9)
      .provider(new ClaimGui())
      .build();

  @Override
  public void init(Player player, InventoryContents contents) {
    contents.fillBorders(InventoryUtils.GLASS);
    InventoryUtils.insertBackButton(0, 8, contents, ManagementGui.INVENTORY);

    Guild guild = SakuraGuildsPlugin.get().guildManager().playerGuild(player.getName());

    for (String chunkData : guild.claims()) {
      Chunk chunk = ChunkUtils.serialize(chunkData);
      List<Player> players = ChunkUtils.getPlayersAt(chunk);

      ItemStack item = ItemBuilder.of(chunk.getWorld().getName().contains("nether") ? Material.NETHERRACK : Material.GRASS_BLOCK  )
          .name("&6#" + guild.claims().indexOf(chunkData)
              + " (" + LocationUtils.legacyDeserialize(ChunkUtils.getCenter(chunk)) + ")")
          .lore("&7&oTwoja gildia posiada ten chunk.")
          .lore("")
          .lore("&7Gracze: &c" + (players.isEmpty() ? "Brak" : ChatUtils.formatPlayerList(players)))
          .lore("")
          .lore("&eKliknij, aby się przeteleportować")
          .amount(players.isEmpty() ? 1 : players.size())
          .build();

      contents.add(ClickableItem.of(item, event -> {
        Location center = ChunkUtils.getCenter(chunk);
        SakuraGuildsPlugin.get().userManager().user(player)
            .beginTeleport(player.getLocation(), center);

        player.closeInventory();
      }));
    }
  }

  @Override
  public void update(Player player, InventoryContents contents) {}
}
