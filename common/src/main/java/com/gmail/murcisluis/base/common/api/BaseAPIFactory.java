package com.gmail.murcisluis.base.common.api;

public class BaseAPIFactory {

    private static BaseAPI apiInstance;

    public static void initialize(BaseAPI api) {
        apiInstance = api;
    }

    public static BaseAPI getAPI() {
        if (apiInstance == null) {
            throw new IllegalStateException("API not initialized");
        }
        return apiInstance;
    }

    public static Base get() {
        if (apiInstance == null) {
            throw new IllegalStateException("API not initialized");
        }
        return apiInstance.get();
    }

    public static BasePlugin getPlugin() {
        if (apiInstance == null) {
            throw new IllegalStateException("API not initialized");
        }
        return apiInstance.get().getPlugin();
    }
}
