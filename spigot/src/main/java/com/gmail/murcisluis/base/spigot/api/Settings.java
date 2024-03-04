package com.gmail.murcisluis.base.spigot.api;

import com.gmail.murcisluis.base.common.api.Base;
import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.common.api.utils.config.*;
import com.gmail.murcisluis.base.spigot.api.emotes.data.ItemConfig;
import com.gmail.murcisluis.base.spigot.api.utils.config.FileConfigSpigot;
import com.google.common.collect.Maps;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@UtilityClass
public class Settings {

    private static final Base BASE = BaseAPIFactory.get();
    private static final FileConfigSpigot CONFIG = (FileConfigSpigot) BASE.getFileConfig("config.yml");

    @Key("update-checker")
    public static Boolean CHECK_FOR_UPDATES = true;

    @Key("menu.material")
    public static String MENU_FILL_MATERIAL="BOOK";


    @Key("menu.player.arrow.left")
    public static ItemConfig MENU_PLAYER_ARROW_LEFT= new ItemConfig("Previous Page", List.of("Click to go to the previous page."), "ARROW", 0, 45);
    @Key("menu.player.close")
    public static ItemConfig MENU_PLAYER_CLOSE = new ItemConfig("Close Menu", List.of("Click to close the menu."), "BARRIER", 0, 49);
    @Key("menu.player.arrow.right")
    public static ItemConfig MENU_PLAYER_ARROW_RIGHT = new ItemConfig("Next Page", List.of("Click to go to the next page."), "ARROW", 0, 53);

    @Key("menu.player.noselect.death")
    public static ItemConfig MENU_PLAYER_SELECT_DEATH = new ItemConfig("Not selected", List.of(), "STRUCTURE_VOID", 0, 51);

    @Key("menu.player.noselect.kill")
    public static ItemConfig MENU_PLAYER_SELECT_KILL = new ItemConfig("Not selected", List.of(), "STRUCTURE_VOID", 0, 47);
    @Key("menu.admin.arrow.left")
    public static ItemConfig MENU_ADMIN_ARROW_LEFT = new ItemConfig("Previous Page", List.of("Click to go to the previous page."), "ARROW", 0, 45);

    @Key("menu.admin.close")
    public static ItemConfig MENU_ADMIN_CLOSE = new ItemConfig("Close Menu", List.of("Click to close the menu."), "BARRIER", 0, 49);

    @Key("menu.admin.arrow.right")
    public static ItemConfig MENU_ADMIN_ARROW_RIGHT = new ItemConfig("Next Page", List.of("Click to go to the next page."), "ARROW", 0, 53);

    @Key("menu.admin.preview")
    public static ItemConfig MENU_ADMIN_PREVIEW = new ItemConfig("Preview Items", List.of("Click to preview items for the player."), "GLASS", 0, 47);

    @Key("menu.config.material")
    public static ItemConfig MENU_CONFIG_MATERIAL = new ItemConfig("Click to change material",new ArrayList<>(),"BOOK", 0,11);

    @Key("menu.config.lore")
    public static ItemConfig MENU_CONFIG_LORE = new ItemConfig("Click to change lore",new ArrayList<>(),"PAPER", 0,13);

    @Key("menu.config.name")
    public static ItemConfig MENU_CONFIG_NAME = new ItemConfig("Click to change name",new ArrayList<>(),"ANVIL", 0,15);

    @Key("menu.lore.add")
    public static ItemConfig MENU_LORE_ADD = new ItemConfig("Click to add new line",new ArrayList<>(),"PAPER", 0,45);

    @Key("menu.lore.clear")
    public static ItemConfig MENU_LORE_CLEAR = new ItemConfig("Click to remove all lines",new ArrayList<>(),"BARRIER", 0,47);

    @Key("menu.config_name.accept")
    public static ItemConfig MENU_CONFIG_NAME_ACCEPT = new ItemConfig("Accept",new ArrayList<>(),"PAPER", 0,0);

    @Key("menu.config_name.cancel")
    public static ItemConfig MENU_CONFIG_NAME_CANCEL = new ItemConfig("Cancel",new ArrayList<>(),"BARRIER", 0,0);

    @Key("menu.config_lore.accept")
    public static ItemConfig MENU_CONFIG_LORE_ACCEPT = new ItemConfig("Accept",new ArrayList<>(),"EMERALD", 0,1);

    @Key("menu.config_lore.cancel")
    public static ItemConfig MENU_CONFIG_LORE_CANCEL = new ItemConfig("Cancel",new ArrayList<>(),"BARRIER", 0,2);




    // ========================================= //

    private static final Map<String, ConfigValue<?>> VALUES = Maps.newHashMap();

    static {  // Bloque de inicialización estática
        try {
            Field[] fields = Settings.class.getFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Key.class)) {
                    field.setAccessible(true);
                    String key = field.getAnnotation(Key.class).value();
                    Object defaultValue = field.get(null);
                    if (defaultValue instanceof Map<?,?>) {
                        VALUES.put(field.getName(), new SectionConfigValue<>(CONFIG, key, defaultValue));
                    }else {
                        VALUES.put(field.getName(), new ConfigValue<>(CONFIG, key, defaultValue));
                    }
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
        CFG.load(BASE.getPlugin(), Settings.class, CONFIG.getFile());
        VALUES.values().forEach(ConfigValue::updateValue);

    }

    public static FileConfig getConfig() {
        return CONFIG;
    }
}