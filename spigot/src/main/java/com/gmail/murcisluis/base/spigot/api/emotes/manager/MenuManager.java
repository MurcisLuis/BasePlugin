package com.gmail.murcisluis.base.spigot.api.emotes.manager;

import com.gmail.murcisluis.base.spigot.api.Settings;
import com.gmail.murcisluis.base.spigot.api.emotes.data.ItemConfig;

import java.util.HashMap;
import java.util.Map;

public class MenuManager {


    public static final Map<Integer, ItemConfig> MENU_ADMIN = new HashMap<>();
    public static final Map<Integer, ItemConfig> MENU_PLAYER = new HashMap<>();

    public static final Map<Integer, ItemConfig> MENU_CONFIG = new HashMap<>();
    public static final Map<Integer, ItemConfig> MENU_LORE = new HashMap<>();



    public static void onEnable() {
        loadMenuItems();

    }

    private static void loadMenuItems() {
        // Limpia los mapas existentes
        MENU_ADMIN.clear();
        MENU_PLAYER.clear();
        MENU_CONFIG.clear();
        MENU_LORE.clear();

        // Añade los elementos de menú del jugador
        MENU_PLAYER.put(Settings.MENU_PLAYER_ARROW_LEFT.getSlot(), Settings.MENU_PLAYER_ARROW_LEFT);
        MENU_PLAYER.put(Settings.MENU_PLAYER_CLOSE.getSlot(), Settings.MENU_PLAYER_CLOSE);
        MENU_PLAYER.put(Settings.MENU_PLAYER_ARROW_RIGHT.getSlot(), Settings.MENU_PLAYER_ARROW_RIGHT);
        MENU_PLAYER.put(Settings.MENU_PLAYER_SELECT_DEATH.getSlot(),Settings.MENU_PLAYER_SELECT_DEATH);
        MENU_PLAYER.put(Settings.MENU_PLAYER_SELECT_KILL.getSlot(),Settings.MENU_PLAYER_SELECT_KILL);


        // Añade los elementos de menú del administrador
        MENU_ADMIN.put(Settings.MENU_ADMIN_ARROW_LEFT.getSlot(), Settings.MENU_ADMIN_ARROW_LEFT);
        MENU_ADMIN.put(Settings.MENU_ADMIN_CLOSE.getSlot(), Settings.MENU_ADMIN_CLOSE);
        MENU_ADMIN.put(Settings.MENU_ADMIN_ARROW_RIGHT.getSlot(), Settings.MENU_ADMIN_ARROW_RIGHT);
        MENU_ADMIN.put(Settings.MENU_ADMIN_PREVIEW.getSlot(), Settings.MENU_ADMIN_PREVIEW);

        // Añadir elementos al menú de configuración
        MENU_CONFIG.put(Settings.MENU_CONFIG_MATERIAL.getSlot(), Settings.MENU_CONFIG_MATERIAL);
        MENU_CONFIG.put(Settings.MENU_CONFIG_LORE.getSlot(), Settings.MENU_CONFIG_LORE);
        MENU_CONFIG.put(Settings.MENU_CONFIG_NAME.getSlot(), Settings.MENU_CONFIG_NAME);

        // Añadir elementos al menú de lore
        MENU_LORE.put(Settings.MENU_LORE_ADD.getSlot(), Settings.MENU_LORE_ADD);
        MENU_LORE.put(Settings.MENU_LORE_CLEAR.getSlot(), Settings.MENU_LORE_CLEAR);
    }



    public static void onDisable() {
        MENU_ADMIN.clear();
        MENU_PLAYER.clear();
    }
}
