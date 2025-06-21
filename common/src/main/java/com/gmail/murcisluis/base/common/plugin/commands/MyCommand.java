package com.gmail.murcisluis.base.common.plugin.commands;

import com.gmail.murcisluis.base.common.api.commands.CommandBase;
import com.gmail.murcisluis.base.common.api.commands.CommandHandler;
import com.gmail.murcisluis.base.common.api.commands.CommandInfo;
import com.gmail.murcisluis.base.common.api.localization.LocalizationManager;
import com.gmail.murcisluis.base.common.api.utils.Common;
import com.gmail.murcisluis.base.common.api.commands.TabCompleteHandler;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public interface MyCommand<T> extends CommandBase, HelpSubCommand<T>, ReloadSubCommand<T>, InfoSubCommand<T>, VersionSubCommand<T> {

    @Override
    default CommandHandler<T> getCommandHandler() {
        return (sender, args) -> {
            if (args.length == 0) {
                // Mostrar ayuda por defecto cuando no hay argumentos
                return HelpSubCommand.super.getCommandHandler().handle(sender, args);
            }
            
            String subCommand = args[0].toLowerCase();
            String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
            
            // Manejar comandos por defecto del framework
            switch (subCommand) {
                case "help":
                    return HelpSubCommand.super.getCommandHandler().handle(sender, subArgs);
                case "reload":
                    return ReloadSubCommand.super.getCommandHandler().handle(sender, subArgs);
                case "info":
                    return InfoSubCommand.super.getCommandHandler().handle(sender, subArgs);
                case "version":
                    return VersionSubCommand.super.getCommandHandler().handle(sender, subArgs);
                default:
                    // Permitir comandos personalizados
                    if (handleCustomCommand(sender, subCommand, subArgs)) {
                        return true;
                    }
                    
                    // Comando no encontrado
                    String unknownMessage = LocalizationManager.getFrameworkMessage("permissions.unknown-command", 
                        "<red>Unknown command '{command}'. Use /{prefix} help for available commands.</red>");
                    Common.tell(sender, unknownMessage, 
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed("command", subCommand),
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed("prefix", getCommandPrefix()));
                    return true;
            }
        };
    }
    
    /**
     * Handle custom commands defined by the plugin.
     * Override this method to add your own subcommands.
     * 
     * @param sender The command sender
     * @param command The subcommand name
     * @param args The subcommand arguments
     * @return true if the command was handled, false if unknown
     */
    default boolean handleCustomCommand(Object sender, String command, String[] args) {
        return false; // Por defecto no maneja comandos personalizados
    }

    @Override
    default TabCompleteHandler<T> getTabCompleteHandler() {
        return (sender, args) -> {
            if (args.length <= 1) {
                // Autocompletar comandos por defecto
                List<String> completions = new ArrayList<>();
                completions.addAll(Arrays.asList("help", "reload", "info", "version"));
                
                // Añadir comandos personalizados
                completions.addAll(getCustomCommandCompletions());
                
                // Filtrar por lo que el usuario ha escrito
                if (args.length == 1) {
                    String partial = args[0].toLowerCase();
                    completions.removeIf(cmd -> !cmd.toLowerCase().startsWith(partial));
                }
                
                return completions;
            }
            
            // Autocompletar para subcomandos específicos
            String subCommand = args[0].toLowerCase();
            String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
            
            return getCustomTabCompletions(sender, subCommand, subArgs);
        };
    }
    
    /**
     * Get custom command names for tab completion.
     * Override this method to add your custom commands to tab completion.
     */
    default List<String> getCustomCommandCompletions() {
        return new ArrayList<>();
    }
    
    /**
     * Get tab completions for custom commands.
     * Override this method to provide tab completion for your custom subcommands.
     */
    default List<String> getCustomTabCompletions(Object sender, String command, String[] args) {
        return new ArrayList<>();
    }
    
    // Implementaciones por defecto para los métodos requeridos por los subcomandos
    
    @Override
    default void reload() {
        // Recargar configuraciones del framework
        LocalizationManager.getInstance().reloadAllLanguages();
        
        // Permitir recarga personalizada
        performCustomReload();
    }
    
    /**
     * Override this method to add custom reload logic
     */
    default void performCustomReload() {
        // Por defecto no hace nada
    }
}
