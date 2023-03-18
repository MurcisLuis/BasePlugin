package com.gmail.murcisluis.baseplugin.api;

import lombok.Getter;
import org.apache.commons.lang3.Validate;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class Base {
    private final JavaPlugin plugin;

    private File dataFolder;
    private boolean updateAvailable;


    Base(JavaPlugin plugin){
        Validate.notNull(plugin);
        this.plugin=plugin;
    }

    protected void load() {

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
}
