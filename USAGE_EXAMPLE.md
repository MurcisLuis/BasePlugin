# Ejemplo de Uso del BasePlugin Framework

Este documento proporciona ejemplos prácticos de cómo usar BasePlugin como dependencia en tus proyectos.

## 📁 Estructura de Proyecto Recomendada

```
TuPlugin/
├── build.gradle
├── settings.gradle
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── tudominio/
│       │           └── tuplugin/
│       │               ├── TuPlugin.java
│       │               ├── commands/
│       │               ├── listeners/
│       │               └── config/
│       └── resources/
│           ├── plugin.yml
│           ├── config.yml
│           └── lang.yml
└── README.md
```

## 🚀 Ejemplo Completo - Plugin de Spigot

### build.gradle

```gradle
plugins {
    id 'java'
    id 'com.gradleup.shadow' version '8.3.6'
}

group = 'com.tudominio'
version = '1.0.0'

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
    maven { url 'https://hub.spigotmc.org/nexus/content/repositories/snapshots/' }
    maven { url 'https://maven.pkg.github.com/murcisluis/BasePlugin' }
}

dependencies {
    implementation 'com.gmail.murcisluis:BasePlugin-spigot:2.0.0'
    compileOnly 'org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT'
}

tasks {
    processResources {
        filesMatching(['plugin.yml']) {
            expand(version: project.version)
        }
    }
    
    shadowJar {
        // Relocate para evitar conflictos
        relocate 'com.gmail.murcisluis.base', 'com.tudominio.tuplugin.libs.base'
        relocate 'net.kyori', 'com.tudominio.tuplugin.libs.kyori'
        relocate 'org.apache.commons', 'com.tudominio.tuplugin.libs.commons'
        
        minimize()
        mergeServiceFiles()
        
        archiveClassifier = ''
    }
    
    build {
        dependsOn shadowJar
    }
}
```

### TuPlugin.java

```java
package com.tudominio.tuplugin;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.spigot.api.BaseSpigotAPI;
import com.gmail.murcisluis.base.spigot.api.BaseSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.AbstractCommandSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandManagerSpigot;
import com.tudominio.tuplugin.commands.MainCommand;
import com.tudominio.tuplugin.listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class TuPlugin extends JavaPlugin {
    
    private BaseSpigot baseSpigot;
    
    @Override
    public void onLoad() {
        try {
            // Inicializar BasePlugin Framework
            BaseAPIFactory.initialize(new BaseSpigotAPI());
            BaseAPIFactory.getAPI().onLoad(this);
            
            getLogger().info("BasePlugin Framework cargado correctamente");
        } catch (Exception e) {
            getLogger().severe("Error al cargar BasePlugin Framework: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public void onEnable() {
        try {
            // Habilitar BasePlugin
            BaseAPIFactory.getAPI().onEnable();
            baseSpigot = (BaseSpigot) BaseAPIFactory.get();
            
            // Registrar comandos
            registerCommands();
            
            // Registrar listeners
            registerListeners();
            
            // Cargar configuraciones
            loadConfigurations();
            
            getLogger().info("Plugin habilitado correctamente usando BasePlugin Framework v2.0.0");
            
        } catch (Exception e) {
            getLogger().severe("Error al habilitar el plugin: " + e.getMessage());
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }
    
    @Override
    public void onDisable() {
        try {
            // Cleanup y shutdown seguro
            BaseAPIFactory.shutdown();
            getLogger().info("Plugin deshabilitado correctamente");
        } catch (Exception e) {
            getLogger().warning("Error durante el shutdown: " + e.getMessage());
        }
    }
    
    private void registerCommands() {
        CommandManagerSpigot commandManager = baseSpigot.getCommandManager();
        
        // Comando principal
        AbstractCommandSpigot mainCommand = new MainCommand();
        commandManager.setMainCommand(mainCommand);
        commandManager.registerCommand(mainCommand);
    }
    
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }
    
    private void loadConfigurations() {
        // Las configuraciones se cargan automáticamente por BasePlugin
        // Pero puedes acceder a ellas así:
        // FileConfig config = baseSpigot.getFileConfig("config.yml");
    }
    
    public BaseSpigot getBaseSpigot() {
        return baseSpigot;
    }
}
```

