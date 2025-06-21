# BasePlugin Framework Migration Guide

## Overview

This guide helps you migrate from the old duplicated `Settings` and `Lang` classes to the new unified `ConfigurationManager` and `LocalizationManager` system.

## What Changed

### 1. Configuration Management

**Before (Deprecated):**
```java
// Old way - duplicated across modules
Settings.reload();
String value = Settings.SOME_VALUE;
```

**After (Recommended):**
```java
// New way - unified configuration manager
ConfigurationManager.getInstance().reloadConfiguration("config.yml");
String value = ConfigurationManager.getInstance().getString("config.yml", "some.key", "default");

// Or use framework settings
boolean updateChecker = ConfigurationManager.FrameworkSettings.isUpdateCheckerEnabled();
```

### 2. Localization Management

**Before (Deprecated):**
```java
// Old way - duplicated across modules
Lang.reload();
Lang.PREFIX.send(sender);
Lang.NO_PERM.send(sender);
```

**After (Recommended):**
```java
// New way - unified localization manager
LocalizationManager.getInstance().reloadAllLanguages();
LocalizationManager.sendMessage(sender, "framework.prefix");
LocalizationManager.sendMessage(sender, "framework.no-permission");

// Or use framework messages directly
String message = LocalizationManager.getFrameworkMessage("no-permission", "<red>No permission</red>");
Common.tell(sender, message);
```

### 3. Framework Messages

**Available Framework Messages:**
- `framework.prefix` - Framework prefix
- `framework.no-permission` - No permission message
- `framework.reloaded` - Configuration reloaded message
- `framework.update-available` - Update available message
- `framework.version-info` - Version information
- `plugin.invalid-args` - Invalid arguments message
- `plugin.player-only` - Player only command message
- `plugin.unknown-command` - Unknown command message

### 4. Configuration Structure

**New config.yml structure:**
```yaml
# Framework-specific settings
framework:
  update-checker: true
  async-timeout: 5000
  debug: false
  max-cached-configs: 50

# Plugin development settings
plugin:
  default-prefix: "&8[&3Plugin&8] &7"
  auto-register-commands: true
  default-language: "en"

# Advanced settings
advanced:
  thread-pool-size: 4
  reflection-cache: true
```

**New lang.yml structure:**
```yaml
# Framework messages
framework:
  prefix: "<gray>[<aqua>BasePlugin</aqua>]</gray> "
  no-permission: "<red>You don't have permission to use this command.</red>"
  reloaded: "<green>Configuration reloaded successfully!</green>"

# Plugin development messages
plugin:
  invalid-args: "<red>Invalid arguments. Usage: {usage}</red>"
  player-only: "<red>This command can only be used by players.</red>"
  unknown-command: "<red>Unknown command. Use /help for available commands.</red>"
```

## Migration Steps

### Step 1: Update Dependencies

Ensure you're using the latest version of BasePlugin framework.

### Step 2: Replace Settings Usage

1. **Find all `Settings.` references in your code**
2. **Replace with `ConfigurationManager` calls:**

```java
// Old
Settings.reload();

// New
ConfigurationManager.getInstance().reloadConfiguration("config.yml");
```

### Step 2.5: 🆕 Migrate to Automatic Default Commands
```java
// OLD - Manual command implementation
public class MyPluginCommand extends AbstractCommandSpigot {
    @Override
    public CommandHandlerSpigot getCommandHandler() {
        return (sender, args) -> {
            if (args.length == 0) {
                // Manual help implementation
                sender.sendMessage("Available commands:");
                sender.sendMessage("/plugin help");
                sender.sendMessage("/plugin reload");
                return true;
            }
            
            switch (args[0].toLowerCase()) {
                case "help":
                    showHelp(sender);
                    return true;
                case "reload":
                    reloadConfig(sender);
                    return true;
                // ... more manual implementations
            }
        };
    }
}

// NEW - Automatic commands with MyCommand interface
public class MyPluginCommand extends AbstractCommandSpigot implements MyCommand<CommandSender> {
    public MyPluginCommand() {
        super("myplugin");
    }
    
    // help, reload, info, version commands are automatic!
    
    @Override
    public boolean handleCustomCommand(CommandSender sender, String command, String[] args) {
        switch (command) {
            case "mycustom":
                sender.sendMessage("Custom command executed!");
                return true;
            default:
                return false;
        }
    }
    
    @Override
    public String getPluginName() {
        return "MyPlugin";
    }
    
    @Override
    public String getCommandPrefix() {
        return "myplugin";
    }
}
```

