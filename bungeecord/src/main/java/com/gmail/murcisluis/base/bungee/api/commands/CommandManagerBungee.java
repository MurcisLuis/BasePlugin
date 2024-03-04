package com.gmail.murcisluis.base.bungee.api.commands;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandManagerBungee {

    private final Map<String, AbstractCommandBungee> commands = new HashMap<>();
    private AbstractCommandBungee mainCommand;

    /*
     *  General Methods
     */

    public void destroy() {
        if (!commands.isEmpty()) {
            commands.values().forEach(CommandManagerBungee::unregister);
            commands.clear();
        }
    }

    public void registerCommand(AbstractCommandBungee command) {
        if (commands.containsKey(command.getName())) return;
        commands.put(command.getName(), command);
        CommandManagerBungee.register(command);
    }

    public void unregisterCommand(String name) {
        if (!commands.containsKey(name)) return;
        AbstractCommandBungee command = commands.remove(name);
        CommandManagerBungee.unregister(command);
    }

    public void setMainCommand(AbstractCommandBungee command) {
        this.mainCommand = command;
    }

    public AbstractCommandBungee getMainCommand() {
        return mainCommand;
    }

    public Set<String> getCommandNames() {
        return commands.keySet();
    }

    public Collection<AbstractCommandBungee> getCommands() {
        return commands.values();
    }

    /*
     *  Static Methods
     */

    public static void register(Command command) {
        if (command == null) return;
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        pluginManager.registerCommand((Plugin) BaseAPIFactory.getAPI().get().getPlugin(), command);
    }

    public static void unregister(Command command) {
        if (command == null) return;
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        pluginManager.unregisterCommand(command);
    }

}
