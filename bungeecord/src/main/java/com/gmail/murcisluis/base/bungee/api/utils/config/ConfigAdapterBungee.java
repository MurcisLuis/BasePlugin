package com.gmail.murcisluis.base.bungee.api.utils.config;

import com.gmail.murcisluis.base.common.api.utils.config.ConfigAdapter;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConfigAdapterBungee extends ConfigAdapter {
    @Override
    public FileConfigBungee loadConfiguration(File file, InputStreamReader isr) {
        Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(isr);
        return new FileConfigBungee(file.getPath(),configuration);
    }

    @Override
    public FileConfig<?> loadConfiguration(File file) {
        Configuration configuration = null;
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new FileConfigBungee(file.getPath(),configuration);
    }
}
