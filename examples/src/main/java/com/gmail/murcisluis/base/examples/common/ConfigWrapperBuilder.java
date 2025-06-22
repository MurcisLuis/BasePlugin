package com.gmail.murcisluis.base.examples.common;

import com.gmail.murcisluis.base.common.api.config.ConfigurationManager;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Builder para ConfigWrapper que permite crear configuraciones
 * programáticamente y facilita el testing.
 * 
 * Ejemplo de uso:
 * ConfigWrapper config = ConfigWrapperBuilder.create()
 *     .serverName("Mi Servidor")
 *     .maxPlayers(50)
 *     .economyEnabled(true)
 *     .startingMoney(2000.0)
 *     .build();
 */
public class ConfigWrapperBuilder {
    
    // Valores por defecto
    private String serverName = "Mi Servidor Minecraft";
    private int maxPlayers = 20;
    private boolean maintenanceMode = false;
    private String motd = "¡Bienvenido a mi servidor!";
    private int serverPort = 25565;
    
    private boolean chatEnabled = true;
    private String chatFormat = "<%player%> %message%";
    private String chatPrefix = "[Chat]";
    private boolean wordFilterEnabled = false;
    private List<String> blockedWords = Arrays.asList("palabra1", "palabra2");
    
    private boolean economyEnabled = true;
    private double startingMoney = 1000.0;
    private String currencySymbol = "$";
    private boolean shopsEnabled = true;
    private double taxRate = 0.05;
    
    private boolean protectionEnabled = true;
    private boolean defaultProtection = true;
    private int maxClaimsPerPlayer = 5;
    
    private List<String> allowedWorlds = Arrays.asList("world", "world_nether", "world_the_end");
    private boolean worldPvp = false;
    private String worldDifficulty = "normal";
    private int spawnProtection = 16;
    
    private String messagePrefix = "&8[&6MiPlugin&8] &r";
    private boolean welcomeEnabled = true;
    private String welcomeMessage = "&a¡Bienvenido al servidor, %player%!";
    private boolean goodbyeEnabled = true;
    private String goodbyeMessage = "&c%player% ha salido del servidor";
    private String noPermissionMessage = "&cNo tienes permisos para hacer esto";
    private String playerNotFoundMessage = "&cJugador no encontrado";
    private String invalidCommandMessage = "&cComando inválido";
    
    private List<String> enabledCommands = Arrays.asList("help", "info", "stats", "config");
    private int helpCooldown = 5;
    private int infoCooldown = 10;
    private int statsCooldown = 30;
    
    private String logLevel = "INFO";
    private boolean fileLogging = true;
    private boolean dailyRotation = true;
    private int keepDays = 7;
    
    private boolean checkUpdates = true;
    private boolean metrics = true;
    private boolean debug = false;
    private int threadPoolSize = 4;
    private int asyncTimeout = 5000;
    
    private String databaseType = "sqlite";
    private String databaseFile = "data.db";
    private String databaseHost = "localhost";
    private int databasePort = 3306;
    private String databaseName = "minecraft";
    private String databaseUsername = "user";
    private String databasePassword = "password";
    private boolean databaseSsl = false;
    
    private ConfigWrapperBuilder() {}
    
    /**
     * Crea una nueva instancia del builder
     */
    public static ConfigWrapperBuilder create() {
        return new ConfigWrapperBuilder();
    }
    
    /**
     * Crea un builder basado en un archivo de configuración existente
     */
    public static ConfigWrapperBuilder fromConfig(String configFile) {
        ConfigWrapperBuilder builder = new ConfigWrapperBuilder();
        FileConfig<?> config = ConfigurationManager.getInstance().getConfig(configFile);
        
        // Cargar valores del archivo
        builder.serverName = (String) config.get("server.name", builder.serverName);
        builder.maxPlayers = (Integer) config.get("server.max-players", builder.maxPlayers);
        builder.maintenanceMode = (Boolean) config.get("server.maintenance", builder.maintenanceMode);
        // ... (cargar todos los demás valores)
        
        return builder;
    }
    
    // ==================== MÉTODOS DEL BUILDER ====================
    
    // Configuración del servidor
    public ConfigWrapperBuilder serverName(String serverName) {
        this.serverName = serverName;
        return this;
    }
    
