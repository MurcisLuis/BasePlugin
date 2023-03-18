package com.gmail.murcisluis.baseplugin.api;

import com.gmail.murcisluis.baseplugin.plugin.BasePlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BaseAPI {

    private static Base implementation;

    public static void onLoad(JavaPlugin plugin){
        if (implementation != null) return;
        implementation = new Base(plugin);
        implementation.load();

    }

    public static void onEnable(){
        if (implementation != null) return;
        implementation.enable();


    }

    public static void onDisable(){
        if (implementation != null) return;
        implementation.disable();
        implementation = null;
    }
}
