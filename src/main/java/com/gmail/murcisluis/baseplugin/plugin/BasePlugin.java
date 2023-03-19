package com.gmail.murcisluis.baseplugin.plugin;

import com.gmail.murcisluis.baseplugin.api.Base;
import com.gmail.murcisluis.baseplugin.api.BaseAPI;
import com.gmail.murcisluis.baseplugin.api.commands.AbstractCommand;
import com.gmail.murcisluis.baseplugin.api.commands.CommandManager;
import com.gmail.murcisluis.baseplugin.plugin.commands.MyCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class BasePlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        BaseAPI.onLoad(this);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Base base = BaseAPI.get();

        BaseAPI.onEnable();
        CommandManager commandManager = base.getCommandManager();
        AbstractCommand mainCommand= new MyCommand();
        commandManager.setMainCommand(mainCommand);
        commandManager.registerCommand(mainCommand);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        BaseAPI.onDisable();
    }


}
