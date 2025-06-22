package com.gmail.murcisluis.base.examples.bungeecord;

import com.gmail.murcisluis.base.examples.common.LocalizationExample;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Listener de eventos para demostrar el uso de los sistemas implementados
 * en ExampleBaseBungeeImpl con soporte para localización mejorada
 */
public class ExampleBungeeListener implements Listener {
    
    private final LocalizationExample localizationExample;
    
    public ExampleBungeeListener(LocalizationExample localizationExample) {
        this.localizationExample = localizationExample;
    }
    
    @EventHandler
    public void onPlayerJoin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        
        try {
            // Usar los sistemas implementados en nuestra clase personalizada
            ExampleBaseBungeeImpl base = ExampleBaseBungeAPI.getExampleImplementation();
            base.onPlayerJoin(player);
            
            // Handle player join with enhanced localization
            if (localizationExample != null) {
                // Get player's locale from client (if available)
                String clientLocale = "en_US"; // Default, in real implementation get from player.getLocale()
                
                // Handle player join with localization
                localizationExample.handlePlayerJoin(
                    player.getUniqueId(),
                    player.getName(),
                    clientLocale
                );
            }
            
        } catch (Exception e) {
            // Manejar error si la API no está inicializada
            System.err.println("Error en onPlayerJoin: " + e.getMessage());
        }
    }
    
    @EventHandler
    public void onPlayerLeave(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        
        try {
            // Handle player quit with enhanced localization
            if (localizationExample != null) {
                localizationExample.handlePlayerQuit(
                    player.getUniqueId(),
                    player.getName()
                );
            }
            
            // Registrar la desconexión en las métricas
            ExampleBaseBungeeImpl base = ExampleBaseBungeAPI.getExampleImplementation();
            base.onPlayerLeave(player);
            
        } catch (Exception e) {
            // Manejar error si la API no está inicializada
            System.err.println("Error en onPlayerLeave: " + e.getMessage());
        }
    }
}