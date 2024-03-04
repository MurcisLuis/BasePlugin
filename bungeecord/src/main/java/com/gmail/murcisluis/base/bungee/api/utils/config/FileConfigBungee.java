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
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    public Object get(String path, Object defaultvalue) {
        return configuration.get(path,defaultvalue);
    }

    @Override
    public void set(String path, Object value) {
        configuration.set(path, value);
    }

    @Override
    public String getString(String path) {
        return configuration.getString(path);
    }

    @Override
    public String getString(String path, String defaultValue) {
        return configuration.getString(path, defaultValue);
    }

    @Override
    public int getInt(String path) {
        return configuration.getInt(path);
    }

    @Override
    public int getInt(String path, int defaultValue) {
        return configuration.getInt(path, defaultValue);
    }

    @Override
    public boolean getBoolean(String path) {
        return configuration.getBoolean(path);
    }

    @Override
    public boolean getBoolean(String path, boolean defaultValue) {
        return configuration.getBoolean(path, defaultValue);
    }

    @Override
    public double getDouble(String path) {
        return configuration.getDouble(path);
    }

    @Override
    public double getDouble(String path, double defaultValue) {
        return configuration.getDouble(path, defaultValue);
    }

    @Override
    public long getLong(String path) {
        return configuration.getLong(path);
    }

    @Override
    public long getLong(String path, long defaultValue) {
        return configuration.getLong(path, defaultValue);
    }

    @Override
    public List<?> getList(String path) {
        return configuration.getList(path);
    }

    @Override
    public List<?> getList(String path, List<?> defaultValue) {
        return configuration.getList(path, defaultValue);
    }

    @Override
    public List<String> getStringList(String path) {
        return configuration.getStringList(path);
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        return configuration.getIntList(path);
    }

    @Override
    public List<Double> getDoubleList(String path) {
        return configuration.getDoubleList(path);
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        return configuration.getBooleanList(path);
    }

    @Override
    public List<Long> getLongList(String path) {
        return configuration.getLongList(path);
    }

    // Método para obtener secciones de configuración, si es necesario
    @Override
    public FileConfig<?> getSection(String path) {
        Configuration section = configuration.getSection(path);
        return section == null ? null : new FileConfigBungee(path, section);
    }

    @Override
    public void setSectionConfig(String path,Map<String, Object> configMap) {
        for (Map.Entry<String, Object> entry : configMap.entrySet()) {
            this.set(path +"."+ entry.getKey(), entry.getValue());
            this.saveData();
        }
         // Save changes to the file
    }

}
