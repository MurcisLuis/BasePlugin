package com.gmail.murcisluis.base.common.api.localization;

import lombok.NonNull;
import lombok.extern.java.Log;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

/**
 * Helper class for player-specific localization operations.
 * Provides convenient methods for working with player language preferences.
 * 
 * @author BasePlugin Framework
 * @since 2.1.0
 */
@Log
public class PlayerLocalizationHelper {
    
    private final LocalizationManager localizationManager;
    
    /**
     * Creates a new PlayerLocalizationHelper instance.
     */
    public PlayerLocalizationHelper() {
        this.localizationManager = LocalizationManager.getInstance();
    }
    
    /**
     * Sets up a player's language preference with validation.
     * 
     * @param playerUUID the player's UUID
     * @param languageCode the desired language code
     * @param playerName the player's name (for logging)
     * @return a CompletableFuture that completes with true if successful
     */
    public CompletableFuture<Boolean> setupPlayerLanguage(@NonNull UUID playerUUID, 
                                                          @NonNull String languageCode, 
                                                          String playerName) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                boolean success = localizationManager.setPlayerLanguage(playerUUID, languageCode);
                if (success) {
                    log.info("Language '" + languageCode + "' set for player: " + 
                            (playerName != null ? playerName : playerUUID.toString()));
                } else {
                    log.warning("Failed to set language '" + languageCode + "' for player: " + 
                               (playerName != null ? playerName : playerUUID.toString()));
                }
                return success;
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error setting up language for player: " + playerUUID, e);
                return false;
            }
        });
    }
    
    /**
     * Gets a welcome message for a player in their preferred language.
     * 
     * @param playerUUID the player's UUID
     * @param playerName the player's name
     * @return the welcome message component
     */
    public Component getWelcomeMessage(@NonNull UUID playerUUID, @NonNull String playerName) {
        return localizationManager.getPlayerComponent(playerUUID, "welcome.message", 
            net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed("player", playerName));
    }
    
    /**
     * Gets a goodbye message for a player in their preferred language.
     * 
     * @param playerUUID the player's UUID
     * @param playerName the player's name
     * @return the goodbye message component
     */
    public Component getGoodbyeMessage(@NonNull UUID playerUUID, @NonNull String playerName) {
        return localizationManager.getPlayerComponent(playerUUID, "goodbye.message", 
            net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed("player", playerName));
    }
    
    /**
     * Gets an error message for a player in their preferred language.
     * 
     * @param playerUUID the player's UUID
     * @param errorKey the error message key
     * @param resolvers optional tag resolvers
     * @return the error message component
     */
    public Component getErrorMessage(@NonNull UUID playerUUID, @NonNull String errorKey, TagResolver... resolvers) {
        return localizationManager.getPlayerComponent(playerUUID, "error." + errorKey, resolvers);
    }
    
    /**
     * Gets a success message for a player in their preferred language.
     * 
     * @param playerUUID the player's UUID
     * @param successKey the success message key
     * @param resolvers optional tag resolvers
     * @return the success message component
     */
    public Component getSuccessMessage(@NonNull UUID playerUUID, @NonNull String successKey, TagResolver... resolvers) {
        return localizationManager.getPlayerComponent(playerUUID, "success." + successKey, resolvers);
    }
    
    /**
     * Gets an info message for a player in their preferred language.
     * 
     * @param playerUUID the player's UUID
     * @param infoKey the info message key
     * @param resolvers optional tag resolvers
     * @return the info message component
     */
    public Component getInfoMessage(@NonNull UUID playerUUID, @NonNull String infoKey, TagResolver... resolvers) {
        return localizationManager.getPlayerComponent(playerUUID, "info." + infoKey, resolvers);
    }
    
    /**
     * Detects the best language for a player based on their locale.
     * 
     * @param playerUUID the player's UUID
     * @param clientLocale the player's client locale (e.g., "en_US", "es_ES")
     * @return the best matching language code
     */
    public String detectBestLanguage(@NonNull UUID playerUUID, String clientLocale) {
        if (clientLocale == null || clientLocale.isEmpty()) {
            return localizationManager.getCurrentLanguage();
        }
        
        // Extract language code from locale (e.g., "en" from "en_US")
        String languageCode = clientLocale.toLowerCase().split("_")[0];
        
        // Check if the language is available
        if (localizationManager.isLanguageLoaded(languageCode)) {
            return languageCode;
        }
        
        // Fallback to default language
        return localizationManager.getCurrentLanguage();
    }
    
    /**
     * Auto-configures a player's language based on their client locale.
     * 
     * @param playerUUID the player's UUID
     * @param clientLocale the player's client locale
     * @param playerName the player's name (for logging)
     * @return a CompletableFuture that completes with the configured language
     */
    public CompletableFuture<String> autoConfigurePlayerLanguage(@NonNull UUID playerUUID, 
                                                                 String clientLocale, 
                                                                 String playerName) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String bestLanguage = detectBestLanguage(playerUUID, clientLocale);
                
                // Only set if different from current preference
                String currentLanguage = localizationManager.getPlayerLanguage(playerUUID);
                if (!bestLanguage.equals(currentLanguage)) {
                    localizationManager.setPlayerLanguage(playerUUID, bestLanguage);
                    log.info("Auto-configured language '" + bestLanguage + "' for player: " + 
                            (playerName != null ? playerName : playerUUID.toString()) + 
                            " (detected from locale: " + clientLocale + ")");
                }
                
                return bestLanguage;
            } catch (Exception e) {
                log.log(Level.WARNING, "Error auto-configuring language for player: " + playerUUID, e);
                return localizationManager.getCurrentLanguage();
            }
        });
    }
    
    /**
     * Gets language statistics in a formatted string.
     * 
     * @return formatted statistics string
     */
    public String getFormattedLanguageStatistics() {
        Map<String, Integer> stats = localizationManager.getLanguageStatistics();
        int totalPlayers = localizationManager.getPlayersWithLanguagePreferences();
        
        StringBuilder sb = new StringBuilder();
        sb.append("Language Statistics:\n");
        sb.append("Total players with preferences: ").append(totalPlayers).append("\n");
        
        if (!stats.isEmpty()) {
            sb.append("Language distribution:\n");
            stats.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> {
                    double percentage = (entry.getValue() * 100.0) / totalPlayers;
                    sb.append("  ").append(entry.getKey().toUpperCase())
                      .append(": ").append(entry.getValue())
                      .append(" (").append(String.format("%.1f", percentage)).append("%)");
                });
        } else {
            sb.append("No language preferences set.");
        }
        
        return sb.toString();
    }
    
    /**
     * Validates if a language code is available.
     * 
     * @param languageCode the language code to validate
     * @return true if the language is available, false otherwise
     */
    public boolean isLanguageAvailable(@NonNull String languageCode) {
        return localizationManager.isLanguageLoaded(languageCode.toLowerCase());
    }
    
    /**
     * Gets all available language codes.
     * 
     * @return a set of available language codes
     */
    public java.util.Set<String> getAvailableLanguages() {
        return localizationManager.getAvailableLanguages();
    }
    
    /**
     * Cleans up language preferences for offline players.
     * This method should be called periodically to maintain data hygiene.
     * 
     * @param activePlayerUUIDs set of currently active player UUIDs
     * @return number of cleaned up preferences
     */
    public int cleanupOfflinePlayerLanguages(@NonNull java.util.Set<UUID> activePlayerUUIDs) {
        Map<String, Integer> stats = localizationManager.getLanguageStatistics();
        int initialCount = localizationManager.getPlayersWithLanguagePreferences();
        
        // This would require access to the internal playerLanguages map
        // For now, we'll just log the request
        log.info("Cleanup requested for offline players. Current preferences: " + initialCount);
        
        // In a real implementation, you would:
        // 1. Iterate through playerLanguages
        // 2. Remove entries not in activePlayerUUIDs
        // 3. Save the updated data
        // 4. Return the number of removed entries
        
        return 0; // Placeholder
    }
}