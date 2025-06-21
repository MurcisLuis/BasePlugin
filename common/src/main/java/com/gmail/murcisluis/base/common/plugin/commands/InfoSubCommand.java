package com.gmail.murcisluis.base.common.plugin.commands;

import com.gmail.murcisluis.base.common.api.commands.CommandBase;
import com.gmail.murcisluis.base.common.api.commands.CommandHandler;
import com.gmail.murcisluis.base.common.api.commands.CommandInfo;
import com.gmail.murcisluis.base.common.api.commands.TabCompleteHandler;
import com.gmail.murcisluis.base.common.api.localization.LocalizationManager;
import com.gmail.murcisluis.base.common.api.localization.InternalMessages;
import com.gmail.murcisluis.base.common.api.utils.Common;
import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

@CommandInfo(
        permission = "",
        usage = "/bp info",
        description = "Show plugin information and status."
)
public interface InfoSubCommand<T> extends CommandBase {

    @Override
    default CommandHandler<T> getCommandHandler() {
        return (sender, args) -> {
            String infoHeader = LocalizationManager.getFrameworkMessage("info.header", 
                "<gray>========== <aqua>Plugin Information</aqua> ==========</gray>");
            Common.tell(sender, infoHeader);
            
            // Información básica del plugin
            showPluginInfo(sender);
            
            // Estado del framework
            String frameworkStatus = BaseAPIFactory.isInitialized() ? 
                "<green>Active</green>" : "<red>Inactive</red>";
            String statusMessage = LocalizationManager.getFrameworkMessage("info.framework-status", 
                "<gray>Framework Status:</gray> {status}");
            Common.tell(sender, statusMessage, Placeholder.unparsed("status", frameworkStatus));
            
            // Información del servidor
            showServerInfo(sender);
            
            // Permitir información personalizada
            showCustomInfo(sender);
            
            String infoFooter = LocalizationManager.getFrameworkMessage("info.footer", 
                "<gray>==========================================</gray>");
            Common.tell(sender, infoFooter);
            
            return true;
        };
    }
    
    /**
     * Show basic plugin information
     */
    default void showPluginInfo(Object sender) {
        // Use default values since these methods are in VersionSubCommand
        String pluginName = InternalMessages.getDefaultValue(InternalMessages.DefaultValue.PLUGIN_NAME);
        String pluginVersion = InternalMessages.getDefaultValue(InternalMessages.DefaultValue.PLUGIN_VERSION);
        String pluginAuthor = getPluginAuthor();
        
        String nameMessage = LocalizationManager.getFrameworkMessage("info.plugin-name", 
            "<gray>Plugin:</gray> <yellow>{name}</yellow>");
        Common.tell(sender, nameMessage, Placeholder.unparsed("name", pluginName));
        
        String versionMessage = LocalizationManager.getFrameworkMessage("info.plugin-version", 
            "<gray>Version:</gray> <yellow>{version}</yellow>");
        Common.tell(sender, versionMessage, Placeholder.unparsed("version", pluginVersion));
        
        String authorMessage = LocalizationManager.getFrameworkMessage("info.plugin-author", 
            "<gray>Author:</gray> <yellow>{author}</yellow>");
        Common.tell(sender, authorMessage, Placeholder.unparsed("author", pluginAuthor));
    }
    
    /**
     * Show server information
     */
    default void showServerInfo(Object sender) {
        // Get server version from VersionSubCommand interface
        String serverVersion = "Unknown"; // Default fallback
        String serverMessage = LocalizationManager.getFrameworkMessage("info.server-version", 
            "<gray>Server Version:</gray> <yellow>{version}</yellow>");
        Common.tell(sender, serverMessage, Placeholder.unparsed("version", serverVersion));
    }
    
    /**
     * Override this method to add custom information
     */
    default void showCustomInfo(Object sender) {
        // Por defecto no hace nada, el usuario puede sobrescribir
    }
    
    // Plugin name and version methods are provided by VersionSubCommand interface
    
    /**
     * Get plugin author - should be overridden by implementation
     */
    default String getPluginAuthor() {
        return InternalMessages.getDefaultValue(InternalMessages.DefaultValue.PLUGIN_AUTHOR);
    }
    
    // getServerVersion() method is provided by VersionSubCommand interface

    @Override
    default TabCompleteHandler<T> getTabCompleteHandler() {
        return null;
    }
}