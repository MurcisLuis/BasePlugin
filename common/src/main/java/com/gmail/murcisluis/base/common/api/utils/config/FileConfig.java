package com.gmail.murcisluis.base.common.api.utils.config;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


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
    abstract Object get(String path,Object defaultvalue);
    abstract void set(String path,Object memo);

    String getString(String path);
    String getString(String path, String defaultValue);

    int getInt(String path);
    int getInt(String path, int defaultValue);

    boolean getBoolean(String path);
    boolean getBoolean(String path, boolean defaultValue);

    double getDouble(String path);
    double getDouble(String path, double defaultValue);

    long getLong(String path);
    long getLong(String path, long defaultValue);

    List<?> getList(String path);
    List<?> getList(String path, List<?> defaultValue);

    List<String> getStringList(String path);

    List<Integer> getIntegerList(String path);

    List<Double> getDoubleList(String path);

    List<Boolean> getBooleanList(String path);

    List<Long> getLongList(String path);

    FileConfig<?> getSection(String path);

    void setSectionConfig(String path,Map<String, Object> configMap);

}