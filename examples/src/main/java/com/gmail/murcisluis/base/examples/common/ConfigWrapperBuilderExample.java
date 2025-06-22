package com.gmail.murcisluis.base.examples.common;

import java.util.Arrays;
import java.util.List;

/**
 * Ejemplo de uso del ConfigWrapperBuilder que demuestra las ventajas
 * de tener valores directamente accesibles como atributos de clase.
 * 
 * Ventajas del patrón Builder + atributos directos:
 * 1. Configuración programática fácil
 * 2. Testing simplificado
 * 3. Valores inmutables después de la construcción
 * 4. Acceso directo sin llamadas a métodos
 * 5. Autocompletado del IDE
 * 6. Verificación de tipos en tiempo de compilación
 */
public class ConfigWrapperBuilderExample {
    
    public static void main(String[] args) {
        // ==================== EJEMPLO 1: CONFIGURACIÓN BÁSICA ====================
        System.out.println("=== Ejemplo 1: Configuración Básica ===");
        
        ConfigWrapper basicConfig = ConfigWrapperBuilder.create()
            .serverName("Servidor de Prueba")
            .maxPlayers(50)
            .economyEnabled(true)
            .startingMoney(2500.0)
            .build();
        
        // Acceso directo a los atributos (sin llamadas a métodos)
        System.out.println("Servidor: " + basicConfig.getServerName());
        System.out.println("Jugadores máximos: " + basicConfig.getMaxPlayers());
        System.out.println("Economía habilitada: " + basicConfig.isEconomyEnabled());
        System.out.println("Dinero inicial: " + basicConfig.getCurrencySymbol() + basicConfig.getStartingMoney());
        
        // ==================== EJEMPLO 2: CONFIGURACIÓN PARA TESTING ====================
        System.out.println("\n=== Ejemplo 2: Configuración para Testing ===");
        
        ConfigWrapper testConfig = ConfigWrapperBuilder.create()
            .serverName("Test Server")
            .maxPlayers(5)
            .maintenanceMode(true)
            .economyEnabled(false)
            .chatEnabled(false)
            .protectionEnabled(false)
            .logging("DEBUG", false, false, 1)
            .advanced(false, false, true) // Sin updates, sin metrics, con debug
            .build();
        
        System.out.println("Configuración de testing creada:");
        System.out.println("- Modo mantenimiento: " + testConfig.isMaintenanceMode());
        System.out.println("- Debug habilitado: " + testConfig.isDebug());
        System.out.println("- Economía deshabilitada: " + !testConfig.isEconomyEnabled());
        
        // ==================== EJEMPLO 3: CONFIGURACIÓN DE PRODUCCIÓN ====================
        System.out.println("\n=== Ejemplo 3: Configuración de Producción ===");
        
        ConfigWrapper prodConfig = ConfigWrapperBuilder.create()
            .serverName("Servidor Principal")
            .maxPlayers(100)
            .motd("&6¡Bienvenido al mejor servidor!")
            .economyEnabled(true)
            .startingMoney(1000.0)
            .currencySymbol("€")
            .shops(true, 0.08) // Tiendas con 8% de impuestos
            .protection(true, true, 10)
            .worldSettings(true, "hard", 32) // PvP, dificultad difícil, protección spawn 32
            .mysqlDatabase("localhost", 3306, "minecraft_prod", "admin", "secretpass", true)
            .logging("INFO", true, true, 30) // Logs por 30 días
            .performance(8, 10000) // 8 threads, timeout 10s
            .build();
        
        System.out.println("Configuración de producción:");
        System.out.println("- Servidor: " + prodConfig.getServerName());
        System.out.println("- Capacidad: " + prodConfig.getMaxPlayers() + " jugadores");
        System.out.println("- Base de datos: " + prodConfig.getDatabaseType().toUpperCase());
        System.out.println("- Impuesto tiendas: " + (prodConfig.getTaxRate() * 100) + "%");
        
        // ==================== EJEMPLO 4: CONFIGURACIÓN DESDE ARCHIVO ====================
        System.out.println("\n=== Ejemplo 4: Configuración desde Archivo ===");
        
        // Crear configuración base y guardarla
        ConfigWrapperBuilder.create()
            .serverName("Mi Servidor Personalizado")
            .maxPlayers(75)
            .economyEnabled(true)
            .startingMoney(1500.0)
            .welcomeMessage(true, "&a¡Hola %player%! ¡Disfruta tu estancia!")
            .saveToFile("mi-config.yml");
        
        // Cargar desde archivo y modificar
        ConfigWrapper fileConfig = ConfigWrapperBuilder.fromConfig("mi-config.yml")
            .maxPlayers(80) // Aumentar capacidad
            .startingMoney(2000.0) // Más dinero inicial
            .build();
        
        System.out.println("Configuración cargada y modificada:");
        System.out.println("- Jugadores: " + fileConfig.getMaxPlayers());
        System.out.println("- Dinero inicial: " + fileConfig.getStartingMoney());
        
        // ==================== EJEMPLO 5: CONFIGURACIONES ESPECIALIZADAS ====================
        System.out.println("\n=== Ejemplo 5: Configuraciones Especializadas ===");
        
        // Configuración para servidor PvP
        ConfigWrapper pvpConfig = ConfigWrapperBuilder.create()
            .serverName("Arena PvP")
            .worldSettings(true, "hard", 0) // PvP, difícil, sin protección spawn
            .protection(false, false, 0) // Sin protecciones
            .economyEnabled(true)
            .startingMoney(500.0) // Menos dinero inicial
            .allowedWorlds(Arrays.asList("pvp_arena", "lobby"))
            .build();
        
        // Configuración para servidor creativo
        ConfigWrapper creativeConfig = ConfigWrapperBuilder.create()
            .serverName("Mundo Creativo")
            .worldSettings(false, "peaceful", 64) // Sin PvP, pacífico
            .economyEnabled(false) // Sin economía
            .protection(true, true, 20) // Máxima protección
            .allowedWorlds(Arrays.asList("creative", "plots", "lobby"))
            .build();
        
        // Configuración para servidor survival
        ConfigWrapper survivalConfig = ConfigWrapperBuilder.create()
            .serverName("Supervivencia Extrema")
            .worldSettings(true, "hard", 16)
            .economyEnabled(true)
            .startingMoney(100.0) // Muy poco dinero inicial
            .protection(true, false, 3) // Protección limitada
            .allowedWorlds(Arrays.asList("world", "world_nether", "world_the_end"))
            .build();
        
        System.out.println("Configuraciones especializadas creadas:");
        System.out.println("- PvP: " + pvpConfig.getServerName() + " (PvP: " + pvpConfig.isWorldPvp() + ")");
        System.out.println("- Creativo: " + creativeConfig.getServerName() + " (Economía: " + creativeConfig.isEconomyEnabled() + ")");
        System.out.println("- Survival: " + survivalConfig.getServerName() + " (Dinero inicial: " + survivalConfig.getStartingMoney() + ")");
        
        // ==================== EJEMPLO 6: TESTING CON DIFERENTES CONFIGURACIONES ====================
        System.out.println("\n=== Ejemplo 6: Testing con Diferentes Configuraciones ===");
        
        testEconomyFeature(basicConfig);
        testEconomyFeature(testConfig);
        testEconomyFeature(creativeConfig);
        
        // ==================== EJEMPLO 7: VALIDACIÓN DE CONFIGURACIÓN ====================
        System.out.println("\n=== Ejemplo 7: Validación de Configuración ===");
        
        validateConfiguration(prodConfig);
        validateConfiguration(testConfig);
    }
    
