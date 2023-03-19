package com.gmail.murcisluis.baseplugin.api;

import lombok.experimental.UtilityClass;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@UtilityClass

public class BaseAPI {

    private static Base implementation;

    private static boolean enabled = false;

    public static void onLoad(@NotNull JavaPlugin plugin){
        if (implementation != null) return;
        implementation = new Base(plugin);
        implementation.load();

    }

    public static void onEnable(){
        if (implementation == null) return;
        enabled = true;
        implementation.enable();


    }

    public static void onDisable(){
        if (implementation != null) return;
        implementation.disable();
        implementation = null;
        enabled = false;

    }
    public static boolean isRunning() {
        return implementation != null && enabled;
    }

    public static Base get() {
        if (implementation == null) {
            throw new IllegalStateException("There is no running instance of BaseChat, enabled it first.");
        }
        return implementation;
    }
}
