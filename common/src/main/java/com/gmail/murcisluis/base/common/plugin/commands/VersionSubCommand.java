package com.gmail.murcisluis.base.common.plugin.commands;

import com.gmail.murcisluis.base.common.api.commands.CommandBase;
import com.gmail.murcisluis.base.common.api.commands.CommandHandler;
import com.gmail.murcisluis.base.common.api.commands.CommandInfo;
import com.gmail.murcisluis.base.common.api.commands.TabCompleteHandler;
import com.gmail.murcisluis.base.common.api.localization.LocalizationManager;
import com.gmail.murcisluis.base.common.api.localization.InternalMessages;
import com.gmail.murcisluis.base.common.api.utils.Common;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

@CommandInfo(
        permission = "",
        usage = "/bp version",
        description = "Show plugin and framework version information."
)
public interface VersionSubCommand<T> extends CommandBase {

    @Override
    default CommandHandler<T> getCommandHandler() {
        return (sender, args) -> {
            // Enviar mensaje de versión usando LocalizationManager
            LocalizationManager.sendVersionMessage(sender);
            
            // Información adicional del framework
            String frameworkInfo = LocalizationManager.getFrameworkMessage("version.framework-info", 
                "<gray>Framework:</gray> <aqua>BasePlugin Framework</aqua> <yellow>v{version}</yellow>");
            Common.tell(sender, frameworkInfo, 
                Placeholder.unparsed("version", getFrameworkVersion()));
            
            // Información del plugin específico
            String pluginInfo = LocalizationManager.getFrameworkMessage("version.plugin-info", 
                "<gray>Plugin:</gray> <aqua>{name}</aqua> <yellow>v{version}</yellow>");
            Common.tell(sender, pluginInfo, 
                Placeholder.unparsed("name", getPluginName()),
                Placeholder.unparsed("version", getPluginVersion()));
            
            // Información del servidor
            String serverInfo = LocalizationManager.getFrameworkMessage("version.server-info", 
                "<gray>Server:</gray> <yellow>{version}</yellow>");
            Common.tell(sender, serverInfo, 
                Placeholder.unparsed("version", getServerVersion()));
            
            // Verificar actualizaciones si está habilitado
            if (shouldCheckUpdates()) {
                checkForUpdates(sender);
            }
            
            return true;
        };
    }
    
    /**
     * Check for plugin updates
     */
    default void checkForUpdates(Object sender) {
        String updateMessage = LocalizationManager.getFrameworkMessage("version.update-check", 
            "<gray>Checking for updates...</gray>");
        Common.tell(sender, updateMessage);
        
        // El usuario puede sobrescribir este método para implementar verificación de actualizaciones
        performUpdateCheck(sender);
    }
    
    /**
     * Override this method to implement actual update checking
     */
    default void performUpdateCheck(Object sender) {
        String noUpdateCheck = LocalizationManager.getFrameworkMessage("version.no-update-check", 
            "<yellow>Update checking not implemented.</yellow>");
        Common.tell(sender, noUpdateCheck);
    }
    
    /**
     * Get framework version
     */
    default String getFrameworkVersion() {
        return InternalMessages.getFrameworkVersion();
    }
    
    /**
     * Get plugin name - should be overridden by implementation
     */
    default String getPluginName() {
        return InternalMessages.getDefaultValue(InternalMessages.DefaultValue.PLUGIN_NAME);
    }
    
    /**
     * Get plugin version - should be overridden by implementation
     */
    default String getPluginVersion() {
        return InternalMessages.getDefaultValue(InternalMessages.DefaultValue.PLUGIN_VERSION);
    }
    
    /**
     * Get server version - should be overridden by implementation
     */
    default String getServerVersion() {
        return InternalMessages.getDefaultValue(InternalMessages.DefaultValue.SERVER_VERSION);
    }
    
    /**
     * Whether to check for updates - can be overridden
     */
    default boolean shouldCheckUpdates() {
        return false; // Por defecto deshabilitado
    }

    @Override
    default TabCompleteHandler<T> getTabCompleteHandler() {
        return null;
    }
}