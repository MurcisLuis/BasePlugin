package com.gmail.murcisluis.base.examples.bungeecord;

import com.gmail.murcisluis.base.bungee.api.utils.config.ConfigAdapterBungee;
import com.gmail.murcisluis.base.bungee.api.utils.scheduler.SchedulerBungeecord;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import com.gmail.murcisluis.base.common.api.Description;
import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.bungee.api.BaseBungeAPI;
import com.gmail.murcisluis.base.bungee.api.BaseBungee;
import com.gmail.murcisluis.base.examples.bungeecord.ExampleBaseBungeAPI;
import com.gmail.murcisluis.base.examples.bungeecord.ExampleBaseBungeeImpl;
import com.gmail.murcisluis.base.bungee.api.commands.AbstractCommandBungee;
import com.gmail.murcisluis.base.bungee.api.commands.CommandManagerBungee;
import com.gmail.murcisluis.base.examples.bungeecord.commands.ExampleCommandBungee;
import com.gmail.murcisluis.base.common.api.utils.scheduler.S;
import com.gmail.murcisluis.base.common.api.config.ConfigurationManager;
import com.gmail.murcisluis.base.common.api.localization.LocalizationManager;
import com.gmail.murcisluis.base.common.api.localization.PlayerLocalizationHelper;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import com.gmail.murcisluis.base.examples.common.LocalizationExample;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginDescription;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * EJEMPLO de implementación de un plugin BungeeCord usando BasePlugin Framework.
 * 
 * IMPORTANTE: Esta es una clase de EJEMPLO. No la uses directamente en producción.
 * Copia y modifica según tus necesidades específicas.
 * 
 * Este ejemplo muestra:
 * - Inicialización correcta del framework
 * - Registro opcional de comandos personalizados
 * - Implementación de métodos requeridos
 */
@Getter
public class ExampleBungeePlugin extends Plugin implements BasePlugin {

    private ConfigAdapterBungee configAdapter;
    private LocalizationExample localizationExample;
    private PlayerLocalizationHelper localizationHelper;

    @Override
    public void onLoad() {
        // Inicializar el framework
        configAdapter = new ConfigAdapterBungee();
        BaseAPIFactory.initialize(new ExampleBaseBungeAPI());
        BaseAPIFactory.getAPI().onLoad(this);
        S.setInstance(new SchedulerBungeecord());
    }
    
    @Override
    public void onEnable() {
        // Habilitar la API
        BaseAPIFactory.getAPI().onEnable();
        
        // Obtener nuestra implementación personalizada
        ExampleBaseBungeeImpl base = ExampleBaseBungeAPI.getExampleImplementation();
        CommandManagerBungee commandManager = base.getCommandManager();

        // EJEMPLO: Registrar un comando personalizado (OPCIONAL)
        // Puedes omitir esto si no necesitas comandos
        AbstractCommandBungee mainCommand = new ExampleCommandBungee();
        commandManager.setMainCommand(mainCommand);
        commandManager.registerCommand(mainCommand);
        
        // Initialize enhanced localization system
        initializeEnhancedLocalization();
        
        // Registrar el listener de eventos para demostrar los sistemas implementados
        getProxy().getPluginManager().registerListener(this, new ExampleBungeeListener(localizationExample));
        
        // EJEMPLOS DE USO DE SISTEMAS DEL FRAMEWORK
        demonstrateFrameworkFeatures();
        
        // Tu lógica de plugin aquí
        getLogger().info("Plugin de ejemplo habilitado usando BasePlugin Framework!");
    }
    
    /**
     * Demonstrate various framework features
     */
    private void demonstrateFrameworkFeatures() {
        getLogger().info("=== Demonstrating Framework Features ===");
        
        demonstrateConfigurationSystem();
        demonstrateLocalizationSystem();
        demonstrateMetricsSystem();
        demonstrateMessagingSystem();
        demonstrateEmoteSystem();
        demonstrateEconomySystem();
    }
    