    /**
     * Ejemplo de testing de funcionalidad de economía
     */
    private static void testEconomyFeature(ConfigWrapper config) {
        System.out.println("\nTesting economía en: " + config.getServerName());
        
        if (config.isEconomyEnabled()) {
            System.out.println("✓ Economía habilitada");
            System.out.println("  - Dinero inicial: " + config.getCurrencySymbol() + config.getStartingMoney());
            
            if (config.isShopsEnabled()) {
                System.out.println("  - Tiendas habilitadas (Impuesto: " + (config.getTaxRate() * 100) + "%)");
            } else {
                System.out.println("  - Tiendas deshabilitadas");
            }
        } else {
            System.out.println("✗ Economía deshabilitada - saltando tests de economía");
        }
    }
    
    /**
     * Ejemplo de validación de configuración
     */
    private static void validateConfiguration(ConfigWrapper config) {
        System.out.println("\nValidando configuración: " + config.getServerName());
        
        boolean isValid = true;
        
        // Validar jugadores máximos
        if (config.getMaxPlayers() <= 0 || config.getMaxPlayers() > 1000) {
            System.out.println("✗ Número de jugadores máximos inválido: " + config.getMaxPlayers());
            isValid = false;
        }
        
        // Validar dinero inicial si la economía está habilitada
        if (config.isEconomyEnabled() && config.getStartingMoney() < 0) {
            System.out.println("✗ Dinero inicial no puede ser negativo: " + config.getStartingMoney());
            isValid = false;
        }
        
        // Validar que hay al menos un mundo permitido
        if (config.getAllowedWorlds().isEmpty()) {
            System.out.println("✗ Debe haber al menos un mundo permitido");
            isValid = false;
        }
        
        // Validar configuración de base de datos
        if ("mysql".equals(config.getDatabaseType()) || "postgresql".equals(config.getDatabaseType())) {
            if (config.getDatabaseHost() == null || config.getDatabaseHost().trim().isEmpty()) {
                System.out.println("✗ Host de base de datos requerido para " + config.getDatabaseType());
                isValid = false;
            }
        }
        
        if (isValid) {
            System.out.println("✓ Configuración válida");
        } else {
            System.out.println("✗ Configuración contiene errores");
        }
    }
    
