package me.xneox.guilds.gui;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.inventory.InventoryUtils;
import me.xneox.guilds.util.inventory.ItemBuilder;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AlliesGui implements InventoryProvider {
  public static final SmartInventory INVENTORY = SmartInventory.builder()
      .title("Zawarte Sojusze")
      .size(6, 9)
      .provider(new AlliesGui())
      .manager(SakuraGuildsPlugin.get().inventoryManager())
      .build();

  @Override
  public void init(Player player, InventoryContents contents) {
    contents.fillBorders(InventoryUtils.GLASS);
    InventoryUtils.insertBackButton(0, 8, contents, ManagementGui.INVENTORY);

    Guild guild = SakuraGuildsPlugin.get().guildManager().playerGuild(player.getName());
    if (guild.allies().isEmpty()) {
      ItemStack empty = ItemBuilder.of(Material.WRITABLE_BOOK)
          .name("&c&n&oNie znaleziono sojuszy")
          .build();

      contents.set(2, 4, ClickableItem.empty(empty));
    }

    for (Guild targetGuild : guild.allies()) {

      ItemStack item =
          ItemBuilder.skullOf(targetGuild.leader().nickname())
              .name("&6" + targetGuild.name())
              .lore("")
              .lore("&eLider:")
              .lore("&f" + targetGuild.leader().nickname())
              .lore("")
              .lore("&eLiczba członków:")
              .lore("&f" + targetGuild.members().size() + "/" + targetGuild.maxSlots()
                  + " &7(&a" + targetGuild.getOnlineMembers().size() + " &fonline&7)")
              .lore("")
              .lore("&eZajęte ziemie:")
              .lore("&f" + targetGuild.claims().size() + " &7(Limit: &f" + targetGuild.maxChunks() + "&7)")
              .lore("")
              .lore("&eStatystyki Wojny:")
              .lore("  &7→ &7Dywizja: " + targetGuild.division().getName())
              .lore("  &7→ &7Puchary rankingowe: &f" + targetGuild.trophies())
              .lore("")
              .lore("&cKliknij aby zerwać sojusz")
              .build();

      contents.add(ClickableItem.of(item, event -> {
        guild.allies().remove(targetGuild);
        targetGuild.allies().remove(guild);

        player.closeInventory();
        VisualUtils.sound(player, Sound.BLOCK_ANVIL_DESTROY);
        ChatUtils.broadcast("&7Gildia &6" + guild.name() + " &7zrywa sojusz z &6" + targetGuild.name());
      }));
    }
  }

  @Override
  public void update(Player player, InventoryContents contents) {}
}
