package com.gmail.murcisluis.baseplugin.api;

import com.gmail.murcisluis.baseplugin.api.utils.config.*;

import com.google.common.collect.Maps;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

@UtilityClass
public class Settings {

    private static final String API_VERSION;
    private static final Base BASE = BaseAPI.get();
    public static final Configuration CONFIG = new Configuration(BASE.getPlugin(), BASE.getDataFolder(), "config.yml");

    public static final ConfigValue<Boolean> CHECK_UPDATES = new ConfigValue<>(CONFIG, false, "update-checker", true);


    // ========================================= //

    private static final Map<String, ConfigValue<?>> VALUES = Maps.newHashMap();

    static {
        // Load API version from properties
        Properties versionProperties = new Properties();
        String apiVersion = "UNKNOWN";
        try {
            versionProperties.load(BASE.getPlugin().getResource("version.properties"));
            apiVersion = versionProperties.getProperty("version");
        } catch (IOException e) {
            e.printStackTrace();
        }
        API_VERSION = apiVersion;

        try {
            Field[] fields = Settings.class.getFields();
            for (Field field : fields) {
                if (field.getType().isAssignableFrom(ConfigValue.class)) {
                    VALUES.put(field.getName(), (ConfigValue<?>) field.get(null));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Settings.reload();
    }

    /**
     * Reload all Settings
     */
    public static void reload() {
        CONFIG.reload();
        for (ConfigValue<?> value : VALUES.values()) {
            value.updateValue();
        }
    }

    public static String getAPIVersion() {
        return API_VERSION;
    }

}