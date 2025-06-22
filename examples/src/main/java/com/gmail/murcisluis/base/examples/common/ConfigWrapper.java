package com.gmail.murcisluis.base.examples.common;

import com.gmail.murcisluis.base.common.api.config.ConfigurationManager;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import lombok.Getter;

import java.util.List;

/**
 * Wrapper de configuración que expone los valores como atributos directos.
 * Esta clase carga la configuración una vez y expone todos los valores
 * como propiedades accesibles, mejorando el rendimiento y la legibilidad.
 * 
 * Ejemplo de uso:
 * ConfigWrapper config = new ConfigWrapper();
 * String nombre = config.serverName;  // En lugar de config.get("server.name")
 * int jugadores = config.maxPlayers;  // En lugar de config.get("server.max-players")
 */
@Getter
public class ConfigWrapper {
    
    // ==================== CONFIGURACIÓN DEL SERVIDOR ====================
    private final String serverName;
    private final int maxPlayers;
    private final boolean maintenanceMode;
    private final String motd;
    private final int serverPort;
    
    // ==================== CONFIGURACIÓN DE CARACTERÍSTICAS ====================
    private final boolean chatEnabled;
    private final String chatFormat;
    private final String chatPrefix;
    private final boolean wordFilterEnabled;
    private final List<String> blockedWords;
    
    private final boolean economyEnabled;
    private final double startingMoney;
    private final String currencySymbol;
    private final boolean shopsEnabled;
    private final double taxRate;
    
    private final boolean protectionEnabled;
    private final boolean defaultProtection;
    private final int maxClaimsPerPlayer;
    
    // ==================== CONFIGURACIÓN DE MUNDOS ====================
    private final List<String> allowedWorlds;
    private final boolean worldPvp;
    private final String worldDifficulty;
    private final int spawnProtection;
    
    // ==================== CONFIGURACIÓN DE MENSAJES ====================
    private final String messagePrefix;
    private final boolean welcomeEnabled;
    private final String welcomeMessage;
    private final boolean goodbyeEnabled;
    private final String goodbyeMessage;
    private final String noPermissionMessage;
    private final String playerNotFoundMessage;
    private final String invalidCommandMessage;
    
    // ==================== CONFIGURACIÓN DE COMANDOS ====================
    private final List<String> enabledCommands;
    private final int helpCooldown;
    private final int infoCooldown;
    private final int statsCooldown;
    
    // ==================== CONFIGURACIÓN DE LOGS ====================
    private final String logLevel;
    private final boolean fileLogging;
    private final boolean dailyRotation;
    private final int keepDays;
    
    // ==================== CONFIGURACIÓN AVANZADA ====================
    private final boolean checkUpdates;
    private final boolean metrics;
    private final boolean debug;
    private final int threadPoolSize;
    private final int asyncTimeout;
    
    // ==================== CONFIGURACIÓN DE BASE DE DATOS ====================
    private final String databaseType;
    private final String databaseFile;
    private final String databaseHost;
    private final int databasePort;
    private final String databaseName;
    private final String databaseUsername;
    private final String databasePassword;
    private final boolean databaseSsl;
    
    // Referencias internas
    private final ConfigurationManager configManager;
    private final FileConfig<?> config;
    
