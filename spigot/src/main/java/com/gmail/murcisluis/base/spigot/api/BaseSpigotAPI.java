package com.gmail.murcisluis.base.spigot.api;

import com.gmail.murcisluis.base.common.api.BaseAPI;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

public final class BaseSpigotAPI implements BaseAPI {

    private static BaseSpigot implementation;
    private static boolean enabled = false;
    @Override
    public void onLoad(@NotNull BasePlugin plugin) {
        if (implementation != null) return;

        implementation = new BaseSpigot(plugin);
        implementation.load();
    }
    @Override
    public void onEnable() {
        if (implementation == null) return;
        enabled = true;
        implementation.enable();
    }
    @Override
    public void onDisable() {
        if (implementation == null) return;
        implementation.disable();
        implementation = null;
        enabled = false;
    }
    @Override
    public boolean isRunning() {
        return implementation != null && enabled;
    }
    @Override
    public BaseSpigot get() {
        if (implementation == null) {
            throw new IllegalStateException("No hay una instancia en ejecuci?n de BaseSpigotAPI, habil?tela primero.");
        }
        return implementation;
    }
}