### Step 3: Replace Lang Usage

1. **Find all `Lang.` references in your code**
2. **Replace with `LocalizationManager` calls:**

```java
// Old
Lang.PREFIX.send(sender);

// New
String prefix = LocalizationManager.getFrameworkMessage("prefix", "<gray>[Plugin]</gray> ");
Common.tell(sender, prefix);
```

### Step 4: Update Configuration Files

1. **Backup your existing config files**
2. **Update to new structure** (see examples above)
3. **Test thoroughly**

### Step 5: Remove Deprecated Imports

Remove any imports to the old `Settings` and `Lang` classes from spigot/bungeecord modules.

## Benefits of Migration

✅ **Eliminated Code Duplication** - No more duplicate classes across modules

✅ **Better Performance** - Thread-safe, cached configuration management

✅ **Multi-Language Support** - Easy language switching and custom language files

✅ **Framework Separation** - Clear distinction between framework and plugin messages

✅ **Modern Architecture** - SOLID principles, better maintainability

✅ **Rich Text Support** - MiniMessage format for advanced text formatting

✅ **🆕 Automatic default commands** (help, reload, info, version) - no more repetitive implementation

✅ **🆕 Smart command routing** with automatic tab completion

✅ **🆕 Consistent user experience** across all plugins using the framework

## Backward Compatibility

The old `Settings` and `Lang` classes are marked as `@Deprecated` but still functional. They now redirect to the new managers internally, so existing code will continue to work during the transition period.

**Important:** The deprecated classes will be removed in a future major version.

## Common Issues and Solutions

### Issue: "Cannot find LocalizationManager"
**Solution:** Add the import: `import com.gmail.murcisluis.base.common.api.localization.LocalizationManager;`

### Issue: "Cannot find ConfigurationManager"
**Solution:** Add the import: `import com.gmail.murcisluis.base.common.config.ConfigurationManager;`

### Issue: "Messages not loading"
**Solution:** Ensure your language files follow the new structure and are properly loaded.

### Issue: "Configuration not found"
**Solution:** Use the full configuration manager API with proper file names and paths.

## Support

If you encounter issues during migration:

1. Check this guide for common solutions
2. Review the new API documentation
3. Look at the example implementations in the framework
4. Create an issue in the project repository

## Example: Complete Migration

**Before:**
```java
public class MyPlugin {
    public void onEnable() {
        Settings.reload();
        Lang.reload();
        
        if (Settings.UPDATE_CHECKER) {
            // Check for updates
        }
    }
    
    public void onCommand(CommandSender sender) {
        if (!sender.hasPermission("myplugin.use")) {
            Lang.NO_PERM.send(sender);
            return;
        }
        Lang.PREFIX.send(sender, "Hello!");
    }
}
```

**After:**
```java
public class MyPlugin {
    public void onEnable() {
        ConfigurationManager.getInstance().reloadConfiguration("config.yml");
        LocalizationManager.getInstance().reloadAllLanguages();
        
        if (ConfigurationManager.FrameworkSettings.isUpdateCheckerEnabled()) {
            // Check for updates
        }
    }
    
    public void onCommand(CommandSender sender) {
        if (!sender.hasPermission("myplugin.use")) {
            String message = LocalizationManager.getFrameworkMessage("no-permission", 
                "<red>You don't have permission to use this command.</red>");
            Common.tell(sender, message);
            return;
        }
        
        String prefix = LocalizationManager.getFrameworkMessage("prefix", 
            "<gray>[Plugin]</gray> ");
        Common.tell(sender, prefix + "Hello!");
    }
}
```

This migration provides a much cleaner, more maintainable, and feature-rich foundation for your plugins!