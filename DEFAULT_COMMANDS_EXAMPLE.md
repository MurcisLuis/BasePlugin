# Comandos Por Defecto del Framework BasePlugin

El framework BasePlugin ahora proporciona automáticamente comandos esenciales que todo plugin necesita, eliminando la necesidad de que los desarrolladores implementen funcionalidad básica repetitiva.

## 🎯 **Comandos Incluidos Automáticamente**

### 1. **Help Command** (`/plugin help`)
- **Descripción**: Muestra todos los comandos disponibles
- **Permiso**: Ninguno (público)
- **Funcionalidad**:
  - Lista comandos del framework
  - Lista comandos personalizados del plugin
  - Muestra permisos requeridos
  - Formato personalizable

### 2. **Reload Command** (`/plugin reload`)
- **Descripción**: Recarga la configuración del plugin
- **Permiso**: `bp.admin` (configurable)
- **Funcionalidad**:
  - Recarga archivos de configuración
  - Recarga archivos de idioma
  - Ejecuta lógica de recarga personalizada
  - Muestra tiempo de recarga

### 3. **Info Command** (`/plugin info`)
- **Descripción**: Muestra información del plugin y servidor
- **Permiso**: Ninguno (público)
- **Funcionalidad**:
  - Información del plugin (nombre, versión, autor)
  - Estado del framework
  - Versión del servidor
  - Información personalizada adicional

### 4. **Version Command** (`/plugin version`)
- **Descripción**: Muestra información detallada de versiones
- **Permiso**: Ninguno (público)
- **Funcionalidad**:
  - Versión del framework BasePlugin
  - Versión del plugin específico
  - Versión del servidor
  - Verificación de actualizaciones (opcional)

## 🚀 **Implementación Simplificada**

### Antes (Código Repetitivo)
```java
@CommandInfo(
    permission = "myplugin.use",
    usage = "/myplugin <args>",
    description = "Main command"
)
public class MyPluginCommand extends AbstractCommandSpigot {
    
    @Override
    public CommandHandlerSpigot getCommandHandler() {
        return (sender, args) -> {
            if (args.length == 0) {
                // Implementar help manualmente
                sender.sendMessage("Available commands:");
                sender.sendMessage("/myplugin help - Show help");
                sender.sendMessage("/myplugin reload - Reload config");
                sender.sendMessage("/myplugin info - Show info");
                return true;
            }
            
            switch (args[0].toLowerCase()) {
                case "help":
                    // Implementar help manualmente
                    showHelp(sender);
                    return true;
                case "reload":
                    // Implementar reload manualmente
                    reloadConfig(sender);
                    return true;
                case "info":
                    // Implementar info manualmente
                    showInfo(sender);
                    return true;
                case "version":
                    // Implementar version manualmente
                    showVersion(sender);
                    return true;
                default:
                    sender.sendMessage("Unknown command!");
                    return true;
            }
        };
    }
    
    // Métodos manuales para cada comando...
    private void showHelp(CommandSender sender) { /* ... */ }
    private void reloadConfig(CommandSender sender) { /* ... */ }
    private void showInfo(CommandSender sender) { /* ... */ }
    private void showVersion(CommandSender sender) { /* ... */ }
}
```

### Ahora (Automático + Personalizable)
```java
@CommandInfo(
    permission = "myplugin.use",
    usage = "/myplugin <args>",
    description = "Main command"
)
public class MyPluginCommand extends AbstractCommandSpigot implements MyCommand<CommandSender> {
    
    public MyPluginCommand() {
        super("myplugin");
    }
    
    // ¡Los comandos help, reload, info, version ya están incluidos automáticamente!
    
    @Override
    public boolean handleCustomCommand(CommandSender sender, String command, String[] args) {
        switch (command) {
            case "custom":
                sender.sendMessage("This is my custom command!");
                return true;
            case "special":
                sender.sendMessage("Special functionality here!");
                return true;
            default:
                return false; // Comando no reconocido
        }
    }
    
    @Override
    public List<String> getCustomCommandCompletions() {
        return Arrays.asList("custom", "special");
    }
    
    @Override
    public void performCustomReload() {
        // Solo implementar lógica específica del plugin
        MyPlugin.getInstance().reloadCustomConfig();
    }
    
    // Personalizar información del plugin
    @Override
    public String getPluginName() {
        return "MyAwesomePlugin";
    }
    
    @Override
    public String getPluginVersion() {
        return MyPlugin.getInstance().getDescription().getVersion();
    }
    
    @Override
    public String getPluginAuthor() {
        return "MyName";
    }
    
    @Override
    public String getCommandPrefix() {
        return "myplugin";
    }
}
```

## 🎨 **Personalización Avanzada**

### Personalizar Mensajes de Help
```java
@Override
public void showCustomCommands(Object sender) {
    showHelpCommand(sender, "custom", "Execute custom functionality", "myplugin.custom");
    showHelpCommand(sender, "special", "Special command for VIPs", "myplugin.vip");
}
```

### Personalizar Información del Plugin
```java
@Override
public void showCustomInfo(Object sender) {
    String dbStatus = isConnectedToDatabase() ? "<green>Connected</green>" : "<red>Disconnected</red>";
    String dbMessage = LocalizationManager.getFrameworkMessage("info-database-status", 
        "<gray>Database:</gray> {status}");
    Common.tell(sender, dbMessage, Placeholder.unparsed("status", dbStatus));
}
```

### Verificación de Actualizaciones
```java
@Override
public boolean shouldCheckUpdates() {
    return true; // Habilitar verificación de actualizaciones
}

@Override
public void performUpdateCheck(Object sender) {
    // Implementar verificación real
    UpdateChecker.checkForUpdates(getPluginVersion())
        .thenAccept(hasUpdate -> {
            if (hasUpdate) {
                String updateMessage = LocalizationManager.getFrameworkMessage("version-update-available", 
                    "<yellow>New version available! Check SpigotMC for updates.</yellow>");
                Common.tell(sender, updateMessage);
            } else {
                String upToDateMessage = LocalizationManager.getFrameworkMessage("version-up-to-date", 
                    "<green>You are running the latest version!</green>");
                Common.tell(sender, upToDateMessage);
            }
        });
}
```

## 📊 **Beneficios**

### ✅ **Para Desarrolladores**
- **80% menos código**: No más implementación manual de comandos básicos
- **Consistencia**: Todos los plugins tienen la misma experiencia de comandos
- **Mantenibilidad**: Actualizaciones automáticas de funcionalidad básica
- **Enfoque**: Concentrarse en la lógica específica del plugin

### ✅ **Para Usuarios**
- **Familiaridad**: Comandos consistentes entre plugins
- **Funcionalidad completa**: Help, reload, info siempre disponibles
- **Mejor experiencia**: Tab completion automático
- **Mensajes claros**: Localización y formato profesional

### ✅ **Para Administradores**
- **Gestión simplificada**: Comandos estándar en todos los plugins
- **Debugging fácil**: Info y version siempre disponibles
- **Recarga confiable**: Funcionalidad de reload consistente
- **Permisos claros**: Sistema de permisos estandarizado

## 🔧 **Migración desde Versión Anterior**

Si ya tienes comandos implementados manualmente, puedes migrar gradualmente:

1. **Mantener comandos existentes** temporalmente
2. **Implementar MyCommand** para obtener comandos automáticos
3. **Mover lógica personalizada** a `handleCustomCommand`
4. **Remover implementaciones manuales** de help, reload, info, version
5. **Personalizar** según necesidades específicas

El framework garantiza que no habrá conflictos durante la migración.