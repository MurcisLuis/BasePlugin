package com.gmail.murcisluis.base.spigot.api.utils.config;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.common.api.utils.config.ConfigAdapter;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;

public class ConfigAdapterSpigot extends ConfigAdapter {
    @Override
    public FileConfigSpigot loadConfiguration(File file,InputStreamReader isr) {
        return new FileConfigSpigot(BaseAPIFactory.getPlugin(),file,YamlConfiguration.loadConfiguration(isr));
    }

    @Override
    public FileConfig<?> loadConfiguration(File file) {
        return new FileConfigSpigot(BaseAPIFactory.getPlugin(),file,YamlConfiguration.loadConfiguration(file));
    }
}
