package com.gmail.murcisluis.base.common.api.localization;

import com.gmail.murcisluis.base.common.api.Base;
import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import lombok.NonNull;
import lombok.extern.java.Log;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.Locale;

/**
 * Modern localization manager that replaces the old duplicated Lang classes.
 * Provides thread-safe multi-language support with automatic loading and caching.
 * 
 * @author BasePlugin Framework
 * @since 2.0.0
 */
@Log
public class LocalizationManager {
    
    private static final Object LOCK = new Object();
    private static volatile LocalizationManager instance;
    
    private final Base base;
    private final Map<String, LanguageFile> languages;
    private final MiniMessage miniMessage;
    private String currentLanguage;
    
    private LocalizationManager() {
        this.base = BaseAPIFactory.get();
        this.languages = new ConcurrentHashMap<>();
        this.miniMessage = MiniMessage.miniMessage();
        this.currentLanguage = "en"; // Default language
    }
    
    /**
     * Gets the singleton instance of LocalizationManager.
     * Thread-safe implementation using double-checked locking.
     * 
     * @return the LocalizationManager instance
     */
    public static LocalizationManager getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new LocalizationManager();
                }
            }
        }
        return instance;
    }
    
    /**
     * Loads a language file.
     * 
     * @param languageCode the language code (e.g., "en", "es", "fr")
     * @param fileName the name of the language file (e.g., "lang_en.yml")
     * @return true if loaded successfully, false otherwise
     */
    public boolean loadLanguage(@NonNull String languageCode, @NonNull String fileName) {
        try {
            FileConfig config = base.getFileConfig(fileName);
            LanguageFile langFile = new LanguageFile(languageCode, config);
            languages.put(languageCode.toLowerCase(), langFile);
            log.info("Language '" + languageCode + "' loaded from '" + fileName + "'");
            return true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to load language: " + languageCode, e);
            return false;
        }
    }
    
    /**
     * Sets the current active language.
     * 
     * @param languageCode the language code to set as active
     * @return true if the language exists and was set, false otherwise
     */
    public boolean setCurrentLanguage(@NonNull String languageCode) {
        String lowerCode = languageCode.toLowerCase();
        if (languages.containsKey(lowerCode)) {
            this.currentLanguage = lowerCode;
            log.info("Current language set to: " + languageCode);
            return true;
        } else {
            log.warning("Language '" + languageCode + "' not found. Available: " + languages.keySet());
            return false;
        }
    }
    
    /**
     * Gets the current active language code.
     * 
     * @return the current language code
     */
    public String getCurrentLanguage() {
        return currentLanguage;
    }
    
    /**
     * Gets a localized message as a string.
     * 
     * @param key the message key
     * @param args optional arguments for placeholders
     * @return the localized message
     */
    public String getMessage(@NonNull String key, Object... args) {
        return getMessage(currentLanguage, key, args);
    }
    
    /**
     * Gets a localized message from a specific language as a string.
     * 
     * @param languageCode the language code
     * @param key the message key
     * @param args optional arguments for placeholders
     * @return the localized message
     */
    public String getMessage(@NonNull String languageCode, @NonNull String key, Object... args) {
        LanguageFile langFile = languages.get(languageCode.toLowerCase());
        if (langFile == null) {
            // Fallback to default language
            langFile = languages.get("en");
            if (langFile == null) {
                return "[Missing: " + key + "]";
            }
        }
        
        String message = langFile.getString(key, "[Missing: " + key + "]");
        
        // Replace placeholders if arguments provided
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                message = message.replace("{" + i + "}", String.valueOf(args[i]));
            }
        }
        
        return message;
    }
    
    /**
     * Gets a localized message as an Adventure Component.
     * 
     * @param key the message key
     * @param resolvers optional tag resolvers for MiniMessage
     * @return the localized Component
     */
    public Component getComponent(@NonNull String key, TagResolver... resolvers) {
        return getComponent(currentLanguage, key, resolvers);
    }
    
    /**
     * Gets a localized message from a specific language as an Adventure Component.
     * 
     * @param languageCode the language code
     * @param key the message key
     * @param resolvers optional tag resolvers for MiniMessage
     * @return the localized Component
     */
    public Component getComponent(@NonNull String languageCode, @NonNull String key, TagResolver... resolvers) {
        String message = getMessage(languageCode, key);
        
        if (resolvers.length > 0) {
            return miniMessage.deserialize(message, resolvers);
        } else {
            return miniMessage.deserialize(message);
        }
    }
    
    /**
     * Reloads a specific language.
     * 
     * @param languageCode the language code to reload
     * @return true if reloaded successfully, false otherwise
     */
    public boolean reloadLanguage(@NonNull String languageCode) {
        LanguageFile langFile = languages.get(languageCode.toLowerCase());
        if (langFile != null) {
            try {
                langFile.reload();
                log.info("Language '" + languageCode + "' reloaded successfully");
                return true;
            } catch (Exception e) {
                log.log(Level.SEVERE, "Failed to reload language: " + languageCode, e);
                return false;
            }
        }
        return false;
    }
    
    /**
     * Reloads all loaded languages.
     * 
     * @return the number of languages successfully reloaded
     */
    public int reloadAllLanguages() {
        int reloadedCount = 0;
        
        for (Map.Entry<String, LanguageFile> entry : languages.entrySet()) {
            try {
                entry.getValue().reload();
                reloadedCount++;
            } catch (Exception e) {
                log.log(Level.SEVERE, "Failed to reload language: " + entry.getKey(), e);
            }
        }
        
        log.info("Reloaded " + reloadedCount + "/" + languages.size() + " languages");
        return reloadedCount;
    }
    
    /**
     * Gets all available language codes.
     * 
     * @return a set of available language codes
     */
    public java.util.Set<String> getAvailableLanguages() {
        return languages.keySet();
    }
    
    /**
     * Checks if a language is loaded.
     * 
     * @param languageCode the language code to check
     * @return true if the language is loaded, false otherwise
     */
    public boolean isLanguageLoaded(@NonNull String languageCode) {
        return languages.containsKey(languageCode.toLowerCase());
    }
    
    /**
     * Gets a framework message with placeholders
     */
    public static String getFrameworkMessage(String key, String defaultValue) {
        return getInstance().getMessage("framework." + key, defaultValue);
    }
    
    /**
     * Send version message to sender
     */
    public static void sendVersionMessage(Object sender) {
        String versionMessage = getFrameworkMessage("version.message", 
            "<gray>Plugin Version:</gray> <yellow>{version}</yellow>");
        String pluginVersion = InternalMessages.getDefaultValue(InternalMessages.DefaultValue.PLUGIN_VERSION);
        com.gmail.murcisluis.base.common.api.utils.Common.tell(sender, versionMessage, 
            net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed("version", pluginVersion));
    }
    
    /**
     * Framework-specific messages that replace the old hardcoded Lang class.
     */
    public static class FrameworkMessages {
        private static final LocalizationManager manager = getInstance();
        
        // Initialize default English messages
        static {
            if (!manager.isLanguageLoaded("en")) {
                manager.loadLanguage("en", "lang.yml");
            }
        }
        
        /**
         * Gets the framework prefix.
         */
        public static String getPrefix() {
            return manager.getMessage("framework.prefix");
        }
        
        /**
         * Gets the no permission message.
         */
        public static Component getNoPermission() {
            return manager.getComponent("framework.no_permission");
        }
        
        /**
         * Gets the player only message.
         */
        public static Component getPlayerOnly() {
            return manager.getComponent("framework.player_only");
        }
        
        /**
         * Gets the reload success message.
         */
        public static Component getReloadSuccess() {
            return manager.getComponent("framework.reload_success");
        }
        
        /**
         * Gets the command usage message.
         */
        public static Component getCommandUsage(String usage) {
            return manager.getComponent("framework.command_usage", 
                Placeholder.unparsed("usage", usage));
        }
        
        /**
         * Gets the unknown subcommand message.
         */
        public static Component getUnknownSubCommand() {
            return manager.getComponent("framework.unknown_subcommand");
        }
        
        /**
         * Gets the use help message.
         */
        public static Component getUseHelp(String commandPrefix) {
            return manager.getComponent("framework.use_help", 
                Placeholder.unparsed("command_prefix", commandPrefix));
        }
        
        /**
         * Reloads framework messages.
         */
        public static void reload() {
            manager.reloadLanguage("en");
        }
    }
    
    /**
     * Internal class representing a language file.
     */
    private static class LanguageFile {
        private final String languageCode;
        private final FileConfig config;
        
        public LanguageFile(String languageCode, FileConfig config) {
            this.languageCode = languageCode;
            this.config = config;
        }
        
        public String getString(String key, String defaultValue) {
            Object value = config.get(key, defaultValue);
            return value != null ? value.toString() : defaultValue;
        }
        
        public void reload() {
            config.reload();
        }
        
        public String getLanguageCode() {
            return languageCode;
        }
    }
}