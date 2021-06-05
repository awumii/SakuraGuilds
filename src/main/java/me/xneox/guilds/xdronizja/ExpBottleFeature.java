package me.xneox.guilds.xdronizja;

import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ExpBottleFeature {

    public static class BottleListener implements Listener {
        @EventHandler
        public void onInteract(PlayerInteractEvent event) {
            if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
                return;
            }

            Player player = event.getPlayer();
            ItemStack stack = player.getInventory().getItemInMainHand();
            ItemMeta meta = stack.getItemMeta();

            if (meta == null || !meta.hasDisplayName() || stack.getType() != Material.EXPERIENCE_BOTTLE) {
                return;
            }

            if (meta.getDisplayName().contains("Butelkowany EXP")) {
                int exp = stack.getEnchantmentLevel(Enchantment.LUCK);
                player.setLevel(player.getLevel() + exp);
                ChatUtils.sendMessage(player, "&7Otrzymano &a" + exp + " expa &7z butelki!");

                if (stack.getAmount() == 1) {
                    player.getInventory().setItemInMainHand(null);
                } else {
                    stack.setAmount(stack.getAmount() - 1);
                }

                event.setCancelled(true);
            }
        }
    }

    public static class BottleCommand implements CommandExecutor {
        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            Player player = (Player) sender;
            if (args.length != 1) {
                ChatUtils.sendMessage(player, "&cPodaj ilość expa do butelki!");
                return true;
            }

            int level = Integer.parseInt(args[0]);
            if (player.getLevel() < level) {
                ChatUtils.sendMessage(player, "&cNie posiadasz tyle poziomów");
                return true;
            }

            ItemStack itemStack = new ItemBuilder(Material.EXPERIENCE_BOTTLE)
                    .name("&eButelkowany EXP")
                    .lore("")
                    .lore("&7Zmagazynowano: &a" + level + " poziomów")
                    .enchantment(Enchantment.LUCK, level)
                    .build();

            player.getInventory().addItem(itemStack);
            player.setLevel(player.getLevel() - level);
            ChatUtils.sendMessage(player, "&7Otrzymano butelkę z &a" + level + " poziomami exp");
            return true;
        }
    }
}
