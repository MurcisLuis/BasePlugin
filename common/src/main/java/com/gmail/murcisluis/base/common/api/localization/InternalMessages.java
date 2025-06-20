package com.gmail.murcisluis.base.common.api.localization;

import lombok.extern.java.Log;

/**
 * Manages internal framework messages that should not be customizable by end users.
 * These messages are for internal operations, error handling, and debugging.
 * Uses enums for better type safety and organization.
 */
@Log
public class InternalMessages {
    
    /**
     * Framework version information
     */
    public enum Version {
        FRAMEWORK("1.0.0");
        
        private final String value;
        
        Version(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    /**
     * Error messages for internal framework operations
     */
    public enum ErrorMessage {
        UTILITY_CLASS("Utility class cannot be instantiated"),
        API_NULL("API instance cannot be null"),
        API_ALREADY_INITIALIZED("API is already initialized. Use shutdown() first if you need to reinitialize."),
        API_NOT_INITIALIZED("API not initialized. Call initialize() first."),
        CONFIG_NAME_EMPTY("Configuration name cannot be empty"),
        NOT_BUNGEE_PLAYER("player is not a BungeeCord ProxiedPlayer"),
        NOT_BUNGEE_SENDER("sender is not a BungeeCord CommandSender"),
        NOT_SPIGOT_SENDER("sender is not a Spigot CommandSender");
        
        private final String message;
        
        ErrorMessage(String message) {
            this.message = message;
        }
        
        public String getMessage() {
            return message;
        }
    }
    
    /**
     * Default values for plugin information
     */
    public enum DefaultValue {
        PLUGIN_NAME("Unknown Plugin"),
        PLUGIN_VERSION("Unknown Version"),
        PLUGIN_AUTHOR("Unknown Author"),
        SERVER_VERSION("Unknown Server");
        
        private final String value;
        
        DefaultValue(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    // Utility class - prevent instantiation
    private InternalMessages() {
        throw new UnsupportedOperationException(ErrorMessage.UTILITY_CLASS.getMessage());
    }
    
    /**
     * Get the framework version
     */
    public static String getFrameworkVersion() {
        return Version.FRAMEWORK.getValue();
    }
    
    /**
     * Get an error message by enum
     */
    public static String getErrorMessage(ErrorMessage error) {
        return error.getMessage();
    }
    
    /**
     * Get an error message by string key (for backward compatibility)
     */
    public static String getErrorMessage(String errorKey) {
        try {
            String enumName = errorKey.toUpperCase().replace("-", "_");
            ErrorMessage error = ErrorMessage.valueOf(enumName);
            return error.getMessage();
        } catch (IllegalArgumentException e) {
            log.warning("Unknown error key: " + errorKey);
            return "Unknown error: " + errorKey;
        }
    }
    
    /**
     * Get a default value by enum
     */
    public static String getDefaultValue(DefaultValue defaultValue) {
        return defaultValue.getValue();
    }
    
    /**
     * Get a default value by string key (for backward compatibility)
     */
    public static String getDefaultValue(String defaultKey) {
        try {
            String enumName = defaultKey.toUpperCase().replace("-", "_");
            DefaultValue defaultValue = DefaultValue.valueOf(enumName);
            return defaultValue.getValue();
        } catch (IllegalArgumentException e) {
            log.warning("Unknown default key: " + defaultKey);
            return "Unknown default: " + defaultKey;
        }
    }
}