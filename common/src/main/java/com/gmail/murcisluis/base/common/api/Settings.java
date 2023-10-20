package com.gmail.murcisluis.base.common.api;

import com.gmail.murcisluis.base.common.api.utils.config.CFG;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import com.gmail.murcisluis.base.common.api.utils.config.Key;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Settings {

    private static final Base BASE = BaseAPIFactory.get();
    private static final FileConfig<?> CONFIG = BASE.getFileConfig("config.yml");

    @Key("update-checker")
    public static boolean CHECK_FOR_UPDATES = true;


    // ========================================= //

    /**
     * Reload all Settings
     */
    public static void reload() {
        CONFIG.reload();

        CFG.load(BASE.getPlugin(), Settings.class, CONFIG.getFile());

    }

    public static FileConfig getConfig() {
        return CONFIG;
    }
}