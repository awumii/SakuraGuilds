package me.xneox.guilds.gui;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Building;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.type.BuildingType;
import me.xneox.guilds.util.InventoryUtils;
import me.xneox.guilds.util.ItemBuilder;
import me.xneox.guilds.util.TimeUtils;
import me.xneox.guilds.util.VisualUtils;
import me.xneox.guilds.util.gui.api.ClickEvent;
import me.xneox.guilds.util.gui.InventoryProviderImpl;
import me.xneox.guilds.util.gui.api.InventorySize;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BuildingsGui extends InventoryProviderImpl {
    public BuildingsGui(NeonGuilds plugin) {
        super(plugin, "Menu ulepszeń gildyjnych", InventorySize.MEDIUM);
    }

    @Override
    public void open(Player player, Inventory inventory) {
        InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE);
        Guild guild = this.plugin.getGuildManager().getGuild(player.getName());

        ItemBuilder townhall = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Ratusz")
                .lore("&7&oZwiększa ilość slotów o +6.")
                .lore("")
                .lore("&ePoziom: &f" + getLevel(guild, BuildingType.TOWNHALL))
                .lore("&eDługość budowy: &f" + getTime(guild, BuildingType.TOWNHALL))
                .lore("")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQ0YzFlOGU4MjY3MmJiYTU4OTJmZDQ2NTlmOGRhZDg0ZDE1NDVkYjI2ZGI1MmVjYzkxOGYzMmExMzkxNTEzIn19fQ==");

                printMaterials(townhall, BuildingType.TOWNHALL, guild);

        ItemBuilder barrack = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Koszary")
                .lore("&7&oZwieksza ilość max. terenu o +6.")
                .lore("")
                .lore("&ePoziom: &f" + getLevel(guild, BuildingType.BARRACK))
                .lore("&eDługość budowy: &f" + getTime(guild, BuildingType.BARRACK))
                .lore("")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzgyZWU4NWZlNmRjNjMyN2RhZDIwMmZjYTkzYzlhOTRhYzk5YjdiMTY5NzUyNGJmZTk0MTc1ZDg4NzI1In19fQ==");

                printMaterials(barrack, BuildingType.BARRACK, guild);

        ItemBuilder storage = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Magazyn")
                .lore("&7&oZwiększa ilość slotów magazynu o +9.")
                .lore("")
                .lore("&ePoziom: &f" + getLevel(guild, BuildingType.STORAGE))
                .lore("&eDługość budowy: &f" + getTime(guild, BuildingType.STORAGE))
                .lore("")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2UyMjM5MWUzNWEzZTViY2VlODlkYjMxMmU4NzRmZGM5ZDllN2E2MzUxMzE0YjgyYmRhOTdmYmQyYmU4N2ViOCJ9fX0=");

                printMaterials(storage, BuildingType.STORAGE, guild);

        ItemBuilder alchemy = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&6Labolatorium")
                .lore("&7&oPolepsza dostępne bonusy dla gildii.")
                .lore("&c&nW chwili obecnej nie robi nic.")
                .lore("")
                .lore("&ePoziom: &f" + getLevel(guild, BuildingType.ALCHEMY))
                .lore("&eDługość budowy: &f" + getTime(guild, BuildingType.ALCHEMY))
                .lore("")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmEwODU5OTdjYThkZWJlZTU5ZmVjMTE4MjM2MjRhNDA4NWYyZWQzZGE1NzIwMTViNDljNzVhOWYyOGRmYiJ9fX0=");

                printMaterials(alchemy, BuildingType.ALCHEMY, guild);

        ItemStack close = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&cPowrót")
                .lore("&7Cofnij do menu gildii.")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
                .build();

        inventory.setItem(10, townhall.build());
        inventory.setItem(12, barrack.build());
        inventory.setItem(14, storage.build());
        inventory.setItem(16, alchemy.build());

        inventory.setItem(8, close);
    }

    @Override
    public void event(ClickEvent event, Player player) {
        VisualUtils.click(player);

        ItemStack item = event.item();
        if (item.getType() == Material.PLAYER_HEAD) {
            if (item.getItemMeta().getDisplayName().contains("Powrót")) {
                this.plugin.getInventoryManager().open("management", player);
                return;
            }

            player.closeInventory();
            switch (event.slot()) {
                case 10:
                    player.performCommand("g build TOWNHALL");
                    break;
                case 12:
                    player.performCommand("g build BARRACK");
                    break;
                case 14:
                    player.performCommand("g build STORAGE");
                    break;
                case 16:
                    player.performCommand("g build ALCHEMY");
                    break;
            }
        }
    }

    private void printMaterials(ItemBuilder builder, BuildingType type, Guild guild) {
        Building building = guild.getBuildingOfType(type);
        if (building != null && building.getLevel() >= type.getMaxLevel()) {
            builder.lore("&cOsiągnięto maksymalny poziom.");
            return;
        }

        builder.lore("&eKoszt ulepszenia: &f");

        List<Pair<Material, Integer>> materials = this.plugin.getConfigManager().getMaterialsFor(type, building != null ? building.getLevel() + 1 : 1);
        for (Pair<Material, Integer> pair : materials) {
            builder.lore(" &8▸ &f" + new ItemStack(pair.getLeft()).getI18NDisplayName() + " &7x" + pair.getRight());
        }

        builder.lore("");
        builder.lore("&7Kliknij, aby budować.");
    }

    private String getLevel(Guild guild, BuildingType type) {
        Building building = guild.getBuildingOfType(type);
        return building != null ? String.valueOf(building.getLevel()) : "Nie Wybudowano";
    }

    private String getTime(Guild guild, BuildingType type) {
        Building building = guild.getBuildingOfType(type);
        if (building != null) {
            return TimeUtils.secondsToTime((int) TimeUnit.MINUTES.toSeconds(type.getProcessTimeForLevel(building.getLevel())));
        }
        return TimeUtils.secondsToTime((int) TimeUnit.MINUTES.toSeconds(type.getTime()));
    }
}
