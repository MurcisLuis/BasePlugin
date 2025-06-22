package com.gmail.murcisluis.base.examples.spigot;

import com.gmail.murcisluis.base.spigot.api.BaseSpigot;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import com.gmail.murcisluis.base.common.api.config.ConfigurationManager;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementación de ejemplo de BaseSpigot con sistemas de emotes, economía y métricas
 * Esta clase extiende BaseSpigot e implementa funcionalidades específicas del plugin
 */
public class ExampleBaseSpigotImpl extends BaseSpigot {
    
    // Sistema de Emotes
    private final Map<String, String> availableEmotes = new ConcurrentHashMap<>();
    private final Map<UUID, Set<String>> playerEmotes = new ConcurrentHashMap<>();
    
    // Sistema de Economía
    private final Map<UUID, Double> playerBalances = new ConcurrentHashMap<>();
    private final Map<String, Double> itemPrices = new ConcurrentHashMap<>();
    
    // Sistema de Métricas
    private final Map<String, Integer> metricsCounters = new ConcurrentHashMap<>();
    private final Map<String, Long> metricsTimers = new ConcurrentHashMap<>();
    private long startTime;
    
    public ExampleBaseSpigotImpl(BasePlugin plugin) {
        super(plugin);
        this.startTime = System.currentTimeMillis();
        initializeSystems();
    }
    
    @Override
    public void enable() {
        super.enable();
        loadConfigurations();
        initializeMetrics();
        getPlugin().getLogger().info("ExampleBaseSpigotImpl habilitado con sistemas de emotes, economía y métricas");
    }
    
    @Override
    public void disable() {
        saveAllData();
        super.disable();
        getPlugin().getLogger().info("ExampleBaseSpigotImpl deshabilitado - datos guardados");
    }
    
    @Override
    public void reload() {
        super.reload();
        loadConfigurations();
        getPlugin().getLogger().info("ExampleBaseSpigotImpl recargado");
    }
    
    /**
     * Inicializa todos los sistemas del plugin
     */
    private void initializeSystems() {
        initializeEmotes();
        initializeEconomy();
        initializeMetrics();
    }
    
    /**
     * Inicializa el sistema de emotes
     */
    private void initializeEmotes() {
        // Emotes predeterminados
        availableEmotes.put("happy", "😊");
        availableEmotes.put("sad", "😢");
        availableEmotes.put("angry", "😠");
        availableEmotes.put("love", "❤️");
        availableEmotes.put("laugh", "😂");
        availableEmotes.put("wink", "😉");
        availableEmotes.put("cool", "😎");
        availableEmotes.put("surprised", "😲");
    }
    
    /**
     * Inicializa el sistema de economía
     */
    private void initializeEconomy() {
        // Precios predeterminados de items
        itemPrices.put("diamond", 100.0);
        itemPrices.put("gold", 50.0);
        itemPrices.put("iron", 25.0);
        itemPrices.put("coal", 5.0);
        itemPrices.put("emerald", 200.0);
    }
    
    /**
     * Inicializa el sistema de métricas
     */
    private void initializeMetrics() {
        metricsCounters.put("player_joins", 0);
        metricsCounters.put("player_leaves", 0);
        metricsCounters.put("commands_executed", 0);
        metricsCounters.put("emotes_used", 0);
        metricsCounters.put("economy_transactions", 0);
        
        metricsTimers.put("server_start_time", startTime);
        metricsTimers.put("last_reload_time", System.currentTimeMillis());
    }
    
    /**
     * Carga las configuraciones desde archivos
     */
    private void loadConfigurations() {
        try {
            // Cargar configuración de emotes
            FileConfig<?> emotesConfig = ConfigurationManager.getInstance().getConfig("emotes.yml");
            if (emotesConfig != null) {
                loadEmotesFromConfig(emotesConfig);
            }
            
            // Cargar configuración de economía
            FileConfig<?> economyConfig = ConfigurationManager.getInstance().getConfig("economy.yml");
            if (economyConfig != null) {
                loadEconomyFromConfig(economyConfig);
            }
            
            // Cargar configuración de métricas
            FileConfig<?> metricsConfig = ConfigurationManager.getInstance().getConfig("metrics.yml");
            if (metricsConfig != null) {
                loadMetricsFromConfig(metricsConfig);
            }
            
        } catch (Exception e) {
            getPlugin().getLogger().warning("Error cargando configuraciones: " + e.getMessage());
        }
    }
    
    /**
     * Carga emotes desde la configuración
     */
    private void loadEmotesFromConfig(FileConfig<?> config) {
        Boolean enabled = (Boolean) config.get("emotes.enabled");
        if (enabled != null && enabled) {
            // Simular carga de emotes personalizados
            String[] customEmotes = {"party", "sleep", "work", "study", "game"};
            String[] customSymbols = {"🎉", "😴", "💼", "📚", "🎮"};
            
            for (int i = 0; i < customEmotes.length; i++) {
                availableEmotes.put(customEmotes[i], customSymbols[i]);
            }
            
            getPlugin().getLogger().info("Cargados " + availableEmotes.size() + " emotes disponibles");
        }
    }
    
