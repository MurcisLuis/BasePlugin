package com.gmail.murcisluis.base.common.api;

import com.gmail.murcisluis.base.common.api.utils.config.ConfigAdapter;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;

public interface BasePlugin {
    public File getDataFolder();

    public InputStream getResource(String filename);

    void saveResource(String resourcePath, boolean replace);

    Description getPluginDescription();


    Object getServerInstance();

    String getVersionServer();

    Logger getLogger();

    ConfigAdapter getConfigAdapter();
}
