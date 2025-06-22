# 📋 Guía de Migración: De Settings a ConfigurationManager

## ¿Por qué migrar?

La clase `Settings` está **deprecated** y será removida en futuras versiones. `ConfigurationManager` ofrece:

- ✅ Mejor gestión de múltiples archivos de configuración
- ✅ Thread-safe y optimizado
- ✅ API más limpia y flexible
- ✅ Mejor manejo de errores
- ✅ Soporte para configuraciones personalizadas

## 🔄 Cambios Principales

### Antes (Settings - Deprecated)
```java
import com.gmail.murcisluis.base.common.api.Settings;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;

// Obtener configuración
FileConfig config = Settings.getConfig();

// Recargar configuración
Settings.reload();

// Verificar actualizaciones
boolean checkUpdates = Settings.CHECK_FOR_UPDATES;
```

### Después (ConfigurationManager - Recomendado)
```java
import com.gmail.murcisluis.base.common.api.config.ConfigurationManager;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;

// Obtener la instancia del manager
ConfigurationManager configManager = ConfigurationManager.getInstance();

// Obtener configuración
FileConfig<?> config = configManager.getConfig("config.yml");

// Recargar configuración específica
configManager.reloadConfig("config.yml");

// Para configuraciones del framework
boolean checkUpdates = ConfigurationManager.FrameworkSettings.isUpdateCheckerEnabled();
```

## 📝 Ejemplos Prácticos de Migración

### 1. Configuración Básica

#### ❌ Código Antiguo
```java
public class MiPlugin {
    private FileConfig config;
    
    public void onEnable() {
        // Usando Settings (deprecated)
        this.config = Settings.getConfig();
        
        String serverName = (String) config.get("server.name", "Default");
        int maxPlayers = (Integer) config.get("server.max-players", 20);
    }
    
    public void reloadConfig() {
        Settings.reload();
    }
}
```

#### ✅ Código Nuevo
```java
public class MiPlugin {
    private ConfigurationManager configManager;
    private FileConfig<?> config;
    
    public void onEnable() {
        // Usando ConfigurationManager
        this.configManager = ConfigurationManager.getInstance();
        this.config = configManager.getConfig("config.yml");
        
        String serverName = (String) config.get("server.name", "Default");
        int maxPlayers = (Integer) config.get("server.max-players", 20);
    }
    
    public void reloadConfig() {
        configManager.reloadConfig("config.yml");
    }
}
```

### 2. Múltiples Archivos de Configuración

#### ✅ Nuevo Enfoque (No era posible con Settings)
```java
public class MiPluginAvanzado {
    private ConfigurationManager configManager;
    
    public void onEnable() {
        this.configManager = ConfigurationManager.getInstance();
        
        // Cargar múltiples configuraciones
        FileConfig<?> mainConfig = configManager.getConfig("config.yml");
        FileConfig<?> messagesConfig = configManager.getConfig("messages.yml");
        FileConfig<?> economyConfig = configManager.getConfig("economy.yml");
        
        // Configurar valores por defecto
        setupDefaultConfigs(mainConfig, messagesConfig, economyConfig);
    }
    
    private void setupDefaultConfigs(FileConfig<?> main, FileConfig<?> messages, FileConfig<?> economy) {
        // Configuración principal
        if (!main.contains("server.name")) {
            main.set("server.name", "Mi Servidor");
            main.saveData();
        }
        
        // Configuración de mensajes
        if (!messages.contains("welcome.message")) {
            messages.set("welcome.message", "¡Bienvenido %player%!");
            messages.saveData();
        }
        
        // Configuración de economía
        if (!economy.contains("starting.money")) {
            economy.set("starting.money", 1000.0);
            economy.saveData();
        }
    }
}
```

### 3. Clase de Configuración Personalizada

