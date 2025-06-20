package com.gmail.murcisluis.base.common.api.config;

import com.gmail.murcisluis.base.common.api.Base;
import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.common.api.localization.InternalMessages;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

/**
 * Modern configuration manager that replaces the old duplicated Settings classes.
 * Provides thread-safe configuration management with automatic loading and caching.
 * 
 * @author BasePlugin Framework
 * @since 2.0.0
 */
@Log
public class ConfigurationManager {
    
    private static final Object LOCK = new Object();
    private static volatile ConfigurationManager instance;
    
    private final Base base;
    private final Map<String, FileConfig<?>> configs;
    
    private ConfigurationManager() {
        this.base = BaseAPIFactory.get();
        this.configs = new ConcurrentHashMap<>();
    }
    
    /**
     * Gets the singleton instance of ConfigurationManager.
     * Thread-safe implementation using double-checked locking.
     * 
     * @return the ConfigurationManager instance
     */
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }
    
    /**
     * Gets or loads a configuration file.
     * 
     * @param configName the name of the configuration file (e.g., "config.yml")
     * @return the FileConfig instance
     * @throws IllegalArgumentException if configName is null or empty
     */
    public FileConfig<?> getConfig(@NonNull String configName) {
        if (configName.trim().isEmpty()) {
            throw new IllegalArgumentException(InternalMessages.getErrorMessage(InternalMessages.ErrorMessage.CONFIG_NAME_EMPTY));
        }
        
        return configs.computeIfAbsent(configName, this::loadConfig);
    }
    
    /**
     * Reloads a specific configuration file.
     * 
     * @param configName the name of the configuration file to reload
     * @return true if the configuration was reloaded successfully, false otherwise
     */
    public boolean reloadConfig(@NonNull String configName) {
        try {
            FileConfig<?> config = configs.get(configName);
            if (config != null) {
                config.reload();
                log.info("Configuration '" + configName + "' reloaded successfully");
                return true;
            } else {
                log.warning("Configuration '" + configName + "' not found for reload");
                return false;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to reload configuration: " + configName, e);
            return false;
        }
    }
    
    /**
     * Reloads all loaded configuration files.
     * 
     * @return the number of configurations successfully reloaded
     */
    public int reloadAllConfigs() {
        int reloadedCount = 0;
        
        for (Map.Entry<String, FileConfig<?>> entry : configs.entrySet()) {
            try {
                entry.getValue().reload();
                reloadedCount++;
            } catch (Exception e) {
                log.log(Level.SEVERE, "Failed to reload configuration: " + entry.getKey(), e);
            }
        }
        
        log.info("Reloaded " + reloadedCount + "/" + configs.size() + " configurations");
        return reloadedCount;
    }
    
    /**
     * Checks if a configuration is currently loaded.
     * 
     * @param configName the name of the configuration file
     * @return true if the configuration is loaded, false otherwise
     */
    public boolean isConfigLoaded(@NonNull String configName) {
        return configs.containsKey(configName);
    }
    
    /**
     * Gets the number of currently loaded configurations.
     * 
     * @return the number of loaded configurations
     */
    public int getLoadedConfigCount() {
        return configs.size();
    }
    
    /**
     * Unloads a specific configuration from memory.
     * 
     * @param configName the name of the configuration to unload
     * @return true if the configuration was unloaded, false if it wasn't loaded
     */
    public boolean unloadConfig(@NonNull String configName) {
        FileConfig<?> removed = configs.remove(configName);
        if (removed != null) {
            log.info("Configuration '" + configName + "' unloaded from memory");
            return true;
        }
        return false;
    }
    
    /**
     * Clears all loaded configurations from memory.
     */
    public void clearAllConfigs() {
        int count = configs.size();
        configs.clear();
        log.info("Cleared " + count + " configurations from memory");
    }
    
    /**
     * Internal method to load a configuration file.
     * 
     * @param configName the name of the configuration file
     * @return the loaded FileConfig
     */
    private FileConfig<?> loadConfig(String configName) {
        try {
            FileConfig<?> config = base.getFileConfig(configName);
            log.info("Configuration '" + configName + "' loaded successfully");
            return config;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to load configuration: " + configName, e);
            throw new RuntimeException("Failed to load configuration: " + configName, e);
        }
    }
    
    /**
     * Gets framework-specific settings with default values.
     * This replaces the old hardcoded Settings class.
     */
    public static class FrameworkSettings {
        private static final ConfigurationManager manager = getInstance();
        private static final FileConfig<?> config = manager.getConfig("config.yml");
        
        /**
         * Whether to check for updates on startup.
         */
        public static boolean isUpdateCheckerEnabled() {
            return config.getBoolean("framework.update-checker", true);
        }
        
        /**
         * Timeout for async operations in milliseconds.
         */
        public static long getAsyncTimeout() {
            return config.getLong("framework.async-timeout", 5000L);
        }
        
        /**
         * Whether debug mode is enabled.
         */
        public static boolean isDebugMode() {
            return config.getBoolean("framework.debug", false);
        }
        
        /**
         * Maximum number of cached configurations.
         */
        public static int getMaxCachedConfigs() {
            return config.getInt("framework.max-cached-configs", 50);
        }
        
        /**
         * Reloads framework settings.
         */
        public static void reload() {
            manager.reloadConfig("config.yml");
        }
    }
}