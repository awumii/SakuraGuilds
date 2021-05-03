package me.xneox.guilds.command.impl.sub;

import me.xneox.guilds.command.SubCommand;
import me.xneox.guilds.element.Building;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.type.BuildingType;
import me.xneox.guilds.type.Permission;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ChunkUtils;
import me.xneox.guilds.util.InventoryUtils;
import me.xneox.guilds.util.ServiceUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.TimeUnit;

public class BuildCommand implements SubCommand {
    @Override
    public void handle(GuildManager manager, Player player, String[] args) {
        Guild guild = manager.getGuild(player.getName());
        if (guild == null) {
            ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
            return;
        }

        if (!guild.getPlayerRank(player).hasPermission(Permission.BUILD)) {
            ChatUtils.sendMessage(player, "&cTwoja pozycja jest zbyt niska.");
            return;
        }

        if (args.length < 2) {
            ChatUtils.sendMessage(player, "&cMusisz podać rodzaj budynku do budowy/ulepszenia.");
            return;
        }

        BuildingType buildingType = BuildingType.valueOf(args[1]);

        // Budynek został wybudowany, tutaj jest on ulepszany
        Building building = guild.getBuildingOfType(buildingType);
        if (building != null) {
            if (building.getState() == Building.State.INBUILT) {
                ChatUtils.sendMessage(player, "&cTen budynek jest w trakcie budowy!");
                return;
            }

            if (building.getLevel() == 4) {
                ChatUtils.sendMessage(player, "&cTen budynek jest na maksymalnym poziomie!");
                return;
            }

            if (resourceCheck(guild, player, buildingType, building.getLevel() + 1)) {
                return;
            }

            takeResources(guild, buildingType, building.getLevel() + 1);

            building.setState(Building.State.INBUILT);
            building.setCompleteTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(buildingType.getProcessTimeForLevel(building.getLevel())));
            ChatUtils.guildAlert(guild, guild.getDisplayName(player) + " &7rozpoczął ulepszanie budowli &6" + building.getType().getName()
                    + " &7na poziom &e" + (building.getLevel() + 1));
            return;
        }

        // Budynek nie został jeszcze wybudowany.
        if (!guild.isClaimed(player.getChunk())) {
            ChatUtils.sendMessage(player, "&cBudynki można stawiać tylko na zajętym terenie.");
            return;
        }

        if (resourceCheck(guild, player, buildingType, 1)) {
            return;
        }

        if (guild.getBuildings().stream().anyMatch(b -> b.getLocation().getChunk().equals(player.getChunk()))) {
            ChatUtils.sendMessage(player, "&cW tym miejscu już znajduje się budynek!");
            return;
        }

        takeResources(guild, buildingType, 1);
        building = new Building(ChunkUtils.getCenter(player.getChunk()), buildingType, Building.State.INBUILT, 0);
        building.setCompleteTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(buildingType.getTime()));

        guild.getBuildings().add(building);
        ChatUtils.guildAlert(guild, guild.getDisplayName(player) + " &7rozpoczął budowę: &6" + building.getType().getName());
    }

    private boolean resourceCheck(Guild guild, Player player, BuildingType type, int level) {
        for (Pair<Material, Integer> pair : ServiceUtils.INSTANCE.getConfigManager().getMaterialsFor(type, level)) {
            ItemStack stack = new ItemStack(pair.getLeft());
            if (!guild.getStorage().containsAtLeast(stack, pair.getRight())) {
                ChatUtils.sendMessage(player, "&7W magazynie znajduje się zbyt mało: &6" + stack.getI18NDisplayName());
                return true;
            }
        }
        return false;
    }

    private void takeResources(Guild guild, BuildingType type, int level) {
        for (Pair<Material, Integer> pair : ServiceUtils.INSTANCE.getConfigManager().getMaterialsFor(type, level)) {
            InventoryUtils.removeItems(guild.getStorage(), pair.getLeft(), pair.getRight());
        }
    }
}
