package com.gmail.murcisluis.base.spigot.api.emotes.data;

import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@SerializableAs("PlayerData")
public class PlayerData implements ConfigurationSerializable {

    private final OfflinePlayer player;
    private String emoteKill;
    private String emoteDeath;
    public PlayerData(Map<String, Object> map) {
        this.player = Bukkit.getOfflinePlayer(UUID.fromString((String) map.get("player")));
        this.emoteKill = getStringOrEmpty((String) map.get("emoteKill"));
        this.emoteDeath = getStringOrEmpty((String) map.get("emoteDeath"));
    }

    private String getStringOrEmpty(String input) {
        return input != null && !input.isEmpty() ? input : null;
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        return ImmutableMap.of(
                "player", player.getUniqueId().toString(),
                "kill", emoteKill != null ? emoteKill : "",
                "death", emoteDeath != null ? emoteDeath : ""
        );
    }
}
