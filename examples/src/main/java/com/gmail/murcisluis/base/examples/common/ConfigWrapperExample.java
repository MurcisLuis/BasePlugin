package com.gmail.murcisluis.base.examples.common;

/**
 * Ejemplo de uso de ConfigWrapper que demuestra las ventajas
 * de tener los valores de configuración como atributos directos.
 * 
 * VENTAJAS:
 * ✅ Acceso directo a propiedades (config.serverName vs config.get("server.name"))
 * ✅ Autocompletado del IDE
 * ✅ Verificación de tipos en tiempo de compilación
 * ✅ Mejor rendimiento (sin parsing repetido)
 * ✅ Código más limpio y legible
 * ✅ Refactoring seguro
 */
public class ConfigWrapperExample {
    
    private ConfigWrapper config;
    
    public ConfigWrapperExample() {
        // Cargar configuración una sola vez
        this.config = new ConfigWrapper();
    }
    
    /**
     * Ejemplo de configuración del servidor
     */
    public void setupServer() {
        System.out.println("=== CONFIGURACIÓN DEL SERVIDOR ===");
        
        // ✅ NUEVO: Acceso directo a propiedades
        System.out.println("Nombre del servidor: " + config.getServerName());
        System.out.println("Jugadores máximos: " + config.getMaxPlayers());
        System.out.println("Puerto: " + config.getServerPort());
        System.out.println("MOTD: " + config.getMotd());
        
        // Verificar modo mantenimiento
        if (config.isMaintenanceMode()) {
            System.out.println("⚠️ SERVIDOR EN MANTENIMIENTO");
            return;
        }
        
        System.out.println("✅ Servidor listo para recibir jugadores");
    }
    
    /**
     * Ejemplo de configuración de características
     */
    public void setupFeatures() {
        System.out.println("\n=== CONFIGURACIÓN DE CARACTERÍSTICAS ===");
        
        // Chat
        if (config.isChatEnabled()) {
            System.out.println("💬 Chat habilitado");
            System.out.println("   Formato: " + config.getChatFormat());
            System.out.println("   Prefijo: " + config.getChatPrefix());
            
            if (config.isWordFilterEnabled()) {
                System.out.println("   🚫 Filtro de palabras: " + config.getBlockedWords().size() + " palabras bloqueadas");
            }
        }
        
        // Economía
        if (config.isEconomyEnabled()) {
            System.out.println("💰 Economía habilitada");
            System.out.println("   Dinero inicial: " + config.getCurrencySymbol() + config.getStartingMoney());
            System.out.println("   Tiendas: " + (config.isShopsEnabled() ? "Habilitadas" : "Deshabilitadas"));
            System.out.println("   Tasa de impuestos: " + (config.getTaxRate() * 100) + "%");
        }
        
        // Protección
        if (config.isProtectionEnabled()) {
            System.out.println("🛡️ Protección habilitada");
            System.out.println("   Protección por defecto: " + config.isDefaultProtection());
            System.out.println("   Máximo claims por jugador: " + config.getMaxClaimsPerPlayer());
        }
    }
    
    /**
     * Ejemplo de gestión de mundos
     */
    public void manageWorlds() {
        System.out.println("\n=== GESTIÓN DE MUNDOS ===");
        
        System.out.println("Mundos permitidos:");
        for (String world : config.getAllowedWorlds()) {
            System.out.println("  - " + world);
        }
        
        // Verificar configuración específica del mundo principal
        System.out.println("\nConfiguración del mundo principal:");
        System.out.println("  PvP: " + (config.isWorldPvp() ? "Habilitado" : "Deshabilitado"));
        System.out.println("  Dificultad: " + config.getWorldDifficulty());
        System.out.println("  Protección del spawn: " + config.getSpawnProtection() + " bloques");
    }
    
    /**
     * Ejemplo de sistema de mensajes
     */
    public void setupMessages() {
        System.out.println("\n=== SISTEMA DE MENSAJES ===");
        
        // Mensajes de bienvenida
        if (config.isWelcomeEnabled()) {
            String welcomeMsg = config.getWelcomeMessage().replace("%player%", "EjemploJugador");
            System.out.println("Bienvenida: " + config.getFormattedMessage(welcomeMsg));
        }
        
        // Mensajes de despedida
        if (config.isGoodbyeEnabled()) {
            String goodbyeMsg = config.getGoodbyeMessage().replace("%player%", "EjemploJugador");
            System.out.println("Despedida: " + goodbyeMsg);
        }
        
        // Mensajes de error
        System.out.println("\nMensajes de error:");
        System.out.println("  Sin permisos: " + config.getFormattedMessage(config.getNoPermissionMessage()));
        System.out.println("  Jugador no encontrado: " + config.getFormattedMessage(config.getPlayerNotFoundMessage()));
        System.out.println("  Comando inválido: " + config.getFormattedMessage(config.getInvalidCommandMessage()));
    }
    
    /**
     * Ejemplo de gestión de comandos
     */
    public void manageCommands() {
        System.out.println("\n=== GESTIÓN DE COMANDOS ===");
        
        System.out.println("Comandos habilitados:");
        for (String command : config.getEnabledCommands()) {
            int cooldown = config.getCommandCooldown(command);
            System.out.println("  - /" + command + (cooldown > 0 ? " (cooldown: " + cooldown + "s)" : ""));
        }
    }
    
