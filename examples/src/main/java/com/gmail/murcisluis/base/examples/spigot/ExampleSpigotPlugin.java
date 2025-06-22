package com.gmail.murcisluis.base.examples.spigot;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.common.api.utils.scheduler.S;
import com.gmail.murcisluis.base.spigot.api.commands.AbstractCommandSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandManagerSpigot;
import com.gmail.murcisluis.base.spigot.api.utils.config.ConfigAdapterSpigot;
import com.gmail.murcisluis.base.spigot.api.utils.scheduler.SchedulerSpigot;
import com.gmail.murcisluis.base.examples.spigot.commands.ExampleCommandSpigot;
import com.gmail.murcisluis.base.spigot.api.BaseSpigotAPI;
import com.gmail.murcisluis.base.examples.spigot.ExampleBaseSpigotImpl;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import com.gmail.murcisluis.base.common.api.Description;
import com.gmail.murcisluis.base.spigot.api.BaseSpigot;
import com.gmail.murcisluis.base.common.api.config.ConfigurationManager;
import com.gmail.murcisluis.base.common.api.localization.LocalizationManager;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * EJEMPLO de implementación de un plugin Spigot usando BasePlugin Framework.
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
public final class ExampleSpigotPlugin extends JavaPlugin implements BasePlugin {

    private ConfigAdapterSpigot configAdapter;

    @Override
    public void onLoad() {
        // Inicializar el framework con nuestra implementación personalizada
        configAdapter = new ConfigAdapterSpigot();
        BaseAPIFactory.initialize(new BaseSpigotAPI<>(ExampleBaseSpigotImpl.class));
        BaseAPIFactory.getAPI().onLoad(this);
    }

    @Override
    public void onEnable() {
        // Habilitar la API
        BaseAPIFactory.getAPI().onEnable();
        S.setInstance(new SchedulerSpigot());
        
        // Obtener nuestra implementación personalizada
        @SuppressWarnings("unchecked")
        BaseSpigotAPI<ExampleBaseSpigotImpl> api = (BaseSpigotAPI<ExampleBaseSpigotImpl>) BaseAPIFactory.getAPI();
        ExampleBaseSpigotImpl base = api.get();
        CommandManagerSpigot commandManager = base.getCommandManager();

        // EJEMPLO: Registrar un comando personalizado (OPCIONAL)
        // Puedes omitir esto si no necesitas comandos
        AbstractCommandSpigot mainCommand = new ExampleCommandSpigot();
        commandManager.setMainCommand(mainCommand);
        
        // Registrar el listener de eventos para demostrar los sistemas implementados
        getServer().getPluginManager().registerEvents(new ExampleSpigotListener(), this);
        commandManager.registerCommand(mainCommand);
        
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
        demonstrateVaultIntegration();
    }
    
