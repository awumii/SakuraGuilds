package me.xneox.guilds.listener;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Iterator;

public class GuildProtectionListener implements Listener {
    private final NeonGuilds plugin;

    public GuildProtectionListener(NeonGuilds plugin) {
        this.plugin = plugin;
    }

    /**
     * @param player The player who invoked the event.
     * @param location Location where the event happened.
     * @return whenever the event should be cancelled.
     */
    private boolean isProtected(Player player, Location location) {
        Guild guild = this.plugin.guildManager().findAt(location);
        if (guild == null) {
            return false;
        }

        if (guild.isMember(player.getName())) {
            return false;
        }

        ChatUtils.sendTitle(player, "", "&cNie możesz tego zrobić na tym terenie.");
        return true;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        event.getPlayer().getInventory().getItemInMainHand();
        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.SPLASH_POTION) {
            if (this.isProtected(event.getPlayer(), event.getPlayer().getLocation())) {
                event.setCancelled(true);
            }
        }

        if (event.getAction() != Action.PHYSICAL && event.getClickedBlock() != null) {
            Material mat = event.getClickedBlock().getType();
            if (mat.name().contains("DOOR") || mat.name().contains("BUTTON") || mat.name().contains("CHEST")
                    || mat.name().contains("FENCE") || mat.name().contains("BARREL") || mat.name().contains("SHULKER") || mat == Material.LEVER) {
                if (this.isProtected(event.getPlayer(), event.getClickedBlock().getLocation())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        if (this.isProtected(event.getPlayer(), event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        if (this.isProtected(event.getPlayer(), event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
        if (this.plugin.guildManager().findAt(event.getBlock().getLocation()) != null) {
            return;
        }

        if (this.plugin.guildManager().findAt(event.getToBlock().getLocation()) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        for (Block block : event.getBlocks()) {
            if (this.plugin.guildManager().findAt(block.getLocation()) != null) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPistonRetread(BlockPistonRetractEvent event) {
        for (Block block : event.getBlocks()) {
            if (this.plugin.guildManager().findAt(block.getLocation()) != null) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (this.isProtected(event.getPlayer(), event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (this.isProtected(event.getPlayer(), event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        Iterator<Block> affectedBlocks = event.blockList().iterator();

        while (affectedBlocks.hasNext()) {
            Block block = affectedBlocks.next();
            Guild guild = this.plugin.guildManager().findAt(block.getLocation());

            if (guild != null && guild.isShieldActive()) {
                affectedBlocks.remove();
            }
        }
    }
}
