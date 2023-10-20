package com.gmail.murcisluis.base.common.api.utils.config;

import java.io.File;
import java.io.InputStreamReader;

public abstract class ConfigAdapter {
    public abstract FileConfig<?> loadConfiguration(File file, InputStreamReader isr);

    public abstract FileConfig<?> loadConfiguration(File file);
}
