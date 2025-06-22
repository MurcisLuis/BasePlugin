package com.gmail.murcisluis.base.examples.common;

import com.gmail.murcisluis.base.common.api.localization.LocalizationManager;
import com.gmail.murcisluis.base.common.api.localization.PlayerLocalizationHelper;
import lombok.extern.java.Log;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

/**
 * Example class demonstrating the enhanced LocalizationManager usage.
 * Shows how to manage player language preferences and use localized messages.
 * 
 * @author BasePlugin Framework
 * @since 2.1.0
 */
@Log
public class LocalizationExample {
    
    private final LocalizationManager localizationManager;
    private final PlayerLocalizationHelper localizationHelper;
    
    public LocalizationExample() {
        this.localizationManager = LocalizationManager.getInstance();
        this.localizationHelper = new PlayerLocalizationHelper();
        
        // Initialize example languages
        initializeExampleLanguages();
    }
    
    /**
     * Initializes example language files.
     */
    private void initializeExampleLanguages() {
        // Load different language files
        localizationManager.loadLanguage("en", "lang_en.yml");
        localizationManager.loadLanguage("es", "lang_es.yml");
        localizationManager.loadLanguage("fr", "lang_fr.yml");
        localizationManager.loadLanguage("de", "lang_de.yml");
        
        log.info("Loaded languages: " + localizationManager.getAvailableLanguages());
    }
    
    /**
     * Example of handling player join with language setup.
     * 
     * @param playerUUID the player's UUID
     * @param playerName the player's name
     * @param clientLocale the player's client locale (e.g., "es_ES")
     */
    public void handlePlayerJoin(UUID playerUUID, String playerName, String clientLocale) {
        log.info("Player " + playerName + " joined with locale: " + clientLocale);
        
        // Auto-configure language based on client locale
        localizationHelper.autoConfigurePlayerLanguage(playerUUID, clientLocale, playerName)
            .thenAccept(configuredLanguage -> {
                log.info("Configured language '" + configuredLanguage + "' for " + playerName);
                
                // Send welcome message in player's language
                Component welcomeMessage = localizationHelper.getWelcomeMessage(playerUUID, playerName);
                sendMessageToPlayer(playerUUID, welcomeMessage);
                
                // Show available commands in player's language
                Component commandsInfo = localizationManager.getPlayerComponent(playerUUID, "info.available_commands");
                sendMessageToPlayer(playerUUID, commandsInfo);
            })
            .exceptionally(throwable -> {
                log.log(Level.WARNING, "Failed to configure language for " + playerName, throwable);
                return null;
            });
    }
    
    /**
     * Example of handling player quit.
     * 
     * @param playerUUID the player's UUID
     * @param playerName the player's name
     */
    public void handlePlayerQuit(UUID playerUUID, String playerName) {
        // Send goodbye message in player's language
        Component goodbyeMessage = localizationHelper.getGoodbyeMessage(playerUUID, playerName);
        sendMessageToPlayer(playerUUID, goodbyeMessage);
        
        log.info("Player " + playerName + " left (language: " + 
                localizationManager.getPlayerLanguage(playerUUID) + ")");
    }
    
    /**
     * Example of handling a language change command.
     * 
     * @param playerUUID the player's UUID
     * @param playerName the player's name
     * @param newLanguage the requested language
     */
    public void handleLanguageChangeCommand(UUID playerUUID, String playerName, String newLanguage) {
        if (!localizationHelper.isLanguageAvailable(newLanguage)) {
            // Send error message in current language
            Component errorMessage = localizationHelper.getErrorMessage(playerUUID, "language_not_available",
                Placeholder.unparsed("language", newLanguage),
                Placeholder.unparsed("available", String.join(", ", localizationHelper.getAvailableLanguages())));
            sendMessageToPlayer(playerUUID, errorMessage);
            return;
        }
        
        // Set the new language
        localizationHelper.setupPlayerLanguage(playerUUID, newLanguage, playerName)
            .thenAccept(success -> {
                if (success) {
                    // Send success message in the NEW language
                    Component successMessage = localizationHelper.getSuccessMessage(playerUUID, "language_changed",
                        Placeholder.unparsed("language", newLanguage.toUpperCase()));
                    sendMessageToPlayer(playerUUID, successMessage);
                } else {
                    // Send error message in current language
                    Component errorMessage = localizationHelper.getErrorMessage(playerUUID, "language_change_failed");
                    sendMessageToPlayer(playerUUID, errorMessage);
                }
            });
    }
    
