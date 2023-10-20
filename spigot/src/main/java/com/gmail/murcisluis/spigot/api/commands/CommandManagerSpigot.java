package com.gmail.murcisluis.spigot.api.commands;

import com.gmail.murcisluis.baseplugin.common.api.commands.AbstractCommand;
import com.gmail.murcisluis.baseplugin.common.api.utils.reflect.ReflectField;
import com.gmail.murcisluis.baseplugin.common.api.utils.reflect.ReflectMethod;
import com.gmail.murcisluis.baseplugin.common.api.utils.reflect.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandManagerSpigot {

    private final Map<String, AbstractCommandSpigot> commands = new HashMap<>();
    private AbstractCommandSpigot mainCommand;

    /*
     *  General Methods
     */

    public void destroy() {
        if (!commands.isEmpty()) {
            commands.values().forEach(CommandManagerSpigot::unregister);
            commands.clear();
        }
    }

    public void registerCommand(AbstractCommandSpigot command) {
        if (commands.containsKey(command.getName())) return;
        commands.put(command.getName(), command);
        CommandManagerSpigot.register(command);
    }

    public void unregisterCommand(String name) {
        if (!commands.containsKey(name)) return;
        AbstractCommandSpigot command = commands.remove(name);
        CommandManagerSpigot.unregister(command);
    }

    public void setMainCommand(AbstractCommandSpigot command) {
        this.mainCommand = command;
    }

    public AbstractCommandSpigot getMainCommand() {
        return mainCommand;
    }

    public Set<String> getCommandNames() {
        return commands.keySet();
    }

    public Collection<AbstractCommandSpigot> getCommands() {
        return commands.values();
    }

    /*
     *  Static Methods
     */

    private static final Class<?> CRAFT_SERVER_CLASS;
    private static final ReflectMethod GET_COMMAND_MAP_METHOD;
    private static final ReflectField<Map<String, Command>> COMMAND_MAP_KNOWN_COMMANDS_FIELD;

    static {
        CRAFT_SERVER_CLASS = ReflectionUtil.getObcClass("CraftServer");
        GET_COMMAND_MAP_METHOD = new ReflectMethod(CRAFT_SERVER_CLASS, "getCommandMap");
        COMMAND_MAP_KNOWN_COMMANDS_FIELD = new ReflectField<>(SimpleCommandMap.class, "knownCommands");
    }

    public static void register(Command command) {
        if (command == null) return;
        SimpleCommandMap commandMap = GET_COMMAND_MAP_METHOD.invoke(Bukkit.getServer());
        CommandManagerSpigot.unregister(command);
        commandMap.register("BasePlugin", command);
    }

    public static void unregister(Command command) {
        if (command == null) return;
        SimpleCommandMap commandMap = GET_COMMAND_MAP_METHOD.invoke(Bukkit.getServer());
        Map<String, Command> cmdMap = COMMAND_MAP_KNOWN_COMMANDS_FIELD.getValue(commandMap);
        if (cmdMap != null && !cmdMap.isEmpty()) {
            cmdMap.remove(command.getLabel());
            for (final String alias : command.getAliases()) {
                cmdMap.remove(alias);
            }
        }
    }

}