### MainCommand.java

```java
package com.tudominio.tuplugin.commands;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.spigot.api.commands.AbstractCommandSpigot;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class MainCommand extends AbstractCommandSpigot {
    
    public MainCommand() {
        super("tuplugin");
        setDescription("Comando principal de TuPlugin");
        setUsage("/tuplugin <reload|info|help>");
        setPermission("tuplugin.admin");
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        Audience audience = BaseAPIFactory.get().audienceSender(sender);
        
        if (args.length == 0) {
            showHelp(audience);
            return;
        }
        
        switch (args[0].toLowerCase()) {
            case "reload":
                handleReload(sender, audience);
                break;
            case "info":
                handleInfo(audience);
                break;
            case "help":
            default:
                showHelp(audience);
                break;
        }
    }
    
    private void handleReload(CommandSender sender, Audience audience) {
        if (!sender.hasPermission("tuplugin.admin.reload")) {
            audience.sendMessage(Component.text("No tienes permisos para usar este comando")
                .color(NamedTextColor.RED));
            return;
        }
        
        try {
            // Recargar configuraciones usando BasePlugin
            com.gmail.murcisluis.base.common.api.Settings.reload();
            com.gmail.murcisluis.base.common.api.Lang.reload();
            
            audience.sendMessage(Component.text("Plugin recargado correctamente")
                .color(NamedTextColor.GREEN));
        } catch (Exception e) {
            audience.sendMessage(Component.text("Error al recargar: " + e.getMessage())
                .color(NamedTextColor.RED));
        }
    }
    
    private void handleInfo(Audience audience) {
        audience.sendMessage(Component.text("=== TuPlugin Info ===").color(NamedTextColor.GOLD));
        audience.sendMessage(Component.text("Versión: 1.0.0").color(NamedTextColor.YELLOW));
        audience.sendMessage(Component.text("BasePlugin Framework: 2.0.0").color(NamedTextColor.YELLOW));
        audience.sendMessage(Component.text("Estado: ").color(NamedTextColor.YELLOW)
            .append(Component.text(BaseAPIFactory.isInitialized() ? "Activo" : "Inactivo")
                .color(BaseAPIFactory.isInitialized() ? NamedTextColor.GREEN : NamedTextColor.RED)));
    }
    
    private void showHelp(Audience audience) {
        audience.sendMessage(Component.text("=== TuPlugin Ayuda ===").color(NamedTextColor.GOLD));
        audience.sendMessage(Component.text("/tuplugin reload - Recarga el plugin").color(NamedTextColor.YELLOW));
        audience.sendMessage(Component.text("/tuplugin info - Muestra información del plugin").color(NamedTextColor.YELLOW));
        audience.sendMessage(Component.text("/tuplugin help - Muestra esta ayuda").color(NamedTextColor.YELLOW));
    }
    
    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "info", "help");
        }
        return Arrays.asList();
    }
}
```

### PlayerListener.java

```java
package com.tudominio.tuplugin.listeners;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Usar BasePlugin para enviar mensajes modernos
        Audience audience = BaseAPIFactory.get().audienceSender(event.getPlayer());
        
        Component welcomeMessage = Component.text("¡Bienvenido ")
            .color(NamedTextColor.GREEN)
            .append(Component.text(event.getPlayer().getName())
                .color(NamedTextColor.GOLD))
            .append(Component.text("!").color(NamedTextColor.GREEN));
        
        audience.sendMessage(welcomeMessage);
    }
}
```

### plugin.yml

