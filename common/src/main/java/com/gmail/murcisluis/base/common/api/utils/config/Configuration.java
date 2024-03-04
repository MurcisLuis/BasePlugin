package com.gmail.murcisluis.base.common.api.utils.config;

import com.google.common.collect.Sets;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public interface Configuration {

    void loadConfig() throws IOException;

    File loadFile();

    void saveData();

    void save(File file) throws IOException;

    void reload();

    void createData();

    void delete();

    Set<String> getSectionKeys(String path);

    Object getOrDefault(String path, Object defaultValue);



}