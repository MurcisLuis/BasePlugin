package com.gmail.murcisluis.base.spigot.api.commands;

import com.gmail.murcisluis.base.common.api.utils.reflect.ReflectField;
import com.gmail.murcisluis.base.common.api.utils.reflect.ReflectMethod;
import com.gmail.murcisluis.base.common.api.utils.reflect.ReflectionUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandManagerSpigot {

    private final Map<String, AbstractCommandSpigot> commands = new HashMap<>();
    @Getter
    @Setter
    private AbstractCommandSpigot mainCommand;
    private final JavaPlugin plugin;

    public CommandManagerSpigot(JavaPlugin plugin) {
        this.plugin = plugin;
    }

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
        this.register(command);
    }

    public void unregisterCommand(String name) {
        if (!commands.containsKey(name)) return;
        AbstractCommandSpigot command = commands.remove(name);
        this.unregister(command);
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

    public void register(Command command) {
        if (command == null) return;
        SimpleCommandMap commandMap = GET_COMMAND_MAP_METHOD.invoke(Bukkit.getServer());
        this.unregister(command);
        commandMap.register(plugin.getDescription().getName(), command);
    }

    public void unregister(Command command) {
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