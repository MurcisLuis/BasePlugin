package com.gmail.murcisluis.base.common.api;

import java.io.File;


public class Description {

    /**
     * Friendly name of the plugin.
     */
    private String name;
    /**
     * Plugin main class. Needs to extend {@link BasePlugin}.
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
