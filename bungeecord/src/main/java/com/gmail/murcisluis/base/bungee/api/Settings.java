package com.gmail.murcisluis.base.bungee.api;

import com.gmail.murcisluis.base.common.api.Base;
import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.common.api.utils.config.CFG;
import com.gmail.murcisluis.base.common.api.utils.config.ConfigValue;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import com.gmail.murcisluis.base.common.api.utils.config.Key;
import com.google.common.collect.Maps;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.Map;

@UtilityClass
public class Settings {

    private static final Base BASE = BaseAPIFactory.get();
    private static final FileConfig<?> CONFIG = BASE.getFileConfig("config.yml");

    @Key("update-checker")
    public static boolean CHECK_FOR_UPDATES = true;

    @Key("seconds-wait")
    public static long TIME_WAIT=2;

    @Key("default-money-add")
    public static long DEFAULT_MONEY=0;

    // ========================================= //

    private static final Map<String, ConfigValue<?>> VALUES = Maps.newHashMap();

    static {  // Bloque de inicialización estática
        try {
            Field[] fields = Settings.class.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Key.class)) {
                    field.setAccessible(true);
                    String key = field.getAnnotation(Key.class).value();
                    Object defaultValue = field.get(null);  // Asumiendo que todos los campos son estáticos
                    CONFIG.set(key, CONFIG.get(key, defaultValue));  // Establece el valor predeterminado si no existe
                    VALUES.put(key, new ConfigValue<>(CONFIG, key, defaultValue));
                }
            }
            CONFIG.saveData();  // Guarda la configuración en el archivo
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reload all Settings
     */
    public static void reload() {
        CONFIG.reload();
        VALUES.values().forEach(ConfigValue::updateValue);
        CFG.load(BASE.getPlugin(), Settings.class, CONFIG.getFile());

    }

    public static FileConfig getConfig() {
        return CONFIG;
    }
}