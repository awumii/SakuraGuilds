package me.xneox.guilds.gui;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.enums.Permission;
import me.xneox.guilds.enums.Upgrade;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.inventory.InventoryUtils;
import me.xneox.guilds.util.inventory.ItemBuilder;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UpgradesGui implements InventoryProvider {
  public static final SmartInventory INVENTORY = SmartInventory.builder()
      .title("Menu ulepszeń dla gildii")
      .size(3, 9)
      .provider(new UpgradesGui())
      .manager(SakuraGuildsPlugin.get().inventoryManager())
      .build();

  @Override
  public void init(Player player, InventoryContents contents) {
    contents.fill(InventoryUtils.GLASS);
    InventoryUtils.insertBackButton(0, 7, contents, ManagementGui.INVENTORY);

    Guild guild = SakuraGuildsPlugin.get().guildManager().playerGuild(player);
    contents.set(1, 1, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQ0YzFlOGU4MjY3MmJiYTU4OTJmZDQ2NTlmOGRhZDg0ZDE1NDVkYjI2ZGI1MmVjYzkxOGYzMmExMzkxNTEzIn19fQ==")
        .name("&6Zwiększenie Slotów")
        .lore("")
        .lore("&7Limit członków: &f" + guild.maxSlots() + " &a(+" + Upgrade.SLOTS.multiplier() + ")")
        .lore("&7Koszt ulepszenia: &6" + Upgrade.SLOTS.cost(guild) + "$")
        .lore("")
        .lore("&eKliknij, aby ulepszyć.")
        .build(), event -> upgrade(player, Upgrade.SLOTS)));

    contents.set(1, 2, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzgyZWU4NWZlNmRjNjMyN2RhZDIwMmZjYTkzYzlhOTRhYzk5YjdiMTY5NzUyNGJmZTk0MTc1ZDg4NzI1In19fQ==")
        .name("&6Więcej Terenu")
        .lore("")
        .lore("&7Limit chunków: &f" + guild.maxChunks() + " &a(+" + Upgrade.CHUNKS.multiplier() + ")")
        .lore("&7Koszt ulepszenia: &6" + Upgrade.CHUNKS.cost(guild) + "$")
        .lore("")
        .lore("&eKliknij, aby ulepszyć.")
        .build(), event -> upgrade(player, Upgrade.CHUNKS)));

    contents.set(1, 3, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGMxYmQwYWNmMmY5NDA2NGNjYTI3YjBhZWYzNWQ4YzEyYTg5MjhkNDM2MzdhN2ZlNzFmNWVlZTQ1ODk3NWUxYSJ9fX0=")
        .name("&6Zwiększony Magazyn")
        .lore("")
        .lore("&7Ilość slotów: &f" + guild.maxStorage() + " &a(+" + Upgrade.STORAGE.multiplier() + ")")
        .lore("&7Koszt ulepszenia: &6" + Upgrade.STORAGE.cost(guild) + "$")
        .lore("")
        .lore("&eKliknij, aby ulepszyć.")
        .build(), event -> upgrade(player, Upgrade.STORAGE)));

    contents.set(1, 5, ClickableItem.empty(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM2ZTk0ZjZjMzRhMzU0NjVmY2U0YTkwZjJlMjU5NzYzODllYjk3MDlhMTIyNzM1NzRmZjcwZmQ0ZGFhNjg1MiJ9fX0=")
        .name("&6Bank gildyjny")
        .lore("")
        .lore("&7W banku znajduje się: &6" + guild.money() + "$")
        .lore("")
        .lore("&7Aby wpłacać pieniądze, użyj komendy:")
        .lore(" &8→ &6&n/g donate <ilość>")
        .lore("")
        .lore("&cWpłaconych pieniędzy nie można wypłacić!")
        .build()));
  }

  @Override
  public void update(Player player, InventoryContents contents) {}

  private void upgrade(@NotNull Player player, @NotNull Upgrade upgrade) {
    Guild guild = SakuraGuildsPlugin.get().guildManager().playerGuild(player);
    if (!guild.member(player).hasPermission(Permission.UPGRADES)) {
      VisualUtils.sound(player, Sound.ENTITY_VILLAGER_NO);
      ChatUtils.sendMessage(player, "&cNie posiadasz do tego uprawnień!");
      return;
    }

    int cost = upgrade.cost(guild);
    if (guild.money() < cost) {
      VisualUtils.sound(player, Sound.ENTITY_VILLAGER_NO);
      ChatUtils.sendMessage(player, "&cTwoja gildia nie posiada tyle pieniędzy!");
      return;
    }

    if (upgrade.currentValue(guild) >= upgrade.maxValue()) {
      VisualUtils.sound(player, Sound.ENTITY_VILLAGER_NO);
      ChatUtils.sendMessage(player, "&cTo ulepszenie jest już na maksymalnym poziomie!");
      return;
    }

    guild.money(guild.money() - cost);
    upgrade.performUpgrade(guild);

    // Play visual alerts
    VisualUtils.sound(player, Sound.ENTITY_VILLAGER_TRADE);
    ChatUtils.guildAlert(guild, guild.member(player).displayName() + " &7kupuje ulepszenie &e" + upgrade.title() +
        " &8(&7wartość: &6" + (upgrade.currentValue(guild) - upgrade.multiplier()) + " ➠ " + upgrade.currentValue(guild) +
        "&7, &a+" + upgrade.multiplier() + "&8)");

    // Refresh inventory
    UpgradesGui.INVENTORY.open(player);
  }
}
