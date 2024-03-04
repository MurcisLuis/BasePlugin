package com.gmail.murcisluis.base.common.api.utils.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class SectionConfigValue<T> extends ConfigValue<T>{
    public SectionConfigValue(FileConfig<?> config, boolean setDefault, String path, T defaultValue) {
        super(config, setDefault, path, defaultValue);
    }

    public SectionConfigValue(FileConfig<?> config, String path, T defaultValue) {
        super(config, path, defaultValue);
    }
    @Override
    @SuppressWarnings("unchecked")
    public void updateValue() {
        if (!config.contains(path)) {
            value = defaultValue;
            if (setDefault) {
                if (value instanceof Map<?,?> section){
                    Map<String, Object> sectionMap = new HashMap<>();
                    for (Map.Entry<?, ?> entry : section.entrySet()) {
                        sectionMap.put((String) entry.getKey(), entry.getValue());
                    }
                    Bukkit.getLogger().log(Level.WARNING,  path+String.valueOf(sectionMap));
                    config.setSectionConfig(path,sectionMap);
                    config.saveData();
                }
            }
        } else {
            Object o = config.get(path);
            try {
                if (o instanceof ConfigurationSection section) {
                    Map<String, Object> sectionMap = section.getValues(false);
                    value = (T) sectionMap;
                } else {
                    value = (T) o;
                }
            } catch (Exception e) {
                e.printStackTrace();
                value = defaultValue;
            }
        }
    }
}
