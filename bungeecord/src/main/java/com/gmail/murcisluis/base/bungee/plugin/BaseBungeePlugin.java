package com.gmail.murcisluis.base.bungee.plugin;

import com.gmail.murcisluis.base.common.api.BasePlugin;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Base BungeeCord plugin class that implements BasePlugin interface
 * 
 * @author BasePlugin Framework
 * @since 2.0.0
 */
public abstract class BaseBungeePlugin extends Plugin implements BasePlugin {
    
    @Override
    public void onEnable() {
        // Initialize the plugin
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        // Cleanup when plugin is disabled
        super.onDisable();
    }
    
    @Override
    public Object getServerInstance() {
        return getProxy();
    }
    
    @Override
    public String getVersionServer() {
        return getProxy().getVersion();
    }
    
    @Override
    public com.gmail.murcisluis.base.common.api.Description getPluginDescription() {
        return new com.gmail.murcisluis.base.common.api.Description(
            getDescription().getName(),
            getDescription().getMain(),
            getDescription().getVersion(),
            getDescription().getAuthor(),
            getDescription().getFile(),
            getDescription().getDescription()
        );
    }
    
    @Override
    public com.gmail.murcisluis.base.common.api.utils.config.ConfigAdapter getConfigAdapter() {
        // Return a default config adapter implementation
        return null; // TODO: Implement proper config adapter
    }
}