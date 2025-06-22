package com.gmail.murcisluis.base.examples.bungeecord.commands;

import com.gmail.murcisluis.base.bungee.api.commands.AbstractCommandBungee;
import com.gmail.murcisluis.base.bungee.api.commands.CommandBaseBungee;
import com.gmail.murcisluis.base.bungee.api.commands.CommandHandlerBungee;
import com.gmail.murcisluis.base.bungee.api.commands.TabCompleteHandlerBungee;
import com.gmail.murcisluis.base.common.api.commands.CommandInfo;
import com.gmail.murcisluis.base.common.api.localization.LocalizationManager;
import com.gmail.murcisluis.base.examples.common.LocalizationExample;
import com.gmail.murcisluis.base.common.api.localization.PlayerLocalizationHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Command to demonstrate the enhanced localization system with player UUID support.
 * Allows players to change their language preferences, view available languages,
 * and (for admins) see language statistics or reload language files.
 */
@CommandInfo(
        permission = "baseexample.lang",
        description = "Language management command",
        usage = "/lang [set|get|list|stats|reload] [language]",
        aliases = {"language", "idioma"}
)
public class LanguageCommandBungee extends AbstractCommandBungee {

    private final LocalizationManager localizationManager;
    private final PlayerLocalizationHelper localizationHelper;
    private final LocalizationExample localizationExample;

    public LanguageCommandBungee(LocalizationManager localizationManager, 
                                PlayerLocalizationHelper localizationHelper,
                                LocalizationExample localizationExample) {
        super("lang");
        this.localizationManager = localizationManager;
        this.localizationHelper = localizationHelper;
        this.localizationExample = localizationExample;
    }
    
    @Override
    public CommandHandlerBungee getCommandHandler() {
        return (sender, args) -> {
            handleCommand(sender, args);
            return true;
        };
    }
    
    @Override
    public TabCompleteHandlerBungee getTabCompleteHandler() {
        return (sender, args) -> {
            if (args.length == 1) {
                return Arrays.asList("set", "get", "list", "stats", "reload");
            } else if (args.length == 2 && "set".equalsIgnoreCase(args[0])) {
                return Arrays.asList("en", "es", "fr");
            }
            return Arrays.asList();
        };
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        handleCommand(sender, args);
    }
    
