package com.gmail.murcisluis.base.spigot.api.utils.config;

import com.gmail.murcisluis.base.common.api.BasePlugin;
import com.gmail.murcisluis.base.common.api.utils.Common;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import lombok.Getter;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

/**
 * This class extends {@link YamlConfiguration} and is used to load and save
 * the configuration file. It also provides a method to reload the configuration
 * file. You can also use it as normal {@link YamlConfiguration}.
 *
 * @author Murcis
 */
@Getter
public class FileConfigSpigot extends YamlConfiguration implements FileConfig<Configuration> {
    protected final @NotNull BasePlugin plugin;
    protected final @NotNull String path;
    protected final @NotNull File file;


    /**
     * Creates a new instance of {@link FileConfigSpigot}.
     * <p>
     * This constructor also creates the file if it doesn't exist and
     * loads the configuration.
     * </p>
     *
     * @param plugin The plugin that this config belongs to.
     * @param path   The path to the file. Must be a relative path to .yml file.
     */
    public FileConfigSpigot(@NotNull BasePlugin plugin, @NotNull String path) {
        this.plugin = plugin;
        this.path = path;
        this.file = new File(plugin.getDataFolder(), path);
        this.createFile();
        this.reload();
    }

    /**
     * Creates a new instance of {@link FileConfigSpigot}.
     * <p>
     * This constructor also creates the file if it doesn't exist and
     * loads the configuration.
     * </p>
     *
     * @param plugin The plugin that this config belongs to.
     * @param file   The file to load. Must be a .yml file.
     */
    public FileConfigSpigot(@NotNull BasePlugin plugin, @NotNull File file) {
        this.plugin = plugin;
        this.path = file.getName();
        this.file = file;
        this.createFile();
        this.reload();
    }

    /**
     * Creates a new instance of {@link FileConfigSpigot} using a pre-existing YamlConfiguration.
     * <p>
     * This constructor also creates the file if it doesn't exist and
     * loads the configuration.
     * </p>
     *
     * @param plugin The plugin that this config belongs to.
     * @param file The file to load. Must be a .yml file.
     * @param configuration The pre-existing YamlConfiguration.
     */
    public FileConfigSpigot(@NotNull BasePlugin plugin, File file, @NotNull YamlConfiguration configuration) {
        this.plugin = plugin;
        this.path = file.getName();
        this.file = file;
        this.createFile();

        // Copy values from the provided configuration to this instance
        this.options().copyDefaults(true);
        this.setDefaults(configuration);
        this.addDefaults(configuration);
        this.reload();
    }

    /**
     * Creates the file if it doesn't exist. If the file is also a resource,
     * it will be copied as the default configuration.
     */
    public void createFile() {
        if (!file.exists()) {
            plugin.getDataFolder().mkdirs();

            // If file isn't a resource, create from scratch
            if (plugin.getResource(this.path) == null) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                plugin.saveResource(this.path, false);
            }
        }
    }

    /**
     * Saves this configuration to the file.
     */
    public void saveData() {
        try {
            this.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reloads the configuration from the file.
     */
    public void reload() {
        try {
            this.load(file);
        } catch (IOException e) {
            // Manejar excepción de tipo IOException
            Common.log(Level.SEVERE, "Error de E/S al recargar la configuraci?n: %s", e.getMessage());
        } catch (InvalidConfigurationException e) {
            // Manejar excepción de tipo InvalidConfigurationException
            Common.log(Level.SEVERE, "Error de configuraci?n inv?lida al recargar la configuraci?n: %s", e.getMessage());
        }
    }

    /**
     * Delete the file.
     */
    public void delete() {
        if (file.exists()) {
            if (!file.delete()) {
                Common.log(Level.WARNING, "Cannot delete existing file '%s' (Permission issue?)", file.getName());
            }
        }
    }

    @Override
    public Configuration loadFile(File file) throws IOException {
        try {
            this.load(file);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public String getString(String path) {
        return super.getString(path);
    }

    @Override
    public String getString(String path, String defaultValue) {
        return super.getString(path, defaultValue);
    }

    @Override
    public int getInt(String path) {
        return super.getInt(path);
    }

    @Override
    public int getInt(String path, int defaultValue) {
        return super.getInt(path, defaultValue);
    }

    @Override
    public boolean getBoolean(String path) {
        return super.getBoolean(path);
    }

    @Override
    public boolean getBoolean(String path, boolean defaultValue) {
        return super.getBoolean(path, defaultValue);
    }

    @Override
    public double getDouble(String path) {
        return super.getDouble(path);
    }

    @Override
    public double getDouble(String path, double defaultValue) {
        return super.getDouble(path, defaultValue);
    }

    @Override
    public long getLong(String path) {
        return super.getLong(path);
    }

    @Override
    public long getLong(String path, long defaultValue) {
        return super.getLong(path, defaultValue);
    }

    @Override
    public List<?> getList(String path) {
        return super.getList(path);
    }

    @Override
    public List<?> getList(String path, List<?> defaultValue) {
        return super.getList(path, defaultValue);
    }

    @Override
    public List<String> getStringList(String path) {
        return super.getStringList(path);
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        return super.getIntegerList(path);
    }

    @Override
    public List<Double> getDoubleList(String path) {
        return super.getDoubleList(path);
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        return super.getBooleanList(path);
    }

    @Override
    public List<Long> getLongList(String path) {
        return super.getLongList(path);
    }

    @Override
    public FileConfig<ConfigurationSection> getSection(String path) {
        return (FileConfig<ConfigurationSection>) super.getConfigurationSection(path);
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        return super.getKeys(deep);
    }

    @Override
    public void setSectionConfig(String path,Map<String, Object> configMap) {
        for (Map.Entry<String, Object> entry : configMap.entrySet()) {
            this.set(path +"."+entry.getKey(), entry.getValue());
        }
    }

}
