package me.xneox.guilds.util.inventory;

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
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ItemBuilder {
  private final List<Component> lore;
  private final HashMap<Enchantment, Integer> enchants;
  private final Material material;

  private int amount;
  private Component title;
  private ItemFlag[] flags;
  private String skullTexture;
  private String skullOwner;

  private ItemBuilder(@NotNull Material material) {
    this.title = null;
    this.lore = new ArrayList<>();
    this.enchants = new HashMap<>();
    this.material = material;
    this.amount = 1;
  }

  // Construct methods

  @NotNull
  public static ItemBuilder of(@NotNull Material material) {
    return new ItemBuilder(material);
  }

  @NotNull
  public static ItemBuilder skull(@NotNull String skullTexture) {
    var builder = new ItemBuilder(Material.PLAYER_HEAD);
    builder.skullTexture = skullTexture;
    return builder;
  }

  @NotNull
  public static ItemBuilder skullOf(@NotNull String skullOwner) {
    var builder = new ItemBuilder(Material.PLAYER_HEAD);
    builder.skullOwner = skullOwner;
    return builder;
  }

  // Builder methods
  // TODO fix the TextDecoration because now it completely removes all italics instead of forcing it everywhere.

  @Contract("_ -> this")
  public ItemBuilder name(@NotNull String title) {
    this.title = ChatUtils.color(title)
      .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE); // italic is default for item stacks.
    return this;
  }

  @Contract("_ -> this")
  public ItemBuilder lore(@NotNull String append) {
    this.lore.add(ChatUtils.color(append)
      .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)); // italic is default for item stacks.
    return this;
  }

  @Contract("_ -> this")
  public ItemBuilder lore(@NotNull List<String> list) {
    list.forEach(this::lore);
    return this;
  }

  @Contract("_ -> this")
  public ItemBuilder lore(@NotNull String... strings) {
    this.lore(Arrays.asList(strings));
    return this;
  }

  @Contract("_ -> this")
  public ItemBuilder amount(int amount) {
    this.amount = amount;
    return this;
  }

  @Contract("_ -> this")
  public ItemBuilder flags(@NotNull ItemFlag... flags) {
    this.flags = flags;
    return this;
  }

  @Contract("_, _ -> this")
  public ItemBuilder enchantment(@NotNull Enchantment enchant, int level) {
    this.enchants.remove(enchant);
    this.enchants.put(enchant, level);
    return this;
  }

  @NotNull
  public ItemStack build() {
    var item = new ItemStack(this.material, this.amount);
    var meta = item.getItemMeta();

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
      var skullMeta = (SkullMeta) meta;
      skullMeta.setPlayerProfile(Bukkit.createProfile(this.skullOwner));
    }

    if (this.skullTexture != null) {
      var skullMeta = (SkullMeta) meta;
      var profile = Bukkit.createProfile(UUID.randomUUID());

      profile.setProperty(new ProfileProperty("textures", this.skullTexture));
      skullMeta.setPlayerProfile(profile);
    }

    item.setItemMeta(meta);
    item.addUnsafeEnchantments(this.enchants);
    return item;
  }
}
