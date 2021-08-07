package me.xneox.guilds.gui;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.inventory.InventorySize;
import me.xneox.guilds.util.inventory.ItemBuilder;
import me.xneox.guilds.util.text.TimeUtils;
import org.bukkit.entity.Player;

public class ManagementGui implements InventoryProvider {
  public static final SmartInventory INVENTORY = SmartInventory.builder()
      .title("Menu zarządzania gildią")
      .size(InventorySize.BIGGEST.rows(), 9)
      .build();

  @Override
  public void init(Player player, InventoryContents contents) {
    Guild guild = SakuraGuildsPlugin.get().guildManager().playerGuild(player.getName());
    if (guild == null) {
      player.closeInventory();
      return;
    }

    User user = SakuraGuildsPlugin.get().userManager().user(player);
    contents.set(0, 4, ClickableItem.empty(ItemBuilder.skullOf(player.getName())
        .name("&6Profil")
        .lore("")
        .lore("&eNick:")
        .lore("&f" + player.getName())
        .lore("")
        .lore("&ePozycja:")
        .lore("&f" + guild.member(player).rank().title())
        .lore("")
        .lore("&eWojna:")
        .lore(" &7→ Puchary: &6" + user.trophies() + "★")
        .lore(" &7→ Zabójstwa: &f" + user.kills())
        .lore(" &7→ Śmierci: &f" + user.deaths())
        .lore("")
        .build()));

    contents.set(2, 2, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19")
        .name("&6Zajęte Ziemie")
        .lore("")
        .lore("&7Kliknij, aby przeglądnąć zajęte tereny.")
        .build(), event -> ClaimGui.INVENTORY.open(player)));