    private void handleCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new net.md_5.bungee.api.chat.TextComponent("§cThis command can only be used by players."));
            return;
        }
        
        ProxiedPlayer player = (ProxiedPlayer) sender;
        
        if (args.length == 0) {
            showCurrentLanguage(player);
            return;
        }
        
        String subCommand = args[0].toLowerCase();
        
        switch (subCommand) {
            case "set":
            case "change":
                if (args.length < 2) {
                    showLanguageUsage(player);
                    return;
                }
                changePlayerLanguage(player, args[1]);
                break;
                
            case "list":
            case "available":
                showAvailableLanguages(player);
                break;
                
            case "stats":
            case "statistics":
                if (player.hasPermission("baseexample.lang.admin")) {
                    showLanguageStatistics(player);
                } else {
                    sendNoPermissionMessage(player);
                }
                break;
                
            case "reset":
                resetPlayerLanguage(player);
                break;
                
            case "reload":
                if (player.hasPermission("baseexample.lang.admin")) {
                    reloadLanguages(player);
                } else {
                    sendNoPermissionMessage(player);
                }
                break;
                
            case "help":
            default:
                showLanguageUsage(player);
                break;
        }
    }
    
    /**
     * Shows the player's current language setting.
     */
    private void showCurrentLanguage(ProxiedPlayer player) {
        String currentLang = localizationManager.getPlayerLanguage(player.getUniqueId());
        Component message = localizationManager.getPlayerComponent(player.getUniqueId(), "info.language_current",
            Placeholder.unparsed("language", currentLang.toUpperCase()));
        
        sendComponentMessage(player, message);
    }
    
    /**
     * Changes the player's language preference.
     */
    private void changePlayerLanguage(ProxiedPlayer player, String newLanguage) {
        if (!localizationHelper.isLanguageAvailable(newLanguage)) {
            Component errorMessage = localizationHelper.getErrorMessage(player.getUniqueId(), "language_not_available",
                Placeholder.unparsed("language", newLanguage),
                Placeholder.unparsed("available", String.join(", ", localizationHelper.getAvailableLanguages())));
            sendComponentMessage(player, errorMessage);
            return;
        }
        
        // Use the localization example to handle the language change
        localizationExample.handleLanguageChangeCommand(player.getUniqueId(), player.getName(), newLanguage);
    }
    
    /**
     * Shows all available languages.
     */
    private void showAvailableLanguages(ProxiedPlayer player) {
        Set<String> availableLanguages = localizationHelper.getAvailableLanguages();
        String currentLang = localizationManager.getPlayerLanguage(player.getUniqueId());
        
        Component header = localizationManager.getPlayerComponent(player.getUniqueId(), "help.header");
        sendComponentMessage(player, header);
        
        for (String lang : availableLanguages) {
            String indicator = lang.equals(currentLang) ? " §a(current)" : "";
            Component langLine = Component.text("§f- §b" + lang.toUpperCase() + indicator);
            sendComponentMessage(player, langLine);
        }
    }
    
    /**
     * Shows language statistics (admin only).
     */
    private void showLanguageStatistics(ProxiedPlayer player) {
        localizationExample.showLanguageStatistics(player.getUniqueId());
    }
    
    /**
     * Resets the player's language to default.
     */
    private void resetPlayerLanguage(ProxiedPlayer player) {
        localizationManager.removePlayerLanguage(player.getUniqueId());
        
        Component successMessage = localizationManager.getPlayerComponent(player.getUniqueId(), "success.language_changed",
            Placeholder.unparsed("language", "DEFAULT"));
        sendComponentMessage(player, successMessage);
    }
    
    /**
     * Reloads all language files (admin only).
     */
    private void reloadLanguages(ProxiedPlayer player) {
        try {
            localizationManager.reloadAllLanguages();
            
            Component successMessage = localizationManager.getPlayerComponent(player.getUniqueId(), "admin.reload_complete");
            sendComponentMessage(player, successMessage);
            
        } catch (Exception e) {
            Component errorMessage = localizationHelper.getErrorMessage(player.getUniqueId(), "unknown_error",
                Placeholder.unparsed("error_type", "reload_failed"));
            sendComponentMessage(player, errorMessage);
        }
    }
    
    /**
     * Shows command usage information.
     */
    private void showLanguageUsage(ProxiedPlayer player) {
        Component header = localizationManager.getPlayerComponent(player.getUniqueId(), "help.header");
        sendComponentMessage(player, header);
        
        Component langCommand = localizationManager.getPlayerComponent(player.getUniqueId(), "help.lang_command");
        sendComponentMessage(player, langCommand);
        
        Component langList = localizationManager.getPlayerComponent(player.getUniqueId(), "help.lang_list");
        sendComponentMessage(player, langList);
        
        // Show additional commands if player has admin permission
        if (player.hasPermission("baseexample.lang.admin")) {
            sendComponentMessage(player, Component.text("§f/lang stats - Show language statistics"));
            sendComponentMessage(player, Component.text("§f/lang reload - Reload language files"));
        }
        
        sendComponentMessage(player, Component.text("§f/lang reset - Reset to default language"));
    }
    
    /**
     * Sends a no permission message to the player.
     */
    private void sendNoPermissionMessage(ProxiedPlayer player) {
        Component errorMessage = localizationHelper.getErrorMessage(player.getUniqueId(), "permission_denied");
        sendComponentMessage(player, errorMessage);
    }
    
    /**
     * Sends a component message to the player.
     * In a real implementation, this would use the platform's Adventure integration.
     */
    private void sendComponentMessage(ProxiedPlayer player, Component component) {
        // For this example, we'll convert to legacy text
        // In a real implementation, you would use proper Adventure integration
        String legacyText = component.toString(); // Simplified conversion
        player.sendMessage(new net.md_5.bungee.api.chat.TextComponent(legacyText));
    }
    
    @Override
    public String getName() {
        return "lang";
    }
    
    @Override
    public String getDescription() {
        return "Language management command with player UUID support";
    }
    
    @Override
    public String getUsage() {
        return "/lang [set <language>|list|stats|reset|reload|help]";
    }
    
    @Override
    public String[] getAliases() {
        return new String[]{"language", "idioma", "langue"};
    }
}