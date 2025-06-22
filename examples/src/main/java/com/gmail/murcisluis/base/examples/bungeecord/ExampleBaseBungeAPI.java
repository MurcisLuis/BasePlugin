package com.gmail.murcisluis.base.examples.bungeecord;

import com.gmail.murcisluis.base.bungee.api.BaseBungee;
import com.gmail.murcisluis.base.common.api.BaseAPI;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import org.jetbrains.annotations.NotNull;

/**
 * API personalizada para Bungee que utiliza ExampleBaseBungeeImpl
 * Esta clase reemplaza la implementación por defecto con nuestra versión extendida
 */
public final class ExampleBaseBungeAPI implements BaseAPI {

    private static BaseBungee implementation;
    private static boolean enabled = false;

    @Override
    public void onLoad(@NotNull BasePlugin plugin){
        if (implementation != null) return;

        // Usar nuestra implementación personalizada en lugar de BaseBungeeImpl
        implementation = new ExampleBaseBungeeImpl(plugin);
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
            throw new IllegalStateException("No hay una instancia en ejecución de ExampleBaseBungeAPI, habilítela primero.");
        }
        return implementation;
    }
    
    /**
     * Obtiene la implementación específica con métodos extendidos
     * @return ExampleBaseBungeeImpl con funcionalidades adicionales
     */
    public static ExampleBaseBungeeImpl getExampleImplementation() {
        if (implementation instanceof ExampleBaseBungeeImpl) {
            return (ExampleBaseBungeeImpl) implementation;
        }
        throw new IllegalStateException("La implementación no es del tipo ExampleBaseBungeeImpl");
    }
}