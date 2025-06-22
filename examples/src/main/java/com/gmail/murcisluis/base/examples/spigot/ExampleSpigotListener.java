package com.gmail.murcisluis.base.examples.spigot;

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
            ExampleBaseSpigotImpl base = ExampleBaseSpigotAPI.getExampleImplementation();
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
            ExampleBaseSpigotImpl base = ExampleBaseSpigotAPI.getExampleImplementation();
            base.onPlayerLeave(player);
        } catch (Exception e) {
            // Manejar error si la API no está inicializada
            System.err.println("Error en onPlayerQuit: " + e.getMessage());
        }
    }
}