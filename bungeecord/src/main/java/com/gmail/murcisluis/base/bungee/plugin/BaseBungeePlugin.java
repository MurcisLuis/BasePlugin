package com.gmail.murcisluis.base.bungee.plugin;

import com.gmail.murcisluis.base.bungee.api.utils.config.ConfigAdapterBungee;
import com.gmail.murcisluis.base.bungee.api.utils.scheduler.SchedulerBungeecord;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import com.gmail.murcisluis.base.common.api.Description;
import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.bungee.api.BaseBungeAPI;
import com.gmail.murcisluis.base.bungee.api.BaseBungee;
import com.gmail.murcisluis.base.bungee.api.commands.AbstractCommandBungee;
import com.gmail.murcisluis.base.bungee.api.commands.CommandManagerBungee;
import com.gmail.murcisluis.base.bungee.plugin.commands.MyCommandBungee;
import com.gmail.murcisluis.base.common.api.utils.config.ConfigAdapter;
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
@Getter
public class BaseBungeePlugin extends Plugin implements BasePlugin {

    private ConfigAdapterBungee configAdapter;

    @Override
    public void onLoad() {
        configAdapter=new ConfigAdapterBungee();
        BaseAPIFactory.initialize(new BaseBungeAPI());
        BaseAPIFactory.getAPI().onLoad(this);
        S.setInstance(new SchedulerBungeecord());

    }
    @Override
    public void onEnable() {
        BaseAPIFactory.getAPI().onEnable();
        BaseBungee base = (BaseBungee) BaseAPIFactory.get();

        CommandManagerBungee commandManager = base.getCommandManager();

        AbstractCommandBungee mainCommand= new MyCommandBungee();
        commandManager.setMainCommand(mainCommand);
        commandManager.registerCommand(mainCommand);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        BaseAPIFactory.getAPI().onDisable();
    }

    @Override
    public InputStream getResource(String filename) {
        return getResourceAsStream(filename);
    }

    @Override
    public void saveResource(String resourcePath, boolean replace) {
        Path outputPath = getDataFolder().toPath().resolve(resourcePath);
        if (!replace && Files.exists(outputPath)) {
            return;  // El archivo ya existe y replace es false, por lo que no sobrescribimos el archivo
        }

        try (InputStream in = getResource(resourcePath)) {
            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + resourcePath);
            }
            Files.createDirectories(outputPath.getParent());  // Asegurar que los directorios existan
            Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);  // Copia el recurso al archivo de salida, reemplazando si es necesario
        } catch (IOException e) {
            e.printStackTrace();  // Manejar la excepci?n como lo desees, aqu? solo se imprime la traza del error
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
