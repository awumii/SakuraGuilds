package me.xneox.guilds.gui;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.inventory.InventoryUtils;
import me.xneox.guilds.util.inventory.ItemBuilder;
import me.xneox.guilds.util.inventory.InventorySize;
import org.bukkit.entity.Player;

public class LeaderboardsGui implements InventoryProvider {
  public static final SmartInventory INVENTORY = SmartInventory.builder()
      .title("Ranking Gildii")
      .size(InventorySize.BIGGEST.rows(), 0)
      .build();

  @Override
  public void init(Player player, InventoryContents contents) {
    contents.fillBorders(InventoryUtils.GLASS);
    InventoryUtils.insertBackButton(0, 8, contents, ManagementGui.INVENTORY);

    contents.set(1, 4, buildGuildInfo(0));
    contents.set(2, 3, buildGuildInfo(1));
    contents.set(2, 5, buildGuildInfo(2));
    contents.set(3, 2, buildGuildInfo(3));
    contents.set(3, 4, buildGuildInfo(4));
    contents.set(3, 6, buildGuildInfo(5));
    contents.set(4, 1, buildGuildInfo(6));
    contents.set(4, 3, buildGuildInfo(7));
    contents.set(4, 5, buildGuildInfo(8));
    contents.set(4, 7, buildGuildInfo(9));
  }

  @Override
  public void update(Player player, InventoryContents contents) {}

  private ClickableItem buildGuildInfo(int position) {
    int realPosition = position + 1;
    if (position >= SakuraGuildsPlugin.get().guildManager().leaderboard().size()) {
      return ClickableItem.empty(
          ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjk3ZTJjOGI4Mjc2Mjc2ZTM4ZGVjYTg4NTA4NzJkNTllY2M5YzFmMzhmMmFkY2U0MDg1OGVkYjllNjM0ZDdiYSJ9fX0=")
          .name("&cNikt nie zajął " + realPosition + " pozycji.")
          .build());
    }

    Guild guild = SakuraGuildsPlugin.get().guildManager().leaderboard().get(position);
    return ClickableItem.empty(
        ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRkZTRkOTViYTRhNDZkYjdlNTY2YjM0NWY3ODk0ZDFkMjU4Zjg5M2ViOTJjNzgwYjNkYTc3NWVlZGY5MSJ9fX0=")
        .name("&7" + realPosition + ". &6" + guild.name())
        .lore("")
        .lore("&eLider:")
        .lore("&f" + guild.leader().nickname())
        .lore("")
        .lore("&eLiczba członków:")
        .lore("&f" + guild.members().size() + "/" + guild.maxSlots() + " &7(&a" + guild.getOnlineMembers().size() + " &fonline&7)")
        .lore("")
        .lore("&eZajęte ziemie:")
        .lore("&f" + guild.claims().size() + " &7(Limit: &f" + guild.maxChunks() + "&7)")
        .lore("")
        .lore("&eStatystyki Wojny:")
        .lore("  &7→ &7Dywizja: " + guild.division().getName())
        .lore("  &7→ &7Puchary rankingowe: &f" + guild.trophies())
        .lore("")
        .build());
  }
}
