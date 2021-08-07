package me.xneox.guilds.gui;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.User;
import me.xneox.guilds.enums.Race;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.InventoryUtils;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.gui.InventoryProviderImpl;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.api.InventorySize;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.TimeUnit;

public class RacesGui extends InventoryProviderImpl {
    public RacesGui(SakuraGuildsPlugin plugin) {
        super(plugin, "Menu wyboru rasy", InventorySize.BIG);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        for (int i = 9; i < 18; i++) {
            inventory.setItem(i, InventoryUtils.BLACK_GLASS);
        }

        User user = this.plugin.userManager().getUser(player);
        ItemStack info = ItemBuilder.of(Material.OAK_SIGN)
                .name("&aInformacje:")
                .lore("&7Wybrana rasa: " + user.race().title())
                .lore("")
                .lore("&7Każda rasa zwiększa jedną &fstatystykę postaci.")
                .lore("&7Nie dają one żadnych negatywnych efektów.")
                .build();

        ItemStack cooldownInactive = ItemBuilder.of(Material.CLOCK)
                .name("&aMożesz zmienić swoją rasę.")
                .lore("&7Po wybraniu rasy, nie będziesz mógł")
                .lore("&7jej zmienić przez następne &f24h")
                .build();

        ItemStack cooldownActive = ItemBuilder.of(Material.CLOCK)
                .name("&cNie możesz zmienić swojej rasy.")
                .lore("&7Poczekaj jeszcze: &c" + this.plugin.cooldownManager().getRemaining(player, "race_change"))
                .build();

        inventory.setItem(3, info);
        inventory.setItem(5, this.plugin.cooldownManager().hasCooldown(player, "race_change") ? cooldownActive : cooldownInactive);

        inventory.setItem(19, build(Race.HUMAN, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNjMzdiZmM0ZDFhYzJmZmU1M2ZkNGFkMjRhYTI3OWQ2NzE2NDJhNWEyYWEzMWYyZWRhMDFhMThhOTZjIn19fQ=="));
        inventory.setItem(29, build(Race.ELF, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzVhZjI3NTAxZmE4MTFlMjM4NTNkOTBhYzQzODNmODc2NTU0MjJmZTVhZTg0MjRlMmNjNDkzNTA3MzJkZmMifX19"));
        inventory.setItem(21, build(Race.DWARF, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA0ODFlNTJkYWYwYjUzNjM0NjljMGYyY2M5YzhiYTIyYzZiYjgyNWUxMjYxNzhlOTkyNGI0Mjg0OTk3NTYifX19"));
        inventory.setItem(31, build(Race.GOBLIN, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGJmNTZjNjU0MWMxMjY2ZmZjNDYzNTEwYmRiNTVhZWY5MzE1YWY1NDg4OThjZjVkM2NiYTFiNWI0YzAxIn19fQ=="));
        inventory.setItem(23, build(Race.ORC, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmI5MDI2M2Q2ODc4YTgyNTVkZTlhZThjNWI3NTU5YmJkMTU4NjBjZGE1MzliZWM1ZTM4Y2UzNTdhYzBlZGU3OCJ9fX0="));
        inventory.setItem(33, build(Race.MORG, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWI3M2NkNDEzZDgxZThjM2NlZTQ2ZmU4YTgzMjI1MjY1MmRjMzM1ODRkZGU0ZGRkZjNjYTgzNmRjZDE3NGUifX19"));
        inventory.setItem(25, build(Race.NONE, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzM4YWIxNDU3NDdiNGJkMDljZTAzNTQzNTQ5NDhjZTY5ZmY2ZjQxZDllMDk4YzY4NDhiODBlMTg3ZTkxOSJ9fX0="));
    }

    @Override
    public void event(ClickEvent event, Player player) {
        User user = this.plugin.userManager().getUser(player);
        Race race = switch (event.slot()) {
            case 19 -> Race.HUMAN;
            case 29 -> Race.ELF;
            case 21 -> Race.DWARF;
            case 31 -> Race.GOBLIN;
            case 23 -> Race.ORC;
            case 33 -> Race.MORG;
            case 25 -> Race.NONE;
            default -> null;
        };
        
        if (race == null) {
            return;
        }

        player.closeInventory();
        if (this.plugin.cooldownManager().hasCooldown(player, "race_change")) {
            ChatUtils.sendMessage(player, "&cRasę możesz zmienić dopiero za: &6" + this.plugin.cooldownManager().getRemaining(player, "race_change"));
            VisualUtils.sound(player, Sound.ENTITY_VILLAGER_NO);
            return;
        }

        if (race != Race.NONE) {
            this.plugin.cooldownManager().add(player, "race_change", 1, TimeUnit.SECONDS);
        }

        VisualUtils.sound(player, Sound.ENTITY_WITHER_DEATH);
        ChatUtils.sendMessage(player, "&7Pomyślnie ustawiono twoją rasę na " + race.title());
        ChatUtils.sendTitle(player, race.title(), "&7Wybór twojej rasy został zaakceptowany...");

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sk modifier remove " + player.getName() + " race");
        user.race(race);

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                ChatUtils.format("sk modifier add {0} {1} race {2}", player.getName(), race.stat().name(), race.multiplier()));
    }

    private ItemStack build(Race race, String skullTexture) {
        return ItemBuilder.skull(skullTexture)
                .name(race.title())
                .lore(race.description())
                .build();
    }
}