    public ConfigWrapperBuilder maxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        return this;
    }
    
    public ConfigWrapperBuilder maintenanceMode(boolean maintenanceMode) {
        this.maintenanceMode = maintenanceMode;
        return this;
    }
    
    public ConfigWrapperBuilder motd(String motd) {
        this.motd = motd;
        return this;
    }
    
    public ConfigWrapperBuilder serverPort(int serverPort) {
        this.serverPort = serverPort;
        return this;
    }
    
    // Configuración de chat
    public ConfigWrapperBuilder chatEnabled(boolean chatEnabled) {
        this.chatEnabled = chatEnabled;
        return this;
    }
    
    public ConfigWrapperBuilder chatFormat(String chatFormat) {
        this.chatFormat = chatFormat;
        return this;
    }
    
    public ConfigWrapperBuilder chatPrefix(String chatPrefix) {
        this.chatPrefix = chatPrefix;
        return this;
    }
    
    public ConfigWrapperBuilder wordFilter(boolean enabled, List<String> blockedWords) {
        this.wordFilterEnabled = enabled;
        this.blockedWords = blockedWords;
        return this;
    }
    
    // Configuración de economía
    public ConfigWrapperBuilder economyEnabled(boolean economyEnabled) {
        this.economyEnabled = economyEnabled;
        return this;
    }
    
    public ConfigWrapperBuilder startingMoney(double startingMoney) {
        this.startingMoney = startingMoney;
        return this;
    }
    
    public ConfigWrapperBuilder currencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
        return this;
    }
    
    public ConfigWrapperBuilder shops(boolean enabled, double taxRate) {
        this.shopsEnabled = enabled;
        this.taxRate = taxRate;
        return this;
    }
    
    // Configuración de protección
    public ConfigWrapperBuilder protection(boolean enabled, boolean defaultProtection, int maxClaims) {
        this.protectionEnabled = enabled;
        this.defaultProtection = defaultProtection;
        this.maxClaimsPerPlayer = maxClaims;
        return this;
    }
    
    // Configuración de mundos
    public ConfigWrapperBuilder allowedWorlds(List<String> allowedWorlds) {
        this.allowedWorlds = allowedWorlds;
        return this;
    }
    
    public ConfigWrapperBuilder worldSettings(boolean pvp, String difficulty, int spawnProtection) {
        this.worldPvp = pvp;
        this.worldDifficulty = difficulty;
        this.spawnProtection = spawnProtection;
        return this;
    }
    
    // Configuración de mensajes
    public ConfigWrapperBuilder messagePrefix(String messagePrefix) {
        this.messagePrefix = messagePrefix;
        return this;
    }
    
    public ConfigWrapperBuilder welcomeMessage(boolean enabled, String message) {
        this.welcomeEnabled = enabled;
        this.welcomeMessage = message;
        return this;
    }
    
    public ConfigWrapperBuilder goodbyeMessage(boolean enabled, String message) {
        this.goodbyeEnabled = enabled;
        this.goodbyeMessage = message;
        return this;
    }
    
    public ConfigWrapperBuilder errorMessages(String noPermission, String playerNotFound, String invalidCommand) {
        this.noPermissionMessage = noPermission;
        this.playerNotFoundMessage = playerNotFound;
        this.invalidCommandMessage = invalidCommand;
        return this;
    }
    
    // Configuración de comandos
    public ConfigWrapperBuilder enabledCommands(List<String> enabledCommands) {
        this.enabledCommands = enabledCommands;
        return this;
    }
    
    public ConfigWrapperBuilder commandCooldowns(int help, int info, int stats) {
        this.helpCooldown = help;
        this.infoCooldown = info;
        this.statsCooldown = stats;
        return this;
    }
    
    // Configuración de logs
    public ConfigWrapperBuilder logging(String level, boolean fileLogging, boolean dailyRotation, int keepDays) {
        this.logLevel = level;
        this.fileLogging = fileLogging;
        this.dailyRotation = dailyRotation;
        this.keepDays = keepDays;
        return this;
    }
    
    // Configuración avanzada
    public ConfigWrapperBuilder advanced(boolean checkUpdates, boolean metrics, boolean debug) {
        this.checkUpdates = checkUpdates;
        this.metrics = metrics;
        this.debug = debug;
        return this;
    }
    
    public ConfigWrapperBuilder performance(int threadPoolSize, int asyncTimeout) {
        this.threadPoolSize = threadPoolSize;
        this.asyncTimeout = asyncTimeout;
        return this;
    }
    
    // Configuración de base de datos
    public ConfigWrapperBuilder sqliteDatabase(String file) {
        this.databaseType = "sqlite";
        this.databaseFile = file;
        return this;
    }
    
    public ConfigWrapperBuilder mysqlDatabase(String host, int port, String database, String username, String password, boolean ssl) {
        this.databaseType = "mysql";
        this.databaseHost = host;
        this.databasePort = port;
        this.databaseName = database;
        this.databaseUsername = username;
        this.databasePassword = password;
        this.databaseSsl = ssl;
        return this;
    }
    
    public ConfigWrapperBuilder postgresqlDatabase(String host, int port, String database, String username, String password, boolean ssl) {
        this.databaseType = "postgresql";
        this.databaseHost = host;
        this.databasePort = port;
        this.databaseName = database;
        this.databaseUsername = username;
        this.databasePassword = password;
        this.databaseSsl = ssl;
        return this;
    }
    
    /**
     * Construye el ConfigWrapper con los valores configurados
     */
    public ConfigWrapper build() {
        return new BuilderConfigWrapper(this);
    }
    
    /**
     * Guarda la configuración en un archivo
     */
    public ConfigWrapperBuilder saveToFile(String configFile) {
        FileConfig<?> config = ConfigurationManager.getInstance().getConfig(configFile);
        
        // Guardar configuración del servidor
        config.set("server.name", serverName);
        config.set("server.max-players", maxPlayers);
        config.set("server.maintenance", maintenanceMode);
        config.set("server.motd", motd);
        config.set("server.port", serverPort);
        
        // Guardar configuración de chat
        config.set("features.chat.enabled", chatEnabled);
        config.set("features.chat.format", chatFormat);
        config.set("features.chat.prefix", chatPrefix);
        config.set("features.chat.word-filter.enabled", wordFilterEnabled);
        config.set("features.chat.word-filter.blocked-words", blockedWords);
        
        // Guardar configuración de economía
        config.set("features.economy.enabled", economyEnabled);
        config.set("features.economy.starting-money", startingMoney);
        config.set("features.economy.currency-symbol", currencySymbol);
        config.set("features.economy.shops.enabled", shopsEnabled);
        config.set("features.economy.shops.tax-rate", taxRate);
        
        // Guardar configuración de protección
        config.set("features.protection.enabled", protectionEnabled);
        config.set("features.protection.default-protection", defaultProtection);
        config.set("features.protection.max-claims-per-player", maxClaimsPerPlayer);
        
        // Guardar configuración de mundos
        config.set("worlds.allowed-worlds", allowedWorlds);
        config.set("worlds.world-settings.world.pvp", worldPvp);
        config.set("worlds.world-settings.world.difficulty", worldDifficulty);
        config.set("worlds.world-settings.world.spawn-protection", spawnProtection);
        
        // Guardar configuración de mensajes
        config.set("messages.prefix", messagePrefix);
        config.set("messages.welcome.enabled", welcomeEnabled);
        config.set("messages.welcome.message", welcomeMessage);
        config.set("messages.goodbye.enabled", goodbyeEnabled);
        config.set("messages.goodbye.message", goodbyeMessage);
        config.set("messages.errors.no-permission", noPermissionMessage);
        config.set("messages.errors.player-not-found", playerNotFoundMessage);
        config.set("messages.errors.invalid-command", invalidCommandMessage);
        
        // Guardar configuración de comandos
        config.set("commands.enabled-commands", enabledCommands);
        config.set("commands.cooldowns.help", helpCooldown);
        config.set("commands.cooldowns.info", infoCooldown);
        config.set("commands.cooldowns.stats", statsCooldown);
        
        // Guardar configuración de logs
        config.set("logging.level", logLevel);
        config.set("logging.file-logging", fileLogging);
        config.set("logging.daily-rotation", dailyRotation);
        config.set("logging.keep-days", keepDays);
        
        // Guardar configuración avanzada
        config.set("advanced.check-updates", checkUpdates);
        config.set("advanced.metrics", metrics);
        config.set("advanced.debug", debug);
        config.set("advanced.performance.thread-pool-size", threadPoolSize);
        config.set("advanced.performance.async-timeout", asyncTimeout);
        
        // Guardar configuración de base de datos
        config.set("database.type", databaseType);
        config.set("database.file", databaseFile);
        config.set("database.host", databaseHost);
        config.set("database.port", databasePort);
        config.set("database.database", databaseName);
        config.set("database.username", databaseUsername);
        config.set("database.password", databasePassword);
        config.set("database.ssl", databaseSsl);
        
        config.saveData();
        return this;
    }
    
    /**
     * Implementación interna de ConfigWrapper que usa los valores del builder
     */
    @Getter
    private static class BuilderConfigWrapper extends ConfigWrapper {
        private final String serverName;
        private final int maxPlayers;
        private final boolean maintenanceMode;
        private final String motd;
        private final int serverPort;
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
        private final List<String> allowedWorlds;
        private final boolean worldPvp;
        private final String worldDifficulty;
        private final int spawnProtection;
        private final String messagePrefix;
        private final boolean welcomeEnabled;
        private final String welcomeMessage;
        private final boolean goodbyeEnabled;
        private final String goodbyeMessage;
        private final String noPermissionMessage;
        private final String playerNotFoundMessage;
        private final String invalidCommandMessage;
        private final List<String> enabledCommands;
        private final int helpCooldown;
        private final int infoCooldown;
        private final int statsCooldown;
        private final String logLevel;
        private final boolean fileLogging;
        private final boolean dailyRotation;
        private final int keepDays;
        private final boolean checkUpdates;
        private final boolean metrics;
        private final boolean debug;
        private final int threadPoolSize;
        private final int asyncTimeout;
        private final String databaseType;
        private final String databaseFile;
        private final String databaseHost;
        private final int databasePort;
        private final String databaseName;
        private final String databaseUsername;
        private final String databasePassword;
        private final boolean databaseSsl;
        
        private BuilderConfigWrapper(ConfigWrapperBuilder builder) {
            // No llamar al constructor padre para evitar cargar desde archivo
            this.serverName = builder.serverName;
            this.maxPlayers = builder.maxPlayers;
            this.maintenanceMode = builder.maintenanceMode;
            this.motd = builder.motd;
            this.serverPort = builder.serverPort;
            this.chatEnabled = builder.chatEnabled;
            this.chatFormat = builder.chatFormat;
            this.chatPrefix = builder.chatPrefix;
            this.wordFilterEnabled = builder.wordFilterEnabled;
            this.blockedWords = builder.blockedWords;
            this.economyEnabled = builder.economyEnabled;
            this.startingMoney = builder.startingMoney;
            this.currencySymbol = builder.currencySymbol;
            this.shopsEnabled = builder.shopsEnabled;
            this.taxRate = builder.taxRate;
            this.protectionEnabled = builder.protectionEnabled;
            this.defaultProtection = builder.defaultProtection;
            this.maxClaimsPerPlayer = builder.maxClaimsPerPlayer;
            this.allowedWorlds = builder.allowedWorlds;
            this.worldPvp = builder.worldPvp;
            this.worldDifficulty = builder.worldDifficulty;
            this.spawnProtection = builder.spawnProtection;
            this.messagePrefix = builder.messagePrefix;
            this.welcomeEnabled = builder.welcomeEnabled;
            this.welcomeMessage = builder.welcomeMessage;
            this.goodbyeEnabled = builder.goodbyeEnabled;
            this.goodbyeMessage = builder.goodbyeMessage;
            this.noPermissionMessage = builder.noPermissionMessage;
            this.playerNotFoundMessage = builder.playerNotFoundMessage;
            this.invalidCommandMessage = builder.invalidCommandMessage;
            this.enabledCommands = builder.enabledCommands;
            this.helpCooldown = builder.helpCooldown;
            this.infoCooldown = builder.infoCooldown;
            this.statsCooldown = builder.statsCooldown;
            this.logLevel = builder.logLevel;
            this.fileLogging = builder.fileLogging;
            this.dailyRotation = builder.dailyRotation;
            this.keepDays = builder.keepDays;
            this.checkUpdates = builder.checkUpdates;
            this.metrics = builder.metrics;
            this.debug = builder.debug;
            this.threadPoolSize = builder.threadPoolSize;
            this.asyncTimeout = builder.asyncTimeout;
            this.databaseType = builder.databaseType;
            this.databaseFile = builder.databaseFile;
            this.databaseHost = builder.databaseHost;
            this.databasePort = builder.databasePort;
            this.databaseName = builder.databaseName;
            this.databaseUsername = builder.databaseUsername;
            this.databasePassword = builder.databasePassword;
            this.databaseSsl = builder.databaseSsl;
        }
    }
}