package com.gmail.murcisluis.base.examples.spigot;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.common.api.utils.scheduler.S;
import com.gmail.murcisluis.base.spigot.api.BaseSpigotAPI;
import com.gmail.murcisluis.base.spigot.api.commands.AbstractCommandSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandManagerSpigot;
import com.gmail.murcisluis.base.spigot.api.utils.config.ConfigAdapterSpigot;
import com.gmail.murcisluis.base.spigot.api.utils.scheduler.SchedulerSpigot;
import com.gmail.murcisluis.base.examples.spigot.commands.ExampleCommandSpigot;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import com.gmail.murcisluis.base.common.api.Description;
import com.gmail.murcisluis.base.spigot.api.BaseSpigot;
import lombok.Getter;
import org.bukkit.Bukkit;
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
        // Inicializar el framework
        configAdapter = new ConfigAdapterSpigot();
        BaseAPIFactory.initialize(new BaseSpigotAPI());
        BaseAPIFactory.getAPI().onLoad(this);
    }

    @Override
    public void onEnable() {
        // Habilitar la API
        BaseAPIFactory.getAPI().onEnable();
        S.setInstance(new SchedulerSpigot());
        
        // Obtener la instancia del framework
        BaseSpigot base = (BaseSpigot) BaseAPIFactory.get();
        CommandManagerSpigot commandManager = base.getCommandManager();

        // EJEMPLO: Registrar un comando personalizado (OPCIONAL)
        // Puedes omitir esto si no necesitas comandos
        AbstractCommandSpigot mainCommand = new ExampleCommandSpigot();
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
    public String getVersionServer() {
        String[] parts = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
        return parts.length > 3 ? parts[3] : "unknown";
    }
}