    /**
     * Constructor que carga y cachea todos los valores de configuración
     */
    public ConfigWrapper() {
        this.configManager = ConfigurationManager.getInstance();
        this.config = configManager.getConfig("config.yml");
        
        // Cargar configuración del servidor
        this.serverName = (String) config.get("server.name", "Mi Servidor Minecraft");
        this.maxPlayers = (Integer) config.get("server.max-players", 20);
        this.maintenanceMode = (Boolean) config.get("server.maintenance", false);
        this.motd = (String) config.get("server.motd", "¡Bienvenido a mi servidor!");
        this.serverPort = (Integer) config.get("server.port", 25565);
        
        // Cargar configuración de chat
        this.chatEnabled = (Boolean) config.get("features.chat.enabled", true);
        this.chatFormat = (String) config.get("features.chat.format", "<%player%> %message%");
        this.chatPrefix = (String) config.get("features.chat.prefix", "[Chat]");
        this.wordFilterEnabled = (Boolean) config.get("features.chat.word-filter.enabled", false);
        this.blockedWords = (List<String>) config.get("features.chat.word-filter.blocked-words", 
            java.util.Arrays.asList("palabra1", "palabra2"));
        
        // Cargar configuración de economía
        this.economyEnabled = (Boolean) config.get("features.economy.enabled", true);
        this.startingMoney = (Double) config.get("features.economy.starting-money", 1000.0);
        this.currencySymbol = (String) config.get("features.economy.currency-symbol", "$");
        this.shopsEnabled = (Boolean) config.get("features.economy.shops.enabled", true);
        this.taxRate = (Double) config.get("features.economy.shops.tax-rate", 0.05);
        
        // Cargar configuración de protección
        this.protectionEnabled = (Boolean) config.get("features.protection.enabled", true);
        this.defaultProtection = (Boolean) config.get("features.protection.default-protection", true);
        this.maxClaimsPerPlayer = (Integer) config.get("features.protection.max-claims-per-player", 5);
        
        // Cargar configuración de mundos
        this.allowedWorlds = (List<String>) config.get("worlds.allowed-worlds", 
            java.util.Arrays.asList("world", "world_nether", "world_the_end"));
        this.worldPvp = (Boolean) config.get("worlds.world-settings.world.pvp", false);
        this.worldDifficulty = (String) config.get("worlds.world-settings.world.difficulty", "normal");
        this.spawnProtection = (Integer) config.get("worlds.world-settings.world.spawn-protection", 16);
        
        // Cargar configuración de mensajes
        this.messagePrefix = (String) config.get("messages.prefix", "&8[&6MiPlugin&8] &r");
        this.welcomeEnabled = (Boolean) config.get("messages.welcome.enabled", true);
        this.welcomeMessage = (String) config.get("messages.welcome.message", "&a¡Bienvenido al servidor, %player%!");
        this.goodbyeEnabled = (Boolean) config.get("messages.goodbye.enabled", true);
        this.goodbyeMessage = (String) config.get("messages.goodbye.message", "&c%player% ha salido del servidor");
        this.noPermissionMessage = (String) config.get("messages.errors.no-permission", "&cNo tienes permisos para hacer esto");
        this.playerNotFoundMessage = (String) config.get("messages.errors.player-not-found", "&cJugador no encontrado");
        this.invalidCommandMessage = (String) config.get("messages.errors.invalid-command", "&cComando inválido");
        
        // Cargar configuración de comandos
        this.enabledCommands = (List<String>) config.get("commands.enabled-commands", 
            java.util.Arrays.asList("help", "info", "stats", "config"));
        this.helpCooldown = (Integer) config.get("commands.cooldowns.help", 5);
        this.infoCooldown = (Integer) config.get("commands.cooldowns.info", 10);
        this.statsCooldown = (Integer) config.get("commands.cooldowns.stats", 30);
        
        // Cargar configuración de logs
        this.logLevel = (String) config.get("logging.level", "INFO");
        this.fileLogging = (Boolean) config.get("logging.file-logging", true);
        this.dailyRotation = (Boolean) config.get("logging.daily-rotation", true);
        this.keepDays = (Integer) config.get("logging.keep-days", 7);
        
        // Cargar configuración avanzada
        this.checkUpdates = (Boolean) config.get("advanced.check-updates", true);
        this.metrics = (Boolean) config.get("advanced.metrics", true);
        this.debug = (Boolean) config.get("advanced.debug", false);
        this.threadPoolSize = (Integer) config.get("advanced.performance.thread-pool-size", 4);
        this.asyncTimeout = (Integer) config.get("advanced.performance.async-timeout", 5000);
        
        // Cargar configuración de base de datos
        this.databaseType = (String) config.get("database.type", "sqlite");
        this.databaseFile = (String) config.get("database.file", "data.db");
        this.databaseHost = (String) config.get("database.host", "localhost");
        this.databasePort = (Integer) config.get("database.port", 3306);
        this.databaseName = (String) config.get("database.database", "minecraft");
        this.databaseUsername = (String) config.get("database.username", "user");
        this.databasePassword = (String) config.get("database.password", "password");
        this.databaseSsl = (Boolean) config.get("database.ssl", false);
    }
    
    /**
     * Recarga la configuración creando una nueva instancia
     * @return Nueva instancia de ConfigWrapper con valores actualizados
     */
    public ConfigWrapper reload() {
        configManager.reloadConfig("config.yml");
        return new ConfigWrapper();
    }
    
