package com.gmail.murcisluis.base.common.api.utils.config;

import java.io.File;
import java.io.IOException;


public interface FileConfig<T>{

    /**
     * Creates the file if it doesn't exist. If the file is also a resource,
     * it will be copied as the default configuration.
     */
    abstract void createFile();

    /**
     * Saves this configuration to the file.
     */
    abstract void saveData();

    /**
     * Reloads the configuration from the file.
     */
    abstract void reload();

    /**
     * Delete the file.
     */
    abstract void delete();

    abstract void save(File file) throws IOException;

    abstract T loadFile(File file) throws IOException;

    abstract File getFile();

    abstract boolean contains(String path);
    abstract Object get(String path);
    abstract void set(String path,Object memo);



}