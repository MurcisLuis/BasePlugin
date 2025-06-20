package com.gmail.murcisluis.base.common.api;

import com.gmail.murcisluis.base.common.api.localization.InternalMessages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Thread-safe singleton factory for BaseAPI instances.
 * Implements the Singleton pattern with proper synchronization and SOLID principles.
 * 
 * @author Luis Murcis
 * @version 2.0.0
 * @since 1.0.0
 */
public final class BaseAPIFactory {

    private static volatile BaseAPI apiInstance;
    private static final Object LOCK = new Object();
    
    // Private constructor to prevent instantiation (Singleton pattern)
    private BaseAPIFactory() {
        throw new UnsupportedOperationException(InternalMessages.getErrorMessage(InternalMessages.ErrorMessage.UTILITY_CLASS));
    }

    /**
     * Initializes the API instance using double-checked locking pattern.
     * This method is thread-safe and ensures only one instance is created.
     * 
     * @param api The BaseAPI implementation to initialize
     * @throws IllegalArgumentException if api is null
     * @throws IllegalStateException if API is already initialized
     */
    public static void initialize(@NotNull BaseAPI api) {
        if (api == null) {
            throw new IllegalArgumentException(InternalMessages.getErrorMessage(InternalMessages.ErrorMessage.API_NULL));
        }
        
        if (apiInstance != null) {
            throw new IllegalStateException(InternalMessages.getErrorMessage(InternalMessages.ErrorMessage.API_ALREADY_INITIALIZED));
        }
        
        synchronized (LOCK) {
            if (apiInstance == null) {
                apiInstance = api;
            } else {
                throw new IllegalStateException(InternalMessages.getErrorMessage(InternalMessages.ErrorMessage.API_ALREADY_INITIALIZED));
            }
        }
    }

    /**
     * Gets the current API instance.
     * 
     * @return The current BaseAPI instance
     * @throws IllegalStateException if API is not initialized
     */
    @NotNull
    public static BaseAPI getAPI() {
        BaseAPI instance = apiInstance;
        if (instance == null) {
            throw new IllegalStateException(InternalMessages.getErrorMessage(InternalMessages.ErrorMessage.API_NOT_INITIALIZED));
        }
        return instance;
    }

    /**
     * Gets the Base implementation from the current API instance.
     * 
     * @return The Base implementation
     * @throws IllegalStateException if API is not initialized
     */
    @NotNull
    public static Base get() {
        return getAPI().get();
    }

    /**
     * Gets the BasePlugin from the current API instance.
     * 
     * @return The BasePlugin instance
     * @throws IllegalStateException if API is not initialized
     */
    @NotNull
    public static BasePlugin getPlugin() {
        return get().getPlugin();
    }
    
    /**
     * Checks if the API is currently initialized and running.
     * 
     * @return true if API is initialized and running, false otherwise
     */
    public static boolean isInitialized() {
        BaseAPI instance = apiInstance;
        return instance != null && instance.isRunning();
    }
    
    /**
     * Gets the current API instance without throwing exceptions.
     * 
     * @return The current BaseAPI instance or null if not initialized
     */
    @Nullable
    public static BaseAPI getAPIOrNull() {
        return apiInstance;
    }
    
    /**
     * Safely shuts down the API instance.
     * This method is thread-safe and can be called multiple times.
     */
    public static void shutdown() {
        synchronized (LOCK) {
            if (apiInstance != null) {
                try {
                    if (apiInstance.isRunning()) {
                        apiInstance.onDisable();
                    }
                } finally {
                    apiInstance = null;
                }
            }
        }
    }
}
