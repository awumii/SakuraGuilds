package me.xneox.guilds.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {
    private final List<String> lore;
    private final HashMap<Enchantment, Integer> enchants;
    private final Material material;
    private final int amount;

    private String title;
    private ItemFlag[] flags;
    private String skullTexture;
    private String skullOwner;

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(Material material, int amount) {
        this.title = null;
        this.lore = new ArrayList<>();
        this.enchants = new HashMap<>();
        this.material = material;
        this.amount = amount;
    }

    public ItemBuilder name(String title) {
        this.title = ChatUtils.colored(title);
        return this;
    }

    public ItemBuilder lore(String lore) {
        this.lore.add(ChatUtils.colored(lore));
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

    public ItemBuilder skullTexture(String texture) {
        this.skullTexture = texture;
        return this;
    }

    public ItemBuilder skullOwner(String owner) {
        this.skullOwner = owner;
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(this.material, this.amount);
        ItemMeta meta = item.getItemMeta();

        if (this.title != null) {
            meta.setDisplayName(this.title);
        }

        if (!this.lore.isEmpty()) {
            meta.setLore(this.lore);
        }

        if (this.flags != null) {
            meta.addItemFlags(this.flags);
        }

        if (this.skullOwner != null) {
            SkullMeta headMeta = (SkullMeta) meta;
            headMeta.setOwningPlayer(Bukkit.getOfflinePlayer(this.skullOwner));
        }

        if (this.skullTexture != null) {
            SkullMeta headMeta = (SkullMeta) meta;
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", this.skullTexture));

            Field profileField;
            try {
                profileField = headMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(headMeta, profile);
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }

        item.setItemMeta(meta);
        item.addUnsafeEnchantments(this.enchants);
        return item;
    }
}
