package com.gmail.murcisluis.baseplugin.common.api;

import org.jetbrains.annotations.NotNull;



public interface BaseAPI {

    void onLoad(@NotNull BasePlugin plugin);

    void onEnable();

    void onDisable();
    boolean isRunning();

    Base get();
}
