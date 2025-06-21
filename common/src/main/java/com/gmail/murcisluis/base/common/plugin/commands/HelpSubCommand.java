package com.gmail.murcisluis.base.common.plugin.commands;

import com.gmail.murcisluis.base.common.api.commands.CommandBase;
import com.gmail.murcisluis.base.common.api.commands.CommandHandler;
import com.gmail.murcisluis.base.common.api.commands.CommandInfo;
import com.gmail.murcisluis.base.common.api.commands.TabCompleteHandler;
import com.gmail.murcisluis.base.common.api.localization.LocalizationManager;
import com.gmail.murcisluis.base.common.api.utils.Common;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

@CommandInfo(
        permission = "",
        usage = "/bp help",
        description = "Show available commands and their usage."
)
public interface HelpSubCommand<T> extends CommandBase {

    @Override
    default CommandHandler<T> getCommandHandler() {
        return (sender, args) -> {
            String helpHeader = LocalizationManager.getFrameworkMessage("help.header", 
                "<gray>========== <aqua>Available Commands</aqua> ==========</gray>");
            Common.tell(sender, helpHeader);
            
            // Mostrar comandos disponibles
            String helpDesc = LocalizationManager.getFrameworkMessage("command-help-description", "Show this help message");
            String reloadDesc = LocalizationManager.getFrameworkMessage("command-reload-description", "Reload plugin configuration");
            String infoDesc = LocalizationManager.getFrameworkMessage("command-info-description", "Show plugin information");
            String versionDesc = LocalizationManager.getFrameworkMessage("command-version-description", "Show plugin version");
            
            showHelpCommand(sender, "help", helpDesc, "");
            showHelpCommand(sender, "reload", reloadDesc, "bp.admin");
            showHelpCommand(sender, "info", infoDesc, "");
            showHelpCommand(sender, "version", versionDesc, "");
            
            // Permitir que el usuario añada comandos personalizados
            showCustomCommands(sender);
            
            String helpFooter = LocalizationManager.getFrameworkMessage("help.footer", 
                "<gray>==========================================</gray>");
            Common.tell(sender, helpFooter);
            
            return true;
        };
    }
    
    default void showHelpCommand(Object sender, String command, String description, String permission) {
        String prefix = getCommandPrefix();
        String permissionText = permission.isEmpty() ? "" : " <gray>(" + permission + ")</gray>";
        
        String helpLine = LocalizationManager.getFrameworkMessage("help.command-format", 
            "<yellow>/{prefix} {command}</yellow> - <white>{description}</white>{permission}");
            
        Common.tell(sender, helpLine, 
            Placeholder.unparsed("prefix", prefix),
            Placeholder.unparsed("command", command),
            Placeholder.unparsed("description", description),
            Placeholder.unparsed("permission", permissionText)
        );
    }
    
    /**
     * Override this method to add custom commands to the help display
     */
    default void showCustomCommands(Object sender) {
        // Por defecto no hace nada, el usuario puede sobrescribir
    }
    
    /**
     * Get the command prefix (e.g., "bp", "myplugin")
     */
    default String getCommandPrefix() {
        return "bp"; // Por defecto, el usuario puede sobrescribir
    }

    @Override
    default TabCompleteHandler<T> getTabCompleteHandler() {
        return null;
    }
}