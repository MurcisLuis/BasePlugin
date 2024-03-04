package com.gmail.murcisluis.base.spigot.api.emotes;

import com.gmail.murcisluis.base.spigot.api.emotes.data.ItemEmote;
import com.gmail.murcisluis.base.spigot.api.emotes.data.PlayerData;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class EmotesPlaceholder extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "lujoemotes";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Murcis_Luis";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        // Manejar el placeholder 'emotes_player_emote_kill'
        if (params.equals("player_emote_kill")) {
            PlayerData playerData = getPlayerData(player);
            return playerData != null ? playerData.getEmoteKill() : "";
        }

        // Manejar el placeholder 'emotes_player_emote_kill_name'
        if (params.equals("player_emote_kill_name")) {
            PlayerData playerData = getPlayerData(player);
            return playerData != null ? getItemEmoteName(playerData.getEmoteKill()) : "";
        }

        // Manejar el placeholder 'emotes_player_emote_kill_lore'
        if (params.equals("player_emote_kill_lore")) {
            PlayerData playerData = getPlayerData(player);
            return playerData != null ? getItemEmoteLore(playerData.getEmoteKill()) : "";
        }

        // Manejar el placeholder 'emotes_player_emote_kill_material'
        if (params.equals("player_emote_kill_material")) {
            PlayerData playerData = getPlayerData(player);
            return playerData != null ? getItemEmoteMaterial(playerData.getEmoteKill()) : "";
        }
        // Manejar el placeholder 'emotes_player_emote_kill_material'
        if (params.equals("player_emote_kill_custom_model_data")) {
            PlayerData playerData = getPlayerData(player);
            return playerData != null ? String.valueOf(getItemEmoteCustomModelData(playerData.getEmoteKill())) : "";
        }

        // Manejar el placeholder 'player_emote_death'
        if (params.equals("player_emote_death")) {
            PlayerData playerData = getPlayerData(player);
            return playerData != null ? playerData.getEmoteDeath() : "";
        }

        // Manejar el placeholder 'emotes_player_emote_death_name'
        if (params.equals("player_emote_death_name")) {
            PlayerData playerData = getPlayerData(player);
            return playerData != null ? getItemEmoteName(playerData.getEmoteDeath()) : "";
        }

        // Manejar el placeholder 'emotes_player_emote_death_lore'
        if (params.equals("player_emote_death_lore")) {
            PlayerData playerData = getPlayerData(player);
            return playerData != null ? getItemEmoteLore(playerData.getEmoteDeath()) : "";
        }

        // Manejar el placeholder 'emotes_player_emote_death_material'
        if (params.equals("player_emote_death_material")) {
            PlayerData playerData = getPlayerData(player);
            return playerData != null ? getItemEmoteMaterial(playerData.getEmoteDeath()) : "";
        }

        if (params.equals("player_emote_death_custom_model_data")) {
            PlayerData playerData = getPlayerData(player);
            return playerData != null ? String.valueOf(getItemEmoteCustomModelData(playerData.getEmoteDeath())) : "";
        }

        // Agregar más placeholders según sea necesario para el death

        return null; // Placeholder no reconocido
    }

    private PlayerData getPlayerData(OfflinePlayer player) {
        return Data.PLAYER_DATA.getValue().stream()
                .filter(playerData -> playerData.getPlayer().equals(player))
                .findFirst()
                .orElse(null);
    }

    private String getItemEmoteName(String mobName) {
        ItemEmote itemEmote = getItemEmoteFromMobName(mobName);
        return itemEmote != null ? itemEmote.getName() : "";
    }

    private String getItemEmoteLore(String mobName) {
        ItemEmote itemEmote = getItemEmoteFromMobName(mobName);
        return itemEmote != null ? String.join("\n", itemEmote.getLore()) : "";
    }

    private String getItemEmoteMaterial(String mobName) {
        ItemEmote itemEmote = getItemEmoteFromMobName(mobName);
        return itemEmote != null ? itemEmote.getMaterial() : "";
    }

    private int getItemEmoteCustomModelData(String mobName){
        ItemEmote itemEmote = getItemEmoteFromMobName(mobName);
        return itemEmote != null ? itemEmote.getCustomModelData() : 0;
    }

    // Agregar más métodos para otros atributos según sea necesario

    private ItemEmote getItemEmoteFromMobName(String mobName) {
        return Data.ITEM_DATA.getValue().stream()
                .filter(itemEmote -> itemEmote.getMobName().equals(mobName))
                .findFirst()
                .orElse(null);
    }
}
