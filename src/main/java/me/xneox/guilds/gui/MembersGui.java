package me.xneox.guilds.gui;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.inventory.InventoryUtils;
import me.xneox.guilds.util.inventory.ItemBuilder;
import me.xneox.guilds.util.text.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class MembersGui implements InventoryProvider {
  public static final SmartInventory INVENTORY = SmartInventory.builder()
      .title("Menu zarządzania członkami")
      .size(6, 9)
      .provider(new MembersGui())
      .build();

  @Override
  public void init(Player player, InventoryContents contents) {
    contents.fillBorders(InventoryUtils.GLASS);
    InventoryUtils.insertBackButton(0, 8, contents, ManagementGui.INVENTORY);

    Guild guild = SakuraGuildsPlugin.get().guildManager().playerGuild(player.getName());
    for (Member member : guild.members()) {
      User user = SakuraGuildsPlugin.get().userManager().user(member.uuid());
      ItemStack skull = ItemBuilder.skullOf(member.nickname())
          .name("&6" + member.nickname())
          .lore("&e(Dołączył do gildii: &f" + member.joinDate() + "&e)")
          .lore("&e(Widziany ostatnio: &f"
              + TimeUtils.timeSince(Bukkit.getOfflinePlayer(member.uuid()).getLastSeen())
              + " temu&e)")
          .lore("")
          .lore("&eRanga: " + member.rank().title())
          .lore("&eWojna: ")
          .lore(" &7→ Puchary: &6" + user.trophies() + "★")
          .lore(" &7→ Zabójstwa: &f" + user.kills())
          .lore(" &7→ Śmierci: &f" + user.deaths())
          .lore("")
          .lore("&7&nKliknij PRAWYM aby")
          .lore("  &fzarządzać rangą w gildii")
          .lore("")
          .lore("&7&nKliknij ŚRODKOWYM aby")
          .lore("  &fwyrzucić gracza z gildii.")
          .build();

      contents.add(ClickableItem.of(skull, event -> {
        if (event.getClick() == ClickType.MIDDLE) {
          player.performCommand("g kick " + member.nickname());
        } else {
          SakuraGuildsPlugin.get().userManager().user(player).editorSubject(member.nickname());
          RankEditorGui.INVENTORY.open(player);
        }
      }));
    }
  }

  @Override
  public void update(Player player, InventoryContents contents) {}
}

