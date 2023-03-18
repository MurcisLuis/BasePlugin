package com.gmail.murcisluis.baseplugin.plugin;

import com.gmail.murcisluis.baseplugin.api.BaseAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class BasePlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        BaseAPI.onLoad(this);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        BaseAPI.onEnable();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        BaseAPI.onDisable();
    }


}
