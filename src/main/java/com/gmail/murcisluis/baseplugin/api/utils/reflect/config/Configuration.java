package com.gmail.murcisluis.baseplugin.api.utils.reflect.config;

import com.google.common.collect.Sets;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Configuration extends YamlConfiguration {

    private final String fileName;
    private final JavaPlugin plugin;
    private final File dataFolder;
    private File file;

    public Configuration(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.fileName = name.endsWith(".yml") ? name : name + ".yml";
        this.dataFolder = plugin.getDataFolder();

        loadFile();
        createData();

        try {
            loadConfig();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Configuration(JavaPlugin plugin, File file) {
        this.plugin = plugin;
        this.file = file;
        this.fileName = file.getName();
        this.dataFolder = plugin.getDataFolder();

        createData();

        try {
            loadConfig();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Configuration(JavaPlugin plugin, File dataFolder, String name) {
        this.plugin = plugin;
        this.fileName = name.endsWith(".yml") ? name : name + ".yml";
        this.dataFolder = dataFolder;

        loadFile();
        createData();

        try {
            loadConfig();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() throws IOException, InvalidConfigurationException {
        this.load(file);
    }

    public File loadFile() {
        file = new File(dataFolder, fileName);
        return file;
    }

    public void saveData() {
        if (file == null) {
            loadFile();
        }

        try {
            this.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Attempting to fix error...");
            createData();
            saveData();
        }
    }

    @Override
    public void save(File file) throws IOException {
        super.save(file);
    }

    public void reload() {
        try {
            this.loadConfig();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void createData() {
        if (!file.exists()) {
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }

            // If file isn't a resource, create from scratch
            if (plugin.getResource(fileName) == null) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                plugin.saveResource(fileName, false);
            }
        }
    }

    public void delete() {
        if (file.exists()) {
            file.delete();
        }
    }

    public Set<String> getSectionKeys(String path) {
        if (!contains(path)) {
            return Sets.newHashSet();
        }
        return getConfigurationSection(path).getKeys(false);
    }

    public Object getOrDefault(String path, Object defaultValue) {
        if (!contains(path)) {
            set(path, defaultValue);
            return defaultValue;
        }
        return get(path);
    }



}