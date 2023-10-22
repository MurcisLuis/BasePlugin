package com.gmail.murcisluis.base.spigot.api.utils.config;

import com.gmail.murcisluis.base.common.api.BasePlugin;
import com.gmail.murcisluis.base.common.api.utils.Common;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import lombok.Getter;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
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
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
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


}
