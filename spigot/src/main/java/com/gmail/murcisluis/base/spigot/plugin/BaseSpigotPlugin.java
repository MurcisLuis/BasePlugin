package com.gmail.murcisluis.base.spigot.plugin;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.common.api.utils.config.ConfigAdapter;
import com.gmail.murcisluis.base.common.api.utils.scheduler.S;
import com.gmail.murcisluis.base.spigot.api.BaseSpigotAPI;
import com.gmail.murcisluis.base.spigot.api.commands.AbstractCommandSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandManagerSpigot;
import com.gmail.murcisluis.base.spigot.api.utils.config.ConfigAdapterSpigot;
import com.gmail.murcisluis.base.spigot.plugin.commands.MyCommandSpigot;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import com.gmail.murcisluis.base.common.api.Description;
import com.gmail.murcisluis.base.spigot.api.BaseSpigot;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;
@Getter
public final class BaseSpigotPlugin extends JavaPlugin implements BasePlugin {

    private ConfigAdapterSpigot configAdapter;

    @Override
    public void onLoad() {
        configAdapter=new ConfigAdapterSpigot();
        BaseAPIFactory.initialize(new BaseSpigotAPI());
        BaseAPIFactory.getAPI().onLoad(this);

    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        BaseAPIFactory.getAPI().onEnable();
        S.setInstance(new SchedulerSpigot());
        BaseSpigot base = (BaseSpigot) BaseAPIFactory.get();

        CommandManagerSpigot commandManager = base.getCommandManager();

        AbstractCommandSpigot mainCommand= new MyCommandSpigot();
        commandManager.setMainCommand(mainCommand);
        commandManager.registerCommand(mainCommand);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        BaseAPIFactory.getAPI().onDisable();
    }

    @Override
    public Description getPluginDescription() {
        PluginDescriptionFile des = super.getDescription();
        return new Description(
                des.getName(),
                des.getMain(),
                des.getVersion(),
                des.getAuthors().get(0),
                getDataFolder(),
                des.getDescription()
        );
    }

    @Override
    public String getVersionServer() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }


}
