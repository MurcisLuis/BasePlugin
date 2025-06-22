package com.gmail.murcisluis.base.spigot.api;

import com.gmail.murcisluis.base.common.api.BaseAPI;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

public final class BaseSpigotAPI<T extends BaseSpigot> implements BaseAPI {

    private static BaseSpigot implementation;
    private static boolean enabled = false;
    private final Class<T> implementationClass;
    
    /**
     * Constructor por defecto que usa BaseSpigotImpl
     */
    @SuppressWarnings("unchecked")
    public BaseSpigotAPI() {
        this.implementationClass = (Class<T>) BaseSpigotImpl.class;
    }
    
    /**
     * Constructor que permite especificar una implementación personalizada
     * @param implementationClass La clase de implementación a usar
     */
    public BaseSpigotAPI(Class<T> implementationClass) {
        this.implementationClass = implementationClass;
    }
    
    @Override
    public void onLoad(@NotNull BasePlugin plugin) {
        if (implementation != null) return;

        try {
            // Crear instancia usando reflection
            implementation = implementationClass.getDeclaredConstructor(BasePlugin.class).newInstance(plugin);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo crear la implementación: " + implementationClass.getSimpleName(), e);
        }
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
    @SuppressWarnings("unchecked")
    public T get() {
        if (implementation == null) {
            throw new IllegalStateException("No hay una instancia en ejecución de BaseSpigotAPI, habilítela primero.");
        }
        return (T) implementation;
    }
}