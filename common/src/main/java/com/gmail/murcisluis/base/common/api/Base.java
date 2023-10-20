package com.gmail.murcisluis.base.common.api;

import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import org.apache.commons.lang3.Validate;

import java.io.File;
@Getter
public abstract class Base {

    protected final BasePlugin plugin;
    protected boolean updateAvailable;

    protected Base(BasePlugin plugin){
        Validate.notNull(plugin);
        this.plugin=plugin;
    }

    public BasePlugin getPlugin() {
        return plugin;
    }

    public File getDataFolder() {
        return plugin.getDataFolder();
    }

    public abstract void load();
    public abstract void enable();
    public abstract void disable();
    public abstract void reload();
    public abstract Audience audienceSender(Object sender);
    public abstract String placeholderAPI(Object player,String message);
    public abstract FileConfig<?> getFileConfig(String path);
}