    /**
     * Demonstrate configuration system usage
     */
    private void demonstrateConfigurationSystem() {
        getLogger().info("--- Configuration System Demo ---");
        
        try {
            ConfigurationManager configManager = ConfigurationManager.getInstance();
            
            // Example: Load a configuration file
            FileConfig<?> config = configManager.getConfig("example.yml");
            
            // Example: Set and get configuration values
            config.set("server.name", "Example BungeeCord Server");
            config.set("server.max-players", 100);
            config.set("features.economy.enabled", true);
            
            String serverName = (String) config.get("server.name", "Default Server");
            int maxPlayers = (Integer) config.get("server.max-players", 50);
            boolean economyEnabled = (Boolean) config.get("features.economy.enabled", false);
            
            getLogger().info("Server Name: " + serverName);
            getLogger().info("Max Players: " + maxPlayers);
            getLogger().info("Economy Enabled: " + economyEnabled);
            
            // Save configuration
            config.saveData();
            
        } catch (Exception e) {
            getLogger().severe("Error demonstrating configuration system: " + e.getMessage());
        }
    }
    
    /**
     * Initialize the enhanced localization system with player UUID support
     */
    private void initializeEnhancedLocalization() {
        try {
            LocalizationManager locManager = LocalizationManager.getInstance();
            
            // Load language files
            locManager.loadLanguage("en", "lang_en.yml");
            locManager.loadLanguage("es", "lang_es.yml");
            locManager.loadLanguage("fr", "lang_fr.yml");
            
            // Set default language
            locManager.setCurrentLanguage("en");
            
            // Initialize helper classes
            this.localizationExample = new LocalizationExample();
            this.localizationHelper = new PlayerLocalizationHelper();
            
            getLogger().info("Enhanced localization system initialized with languages: " + 
                    locManager.getAvailableLanguages());
            
        } catch (Exception e) {
            getLogger().severe("Failed to initialize enhanced localization system: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrate enhanced localization system usage with player UUID support
     */
    private void demonstrateLocalizationSystem() {
        getLogger().info("--- Enhanced Localization System Demo ---");
        
        try {
            LocalizationManager locManager = LocalizationManager.getInstance();
            
            // Example: Get localized messages in default language
            String welcomeMessage = locManager.getMessage("welcome.message", "Welcome to the server!");
            String goodbyeMessage = locManager.getMessage("goodbye.message", "Thanks for playing!");
            
            getLogger().info("Welcome Message (EN): " + welcomeMessage);
            getLogger().info("Goodbye Message (EN): " + goodbyeMessage);
            
            // Example: Get localized messages in different languages
            String welcomeES = locManager.getMessage("es", "welcome.message", "¡Bienvenido al servidor!");
            String welcomeFR = locManager.getMessage("fr", "welcome.message", "Bienvenue sur le serveur!");
            
            getLogger().info("Welcome Message (ES): " + welcomeES);
            getLogger().info("Welcome Message (FR): " + welcomeFR);
            
            // Example: Show available languages
            getLogger().info("Available languages: " + localizationHelper.getAvailableLanguages());
            
            // Example: Show language statistics
            String stats = localizationHelper.getFormattedLanguageStatistics();
            getLogger().info("Language Statistics:\n" + stats);
            
            // Example: Demonstrate player-specific localization (simulated)
            java.util.UUID testUUID = java.util.UUID.randomUUID();
            localizationHelper.setupPlayerLanguage(testUUID, "es", "TestPlayer")
                .thenAccept(success -> {
                    if (success) {
                        getLogger().info("Test player language set to Spanish");
                        
                        // Get message in player's language
                        String playerMessage = locManager.getPlayerMessage(testUUID, "welcome.message", "Welcome!");
                        getLogger().info("Player-specific message: " + playerMessage);
                    }
                });
            
        } catch (Exception e) {
            getLogger().severe("Error demonstrating enhanced localization system: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrate metrics system usage
     */
    private void demonstrateMetricsSystem() {
        getLogger().info("--- Metrics System Demo ---");
        
        try {
            ConfigurationManager configManager = ConfigurationManager.getInstance();
            FileConfig<?> metricsConfig = configManager.getConfig("metrics.yml");
            
            // Check if metrics are enabled
            boolean metricsEnabled = (Boolean) metricsConfig.get("metrics.enabled", true);
            int collectionInterval = (Integer) metricsConfig.get("metrics.collection-interval", 30);
            
            getLogger().info("Metrics Enabled: " + metricsEnabled);
            getLogger().info("Collection Interval: " + collectionInterval + " seconds");
            
            if (metricsEnabled) {
                // Example: Log performance metrics
                long startTime = System.currentTimeMillis();
                
                // Simulate some work
                Thread.sleep(10);
                
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                
                getLogger().info("Example operation took: " + executionTime + "ms");
                getLogger().info("Connected players: " + getProxy().getOnlineCount());
                getLogger().info("Available servers: " + getProxy().getServers().size());
                
                // Example: Memory usage
                Runtime runtime = Runtime.getRuntime();
                long totalMemory = runtime.totalMemory();
                long freeMemory = runtime.freeMemory();
                long usedMemory = totalMemory - freeMemory;
                
                getLogger().info("Memory Usage: " + (usedMemory / 1024 / 1024) + "MB / " + (totalMemory / 1024 / 1024) + "MB");
                
                // Check memory threshold
                double memoryUsagePercent = (double) usedMemory / totalMemory * 100;
                double threshold = (Double) metricsConfig.get("metrics.alerts.high-memory-threshold", 85.0);
                
                if (memoryUsagePercent > threshold) {
                    getLogger().warning("High memory usage detected: " + String.format("%.2f", memoryUsagePercent) + "%");
                }
                
                // Log custom metrics
                logCustomMetric("bungee.players.online", getProxy().getOnlineCount());
                logCustomMetric("bungee.servers.count", getProxy().getServers().size());
                logCustomMetric("bungee.memory.usage.percent", memoryUsagePercent);
            }
            
        } catch (Exception e) {
            getLogger().severe("Error demonstrating metrics system: " + e.getMessage());
        }
    }
    
    /**
     * Log a custom metric
     */
    private void logCustomMetric(String metricName, Object value) {
        getLogger().info("[METRIC] " + metricName + ": " + value);
        // En una implementación real, aquí se enviarían las métricas a un sistema de monitoreo
    }
    
    /**
     * Demonstrate messaging system usage
     */
    private void demonstrateMessagingSystem() {
        getLogger().info("--- Messaging System Demo ---");
        
        try {
            // Example: Send messages to all players
            if (getProxy().getOnlineCount() > 0) {
                getProxy().getPlayers().forEach(player -> {
                    player.sendMessage(new net.md_5.bungee.api.chat.TextComponent("§aHello from the example plugin!"));
                });
                getLogger().info("Sent welcome message to all online players");
            } else {
                getLogger().info("No players online to send messages to");
            }
            
        } catch (Exception e) {
            getLogger().severe("Error demonstrating messaging system: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrate emote system usage
     */
    private void demonstrateEmoteSystem() {
        getLogger().info("--- Emote System Demo ---");
        
        try {
            ConfigurationManager configManager = ConfigurationManager.getInstance();
            FileConfig<?> emotesConfig = configManager.getConfig("emotes.yml");
            
            boolean emotesEnabled = (Boolean) emotesConfig.get("emotes.enabled", true);
            int cooldown = (Integer) emotesConfig.get("emotes.cooldown", 5);
            
            getLogger().info("Emotes Enabled: " + emotesEnabled);
            getLogger().info("Global Cooldown: " + cooldown + " seconds");
            
            if (emotesEnabled) {
                // List available emotes
                getLogger().info("Available emotes:");
                // Simulate emote listing since FileConfig doesn't support getConfigurationSection
                String[] emoteKeys = {"dance", "wave", "cry", "laugh", "angry"};
                for (String emoteKey : emoteKeys) {
                    String emoteName = (String) emotesConfig.get("emotes.available-emotes." + emoteKey + ".name", emoteKey);
                    String description = (String) emotesConfig.get("emotes.available-emotes." + emoteKey + ".description", "No description");
                    int emoteCooldown = (Integer) emotesConfig.get("emotes.available-emotes." + emoteKey + ".cooldown", cooldown);
                    String permission = (String) emotesConfig.get("emotes.available-emotes." + emoteKey + ".permission", "emotes." + emoteKey);
                    
                    getLogger().info("  - " + emoteName + ": " + description + " (Cooldown: " + emoteCooldown + "s)");
                    getLogger().info("    Permission: " + permission);
                }
                
                // Simulate emote usage tracking
                logCustomMetric("emotes.total.available", 5); // Number of simulated emotes
                logCustomMetric("emotes.system.enabled", emotesEnabled ? 1 : 0);
            }
            
        } catch (Exception e) {
            getLogger().severe("Error demonstrating emote system: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrate economy system usage
     */
    private void demonstrateEconomySystem() {
        getLogger().info("--- Economy System Demo ---");
        
        try {
            ConfigurationManager configManager = ConfigurationManager.getInstance();
            FileConfig<?> economyConfig = configManager.getConfig("economy.yml");
            
            double startingBalance = (Double) economyConfig.get("economy.starting-balance", 1000.0);
            String currencySymbol = (String) economyConfig.get("economy.currency-symbol", "$");
            String currencyName = (String) economyConfig.get("economy.currency-name", "Coins");
            boolean vaultIntegration = (Boolean) economyConfig.get("economy.vault-integration", true);
            
            getLogger().info("Starting Balance: " + currencySymbol + startingBalance);
            getLogger().info("Currency: " + currencyName + " (" + currencySymbol + ")");
            getLogger().info("Vault Integration: " + vaultIntegration);
            
            // Shop configuration
            if ((Boolean) economyConfig.get("economy.shop.enabled", true)) {
                int vipDiscount = (Integer) economyConfig.get("economy.shop.vip-discount", 10);
                int salesTax = (Integer) economyConfig.get("economy.shop.sales-tax", 5);
                
                getLogger().info("Shop System:");
                getLogger().info("  - VIP Discount: " + vipDiscount + "%");
                getLogger().info("  - Sales Tax: " + salesTax + "%");
            }
            
            // Transfer configuration
            if ((Boolean) economyConfig.get("economy.transfers.enabled", true)) {
                double minAmount = (Double) economyConfig.get("economy.transfers.minimum-amount", 1.0);
                double maxAmount = (Double) economyConfig.get("economy.transfers.maximum-amount", 10000.0);
                int feePercentage = (Integer) economyConfig.get("economy.transfers.fee-percentage", 2);
                
                getLogger().info("Transfer System:");
                getLogger().info("  - Min Amount: " + currencySymbol + minAmount);
                getLogger().info("  - Max Amount: " + currencySymbol + maxAmount);
                getLogger().info("  - Fee: " + feePercentage + "%");
            }
            
            // Bank configuration
            if ((Boolean) economyConfig.get("economy.bank.enabled", true)) {
                double interestRate = (Double) economyConfig.get("economy.bank.interest-rate", 5.0);
                double depositLimit = (Double) economyConfig.get("economy.bank.deposit-limit", 100000.0);
                
                getLogger().info("Bank System:");
                getLogger().info("  - Interest Rate: " + interestRate + "% annually");
                getLogger().info("  - Deposit Limit: " + currencySymbol + depositLimit);
            }
            
            // Simulate economy metrics
            logCustomMetric("economy.starting.balance", startingBalance);
            logCustomMetric("economy.vault.integration", vaultIntegration ? 1 : 0);
            logCustomMetric("economy.systems.enabled", 
                ((Boolean) economyConfig.get("economy.shop.enabled", true) ? 1 : 0) +
                ((Boolean) economyConfig.get("economy.transfers.enabled", true) ? 1 : 0) +
                ((Boolean) economyConfig.get("economy.bank.enabled", true) ? 1 : 0)
            );
            
        } catch (Exception e) {
            getLogger().severe("Error demonstrating economy system: " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        // Deshabilitar la API de forma segura
        BaseAPIFactory.getAPI().onDisable();
        getLogger().info("Plugin de ejemplo deshabilitado.");
    }

    @Override
    public InputStream getResource(String filename) {
        return getResourceAsStream(filename);
    }

    @Override
    public void saveResource(String resourcePath, boolean replace) {
        Path outputPath = getDataFolder().toPath().resolve(resourcePath);
        if (!replace && Files.exists(outputPath)) {
            return;
        }

        try (InputStream in = getResource(resourcePath)) {
            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + resourcePath);
            }
            Files.createDirectories(outputPath.getParent());
            Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            getLogger().severe("Error saving resource " + resourcePath + ": " + e.getMessage());
        }
    }

    public ProxyServer getServer() {
        return ProxyServer.getInstance();
    }

    @Override
    public Object getServerInstance() {
        return getProxy();
    }

    @Override
    public Description getPluginDescription() {
        PluginDescription des = super.getDescription();
        return new Description(
                des.getName(),
                des.getMain(),
                des.getVersion(),
                des.getAuthor(),
                des.getFile(),
                des.getDescription()
        );
    }

    @Override
    public String getVersionServer() {
        return getProxy().getVersion().split("\\:")[3];
    }

    @Override
    public ConfigAdapterBungee getConfigAdapter() {
        return configAdapter;
    }
}