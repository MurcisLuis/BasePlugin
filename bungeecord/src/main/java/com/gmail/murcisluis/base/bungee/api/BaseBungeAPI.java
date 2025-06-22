package com.gmail.murcisluis.base.bungee.api;

import com.gmail.murcisluis.base.common.api.BasePlugin;
import com.gmail.murcisluis.base.common.api.BaseAPI;
import org.jetbrains.annotations.NotNull;

public final class BaseBungeAPI implements BaseAPI {

    private static BaseBungee implementation;
    private static boolean enabled = false;

    @Override
    public void onLoad(@NotNull BasePlugin plugin){
        if (implementation != null) return;

        implementation = new BaseBungeeImpl(plugin);
        implementation.load();
    }
    @Override
    public void onEnable(){
        if (implementation == null) return;
        enabled = true;
        implementation.enable();
    }
    @Override
    public void onDisable(){
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
    public BaseBungee get() {
        if (implementation == null) {
            throw new IllegalStateException("There is no running instance of BaseBungeAPI, enabled it first.");
        }
        return implementation;
    }
}