    /**
     * Obtiene un mensaje formateado con el prefijo
     * @param message El mensaje a formatear
     * @return Mensaje con prefijo aplicado
     */
    public String getFormattedMessage(String message) {
        return messagePrefix + message;
    }
    
    /**
     * Verifica si un comando está habilitado
     * @param command El comando a verificar
     * @return true si el comando está habilitado
     */
    public boolean isCommandEnabled(String command) {
        return enabledCommands.contains(command.toLowerCase());
    }
    
    /**
     * Verifica si un mundo está permitido
     * @param world El nombre del mundo
     * @return true si el mundo está permitido
     */
    public boolean isWorldAllowed(String world) {
        return allowedWorlds.contains(world);
    }
    
    /**
     * Obtiene el cooldown de un comando específico
     * @param command El comando
     * @return Cooldown en segundos, 0 si no tiene cooldown
     */
    public int getCommandCooldown(String command) {
        switch (command.toLowerCase()) {
            case "help": return helpCooldown;
            case "info": return infoCooldown;
            case "stats": return statsCooldown;
            default: return 0;
        }
    }
    
    /**
     * Verifica si la base de datos es MySQL o PostgreSQL
     * @return true si usa base de datos externa
     */
    public boolean isExternalDatabase() {
        return "mysql".equalsIgnoreCase(databaseType) || "postgresql".equalsIgnoreCase(databaseType);
    }
    
    /**
     * Obtiene la URL de conexión de la base de datos
     * @return URL de conexión formateada
     */
    public String getDatabaseUrl() {
        if ("sqlite".equalsIgnoreCase(databaseType)) {
            return "jdbc:sqlite:" + databaseFile;
        } else if ("mysql".equalsIgnoreCase(databaseType)) {
            return String.format("jdbc:mysql://%s:%d/%s?useSSL=%s", 
                databaseHost, databasePort, databaseName, databaseSsl);
        } else if ("postgresql".equalsIgnoreCase(databaseType)) {
            return String.format("jdbc:postgresql://%s:%d/%s?ssl=%s", 
                databaseHost, databasePort, databaseName, databaseSsl);
        }
        return null;
    }
    