    /**
     * Ejemplo de configuración de base de datos
     */
    public void setupDatabase() {
        System.out.println("\n=== CONFIGURACIÓN DE BASE DE DATOS ===");
        
        System.out.println("Tipo: " + config.getDatabaseType().toUpperCase());
        
        if (config.isExternalDatabase()) {
            System.out.println("Host: " + config.getDatabaseHost() + ":" + config.getDatabasePort());
            System.out.println("Base de datos: " + config.getDatabaseName());
            System.out.println("Usuario: " + config.getDatabaseUsername());
            System.out.println("SSL: " + (config.isDatabaseSsl() ? "Habilitado" : "Deshabilitado"));
        } else {
            System.out.println("Archivo: " + config.getDatabaseFile());
        }
        
        System.out.println("URL de conexión: " + config.getDatabaseUrl());
    }
    
    /**
     * Ejemplo de configuración avanzada
     */
    public void setupAdvanced() {
        System.out.println("\n=== CONFIGURACIÓN AVANZADA ===");
        
        System.out.println("Verificar actualizaciones: " + config.isCheckUpdates());
        System.out.println("Métricas: " + config.isMetrics());
        System.out.println("Modo debug: " + config.isDebug());
        System.out.println("Pool de hilos: " + config.getThreadPoolSize());
        System.out.println("Timeout async: " + config.getAsyncTimeout() + "ms");
        
        // Configuración de logs
        System.out.println("\nLogs:");
        System.out.println("  Nivel: " + config.getLogLevel());
        System.out.println("  Archivo: " + config.isFileLogging());
        System.out.println("  Rotación diaria: " + config.isDailyRotation());
        System.out.println("  Mantener por: " + config.getKeepDays() + " días");
    }
    
    /**
     * Ejemplo de validación de configuración
     */
    public void validateConfiguration() {
        System.out.println("\n=== VALIDACIÓN DE CONFIGURACIÓN ===");
        
        boolean valid = true;
        
        // Validar servidor
        if (config.getMaxPlayers() <= 0) {
            System.out.println("❌ Error: max-players debe ser mayor que 0");
            valid = false;
        }
        
        if (config.getServerName().trim().isEmpty()) {
            System.out.println("❌ Error: server.name no puede estar vacío");
            valid = false;
        }
        
        // Validar economía
        if (config.isEconomyEnabled() && config.getStartingMoney() < 0) {
            System.out.println("❌ Error: starting-money no puede ser negativo");
            valid = false;
        }
        
        // Validar mundos
        if (config.getAllowedWorlds().isEmpty()) {
            System.out.println("⚠️ Advertencia: No hay mundos permitidos configurados");
        }
        
        // Validar base de datos
        if (config.isExternalDatabase()) {
            if (config.getDatabaseHost().trim().isEmpty()) {
                System.out.println("❌ Error: database.host no puede estar vacío para bases de datos externas");
                valid = false;
            }
            if (config.getDatabaseUsername().trim().isEmpty()) {
                System.out.println("❌ Error: database.username no puede estar vacío para bases de datos externas");
                valid = false;
            }
        }
        
        if (valid) {
            System.out.println("✅ Configuración válida");
        } else {
            System.out.println("❌ Se encontraron errores en la configuración");
        }
    }
    
    /**
     * Ejemplo de recarga de configuración
     */
    public void reloadConfiguration() {
        System.out.println("\n=== RECARGA DE CONFIGURACIÓN ===");
        
        System.out.println("Configuración actual: " + config.getServerName());
        
        // Recargar configuración
        this.config = config.reload();
        
        System.out.println("Configuración recargada: " + config.getServerName());
        System.out.println("✅ Configuración actualizada exitosamente");
    }
    
    /**
     * Comparación: Método antiguo vs nuevo
     */
    public void comparisonExample() {
        System.out.println("\n=== COMPARACIÓN: ANTIGUO VS NUEVO ===");
        
        // ❌ MÉTODO ANTIGUO (con FileConfig directamente)
        /*
        FileConfig oldConfig = ConfigurationManager.getInstance().getConfig("config.yml");
        String serverName = (String) oldConfig.get("server.name", "Default");
        int maxPlayers = (Integer) oldConfig.get("server.max-players", 20);
        boolean maintenance = (Boolean) oldConfig.get("server.maintenance", false);
        */
        
        // ✅ MÉTODO NUEVO (con ConfigWrapper)
        String serverName = config.getServerName();  // Autocompletado + verificación de tipos
        int maxPlayers = config.getMaxPlayers();     // Sin casting
        boolean maintenance = config.isMaintenanceMode(); // Nombres más descriptivos
        
        System.out.println("Servidor: " + serverName);
        System.out.println("Jugadores: " + maxPlayers);
        System.out.println("Mantenimiento: " + maintenance);
        
        System.out.println("\n✅ Ventajas del nuevo método:");
        System.out.println("  - Sin casting manual");
        System.out.println("  - Autocompletado del IDE");
        System.out.println("  - Verificación de tipos en compilación");
        System.out.println("  - Mejor rendimiento (sin parsing repetido)");
        System.out.println("  - Código más limpio y legible");
        System.out.println("  - Refactoring seguro");
    }
    
    /**
     * Método principal para demostrar el uso
     */
    public static void main(String[] args) {
        ConfigWrapperExample example = new ConfigWrapperExample();
        
        // Mostrar resumen
        example.config.printConfigSummary();
        
        // Ejecutar ejemplos
        example.setupServer();
        example.setupFeatures();
        example.manageWorlds();
        example.setupMessages();
        example.manageCommands();
        example.setupDatabase();
        example.setupAdvanced();
        example.validateConfiguration();
        example.comparisonExample();
        
        // Ejemplo de recarga
        example.reloadConfiguration();
    }
}