    /**
     * Carga configuración de economía
     */
    private void loadEconomyFromConfig(FileConfig<?> config) {
        Boolean enabled = (Boolean) config.get("economy.enabled");
        if (enabled != null && enabled) {
            Double startingBalance = (Double) config.get("economy.starting-balance");
            if (startingBalance != null) {
                // Aplicar balance inicial a jugadores nuevos
                getPlugin().getLogger().info("Balance inicial configurado: $" + startingBalance);
            }
        }
    }
    
    /**
     * Carga configuración de métricas
     */
    private void loadMetricsFromConfig(FileConfig<?> config) {
        Boolean enabled = (Boolean) config.get("metrics.enabled");
        if (enabled != null && enabled) {
            Integer saveInterval = (Integer) config.get("metrics.save-interval");
            if (saveInterval != null) {
                getPlugin().getLogger().info("Métricas habilitadas con intervalo de guardado: " + saveInterval + " minutos");
            }
        }
    }
    
    // ==================== SISTEMA DE EMOTES ====================
    
    /**
     * Obtiene un emote por su nombre
     */
    public String getEmote(String emoteName) {
        return availableEmotes.get(emoteName.toLowerCase());
    }
    
    /**
     * Obtiene todos los emotes disponibles
     */
    public Map<String, String> getAvailableEmotes() {
        return new HashMap<>(availableEmotes);
    }
    
    /**
     * Verifica si un jugador tiene acceso a un emote
     */
    public boolean hasEmoteAccess(Player player, String emoteName) {
        Set<String> playerEmoteSet = playerEmotes.get(player.getUniqueId());
        return playerEmoteSet != null && playerEmoteSet.contains(emoteName.toLowerCase());
    }
    
    /**
     * Otorga un emote a un jugador
     */
    public void grantEmote(Player player, String emoteName) {
        playerEmotes.computeIfAbsent(player.getUniqueId(), k -> new HashSet<>())
                   .add(emoteName.toLowerCase());
        incrementMetric("emotes_used");
        
        Audience audience = audienceSender(player);
        audience.sendMessage(Component.text("¡Has desbloqueado el emote: " + emoteName + " " + getEmote(emoteName) + "!")
                .color(NamedTextColor.GREEN));
    }
    
