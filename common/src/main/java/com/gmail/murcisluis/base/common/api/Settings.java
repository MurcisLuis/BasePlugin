package com.gmail.murcisluis.base.common.api;

import com.gmail.murcisluis.base.common.api.config.ConfigurationManager;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import lombok.experimental.UtilityClass;

/**
 * @deprecated Use {@link ConfigurationManager} instead.
 * This class is kept for backward compatibility and will be removed in a future version.
 * 
 * Migration guide:
 * - Replace Settings.getConfig() with ConfigurationManager.getInstance().getConfig("config.yml")
 * - Replace Settings.reload() with ConfigurationManager.getInstance().reloadConfig("config.yml")
 * - Use ConfigurationManager.FrameworkSettings for framework-specific settings
 */
@Deprecated
@UtilityClass
public class Settings {

    private static final ConfigurationManager configManager = ConfigurationManager.getInstance();

    /**
     * @deprecated Use {@link ConfigurationManager.FrameworkSettings#isUpdateCheckerEnabled()} instead.
     */
    @Deprecated
    public static boolean CHECK_FOR_UPDATES = true;

    // ========================================= //

    /**
     * Reload all Settings
     * @deprecated Use {@link ConfigurationManager#reloadConfig(String)} instead.
     */
    @Deprecated
    public static void reload() {
        configManager.reloadConfig("config.yml");
    }

    /**
     * @deprecated Use {@link ConfigurationManager#getConfig(String)} instead.
     */
    @Deprecated
    public static FileConfig getConfig() {
        return configManager.getConfig("config.yml");
    }

}