    contents.set(2, 3, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzgyZWU4NWZlNmRjNjMyN2RhZDIwMmZjYTkzYzlhOTRhYzk5YjdiMTY5NzUyNGJmZTk0MTc1ZDg4NzI1In19fQ==")
        .name("&6Członkowie")
        .lore("")
        .lore("&7Kliknij, aby zarządzać członkami gildii")
        .amount(guild.members().size())
        .build(), event -> MembersGui.INVENTORY.open(player)));

    contents.set(2, 4, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNkMDJjZGMwNzViYjFjYzVmNmZlM2M3NzExYWU0OTc3ZTM4YjkxMGQ1MGVkNjAyM2RmNzM5MTNlNWU3ZmNmZiJ9fX0=")
        .name("&6Baza Gildii")
        .lore("")
        .lore("&7Kliknij aby przeteleportować się do bazy")
        .build(), event -> {
          player.closeInventory();
          player.performCommand("g home");
        }));

    contents.set(2, 5, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg0MzI2MDdlODZiMDJlYTNjYWFmOTM1NzA2NmNhM2Y1ZTRlZGVlZjdiZTllZGNiYjVlNDU3MTFjNjU1NyJ9fX0=")
        .name("&6Zawarte Sojusze")
        .lore("")
        .lore("&7Kliknij, aby przeglądać zawarte sojusze.")
        .build(), event -> AlliesGui.INVENTORY.open(player)));

    contents.set(2, 6, ClickableItem.empty(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzhjZWI4NjMxYWRkN2NiYjU2NWRjYjcwNWYxMjEyMzQ5Y2NjZDc1NTk2NWM0NmE5MjI4NTJjOWZkOTQ4YjRiYiJ9fX0=")
        .name("&6Tarcza Wojenna")
        .lore("")
        .lore("&7Tarcza wojenna chroni twoją gildię")
        .lore("&7oraz jej nexus, przed atakami wrogów.")
        .lore("")
        .lore("&7Status tarczy: &c" + TimeUtils.futureMillisToTime(guild.shieldDuration()))
        .build()));

    contents.set(3, 2, ClickableItem.empty(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRkZTRkOTViYTRhNDZkYjdlNTY2YjM0NWY3ODk0ZDFkMjU4Zjg5M2ViOTJjNzgwYjNkYTc3NWVlZGY5MSJ9fX0=")
        .name("&6Statystyki Wojny")
        .lore("")
        .lore("&eRanking:")
        .lore(" &7→ Dywizja: " + guild.division().getName())
        .lore(" &7→ Puchary: &f" + guild.trophies() + "★")
        .lore("")
        .build()));

    contents.set(3, 3, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGJlNjIyNmQ2MWVjY2NkYjU3MzJiNmY3MTU2MGQ2NDAxZDJjYTBlZmY4ZTFhYWZiYmUzY2I3M2ZiNWE5ZiJ9fX0=")
        .name("&6Ranking Gildii")
        .lore("")
        .lore("&7Kliknij, aby przeglądać topowe gildie.")
        .build(), event -> player.performCommand("g top")));

    contents.set(3, 4, ClickableItem.empty(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZhNzY2MzFjMjNlZTJhNzAwZjMzZGNmYTY1Yzc0ZGQzMGQwZjE1N2YzNTE1ZGIyMjU3Y2FkNjhlMmRjN2MzYSJ9fX0=")
        .name("&c❤ Zdrowie Nexusa: &f" + guild.health() + "/3 HP")
        .lore("")
        .lore("&7Nexus to serce twojej gildii.")
        .lore("")
        .lore("&7Jeśli &fHP Nexusa &7spadnie &c&ndo zera&7,")
        .lore("&7a twoja gildia nie posiada &6Tarczy Wojennej&7,")
        .lore("&7zostanie ona całkowicie &4rozwiązana.")
        .lore("")
        .lore(guild.isShieldActive() ? "&a(Tarcza wojenna jest aktywna)" : "&c(Tarcza wojenna jest nieaktywna)")
        .build()));

    contents.set(3, 5, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQzMTZhNjQwOTlkODk2ZjY2OWQwZjA4ODUyMDIxN2E4M2RlY2Q0YTNiNjdlNTdhZjg5YjMzZDIwYzMyMWYzNCJ9fX0=")
        .name("&6Ulepszenia Gildii")
        .lore("")
        .lore("&7Kliknij, aby zakupić ulepszenia.")
        .build(), event -> UpgradesGui.INVENTORY.open(player)));

    contents.set(3, 6, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGMxYmQwYWNmMmY5NDA2NGNjYTI3YjBhZWYzNWQ4YzEyYTg5MjhkNDM2MzdhN2ZlNzFmNWVlZTQ1ODk3NWUxYSJ9fX0=")
        .name("&6Magazyn Gildyjny")
        .lore("")
        .lore("&7Kliknij, aby otworzyć magazyn.")
        .amount(guild.maxStorage())
        .build(), event -> player.openInventory(guild.storage())));

    contents.set(6, 3, ClickableItem.empty(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmUzZjUwYmE2MmNiZGEzZWNmNTQ3OWI2MmZlZGViZDYxZDc2NTg5NzcxY2MxOTI4NmJmMjc0NWNkNzFlNDdjNiJ9fX0=")
        .name("&6Informacje o Gildii")
        .lore("")
        .lore("&eNazwa:")
        .lore("&f" + guild.name())
        .lore("")
        .lore("&eData założenia:")
        .lore("&f" + guild.creationDate())
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
        .build()));

    contents.set(6, 5, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWNmMmFiZDYxNzlmNDE2YzI2YjRkMTI5NzE4ODFmMTI0Mzc2NDM5OWE2N2Y5Yzk0ZmQ4NTlmMTNlNzdjIn19fQ==")
        .name("&6Lista Komend")
        .lore("")
        .lore("&7Kliknij, aby zobaczyć dostępne komendy.")
        .build(), event -> {
          player.closeInventory();
          player.performCommand("g help");
        }));
  }

  @Override
  public void update(Player player, InventoryContents contents) {}
}
