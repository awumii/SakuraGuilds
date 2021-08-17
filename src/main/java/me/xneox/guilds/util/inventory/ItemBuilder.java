package me.xneox.guilds.util.inventory;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import me.xneox.guilds.util.text.ChatUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {
  private final List<Component> lore;
  private final HashMap<Enchantment, Integer> enchants;
  private final Material material;

  private int amount;
  private Component title;
  private ItemFlag[] flags;
  private String skullTexture;
  private String skullOwner;

  private ItemBuilder(Material material) {
    this.title = null;
    this.lore = new ArrayList<>();
    this.enchants = new HashMap<>();
    this.material = material;
    this.amount = 1;
  }

  public static ItemBuilder of(Material material) {
    return new ItemBuilder(material);
  }

  public static ItemBuilder skull(String skullTexture) {
    ItemBuilder builder = new ItemBuilder(Material.PLAYER_HEAD);
    builder.skullTexture = skullTexture;
    return builder;
  }

  public static ItemBuilder skullOf(String skullOwner) {
    ItemBuilder builder = new ItemBuilder(Material.PLAYER_HEAD);
    builder.skullOwner = skullOwner;
    return builder;
  }

  public ItemBuilder name(String title) {
    this.title = ChatUtils.color(title)
      .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE); // italic is default for item stacks.
    return this;
  }

  public ItemBuilder lore(String append) {
    this.lore.add(ChatUtils.color(append)
      .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)); // italic is default for item stacks.
    return this;
  }

  public ItemBuilder lore(List<String> list) {
    list.forEach(this::lore);
    return this;
  }

  public ItemBuilder lore(String... strings) {
    this.lore(Arrays.asList(strings));
    return this;
  }

  public ItemBuilder amount(int amount) {
    this.amount = amount;
    return this;
  }

  public ItemBuilder flags(ItemFlag... flags) {
    this.flags = flags;
    return this;
  }

  public ItemBuilder enchantment(Enchantment enchant, int level) {
    this.enchants.remove(enchant);
    this.enchants.put(enchant, level);
    return this;
  }

  public ItemStack build() {
    ItemStack item = new ItemStack(this.material, this.amount);
    ItemMeta meta = item.getItemMeta();

    if (this.title != null) {
      meta.displayName(this.title);
    }

    if (!this.lore.isEmpty()) {
      meta.lore(this.lore);
    }

    if (this.flags != null) {
      meta.addItemFlags(this.flags);
    }

    if (this.skullOwner != null) {
      SkullMeta headMeta = (SkullMeta) meta;
      headMeta.setPlayerProfile(Bukkit.createProfile(this.skullOwner));
    }

    if (this.skullTexture != null) {
      SkullMeta headMeta = (SkullMeta) meta;

      PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
      profile.setProperty(new ProfileProperty("textures", this.skullTexture));
      headMeta.setPlayerProfile(profile);
    }

    item.setItemMeta(meta);
    item.addUnsafeEnchantments(this.enchants);
    return item;
  }
}