    /**
     * Demonstrate configuration system usage
     */
    private void demonstrateConfigurationSystem() {
        getLogger().info("--- Configuration System Demo ---");
        
        try {
            ConfigurationManager configManager = ConfigurationManager.getInstance();
             FileConfig<?> config = configManager.getConfig("example.yml");
             
             // Example: Set and get configuration values
             config.set("server.name", "Example Spigot Server");
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
     * Demonstrate localization system usage
     */
    private void demonstrateLocalizationSystem() {
        getLogger().info("--- Localization System Demo ---");
        
        try {
            LocalizationManager locManager = LocalizationManager.getInstance();
            
            // Example: Get localized messages
            String welcomeMessage = locManager.getMessage("welcome.message", "Welcome to the server!");
            String goodbyeMessage = locManager.getMessage("goodbye.message", "Thanks for playing!");
            
            getLogger().info("Welcome Message: " + welcomeMessage);
            getLogger().info("Goodbye Message: " + goodbyeMessage);
            
            // Example: Get localized message with placeholders
            String playerJoinMessage = locManager.getMessage("player.join", "{player} has joined the server!");
            getLogger().info("Player Join Template: " + playerJoinMessage);
            
        } catch (Exception e) {
            getLogger().severe("Error demonstrating localization system: " + e.getMessage());
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
                getLogger().info("Online players: " + getServer().getOnlinePlayers().size());
                getLogger().info("Loaded worlds: " + getServer().getWorlds().size());
                getLogger().info("Loaded chunks: " + getServer().getWorlds().stream().mapToInt(world -> world.getLoadedChunks().length).sum());
                
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
                logCustomMetric("spigot.players.online", getServer().getOnlinePlayers().size());
                logCustomMetric("spigot.worlds.count", getServer().getWorlds().size());
                logCustomMetric("spigot.memory.usage.percent", memoryUsagePercent);
                
                // TPS monitoring (simulated since getTPS() may not be available in all versions)
                try {
                    // Use reflection to access getTPS() method if available
                    java.lang.reflect.Method getTpsMethod = getServer().getClass().getMethod("getTPS");
                    double[] tps = (double[]) getTpsMethod.invoke(getServer());
                    if (tps.length > 0) {
                        logCustomMetric("spigot.tps.1min", tps[0]);
                        logCustomMetric("spigot.tps.5min", tps[1]);
                        logCustomMetric("spigot.tps.15min", tps[2]);
                        if (tps[0] < (Double) metricsConfig.get("metrics.alerts.low-tps-threshold", 18.0)) {
                            getLogger().warning("Low TPS detected: " + String.format("%.2f", tps[0]));
                        }
                    }
                } catch (Exception e) {
                    getLogger().info("TPS monitoring not available in this server version, using simulated values");
                    // Server performance metrics (simulated since getTPS() may not be available)
                    double simulatedTPS = 20.0; // Simulate perfect TPS
                    logCustomMetric("server.tps.1min", simulatedTPS);
                    logCustomMetric("server.tps.5min", simulatedTPS);
                    logCustomMetric("server.tps.15min", simulatedTPS);
                }
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
            if (!getServer().getOnlinePlayers().isEmpty()) {
                for (Player player : getServer().getOnlinePlayers()) {
                    player.sendMessage("§aHello from the example plugin!");
                }
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
             boolean particlesEnabled = (Boolean) emotesConfig.get("emotes.particles", true);
             boolean soundsEnabled = (Boolean) emotesConfig.get("emotes.sounds", true);
            
            getLogger().info("Emotes Enabled: " + emotesEnabled);
            getLogger().info("Global Cooldown: " + cooldown + " seconds");
            getLogger().info("Particles Enabled: " + particlesEnabled);
            getLogger().info("Sounds Enabled: " + soundsEnabled);
            
            if (emotesEnabled) {
                // List available emotes
                 if (emotesConfig.contains("emotes.available-emotes")) {
                     getLogger().info("Available emotes:");
                     // Simulate emote listing since we can't access ConfigurationSection directly
                     String[] emoteKeys = {"dance", "wave", "cry", "laugh", "angry"};
                     for (String emoteKey : emoteKeys) {
                         String emoteName = (String) emotesConfig.get("emotes.available-emotes." + emoteKey + ".name", emoteKey);
                         String description = (String) emotesConfig.get("emotes.available-emotes." + emoteKey + ".description", "No description");
                         int emoteCooldown = (Integer) emotesConfig.get("emotes.available-emotes." + emoteKey + ".cooldown", cooldown);
                         String permission = (String) emotesConfig.get("emotes.available-emotes." + emoteKey + ".permission", "emotes." + emoteKey);
                         
                         getLogger().info("  - " + emoteName + ": " + description);
                         getLogger().info("    Cooldown: " + emoteCooldown + "s, Permission: " + permission);
                     }
                 }
                
                // GUI configuration
                 if ((Boolean) emotesConfig.get("emotes.gui.enabled", true)) {
                     String guiTitle = (String) emotesConfig.get("emotes.gui.title", "🎭 Selector de Emotes");
                     int guiSize = (Integer) emotesConfig.get("emotes.gui.size", 27);
                    
                    getLogger().info("Emote GUI:");
                    getLogger().info("  - Title: " + guiTitle);
                    getLogger().info("  - Size: " + guiSize + " slots");
                }
                
                // Simulate emote usage tracking
                 logCustomMetric("emotes.total.available", 5); // Simulated count
                logCustomMetric("emotes.system.enabled", emotesEnabled ? 1 : 0);
                logCustomMetric("emotes.particles.enabled", particlesEnabled ? 1 : 0);
                logCustomMetric("emotes.sounds.enabled", soundsEnabled ? 1 : 0);
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
            
            // Jobs configuration
             if ((Boolean) economyConfig.get("economy.jobs.enabled", true)) {
                 double baseSalary = (Double) economyConfig.get("economy.jobs.base-salary", 50.0);
                 double experienceBonus = (Double) economyConfig.get("economy.jobs.experience-bonus", 1.5);
                
                getLogger().info("Jobs System:");
                getLogger().info("  - Base Salary: " + currencySymbol + baseSalary + " per hour");
                getLogger().info("  - Experience Bonus: x" + experienceBonus);
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
                 ((Boolean) economyConfig.get("economy.bank.enabled", true) ? 1 : 0) +
                 ((Boolean) economyConfig.get("economy.jobs.enabled", true) ? 1 : 0)
             );
            
        } catch (Exception e) {
            getLogger().severe("Error demonstrating economy system: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrate Vault integration
     */
    private void demonstrateVaultIntegration() {
        getLogger().info("--- Vault Integration Demo ---");
        
        try {
            // Check if Vault is available
            if (getServer().getPluginManager().getPlugin("Vault") != null) {
                getLogger().info("Vault plugin detected");
                // Here you would typically set up economy, permissions, etc.
                getLogger().info("Economy integration: Available");
                getLogger().info("Permissions integration: Available");
                getLogger().info("Chat integration: Available");
                
                // Simulate Vault metrics
                logCustomMetric("vault.plugin.detected", 1);
                logCustomMetric("vault.economy.available", 1);
                logCustomMetric("vault.permissions.available", 1);
            } else {
                getLogger().info("Vault plugin not found - economy features will be limited");
                logCustomMetric("vault.plugin.detected", 0);
            }
            
        } catch (Exception e) {
            getLogger().severe("Error demonstrating Vault integration: " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        // Deshabilitar la API de forma segura
        BaseAPIFactory.getAPI().onDisable();
        getLogger().info("Plugin de ejemplo deshabilitado.");
    }

    @Override
    public Description getPluginDescription() {
        PluginDescriptionFile des = super.getDescription();
        return new Description(
                des.getName(),
                des.getMain(),
                des.getVersion(),
                des.getAuthors().isEmpty() ? "Unknown" : des.getAuthors().get(0),
                getDataFolder(),
                des.getDescription()
        );
    }

    @Override
    public Object getServerInstance() {
        return getServer();
    }

    @Override
    public String getVersionServer() {
        String[] parts = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
        return parts.length > 3 ? parts[3] : "unknown";
    }

    @Override
    public ConfigAdapterSpigot getConfigAdapter() {
        return configAdapter;
    }
}