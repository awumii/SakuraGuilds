package me.xneox.guilds.gui;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.element.Member;
import me.xneox.guilds.enums.Permission;
import me.xneox.guilds.enums.Rank;
import me.xneox.guilds.util.text.ChatUtils;
import me.xneox.guilds.util.inventory.InventoryUtils;
import me.xneox.guilds.util.inventory.ItemBuilder;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.inventory.InventorySize;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RankEditorGui implements InventoryProvider {
  public static final SmartInventory INVENTORY = SmartInventory.builder()
      .title("Zarządzanie uprawnieniami użytkownika")
      .size(InventorySize.BIG.rows(), 9)
      .build();

  @Override
  public void init(Player player, InventoryContents contents) {
    contents.fillRect(1, 0, 1, 8, InventoryUtils.GLASS);
    InventoryUtils.insertBackButton(0, 8, contents, MembersGui.INVENTORY);

    Guild guild = SakuraGuildsPlugin.get().guildManager().playerGuild(player.getName());
    String target = SakuraGuildsPlugin.get().userManager().user(player).editorSubject();
    Member member = guild.member(target);

    contents.set(0, 0, ClickableItem.empty(ItemBuilder.skullOf(target)
        .name("&6" + target)
        .lore("&7Ranga: " + member.rank().title())
        .build()));

    // Insert premade ranks
    contents.set(0, 2, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQxNWFhY2I3MjEzYzgzMTFlZWQ3YmFmMzdlYmI1OGE1ZjRiOTI1NjMxN2Q4NDU4ZDE1ZDMzN2E3NGU0YmU2In19fQ==")
        .name(Rank.LEADER.title())
        .lore("")
        .lore("&cUWAGA: Po nadaniu tej rangi,")
        .lore("&cutracisz status lidera gildii!")
        .lore("")
        .lore("&eKliknij aby nadać.")
        .build(), event -> changeRank(guild, player, member, Rank.LEADER)));

    contents.set(0, 3, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZlZjMwNzBmNmQyZTQ3YjViMTA1OWI0NWRiYzMyMmIyNDI1ZWEzZGUxZjFiY2I5NDI5MzM3ZmQ2OTNmYzYifX19")
        .name(Rank.GENERAL.title())
        .lore("")
        .lore("&eDomyślne Uprawnienia:")
        .lore(" &8▸ &7Budowanie")
        .lore(" &8▸ &7Wyrzucanie członków")
        .lore(" &8▸ &7Zmiana bazy gildii")
        .lore(" &8▸ &7Zajmowanie terenu")
        .lore(" &8▸ &7Zarządzanie rangami")
        .lore(" &8▸ &7Kupowanie ulepszeń")
        .lore(" &8▸ &7Zarządzanie sojuszami")
        .lore("")
        .lore("&eKliknij aby nadać.")
        .build(), event -> changeRank(guild, player, member, Rank.GENERAL)));

    contents.set(0, 4, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmI0MTZiM2Q5NzNlMzQ4MDY2NDE2MmM5MjhjMGY3ZDJmNGYzMmI4ZDIwMjIyNWFiNjIyN2ZmNTllZTFjNWMzMSJ9fX0=")
        .name(Rank.OFFICER.title())
        .lore("")
        .lore("&eDomyślne Uprawnienia:")
        .lore(" &8▸ &7Budowanie")
        .lore(" &8▸ &7Zmiana bazy gildii")
        .lore(" &8▸ &7Zajmowanie terenu")
        .lore(" &8▸ &7Kupowanie ulepszeń")
        .lore(" &8▸ &7Zarządzanie sojuszami")
        .lore("")
        .lore("&eKliknij aby nadać.")
        .build(), event -> changeRank(guild, player, member, Rank.OFFICER)));

    contents.set(0, 5, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY3ZWRhNjhhZTJmZmY1MjRlODNmOGE5MTZlYzMzOTBhZGVjYTM5NGY3ZDg2MTdhNGQ3N2ZiYTNlNjg5Yjc1In19fQ==")
        .name(Rank.CORPORAL.title())
        .lore("")
        .lore("&eDomyślne Uprawnienia:")
        .lore(" &8▸ &7Budowanie")
        .lore(" &8▸ &7Zmiana bazy gildii")
        .lore(" &8▸ &7Zajmowanie terenu")
        .lore("")
        .lore("&eKliknij aby nadać.")
        .build(), event -> changeRank(guild, player, member, Rank.CORPORAL)));

    contents.set(0, 6, ClickableItem.of(ItemBuilder.skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZThiOGM2YTQ2ZDg3Y2Y4NmE1NWRmMjE0Y2Y4NGJmNDVjY2EyNWVkYjlhNjc2ZTk2MzY0ZGQ2YTZlZWEyMzViMyJ9fX0=")
        .name(Rank.NEWBIE.title())
        .lore("")
        .lore("&eDomyślne Uprawnienia:")
        .lore(" &8▸ &7Budowanie")
        .lore("")
        .lore("&eKliknij aby nadać.")
        .build(), event -> changeRank(guild, player, member, Rank.NEWBIE)));

    // Insert all avaible permissions
    for (Permission permission : Permission.values()) {
      ItemStack stack = ItemBuilder.skull(member.hasPermission(permission)
              ? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDMxMmNhNDYzMmRlZjVmZmFmMmViMGQ5ZDdjYzdiNTVhNTBjNGUzOTIwZDkwMzcyYWFiMTQwNzgxZjVkZmJjNCJ9fX0="
              : "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmViNTg4YjIxYTZmOThhZDFmZjRlMDg1YzU1MmRjYjA1MGVmYzljYWI0MjdmNDYwNDhmMThmYzgwMzQ3NWY3In19fQ==")
          .name((member.hasPermission(permission) ? "&a" : "&c") + permission.getDescription())
          .build();

      contents.add(ClickableItem.of(stack, event -> {
        if (canAccess(guild, player, member)) {
          if (member.hasPermission(permission)) {
            member.permissions().remove(permission);
          } else {
            member.permissions().add(permission);
          }

          // refresh inventory
          RankEditorGui.INVENTORY.open(player);
        }
      }));
    }

    // Restore empty spaces between ranks.
    contents.set(0, 1, ClickableItem.empty(new ItemStack(Material.AIR)));
    contents.set(0, 7, ClickableItem.empty(new ItemStack(Material.AIR)));
  }

  @Override
  public void update(Player player, InventoryContents contents) {}

  private void changeRank(@NotNull Guild guild, @NotNull Player player, @NotNull Member target, Rank rank) {
    if (canAccess(guild, player, target)) {
      guild.changeRank(target, rank);
      ChatUtils.guildAlert(guild, "&6" + player.getName() + " &7zmienił pozycję gracza &6" + target.rank() + " &7na " + rank.title());
    }
  }

  private boolean canAccess(@NotNull Guild guild, @NotNull Player player, @NotNull Member target) {
    Member member = guild.member(player);

    if (player.getName().equals(target.nickname())) {
      VisualUtils.sound(player, Sound.ENTITY_VILLAGER_NO);
      ChatUtils.sendMessage(player, "&cNie możesz zarządzać własnymi uprawnieniami!");
      return false;
    }

    // Block editing if lower rank or no permission.
    if (!member.hasPermission(Permission.RANKS)) {
      VisualUtils.sound(player, Sound.ENTITY_VILLAGER_NO);
      ChatUtils.sendMessage(player, "&cNie posiadasz uprawnień do zarządzania uprawnieniami.");
      return false;
    }

    if (!member.rank().isHigher(target.rank())) {
      VisualUtils.sound(player, Sound.ENTITY_VILLAGER_NO);
      ChatUtils.sendMessage(player, "&cTwoja ranga w gildii jest niższa od docelowego gracza.");
      return false;
    }
    return true;
  }
}