#### ✅ Enfoque Recomendado
```java
public class ConfigManager {
    private final ConfigurationManager configManager;
    private FileConfig<?> mainConfig;
    private FileConfig<?> messagesConfig;
    
    public ConfigManager() {
        this.configManager = ConfigurationManager.getInstance();
        loadConfigs();
    }
    
    private void loadConfigs() {
        this.mainConfig = configManager.getConfig("config.yml");
        this.messagesConfig = configManager.getConfig("messages.yml");
        
        setupDefaults();
    }
    
    private void setupDefaults() {
        // Configuración principal
        setDefault(mainConfig, "server.name", "Mi Servidor");
        setDefault(mainConfig, "server.max-players", 20);
        setDefault(mainConfig, "features.economy.enabled", true);
        
        // Mensajes
        setDefault(messagesConfig, "prefix", "&8[&6MiPlugin&8] &r");
        setDefault(messagesConfig, "no-permission", "&cNo tienes permisos");
        
        // Guardar cambios
        mainConfig.saveData();
        messagesConfig.saveData();
    }
    
    private void setDefault(FileConfig<?> config, String path, Object value) {
        if (!config.contains(path)) {
            config.set(path, value);
        }
    }
    
    // Métodos de acceso fácil
    public String getServerName() {
        return (String) mainConfig.get("server.name", "Default Server");
    }
    
    public int getMaxPlayers() {
        return (Integer) mainConfig.get("server.max-players", 20);
    }
    
    public String getMessage(String key) {
        String prefix = (String) messagesConfig.get("prefix", "");
        String message = (String) messagesConfig.get(key, "Mensaje no encontrado");
        return prefix + message;
    }
    
    public void reload() {
        configManager.reloadConfig("config.yml");
        configManager.reloadConfig("messages.yml");
        loadConfigs();
    }
}
```

## 🚀 Ejemplo Completo de Plugin

```java
public class EjemploPluginCompleto {
    private ConfigurationManager configManager;
    private ConfigManager config;
    
    public void onEnable() {
        // Inicializar el sistema de configuración
        this.configManager = ConfigurationManager.getInstance();
        this.config = new ConfigManager();
        
        // Usar la configuración
        getLogger().info("Servidor: " + config.getServerName());
        getLogger().info("Jugadores máximos: " + config.getMaxPlayers());
        
        // Registrar comando de recarga
        registerReloadCommand();
    }
    
    private void registerReloadCommand() {
        // Ejemplo de comando para recargar configuración
        // (implementación específica depende de tu plataforma)
    }
    
    public void reloadConfigs() {
        config.reload();
        getLogger().info("Configuraciones recargadas");
    }
}
```

## 📋 Lista de Verificación para Migración

- [ ] Reemplazar todas las importaciones de `Settings`
- [ ] Cambiar `Settings.getConfig()` por `ConfigurationManager.getInstance().getConfig("config.yml")`
- [ ] Cambiar `Settings.reload()` por `configManager.reloadConfig("config.yml")`
- [ ] Actualizar verificaciones de `Settings.CHECK_FOR_UPDATES`
- [ ] Considerar usar múltiples archivos de configuración
- [ ] Crear una clase wrapper para facilitar el acceso
- [ ] Probar que todo funciona correctamente
- [ ] Eliminar imports no utilizados

## ⚠️ Notas Importantes

1. **Thread Safety**: `ConfigurationManager` es thread-safe, pero asegúrate de no modificar configuraciones desde múltiples hilos simultáneamente.

2. **Rendimiento**: `ConfigurationManager` cachea las configuraciones, por lo que llamadas múltiples a `getConfig()` con el mismo nombre son eficientes.

3. **Nombres de Archivos**: Siempre incluye la extensión `.yml` en los nombres de archivo.

4. **Valores por Defecto**: Siempre proporciona valores por defecto en `config.get(path, defaultValue)`.

5. **Guardado**: No olvides llamar `config.saveData()` después de modificar valores.

## 🔗 Referencias

- [Documentación de ConfigurationManager](javadoc/common/com/gmail/murcisluis/base/common/api/config/ConfigurationManager.html)
- [Ejemplo Simple](examples/src/main/java/com/gmail/murcisluis/base/examples/common/SimpleConfigExample.java)
- [Archivo de Configuración de Ejemplo](examples/src/main/resources/example-config.yml)