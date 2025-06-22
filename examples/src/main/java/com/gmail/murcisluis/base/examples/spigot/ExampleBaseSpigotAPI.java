package com.gmail.murcisluis.base.examples.spigot;

import com.gmail.murcisluis.base.spigot.api.BaseSpigot;
import com.gmail.murcisluis.base.common.api.BaseAPI;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import org.jetbrains.annotations.NotNull;

/**
 * API personalizada para Spigot que utiliza ExampleBaseSpigotImpl
 * Esta clase reemplaza la implementación por defecto con nuestra versión extendida
 */
public final class ExampleBaseSpigotAPI implements BaseAPI {

    private static BaseSpigot implementation;
    private static boolean enabled = false;

    @Override
    public void onLoad(@NotNull BasePlugin plugin) {
        if (implementation != null) return;

        // Usar nuestra implementación personalizada en lugar de BaseSpigotImpl
        implementation = new ExampleBaseSpigotImpl(plugin);
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
            throw new IllegalStateException("No hay una instancia en ejecución de ExampleBaseSpigotAPI, habilítela primero.");
        }
        return implementation;
    }
    
    /**
     * Obtiene la implementación específica con métodos extendidos
     * @return ExampleBaseSpigotImpl con funcionalidades adicionales
     */
    public static ExampleBaseSpigotImpl getExampleImplementation() {
        if (implementation instanceof ExampleBaseSpigotImpl) {
            return (ExampleBaseSpigotImpl) implementation;
        }
        throw new IllegalStateException("La implementación no es del tipo ExampleBaseSpigotImpl");
    }
}