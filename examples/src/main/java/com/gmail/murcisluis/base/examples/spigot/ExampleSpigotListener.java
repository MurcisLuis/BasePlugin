package com.gmail.murcisluis.base.examples.spigot;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.spigot.api.BaseSpigotAPI;
import com.gmail.murcisluis.base.examples.spigot.ExampleBaseSpigotImpl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Listener de eventos para demostrar el uso de los sistemas implementados
 * en ExampleBaseSpigotImpl
 */
public class ExampleSpigotListener implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        try {
            // Usar los sistemas implementados en nuestra clase personalizada
            @SuppressWarnings("unchecked")
            BaseSpigotAPI<ExampleBaseSpigotImpl> api = (BaseSpigotAPI<ExampleBaseSpigotImpl>) BaseAPIFactory.getAPI();
            ExampleBaseSpigotImpl base = api.get();
            base.onPlayerJoin(player);
        } catch (Exception e) {
            // Manejar error si la API no está inicializada
            System.err.println("Error en onPlayerJoin: " + e.getMessage());
        }
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        
        try {
            // Registrar la desconexión en las métricas
            @SuppressWarnings("unchecked")
            BaseSpigotAPI<ExampleBaseSpigotImpl> api = (BaseSpigotAPI<ExampleBaseSpigotImpl>) BaseAPIFactory.getAPI();
            ExampleBaseSpigotImpl base = api.get();
            base.onPlayerLeave(player);
        } catch (Exception e) {
            // Manejar error si la API no está inicializada
            System.err.println("Error en onPlayerQuit: " + e.getMessage());
        }
    }
}