package com.gmail.murcisluis.base.examples.common;

import com.gmail.murcisluis.base.common.api.config.ConfigurationManager;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;

/**
 * Ejemplo simple de cómo usar ConfigurationManager directamente
 * en lugar de la clase Settings (deprecated).
 * 
 * Este ejemplo muestra:
 * - Cómo obtener una instancia de ConfigurationManager
 * - Cómo cargar/crear archivos de configuración
 * - Cómo leer y escribir valores
 * - Cómo guardar los cambios
 */
public class SimpleConfigExample {
    
    private final ConfigurationManager configManager;
    
    public SimpleConfigExample() {
        // Obtener la instancia singleton de ConfigurationManager
        this.configManager = ConfigurationManager.getInstance();
    }
    
    /**
     * Ejemplo básico de uso de configuración
     */
    public void basicConfigExample() {
        // Cargar o crear el archivo config.yml
        FileConfig<?> config = configManager.getConfig("config.yml");
        
        // Establecer valores por defecto si no existen
        setupDefaultValues(config);
        
        // Leer valores de configuración
        String serverName = (String) config.get("server.name", "Mi Servidor");
        int maxPlayers = (Integer) config.get("server.max-players", 20);
        boolean maintenanceMode = (Boolean) config.get("server.maintenance", false);
        
        System.out.println("=== Configuración del Servidor ===");
        System.out.println("Nombre: " + serverName);
        System.out.println("Jugadores máximos: " + maxPlayers);
        System.out.println("Modo mantenimiento: " + maintenanceMode);
        
        // Guardar los cambios
        config.saveData();
    }
    
    /**
     * Ejemplo de configuración personalizada para un plugin
     */
    public void customPluginConfig() {
        // Crear un archivo de configuración específico para tu plugin
        FileConfig<?> pluginConfig = configManager.getConfig("miplugin.yml");
        
        // Configurar valores específicos del plugin
        pluginConfig.set("plugin.enabled", true);
        pluginConfig.set("plugin.version", "1.0.0");
        pluginConfig.set("features.chat.enabled", true);
        pluginConfig.set("features.chat.prefix", "[MiPlugin]");
        pluginConfig.set("features.economy.starting-money", 1000.0);
        
        // Configurar una lista de mundos permitidos
        pluginConfig.set("allowed-worlds", java.util.Arrays.asList("world", "world_nether", "world_the_end"));
        
        // Guardar la configuración
        pluginConfig.saveData();
        
        System.out.println("Configuración del plugin guardada en miplugin.yml");
    }
    
    /**
     * Ejemplo de recargar configuración
     */
    public void reloadConfigExample() {
        System.out.println("Recargando configuración...");
        
        // Recargar un archivo específico
        boolean success = configManager.reloadConfig("config.yml");
        
        if (success) {
            System.out.println("Configuración recargada exitosamente");
        } else {
            System.out.println("Error al recargar la configuración");
        }
    }
    
    /**
     * Configurar valores por defecto
     */
    private void setupDefaultValues(FileConfig<?> config) {
        // Solo establecer si no existe el valor
        if (!config.contains("server.name")) {
            config.set("server.name", "Mi Servidor Minecraft");
        }
        
        if (!config.contains("server.max-players")) {
            config.set("server.max-players", 20);
        }
        
        if (!config.contains("server.maintenance")) {
            config.set("server.maintenance", false);
        }
        
        if (!config.contains("server.motd")) {
            config.set("server.motd", "¡Bienvenido a mi servidor!");
        }
        
        // Configuración de características
        if (!config.contains("features.chat.enabled")) {
            config.set("features.chat.enabled", true);
        }
        
        if (!config.contains("features.chat.format")) {
            config.set("features.chat.format", "<%player%> %message%");
        }
    }
    
    /**
     * Método principal para demostrar el uso
     */
    public static void demonstrateUsage() {
        SimpleConfigExample example = new SimpleConfigExample();
        
        System.out.println("=== Ejemplo Simple de ConfigurationManager ===");
        example.basicConfigExample();
        
        System.out.println("\n=== Configuración Personalizada ===");
        example.customPluginConfig();
        
        System.out.println("\n=== Recarga de Configuración ===");
        example.reloadConfigExample();
    }
}