```yaml
name: TuPlugin
version: ${version}
main: com.tudominio.tuplugin.TuPlugin
api-version: 1.21
authors: [TuNombre]
description: Un plugin de ejemplo usando BasePlugin Framework
website: https://tudominio.com

commands:
  tuplugin:
    description: Comando principal de TuPlugin
    usage: /tuplugin <reload|info|help>
    permission: tuplugin.use
    aliases: [tp]

permissions:
  tuplugin.use:
    description: Permite usar comandos básicos
    default: true
  tuplugin.admin:
    description: Permite usar comandos de administración
    default: op
    children:
      tuplugin.use: true
      tuplugin.admin.reload: true
  tuplugin.admin.reload:
    description: Permite recargar el plugin
    default: op
```

## 🔧 Configuraciones Personalizadas

### config.yml

```yaml
# Configuración de TuPlugin
plugin:
  enabled: true
  debug: false
  
messages:
  prefix: "&8[&6TuPlugin&8] &7"
  welcome: "&a¡Bienvenido {player}!"
  
features:
  auto-save: true
  save-interval: 300 # segundos
```

### Acceder a configuraciones personalizadas

```java
// En tu clase principal o donde necesites la configuración
FileConfig config = BaseAPIFactory.get().getFileConfig("config.yml");

boolean enabled = config.getBoolean("plugin.enabled", true);
boolean debug = config.getBoolean("plugin.debug", false);
String prefix = config.getString("messages.prefix", "[TuPlugin] ");
int saveInterval = config.getInt("features.save-interval", 300);
```

## 🎯 Mejores Prácticas

### 1. Manejo de Errores

```java
try {
    BaseAPIFactory.getAPI().onEnable();
} catch (IllegalStateException e) {
    getLogger().severe("BasePlugin no está inicializado: " + e.getMessage());
    getServer().getPluginManager().disablePlugin(this);
    return;
} catch (Exception e) {
    getLogger().severe("Error inesperado: " + e.getMessage());
    e.printStackTrace();
}
```

### 2. Verificación de Estado

```java
if (!BaseAPIFactory.isInitialized()) {
    getLogger().warning("BasePlugin no está disponible");
    return;
}
```

### 3. Cleanup Adecuado

```java
@Override
public void onDisable() {
    // Guardar datos importantes
    saveData();
    
    // Cancelar tareas
    getServer().getScheduler().cancelTasks(this);
    
    // Shutdown BasePlugin
    BaseAPIFactory.shutdown();
}
```

## 📊 Ejemplo de Plugin BungeeCord

```java
package com.tudominio.tuplugin.bungee;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.bungeecord.api.BaseBungeeCordAPI;
import net.md_5.bungee.api.plugin.Plugin;

public class TuPluginBungee extends Plugin {
    
    @Override
    public void onLoad() {
        BaseAPIFactory.initialize(new BaseBungeeCordAPI());
        BaseAPIFactory.getAPI().onLoad(this);
    }
    
    @Override
    public void onEnable() {
        BaseAPIFactory.getAPI().onEnable();
        getLogger().info("Plugin BungeeCord habilitado con BasePlugin Framework!");
    }
    
    @Override
    public void onDisable() {
        BaseAPIFactory.shutdown();
    }
}
```

## 🚀 Compilación y Distribución

```bash
# Compilar el plugin
./gradlew build

# El archivo JAR estará en build/libs/
# Copia TuPlugin-1.0.0.jar a tu carpeta de plugins
```

## 📝 Notas Importantes

1. **Relocación**: Siempre usa relocación en shadowJar para evitar conflictos
2. **Dependencias**: BasePlugin incluye todas las dependencias necesarias
3. **Versiones**: Mantén las versiones actualizadas
4. **Testing**: Prueba en un servidor de desarrollo antes de producción
5. **Documentación**: Documenta tu código para facilitar el mantenimiento

¡Con estos ejemplos deberías poder integrar BasePlugin Framework en tus proyectos fácilmente!