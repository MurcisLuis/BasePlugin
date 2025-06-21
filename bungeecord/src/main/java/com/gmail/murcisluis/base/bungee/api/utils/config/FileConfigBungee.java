package com.gmail.murcisluis.base.bungee.api.utils.config;

import com.gmail.murcisluis.base.bungee.plugin.BaseBungeePlugin;
import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.common.api.utils.Common;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import lombok.Getter;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;

@Getter
public class FileConfigBungee implements FileConfig<Configuration> {
    protected final @NotNull BaseBungeePlugin plugin;
    protected final @NotNull String path;
    protected final @NotNull File file;
    private Configuration configuration;

    public FileConfigBungee(@NotNull BaseBungeePlugin plugin, @NotNull String path) {
        this.plugin = plugin;
        this.path = path;
        this.file = new File(plugin.getDataFolder(), path);
        this.createFile();
        this.reload();
    }

    public FileConfigBungee(@NotNull BaseBungeePlugin plugin, @NotNull File file) {
        this.plugin = plugin;
        this.path = file.getName();
        this.file = file;
        this.createFile();
        this.reload();
    }

    public FileConfigBungee(@NotNull String path, @NotNull Configuration configuration) {
        this.plugin = (BaseBungeePlugin) BaseAPIFactory.getPlugin();
        this.path = path;
        this.file = new File(plugin.getDataFolder(), path);
        this.configuration = configuration;
        if (path.isEmpty()) return;
        this.createFile();
    }

    public void createFile() {
        if (!file.exists()) {
            plugin.getDataFolder().mkdirs();
            try (InputStream in = plugin.getResourceAsStream(path)) {
                if (in == null) {
                    file.createNewFile();
                } else {
                    Files.copy(in, file.toPath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveData() {
        try {
            save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(File file) throws IOException {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
    }
    @Override
    public void reload() {
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void delete() {
        if (file.exists()) {
            if (!file.delete()) {
                Common.log(Level.WARNING, "Cannot delete existing file '%s' (Permission issue?)", file.getName());
            }
        }
    }





    @Override
    public Configuration loadFile(File file) throws IOException {
        return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }

    @Override
    public boolean contains(String path) {
        return configuration.contains(path);
    }

    @Override
    public Object get(String path) {
        return configuration.get(path);
    }

    @Override
    public Object get(String path, Object defaultValue) {
        return configuration.get(path, defaultValue);
    }

    @Override
    public void set(String path, Object value) {
        configuration.set(path, value);
    }
}
