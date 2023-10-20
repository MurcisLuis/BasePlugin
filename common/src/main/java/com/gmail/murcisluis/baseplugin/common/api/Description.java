package com.gmail.murcisluis.baseplugin.common.api;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginDescription;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Description {

    /**
     * Friendly name of the plugin.
     */
    private String name;
    /**
     * Plugin main class. Needs to extend {@link Plugin}.
     */
    private String main;
    /**
     * Plugin version.
     */
    private String version;
    /**
     * Plugin author.
     */
    private String author;
    /**
     * File we were loaded from.
     */
    private File file = null;
    /**
     * Optional description.
     */
    private String description = null;

    public Description(String name, String main, String version, String author, File file, String description) {
        this.name = name;
        this.main = main;
        this.version = version;
        this.author = author;
        this.file = file;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getMain() {
        return main;
    }

    public String getVersion() {
        return version;
    }

    public String getAuthor() {
        return author;
    }

    public File getFile() {
        return file;
    }

    public String getDescription() {
        return description;
    }
}
