package com.gmail.murcisluis.base.spigot.api.emotes.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
@Getter
@Setter
@SerializableAs("ItemEmote")
public class ItemEmote extends ItemConfig{
    private final String mobName;

    public ItemEmote(String name, List<String> lore, String material, int customModelData, int slot, String mobName) {
        super(name, lore, material, customModelData, slot);
        this.mobName = mobName;
    }

    public ItemEmote(Map<String, Object> map) {
        super(map);
        mobName= (String) map.get("mobName");
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        return Map.of(
                "name", name,
                "lore", lore,
                "material", material,
                "customModelData", customModelData,
                "slot", slot,
                "mobName",mobName);

    }
}
