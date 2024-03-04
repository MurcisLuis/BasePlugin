package com.gmail.murcisluis.base.spigot.api.emotes;

import com.gmail.murcisluis.base.common.api.Base;
import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.common.api.utils.config.*;
import com.gmail.murcisluis.base.spigot.api.emotes.data.ItemConfig;
import com.gmail.murcisluis.base.spigot.api.emotes.data.ItemEmote;
import com.gmail.murcisluis.base.spigot.api.emotes.data.PlayerData;
import com.gmail.murcisluis.base.spigot.api.utils.config.FileConfigSpigot;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@UtilityClass
public class Data {

    private static final Base BASE = BaseAPIFactory.get();
    private static final FileConfigSpigot CONFIG = (FileConfigSpigot) BASE.getFileConfig("data.yml");

    public static ConfigValue<List<ItemEmote>> ITEM_DATA = new ConfigValue<>(CONFIG,"emotes",Lists.newArrayList());

    public static ConfigValue<List<PlayerData>> PLAYER_DATA = new ConfigValue<>(CONFIG,"player-data",Lists.newArrayList());


    private static final Map<String, ConfigValue<?>> VALUES = Maps.newHashMap();

    static {  // Bloque de inicializaci?n est?tica
        try {
            Field[] fields = Data.class.getFields();
            for (Field field : fields) {
                if (field.getType().isAssignableFrom(ConfigValue.class)) {
                    VALUES.put(field.getName(), (ConfigValue) field.get(null));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Data.reload();
    }


    /**
     * Reload all Data
     */
    public static void reload() {
        CONFIG.reload();
        CFG.load(BASE.getPlugin(), Data.class, CONFIG.getFile());
        VALUES.values().forEach(ConfigValue::updateValue);
    }



    public static FileConfig getConfig() {
        return CONFIG;
    }
}