    /**
     * Guarda la configuración actual en memoria a un archivo YAML
     * @param fileName Nombre del archivo (ej: "backup-config.yml")
     * @return true si se guardó correctamente
     */
    public boolean saveToYaml(String fileName) {
        try {
            FileConfig<?> newConfig = configManager.getConfig(fileName);
            
            // Guardar configuración del servidor
            newConfig.set("server.name", serverName);
            newConfig.set("server.max-players", maxPlayers);
            newConfig.set("server.maintenance", maintenanceMode);
            newConfig.set("server.motd", motd);
            newConfig.set("server.port", serverPort);
            
            // Guardar configuración de chat
            newConfig.set("features.chat.enabled", chatEnabled);
            newConfig.set("features.chat.format", chatFormat);
            newConfig.set("features.chat.prefix", chatPrefix);
            newConfig.set("features.chat.word-filter.enabled", wordFilterEnabled);
            newConfig.set("features.chat.word-filter.blocked-words", blockedWords);
            
            // Guardar configuración de economía
            newConfig.set("features.economy.enabled", economyEnabled);
            newConfig.set("features.economy.starting-money", startingMoney);
            newConfig.set("features.economy.currency-symbol", currencySymbol);
            newConfig.set("features.economy.shops.enabled", shopsEnabled);
            newConfig.set("features.economy.shops.tax-rate", taxRate);
            
            // Guardar configuración de protección
            newConfig.set("features.protection.enabled", protectionEnabled);
            newConfig.set("features.protection.default-protection", defaultProtection);
            newConfig.set("features.protection.max-claims-per-player", maxClaimsPerPlayer);
            
            // Guardar configuración de mundos
            newConfig.set("worlds.allowed-worlds", allowedWorlds);
            newConfig.set("worlds.world-settings.world.pvp", worldPvp);
            newConfig.set("worlds.world-settings.world.difficulty", worldDifficulty);
            newConfig.set("worlds.world-settings.world.spawn-protection", spawnProtection);
            
            // Guardar configuración de mensajes
            newConfig.set("messages.prefix", messagePrefix);
            newConfig.set("messages.welcome.enabled", welcomeEnabled);
            newConfig.set("messages.welcome.message", welcomeMessage);
            newConfig.set("messages.goodbye.enabled", goodbyeEnabled);
            newConfig.set("messages.goodbye.message", goodbyeMessage);
            newConfig.set("messages.errors.no-permission", noPermissionMessage);
            newConfig.set("messages.errors.player-not-found", playerNotFoundMessage);
            newConfig.set("messages.errors.invalid-command", invalidCommandMessage);
            
            // Guardar configuración de comandos
            newConfig.set("commands.enabled-commands", enabledCommands);
            newConfig.set("commands.cooldowns.help", helpCooldown);
            newConfig.set("commands.cooldowns.info", infoCooldown);
            newConfig.set("commands.cooldowns.stats", statsCooldown);
            
            // Guardar configuración de logs
            newConfig.set("logging.level", logLevel);
            newConfig.set("logging.file-logging", fileLogging);
            newConfig.set("logging.daily-rotation", dailyRotation);
            newConfig.set("logging.keep-days", keepDays);
            
            // Guardar configuración avanzada
            newConfig.set("advanced.check-updates", checkUpdates);
            newConfig.set("advanced.metrics", metrics);
            newConfig.set("advanced.debug", debug);
            newConfig.set("advanced.performance.thread-pool-size", threadPoolSize);
            newConfig.set("advanced.performance.async-timeout", asyncTimeout);
            
            // Guardar configuración de base de datos
            newConfig.set("database.type", databaseType);
            newConfig.set("database.file", databaseFile);
            newConfig.set("database.host", databaseHost);
            newConfig.set("database.port", databasePort);
            newConfig.set("database.database", databaseName);
            newConfig.set("database.username", databaseUsername);
            newConfig.set("database.password", databasePassword);
            newConfig.set("database.ssl", databaseSsl);
            
            // Guardar el archivo
            newConfig.saveData();
            return true;
            
        } catch (Exception e) {
            System.err.println("Error al guardar configuración a " + fileName + ": " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Crea un backup de la configuración actual con timestamp
     * @return Nombre del archivo de backup creado
     */
    public String createBackup() {
        String timestamp = java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String backupFileName = "config-backup-" + timestamp + ".yml";
        
        if (saveToYaml(backupFileName)) {
            System.out.println("Backup creado: " + backupFileName);
            return backupFileName;
        } else {
            System.err.println("Error al crear backup");
            return null;
        }
    }
    
    /**
     * Exporta la configuración actual a un archivo YAML con comentarios
     * @param fileName Nombre del archivo
     * @return true si se exportó correctamente
     */
    public boolean exportToYamlWithComments(String fileName) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter(fileName);
            
            writer.write("# ==========================================\n");
            writer.write("# Configuración exportada desde ConfigWrapper\n");
            writer.write("# Fecha: " + java.time.LocalDateTime.now() + "\n");
            writer.write("# ==========================================\n\n");
            
            // Configuración del servidor
            writer.write("# Configuración del servidor\n");
            writer.write("server:\n");
            writer.write("  name: \"" + serverName + "\"\n");
            writer.write("  max-players: " + maxPlayers + "\n");
            writer.write("  maintenance: " + maintenanceMode + "\n");
            writer.write("  motd: \"" + motd + "\"\n");
            writer.write("  port: " + serverPort + "\n\n");
            
            // Características
            writer.write("# Características del servidor\n");
            writer.write("features:\n");
            writer.write("  chat:\n");
            writer.write("    enabled: " + chatEnabled + "\n");
            writer.write("    format: \"" + chatFormat + "\"\n");
            writer.write("    prefix: \"" + chatPrefix + "\"\n");
            writer.write("    word-filter:\n");
            writer.write("      enabled: " + wordFilterEnabled + "\n");
            writer.write("      blocked-words:\n");
            for (String word : blockedWords) {
                writer.write("        - \"" + word + "\"\n");
            }
            
            writer.write("  economy:\n");
            writer.write("    enabled: " + economyEnabled + "\n");
            writer.write("    starting-money: " + startingMoney + "\n");
            writer.write("    currency-symbol: \"" + currencySymbol + "\"\n");
            writer.write("    shops:\n");
            writer.write("      enabled: " + shopsEnabled + "\n");
            writer.write("      tax-rate: " + taxRate + "\n");
            
            writer.write("  protection:\n");
            writer.write("    enabled: " + protectionEnabled + "\n");
            writer.write("    default-protection: " + defaultProtection + "\n");
            writer.write("    max-claims-per-player: " + maxClaimsPerPlayer + "\n\n");
            
            // Mundos
            writer.write("# Configuración de mundos\n");
            writer.write("worlds:\n");
            writer.write("  allowed-worlds:\n");
            for (String world : allowedWorlds) {
                writer.write("    - \"" + world + "\"\n");
            }
            writer.write("  world-settings:\n");
            writer.write("    world:\n");
            writer.write("      pvp: " + worldPvp + "\n");
            writer.write("      difficulty: \"" + worldDifficulty + "\"\n");
            writer.write("      spawn-protection: " + spawnProtection + "\n\n");
            
            // Mensajes
            writer.write("# Configuración de mensajes\n");
            writer.write("messages:\n");
            writer.write("  prefix: \"" + messagePrefix + "\"\n");
            writer.write("  welcome:\n");
            writer.write("    enabled: " + welcomeEnabled + "\n");
            writer.write("    message: \"" + welcomeMessage + "\"\n");
            writer.write("  goodbye:\n");
            writer.write("    enabled: " + goodbyeEnabled + "\n");
            writer.write("    message: \"" + goodbyeMessage + "\"\n");
            writer.write("  errors:\n");
            writer.write("    no-permission: \"" + noPermissionMessage + "\"\n");
            writer.write("    player-not-found: \"" + playerNotFoundMessage + "\"\n");
            writer.write("    invalid-command: \"" + invalidCommandMessage + "\"\n\n");
            
            // Comandos
            writer.write("# Configuración de comandos\n");
            writer.write("commands:\n");
            writer.write("  enabled-commands:\n");
            for (String command : enabledCommands) {
                writer.write("    - \"" + command + "\"\n");
            }
            writer.write("  cooldowns:\n");
            writer.write("    help: " + helpCooldown + "\n");
            writer.write("    info: " + infoCooldown + "\n");
            writer.write("    stats: " + statsCooldown + "\n\n");
            
            // Logging
            writer.write("# Configuración de logging\n");
            writer.write("logging:\n");
            writer.write("  level: \"" + logLevel + "\"\n");
            writer.write("  file-logging: " + fileLogging + "\n");
            writer.write("  daily-rotation: " + dailyRotation + "\n");
            writer.write("  keep-days: " + keepDays + "\n\n");
            
            // Configuración avanzada
            writer.write("# Configuración avanzada\n");
            writer.write("advanced:\n");
            writer.write("  check-updates: " + checkUpdates + "\n");
            writer.write("  metrics: " + metrics + "\n");
            writer.write("  debug: " + debug + "\n");
            writer.write("  performance:\n");
            writer.write("    thread-pool-size: " + threadPoolSize + "\n");
            writer.write("    async-timeout: " + asyncTimeout + "\n\n");
            
            // Base de datos
            writer.write("# Configuración de base de datos\n");
            writer.write("database:\n");
            writer.write("  type: \"" + databaseType + "\"\n");
            writer.write("  file: \"" + databaseFile + "\"\n");
            writer.write("  host: \"" + databaseHost + "\"\n");
            writer.write("  port: " + databasePort + "\n");
            writer.write("  database: \"" + databaseName + "\"\n");
            writer.write("  username: \"" + databaseUsername + "\"\n");
            writer.write("  password: \"" + databasePassword + "\"\n");
            writer.write("  ssl: " + databaseSsl + "\n");
            
            writer.close();
            return true;
            
        } catch (Exception e) {
            System.err.println("Error al exportar configuración: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Imprime un resumen de la configuración actual
     */
    public void printConfigSummary() {
        System.out.println("=== RESUMEN DE CONFIGURACIÓN ===");
        System.out.println("Servidor: " + serverName + " (" + maxPlayers + " jugadores)");
        System.out.println("Mantenimiento: " + (maintenanceMode ? "ACTIVADO" : "DESACTIVADO"));
        System.out.println("Chat: " + (chatEnabled ? "HABILITADO" : "DESHABILITADO"));
        System.out.println("Economía: " + (economyEnabled ? "HABILITADA" : "DESHABILITADA"));
        System.out.println("Protección: " + (protectionEnabled ? "HABILITADA" : "DESHABILITADA"));
        System.out.println("Base de datos: " + databaseType.toUpperCase());
        System.out.println("Debug: " + (debug ? "ACTIVADO" : "DESACTIVADO"));
        System.out.println("Mundos permitidos: " + allowedWorlds.size());
        System.out.println("Comandos habilitados: " + enabledCommands.size());
    }
}