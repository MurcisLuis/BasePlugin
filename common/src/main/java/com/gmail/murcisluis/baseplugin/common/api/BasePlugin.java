package com.gmail.murcisluis.baseplugin.common.api;

import java.io.File;
import java.io.InputStream;

public interface BasePlugin {
    public File getDataFolder();

    public InputStream getResource(String filename);

    public void saveResource(String resourcePath, boolean replace);

    public Description getPluginDescription();


    public Object getServer();
}