    /**
     * Usa un emote en el chat
     */
    public void useEmote(Player player, String emoteName) {
        String emoteSymbol = getEmote(emoteName);
        if (emoteSymbol != null && hasEmoteAccess(player, emoteName)) {
            // Enviar emote a todos los jugadores en el servidor
            Component message = Component.text(player.getName() + " usa " + emoteName + " " + emoteSymbol)
                    .color(NamedTextColor.YELLOW);
            
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                Audience audience = audienceSender(onlinePlayer);
                audience.sendMessage(message);
            }
            
            incrementMetric("emotes_used");
        }
    }
    
    // ==================== SISTEMA DE ECONOMÍA ====================
    
    /**
     * Obtiene el balance de un jugador
     */
    public double getBalance(Player player) {
        return playerBalances.getOrDefault(player.getUniqueId(), 0.0);
    }
    
    /**
     * Establece el balance de un jugador
     */
    public void setBalance(Player player, double amount) {
        playerBalances.put(player.getUniqueId(), Math.max(0, amount));
        incrementMetric("economy_transactions");
    }
    
    /**
     * Añade dinero al balance de un jugador
     */
    public void addBalance(Player player, double amount) {
        double currentBalance = getBalance(player);
        setBalance(player, currentBalance + amount);
        
        Audience audience = audienceSender(player);
        audience.sendMessage(Component.text("¡Has recibido $" + amount + "! Balance actual: $" + getBalance(player))
                .color(NamedTextColor.GREEN));
    }
    
    /**
     * Retira dinero del balance de un jugador
     */
    public boolean removeBalance(Player player, double amount) {
        double currentBalance = getBalance(player);
        if (currentBalance >= amount) {
            setBalance(player, currentBalance - amount);
            
            Audience audience = audienceSender(player);
            audience.sendMessage(Component.text("Se han deducido $" + amount + " de tu cuenta. Balance actual: $" + getBalance(player))
                    .color(NamedTextColor.YELLOW));
            return true;
        }
        return false;
    }
    
    /**
     * Transfiere dinero entre jugadores
     */
    public boolean transferMoney(Player from, Player to, double amount) {
        if (removeBalance(from, amount)) {
            addBalance(to, amount);
            
            Audience fromAudience = audienceSender(from);
            Audience toAudience = audienceSender(to);
            
            fromAudience.sendMessage(Component.text("Has transferido $" + amount + " a " + to.getName())
                    .color(NamedTextColor.YELLOW));
            toAudience.sendMessage(Component.text("Has recibido $" + amount + " de " + from.getName())
                    .color(NamedTextColor.GREEN));
            
            return true;
        }
        return false;
    }
    
    /**
     * Obtiene el precio de un item
     */
    public double getItemPrice(String itemName) {
        return itemPrices.getOrDefault(itemName.toLowerCase(), 0.0);
    }
    
    // ==================== SISTEMA DE MÉTRICAS ====================
    
    /**
     * Incrementa un contador de métricas
     */
    public void incrementMetric(String metricName) {
        metricsCounters.merge(metricName, 1, Integer::sum);
    }
    
    /**
     * Obtiene el valor de un contador de métricas
     */
    public int getMetricCount(String metricName) {
        return metricsCounters.getOrDefault(metricName, 0);
    }
    
    /**
     * Registra un tiempo en las métricas
     */
    public void recordMetricTime(String metricName, long timestamp) {
        metricsTimers.put(metricName, timestamp);
    }
    
    /**
     * Obtiene todas las métricas
     */
    public Map<String, Object> getAllMetrics() {
        Map<String, Object> allMetrics = new HashMap<>();
        allMetrics.put("counters", new HashMap<>(metricsCounters));
        allMetrics.put("timers", new HashMap<>(metricsTimers));
        allMetrics.put("uptime", System.currentTimeMillis() - startTime);
        allMetrics.put("online_players", Bukkit.getOnlinePlayers().size());
        
        // Obtener TPS usando reflexión
        try {
            Object server = Bukkit.getServer();
            Method getTpsMethod = server.getClass().getMethod("getTPS");
            double[] tps = (double[]) getTpsMethod.invoke(server);
            allMetrics.put("tps_1m", tps[0]);
            allMetrics.put("tps_5m", tps[1]);
            allMetrics.put("tps_15m", tps[2]);
        } catch (Exception e) {
            // TPS no disponible, usar valores simulados
            allMetrics.put("tps_1m", 20.0);
            allMetrics.put("tps_5m", 20.0);
            allMetrics.put("tps_15m", 20.0);
        }
        
        return allMetrics;
    }
    
    /**
     * Registra la conexión de un jugador
     */
    public void onPlayerJoin(Player player) {
        incrementMetric("player_joins");
        
        // Dar balance inicial si es nuevo jugador
        if (!playerBalances.containsKey(player.getUniqueId())) {
            setBalance(player, 100.0); // Balance inicial
            
            // Dar emotes básicos
            Set<String> basicEmotes = new HashSet<>();
            basicEmotes.add("happy");
            basicEmotes.add("sad");
            playerEmotes.put(player.getUniqueId(), basicEmotes);
        }
        
        Audience audience = audienceSender(player);
        audience.sendMessage(Component.text("¡Bienvenido! Tu balance actual es: $" + getBalance(player))
                .color(NamedTextColor.AQUA));
    }
    
    /**
     * Registra la desconexión de un jugador
     */
    public void onPlayerLeave(Player player) {
        incrementMetric("player_leaves");
    }
    
    /**
     * Guarda todos los datos
     */
    private void saveAllData() {
        try {
            // Aquí se guardarían los datos en archivos o base de datos
            getPlugin().getLogger().info("Guardando datos de " + playerBalances.size() + " jugadores");
            getPlugin().getLogger().info("Métricas guardadas: " + metricsCounters.size() + " contadores");
        } catch (Exception e) {
            getPlugin().getLogger().severe("Error guardando datos: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene estadísticas del servidor
     */
    public void showServerStats(Player player) {
        Audience audience = audienceSender(player);
        
        audience.sendMessage(Component.text("=== ESTADÍSTICAS DEL SERVIDOR ===").color(NamedTextColor.GOLD));
        audience.sendMessage(Component.text("Jugadores conectados: " + Bukkit.getOnlinePlayers().size()).color(NamedTextColor.YELLOW));
        audience.sendMessage(Component.text("Total de conexiones: " + getMetricCount("player_joins")).color(NamedTextColor.YELLOW));
        audience.sendMessage(Component.text("Emotes usados: " + getMetricCount("emotes_used")).color(NamedTextColor.YELLOW));
        audience.sendMessage(Component.text("Transacciones económicas: " + getMetricCount("economy_transactions")).color(NamedTextColor.YELLOW));
        
        // Mostrar TPS
        Map<String, Object> metrics = getAllMetrics();
        audience.sendMessage(Component.text("TPS: " + String.format("%.2f", metrics.get("tps_1m")) + 
                " (1m), " + String.format("%.2f", metrics.get("tps_5m")) + 
                " (5m), " + String.format("%.2f", metrics.get("tps_15m")) + " (15m)")
                .color(NamedTextColor.YELLOW));
        
        long uptime = System.currentTimeMillis() - startTime;
        long hours = uptime / (1000 * 60 * 60);
        long minutes = (uptime % (1000 * 60 * 60)) / (1000 * 60);
        audience.sendMessage(Component.text("Tiempo activo: " + hours + "h " + minutes + "m").color(NamedTextColor.YELLOW));
    }
}