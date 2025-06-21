package com.gmail.murcisluis.base.examples.bungeecord;

import com.gmail.murcisluis.base.bungee.api.utils.config.ConfigAdapterBungee;
import com.gmail.murcisluis.base.bungee.api.utils.scheduler.SchedulerBungeecord;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import com.gmail.murcisluis.base.common.api.Description;
import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.bungee.api.BaseBungeAPI;
import com.gmail.murcisluis.base.bungee.api.BaseBungee;
import com.gmail.murcisluis.base.bungee.api.commands.AbstractCommandBungee;
import com.gmail.murcisluis.base.bungee.api.commands.CommandManagerBungee;
import com.gmail.murcisluis.base.examples.bungeecord.commands.ExampleCommandBungee;
import com.gmail.murcisluis.base.common.api.utils.scheduler.S;
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

    @Override
    public void onLoad() {
        // Inicializar el framework
        configAdapter = new ConfigAdapterBungee();
        BaseAPIFactory.initialize(new BaseBungeAPI());
        BaseAPIFactory.getAPI().onLoad(this);
        S.setInstance(new SchedulerBungeecord());
    }
    
    @Override
    public void onEnable() {
        // Habilitar la API
        BaseAPIFactory.getAPI().onEnable();
        
        // Obtener la instancia del framework
        BaseBungee base = (BaseBungee) BaseAPIFactory.get();
        CommandManagerBungee commandManager = base.getCommandManager();

        // EJEMPLO: Registrar un comando personalizado (OPCIONAL)
        // Puedes omitir esto si no necesitas comandos
        AbstractCommandBungee mainCommand = new ExampleCommandBungee();
        commandManager.setMainCommand(mainCommand);
        commandManager.registerCommand(mainCommand);
        
        // Tu lógica de plugin aquí
        getLogger().info("Plugin de ejemplo habilitado usando BasePlugin Framework!");
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

    @Override
    public ProxyServer getServer() {
        return ProxyServer.getInstance();
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
}