package me.xneox.guilds.integration;

import me.xneox.guilds.util.LogUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VaultIntegration {
  // Vault economy manager.
  public static @Nullable Economy VAULT_ECONOMY;

  static {
    var economyService = Bukkit.getServicesManager().getRegistration(Economy.class);
    if (economyService != null) {
      VaultIntegration.VAULT_ECONOMY = economyService.getProvider();
    } else {
      LogUtils.LOGGER.error("Vault is not installed. Economy is not available.");
    }
  }

  // VaultAPI: taxes >:) (there are no taxes in this plugin... yet)
  public static void ecoWithdraw(@NotNull Player player, double amount) {
    if (VAULT_ECONOMY != null) {
      VAULT_ECONOMY.withdrawPlayer(player, amount);
    }
  }

  // VaultAPI: checking player's balance.
  public static boolean ecoHasAtLeast(@NotNull Player player, double amount) {
    if (VAULT_ECONOMY == null) {
      return false;
    }

    return VAULT_ECONOMY.has(player, amount);
  }
}
