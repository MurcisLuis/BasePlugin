package com.gmail.murcisluis.base.spigot.plugin;

import com.gmail.murcisluis.base.common.api.BasePlugin;
import com.gmail.murcisluis.base.common.api.Description;
import com.gmail.murcisluis.base.common.api.utils.config.ConfigAdapter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;

public class BaseSpigotPlugin extends JavaPlugin implements BasePlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public Object getServerInstance() {
        return super.getServer();
    }

    @Override
    public String getVersionServer() {
        return super.getServer().getVersion();
    }

    @Override
    public Description getPluginDescription() {
        return new Description(
            getDescription().getName(),
            getDescription().getMain(),
            getDescription().getVersion(),
            getDescription().getAuthors().isEmpty() ? "Unknown" : getDescription().getAuthors().get(0),
            getFile(),
            getDescription().getDescription()
        );
    }

    @Override
    public ConfigAdapter getConfigAdapter() {
        // TODO: Implement config adapter for Spigot
        return null;
    }

    @Override
    public InputStream getResource(String filename) {
        return super.getResource(filename);
    }

    @Override
    public void saveResource(String resourcePath, boolean replace) {
        super.saveResource(resourcePath, replace);
    }

    @Override
    public Logger getLogger() {
        return super.getLogger();
    }
}