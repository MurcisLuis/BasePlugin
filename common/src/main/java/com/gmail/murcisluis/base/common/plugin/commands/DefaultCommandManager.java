package com.gmail.murcisluis.base.common.plugin.commands;

import com.gmail.murcisluis.base.common.api.commands.CommandBase;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Manages default framework commands that are automatically available
 * to all plugins using the BasePlugin framework.
 */
public class DefaultCommandManager {
    
    private static final Map<String, Class<? extends CommandBase>> DEFAULT_COMMANDS = new HashMap<>();
    
    static {
        // Registrar comandos por defecto del framework
        registerDefaultCommand("help", HelpSubCommand.class);
        registerDefaultCommand("reload", ReloadSubCommand.class);
        registerDefaultCommand("info", InfoSubCommand.class);
        registerDefaultCommand("version", VersionSubCommand.class);
    }
    
    /**
     * Register a default framework command
     */
    private static void registerDefaultCommand(String name, Class<? extends CommandBase> commandClass) {
        DEFAULT_COMMANDS.put(name.toLowerCase(), commandClass);
    }
    
    /**
     * Get all default command names
     */
    public static Set<String> getDefaultCommandNames() {
        return DEFAULT_COMMANDS.keySet();
    }
    
    /**
     * Check if a command is a default framework command
     */
    public static boolean isDefaultCommand(String commandName) {
        return DEFAULT_COMMANDS.containsKey(commandName.toLowerCase());
    }
    
    /**
     * Get the class for a default command
     */
    public static Class<? extends CommandBase> getDefaultCommandClass(String commandName) {
        return DEFAULT_COMMANDS.get(commandName.toLowerCase());
    }
    
    /**
     * Get all default commands
     */
    public static Map<String, Class<? extends CommandBase>> getAllDefaultCommands() {
        return new HashMap<>(DEFAULT_COMMANDS);
    }
    
    /**
     * Check if user can override a default command
     */
    public static boolean canOverrideCommand(String commandName) {
        // Permitir sobrescribir todos los comandos por defecto
        // excepto 'help' que es crítico para la usabilidad
        return !"help".equalsIgnoreCase(commandName);
    }
    
    /**
     * Get help text for default commands
     */
    public static String getDefaultCommandHelp() {
        StringBuilder help = new StringBuilder();
        help.append("Default Framework Commands:\n");
        help.append("- help: Show available commands\n");
        help.append("- reload: Reload plugin configuration (requires permission)\n");
        help.append("- info: Show plugin information\n");
        help.append("- version: Show version information\n");
        return help.toString();
    }
}