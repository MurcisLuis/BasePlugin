package com.gmail.murcisluis.base.spigot.api.emotes.data;

import lombok.*;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@SerializableAs("Item")
public class ItemConfig implements ConfigurationSerializable {
    protected String name;
    protected List<String> lore;
    protected String material;
    protected int customModelData;
    protected int slot;

    public ItemConfig(Map<String, Object> map) {
        this.name = (String) map.get("name");
        this.lore = (List<String>) map.get("lore");
        this.material = (String) map.get("material");
        this.customModelData = (Integer) map.get("customModelData");
        this.slot = (Integer) map.get("slot");
    }


    @NotNull
    @Override
    public Map<String, Object> serialize() {
        return Map.of(
        "name", name,
        "lore", lore,
        "material", material,
        "customModelData", customModelData,
        "slot", slot);

    }

    public ItemStack asItemStack() {
        Material mat = Material.matchMaterial(material);
        if (mat == null) {
            throw new IllegalArgumentException("Material inv?lido: " + material);
        }
        ItemStack item = new ItemStack(mat);

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(lore);
            meta.setCustomModelData(customModelData);
            item.setItemMeta(meta);
        }
        return item;
    }

    public boolean equalItemStack(ItemStack other) {
        if (other == null || !other.hasItemMeta()) {
            return false;
        }
        ItemMeta otherMeta = other.getItemMeta();
        if (otherMeta == null) {
            return false;
        }
        return Objects.equals(other.getType().name(), material)
                && Objects.equals(otherMeta.getDisplayName(), name)
                && Objects.equals(otherMeta.getLore(), lore)
                && otherMeta.getCustomModelData() == customModelData;
    }

    public boolean isSimilarItemStack(ItemStack other) {
        if (other == null || !other.hasItemMeta()) {
            return false;
        }
        ItemMeta otherMeta = other.getItemMeta();
        if (otherMeta == null) {
            return false;
        }

        // Verifica que todas las propiedades del ItemConfig est?n contenidas en el ItemStack
        boolean nameContained = name == null || otherMeta.getDisplayName().contains(name);
        boolean materialContained = material == null || other.getType().name().contains(material);
        boolean loreContained;
        if (lore == null || lore.isEmpty()) {
            loreContained = true;  // Si el lore de ItemConfig es nulo, consideramos que est? contenido
        } else {
            List<String> otherLore = otherMeta.getLore();
            loreContained = otherLore != null && (otherLore.isEmpty() || otherLore.containsAll(lore));
        }

        return nameContained && materialContained && loreContained;
    }


}
