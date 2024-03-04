package com.gmail.murcisluis.base.spigot.api.utils.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemStackUtils {

    public static ItemStack createItem(Material material, String displayName, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(displayName);
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    public static ItemStack addLore(ItemStack itemStack, String lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            List<String> itemLore = itemMeta.getLore();
            if (itemLore == null) {
                itemLore = new ArrayList<>();
            }
            itemLore.add(lore);
            itemMeta.setLore(itemLore);
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    public static ItemStack setDisplayName(ItemStack itemStack, String displayName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(displayName);
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    public static ItemStack setLore(ItemStack itemStack, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    public static ItemStack addEnchantment(ItemStack itemStack, Enchantment enchantment, int level, boolean hideFlags) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.addEnchant(enchantment, level, true);
            if (hideFlags) {
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    public static List<String> getLore(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta != null ? new ArrayList<>(itemMeta.getLore()) : new ArrayList<>();
    }

    public static int getCustomModelData(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta != null && itemMeta.hasCustomModelData() ? itemMeta.getCustomModelData() : 0;
    }

    public static void toggleEnchantment(ItemStack itemStack, Enchantment enchantment, int level) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            if (itemMeta.hasEnchant(enchantment)) {
                itemMeta.removeEnchant(enchantment);
            } else {
                itemMeta.addEnchant(enchantment, level, true);
            }
            itemStack.setItemMeta(itemMeta);
        }
    }
    public static String getDisplayName(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta != null ? itemMeta.getDisplayName() : null;
    }

    public static void addItemFlags(ItemStack itemStack, ItemFlag... flags) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.addItemFlags(flags);
            itemStack.setItemMeta(itemMeta);
        }
    }
}
