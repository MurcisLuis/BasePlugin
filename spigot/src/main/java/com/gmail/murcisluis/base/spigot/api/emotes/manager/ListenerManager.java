package com.gmail.murcisluis.base.spigot.api.emotes.manager;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.spigot.api.emotes.listener.DeadListener;
import com.gmail.murcisluis.base.spigot.api.emotes.listener.InventoryListener;
import com.gmail.murcisluis.base.spigot.plugin.BaseSpigotPlugin;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ListenerManager {


    private final Map<Class<? extends Listener>, Listener> listeners = new HashMap<>();

    public ListenerManager() {
        register(new DeadListener());
        register(new InventoryListener());
        enable();
    }

    private void register(Listener listener) {
        listeners.put(listener.getClass(), listener);
    }

    public void enable() {
        for (Listener listener : listeners.values()) {
            Bukkit.getPluginManager().registerEvents(listener, (BaseSpigotPlugin) BaseAPIFactory.getPlugin());
        }
    }

    public void disable() {
        HandlerList.unregisterAll((BaseSpigotPlugin)BaseAPIFactory.getPlugin());
    }

    @SuppressWarnings("unchecked")
    public <T extends Listener> T getListener(Class<T> type) {
        return (T) listeners.get(type);
    }
}