    /**
     * Ejemplo de cómo usar la configuración en diferentes contextos
     */
    public static class ServerManager {
        private final ConfigWrapper config;
        
        public ServerManager(ConfigWrapper config) {
            this.config = config;
        }
        
        public void startServer() {
            System.out.println("Iniciando servidor: " + config.getServerName());
            System.out.println("Puerto: " + config.getServerPort());
            System.out.println("Capacidad: " + config.getMaxPlayers() + " jugadores");
            
            if (config.isMaintenanceMode()) {
                System.out.println("⚠️ Servidor en modo mantenimiento");
                return;
            }
            
            // Inicializar características según configuración
            if (config.isEconomyEnabled()) {
                initializeEconomy();
            }
            
            if (config.isChatEnabled()) {
                initializeChat();
            }
            
            if (config.isProtectionEnabled()) {
                initializeProtection();
            }
            
            System.out.println("✓ Servidor iniciado correctamente");
        }
        
        private void initializeEconomy() {
            System.out.println("  - Inicializando economía...");
            System.out.println("    Dinero inicial: " + config.getCurrencySymbol() + config.getStartingMoney());
            
            if (config.isShopsEnabled()) {
                System.out.println("    Tiendas habilitadas (Impuesto: " + (config.getTaxRate() * 100) + "%)");
            }
        }
        
        private void initializeChat() {
            System.out.println("  - Inicializando chat...");
            System.out.println("    Formato: " + config.getChatFormat());
            System.out.println("    Prefijo: " + config.getChatPrefix());
            
            if (config.isWordFilterEnabled()) {
                System.out.println("    Filtro de palabras habilitado (" + config.getBlockedWords().size() + " palabras bloqueadas)");
            }
        }
        
        private void initializeProtection() {
            System.out.println("  - Inicializando protección...");
            System.out.println("    Protección por defecto: " + config.isDefaultProtection());
            System.out.println("    Máximo claims por jugador: " + config.getMaxClaimsPerPlayer());
        }
    }
}