    /**
     * Example of showing server statistics to an admin.
     * 
     * @param adminUUID the admin's UUID
     */
    public void showLanguageStatistics(UUID adminUUID) {
        // Get statistics
        Map<String, Integer> stats = localizationManager.getLanguageStatistics();
        int totalPlayers = localizationManager.getPlayersWithLanguagePreferences();
        
        // Send header
        Component header = localizationManager.getPlayerComponent(adminUUID, "admin.language_stats_header");
        sendMessageToPlayer(adminUUID, header);
        
        // Send total players info
        Component totalInfo = localizationManager.getPlayerComponent(adminUUID, "admin.total_players_with_prefs",
            Placeholder.unparsed("count", String.valueOf(totalPlayers)));
        sendMessageToPlayer(adminUUID, totalInfo);
        
        // Send language distribution
        if (!stats.isEmpty()) {
            stats.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> {
                    double percentage = (entry.getValue() * 100.0) / totalPlayers;
                    Component langStat = localizationManager.getPlayerComponent(adminUUID, "admin.language_stat_line",
                        Placeholder.unparsed("language", entry.getKey().toUpperCase()),
                        Placeholder.unparsed("count", String.valueOf(entry.getValue())),
                        Placeholder.unparsed("percentage", String.format("%.1f", percentage)));
                    sendMessageToPlayer(adminUUID, langStat);
                });
        } else {
            Component noStats = localizationManager.getPlayerComponent(adminUUID, "admin.no_language_stats");
            sendMessageToPlayer(adminUUID, noStats);
        }
    }
    
    /**
     * Example of handling an economy transaction with localized messages.
     * 
     * @param playerUUID the player's UUID
     * @param amount the transaction amount
     * @param success whether the transaction was successful
     */
    public void handleEconomyTransaction(UUID playerUUID, double amount, boolean success) {
        if (success) {
            Component successMessage = localizationHelper.getSuccessMessage(playerUUID, "economy_transaction",
                Placeholder.unparsed("amount", String.format("%.2f", amount)));
            sendMessageToPlayer(playerUUID, successMessage);
        } else {
            Component errorMessage = localizationHelper.getErrorMessage(playerUUID, "insufficient_funds",
                Placeholder.unparsed("amount", String.format("%.2f", amount)));
            sendMessageToPlayer(playerUUID, errorMessage);
        }
    }
    
    /**
     * Example of sending a broadcast message in all player languages.
     * 
     * @param messageKey the message key
     * @param resolvers optional tag resolvers
     */
    public void sendBroadcastMessage(String messageKey, net.kyori.adventure.text.minimessage.tag.resolver.TagResolver... resolvers) {
        // Get all players with language preferences
        Map<String, Integer> languageStats = localizationManager.getLanguageStatistics();
        
        // Send message in each language
        for (String language : languageStats.keySet()) {
            Component message = localizationManager.getComponent(language, messageKey, resolvers);
            // Here you would send to all players with that language preference
            log.info("Broadcasting in " + language + ": " + message);
        }
        
        // Also send in default language for players without preferences
        Component defaultMessage = localizationManager.getComponent(messageKey, resolvers);
        log.info("Broadcasting in default language: " + defaultMessage);
    }
    
    /**
     * Example method to demonstrate error handling with localization.
     * 
     * @param playerUUID the player's UUID
     * @param errorType the type of error
     */
    public void handleError(UUID playerUUID, String errorType) {
        Component errorMessage;
        
        switch (errorType) {
            case "permission_denied":
                errorMessage = localizationHelper.getErrorMessage(playerUUID, "permission_denied");
                break;
            case "player_not_found":
                errorMessage = localizationHelper.getErrorMessage(playerUUID, "player_not_found");
                break;
            case "invalid_command":
                errorMessage = localizationHelper.getErrorMessage(playerUUID, "invalid_command");
                break;
            default:
                errorMessage = localizationHelper.getErrorMessage(playerUUID, "unknown_error",
                    Placeholder.unparsed("error_type", errorType));
                break;
        }
        
        sendMessageToPlayer(playerUUID, errorMessage);
    }
    
    /**
     * Placeholder method for sending messages to players.
     * In a real implementation, this would use the appropriate platform's messaging system.
     * 
     * @param playerUUID the player's UUID
     * @param message the message component
     */
    private void sendMessageToPlayer(UUID playerUUID, Component message) {
        // This is a placeholder - in real implementation you would:
        // 1. Get the player object from UUID
        // 2. Send the message using the platform's messaging system
        log.info("Sending to " + playerUUID + ": " + message);
    }
    
    /**
     * Example of periodic cleanup task.
     */
    public void performPeriodicCleanup() {
        try {
            // Get current statistics
            String stats = localizationHelper.getFormattedLanguageStatistics();
            log.info("Current language statistics:\n" + stats);
            
            // In a real implementation, you might:
            // 1. Clean up preferences for players who haven't joined in X days
            // 2. Update statistics
            // 3. Optimize data storage
            
            log.info("Periodic localization cleanup completed");
        } catch (Exception e) {
            log.log(Level.WARNING, "Error during localization cleanup", e);
        }
    }
    
    /**
     * Gets the localization manager instance.
     * 
     * @return the localization manager
     */
    public LocalizationManager getLocalizationManager() {
        return localizationManager;
    }
    
    /**
     * Gets the localization helper instance.
     * 
     * @return the localization helper
     */
    public PlayerLocalizationHelper getLocalizationHelper() {
        return localizationHelper;
    }
}