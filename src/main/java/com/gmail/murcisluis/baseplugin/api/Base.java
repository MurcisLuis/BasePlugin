package com.gmail.murcisluis.baseplugin.api;

import com.gmail.murcisluis.baseplugin.api.commands.CommandManager;
import com.gmail.murcisluis.baseplugin.api.utils.Common;
import com.gmail.murcisluis.baseplugin.api.utils.reflect.ReflectionUtil;
import com.gmail.murcisluis.baseplugin.api.utils.reflect.Version;


import lombok.Getter;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

@Getter
public class Base {
    private final JavaPlugin plugin;

    private CommandManager commandManager;


    private File dataFolder;
    private boolean updateAvailable;


    Base(JavaPlugin plugin){
        Validate.notNull(plugin);
        this.plugin=plugin;
    }

    protected void load() {
        if (Version.CURRENT == null) {
            Common.log(Level.SEVERE, "Unsupported server version: " + ReflectionUtil.getVersion());
            Common.log(Level.SEVERE, "Plugin will be disabled.");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }
    protected void enable() {


    }
    protected void disable() {

    }

    public void reload(){

    }



    public File getDataFolder() {
        if (dataFolder == null) {
            dataFolder = new File("plugins/"+ plugin.getName());
        }
        return dataFolder;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
