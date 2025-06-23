# BasePlugin Framework

[![Version](https://img.shields.io/badge/version-2.3.1-blue.svg)](https://github.com/murcisluis/BasePlugin)
[![JitPack](https://jitpack.io/v/MurcisLuis/BasePlugin.svg)](https://jitpack.io/#MurcisLuis/BasePlugin)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://opensource.org/licenses/MIT)

Un framework base moderno y robusto para el desarrollo de plugins de Minecraft (Spigot/BungeeCord) que implementa principios SOLID y patrones de diseño como Singleton. Diseñado para ser usado como dependencia en otros proyectos.

> 📁 **¿Buscas ejemplos?** Revisa el módulo [`examples/`](examples/) para ver implementaciones de referencia.

## 🚀 Características

- **Framework Puro**: Solo APIs y utilidades, sin implementaciones forzadas
- **Control Total**: El desarrollador decide qué inicializar y registrar
- **Arquitectura Modular**: Separación clara entre common, spigot, bungeecord y examples
- **Principios SOLID**: Implementación de principios de diseño sólidos
- **Patrón Singleton Thread-Safe**: Factory pattern con sincronización adecuada
- **API Moderna**: Uso de Adventure API para mensajes modernos
- **Configuración Avanzada**: Sistema de configuración flexible y extensible
- **Comandos Inteligentes**: Sistema de comandos con autocompletado
- **Soporte Multi-Plataforma**: Compatible con Spigot y BungeeCord
- **Ejemplos Incluidos**: Módulo `examples/` con implementaciones de referencia
- **Versiones Actualizadas**: Todas las dependencias actualizadas a las últimas versiones

## 📋 Requisitos

- **Java 21** o superior
- **Gradle 8.3+**
- **Spigot/Paper 1.21.4+** o **BungeeCord 1.21+**

## 📦 Instalación como Dependencia

### 🚀 Instalación Rápida (Sin autenticación)

**Paso 1:** Agregar repositorios en tu `build.gradle`:

```gradle
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
    maven { url 'https://hub.spigotmc.org/nexus/content/repositories/snapshots/' }
    maven { url 'https://oss.sonatype.org/content/repositories/snapshots' }
}
```

**Paso 2:** Agregar dependencias según tu plataforma:

#### 🔧 Para Spigot/Paper
```gradle
dependencies {
    implementation 'com.github.murcisluis.BasePlugin:spigot:v2.3.1'
    implementation 'com.github.murcisluis.BasePlugin:common:v2.3.1'
}
```

#### 🌐 Para BungeeCord/Waterfall
```gradle
dependencies {
    implementation 'com.github.murcisluis.BasePlugin:bungeecord:v2.3.1'
    implementation 'com.github.murcisluis.BasePlugin:common:v2.3.1'
}
```

#### 📦 Todos los módulos
```gradle
dependencies {
    implementation 'com.github.murcisluis:BasePlugin:v2.3.1'
}
```

> ✅ **Sin configuración adicional** - No necesitas tokens ni autenticación

> 📖 **Instrucciones detalladas**: Ver [JITPACK.md](JITPACK.md) para guía completa de configuración con JitPack.

### 📦 Maven

**Paso 1:** Agregar repositorio JitPack:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

**Paso 2:** Agregar dependencias según tu plataforma:

#### Para Spigot/Paper
```xml
<dependencies>
    <dependency>
        <groupId>com.github.murcisluis.BasePlugin</groupId>
        <artifactId>spigot</artifactId>
        <version>v2.3.1</version>
    </dependency>
    <dependency>
        <groupId>com.github.murcisluis.BasePlugin</groupId>
        <artifactId>common</artifactId>
        <version>v2.3.1</version>
    </dependency>
</dependencies>
```

#### Para BungeeCord/Waterfall
```xml
<dependencies>
    <dependency>
        <groupId>com.github.murcisluis.BasePlugin</groupId>
        <artifactId>bungeecord</artifactId>
        <version>v2.3.1</version>
    </dependency>
    <dependency>
        <groupId>com.github.murcisluis.BasePlugin</groupId>
        <artifactId>common</artifactId>
        <version>v2.3.1</version>
    </dependency>
</dependencies>
```

## 🛠️ Uso Básico

> 💡 **Tip**: Para ejemplos completos y detallados, consulta el módulo [`examples/`](examples/)

### Plugin de Spigot

```java
package com.tudominio.tuplugin;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.spigot.api.BaseSpigotAPI;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class TuPlugin extends JavaPlugin implements BasePlugin {
    
    @Override
    public void onLoad() {
        // Inicializar la API de BasePlugin
        // Inicializar el framework
        BaseAPIFactory.initialize(new BaseSpigotAPI());
        BaseAPIFactory.getAPI().onLoad(this);
    }
    
    @Override
    public void onEnable() {
        // Habilitar la API
        BaseAPIFactory.getAPI().onEnable();
        
        // Tu lógica de plugin aquí
        getLogger().info("Plugin habilitado usando BasePlugin Framework!");
        
        // Registrar comandos personalizados (opcional)
        // registerCommand(new MiComandoPersonalizado());
    }
    
    @Override
    public void onDisable() {
        // Deshabilitar la API de forma segura
        BaseAPIFactory.getAPI().onDisable();
    }
    
    // Implementar métodos requeridos de BasePlugin
    // Ver examples/ para implementaciones completas
}
```

### Plugin de BungeeCord

```java
package com.tudominio.tuplugin;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.bungeecord.api.BaseBungeeCordAPI;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import net.md_5.bungee.api.plugin.Plugin;

public class TuPlugin extends Plugin implements BasePlugin {
    
    @Override
    public void onLoad() {
        BaseAPIFactory.initialize(new BaseBungeeCordAPI());
        BaseAPIFactory.getAPI().onLoad(this);
    }
    
    @Override
    public void onEnable() {
        BaseAPIFactory.getAPI().onEnable();
        getLogger().info("Plugin BungeeCord habilitado!");
        
        // Registrar comandos personalizados (opcional)
        // registerCommand(new MiComandoBungee());
    }
    
    @Override
    public void onDisable() {
        BaseAPIFactory.getAPI().onDisable();
    }
    
    // Implementar métodos requeridos de BasePlugin
    // Ver examples/ para implementaciones completas
}
```

## 🎯 Características Avanzadas

### Sistema de Configuración

```java
// Acceder a la configuración
FileConfig config = BaseAPIFactory.get().getFileConfig("config.yml");
String valor = config.getString("mi.configuracion", "valor_por_defecto");

// Recargar configuraciones
Settings.reload();
Lang.reload();
```

### Sistema de Mensajes con Adventure

```java
// Enviar mensajes modernos
Audience audience = BaseAPIFactory.get().audienceSender(player);
audience.sendMessage(Component.text("¡Hola mundo!").color(NamedTextColor.GREEN));

// Usar MiniMessage
Component message = MiniMessage.miniMessage().deserialize(
    "<gradient:blue:green>¡Mensaje con gradiente!</gradient>"
);
audience.sendMessage(message);
```

### Comandos Personalizados

> 📖 **Ejemplos completos**: Consulta [`examples/src/main/java/.../commands/`](examples/src/main/java/com/gmail/murcisluis/base/examples/) para ver implementaciones detalladas.

```java
@CommandInfo(
    permission = "miplugin.usar",
    usage = "/micomando <args>",
    description = "Mi comando personalizado",
    aliases = {"mc", "cmd"}
)
public class MiComando extends AbstractCommandSpigot implements CommandBaseSpigot {
    
    public MiComando() {
        super("micomando");
    }
    
    @Override
    public CommandHandlerSpigot getCommandHandler() {
        return (sender, args) -> {
            sender.sendMessage("¡Comando ejecutado!");
            return true;
        };
    }
    
    @Override
    public TabCompleteHandlerSpigot getTabCompleteHandler() {
        return (sender, args) -> Arrays.asList("opcion1", "opcion2", "opcion3");
    }
}
```

## 🔧 Configuración del Proyecto

### build.gradle para tu plugin

```gradle
plugins {
    id 'java'
    id 'com.gradleup.shadow' version '8.3.6'
}

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
    implementation 'com.gmail.murcisluis:BasePlugin-spigot:2.3.0'
    compileOnly 'org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT'
}

tasks {
    shadowJar {
        // Relocate BasePlugin para evitar conflictos
        relocate 'com.gmail.murcisluis.base', 'com.tudominio.tuplugin.libs.base'
        
        // Minimizar el JAR
        minimize()
        
        // Merge service files
        mergeServiceFiles()
    }
    
    build {
        dependsOn shadowJar
    }
}
```

## 📚 Documentación de la API

### BaseAPIFactory (Singleton Thread-Safe)

- `initialize(BaseAPI api)`: Inicializa la API
- `getAPI()`: Obtiene la instancia de la API
- `get()`: Obtiene la implementación Base
- `getPlugin()`: Obtiene el plugin base
- `isInitialized()`: Verifica si está inicializado
- `shutdown()`: Cierra la API de forma segura

### Principios SOLID Implementados

1. **Single Responsibility**: Cada clase tiene una responsabilidad específica
2. **Open/Closed**: Extensible sin modificar el código base
3. **Liskov Substitution**: Las implementaciones son intercambiables
4. **Interface Segregation**: Interfaces específicas y cohesivas
5. **Dependency Inversion**: Dependencias de abstracciones, no concreciones

## 📁 Módulo Examples

El proyecto incluye un módulo `examples/` con implementaciones de referencia:

### Estructura del módulo examples

```
examples/
├── src/main/java/com/gmail/murcisluis/base/examples/
│   ├── bungeecord/
│   │   ├── ExampleBungeePlugin.java      # Plugin BungeeCord de ejemplo
│   │   └── commands/
│   │       └── ExampleCommandBungee.java # Comando BungeeCord de ejemplo
│   └── spigot/
│       ├── ExampleSpigotPlugin.java      # Plugin Spigot de ejemplo
│       └── commands/
│           └── ExampleCommandSpigot.java # Comando Spigot de ejemplo
└── README.md                             # Documentación detallada
```

### ¿Cómo usar los ejemplos?

1. **Consulta** el [README del módulo examples](examples/README.md)
2. **Copia** las clases que necesites a tu proyecto
3. **Modifica** según tus necesidades específicas
4. **Personaliza** comandos, configuraciones y lógica

> ⚠️ **Importante**: Los ejemplos son para **referencia educativa**. No los uses directamente en producción sin adaptarlos.

### Diferencias clave

- **Framework Core**: Solo APIs y utilidades
- **Examples**: Implementaciones completas y funcionales
- **Tu Plugin**: Adaptación personalizada de los ejemplos

## 🔄 Changelog

### v2.3.1 - Corrección de Errores de Compilación

- **🐛 Fix**: Corregido error de compilación en `CommandManagerSpigot.java`
  - Solucionado "incompatible types: invalid method reference" en línea 36
  - Cambiado `CommandManagerSpigot::unregister` por `this::unregister`
- **🔧 Mejoras**: Actualizado `ReflectionUtil` con mejoras de LujoEmotes
  - Sistema de caché para campos con `ConcurrentHashMap`
  - Nuevo método `getFieldValue()` para obtener valores de campos
  - Anotaciones JetBrains `@NotNull` y `@Nullable`
  - Documentación JavaDoc completa
  - Manejo mejorado de errores sin spam en logs
  - Optimizaciones de rendimiento thread-safe

### v2.3.0 - Mejoras en CommandManagerSpigot

- **🔧 CommandManagerSpigot Dinámico**: El registro de comandos ahora usa el nombre del plugin dinámicamente en lugar de "BasePlugin" hardcoded
- **🏗️ Constructor Mejorado**: CommandManagerSpigot ahora requiere una instancia de JavaPlugin en su constructor
- **📦 Métodos de Instancia**: Los métodos `register()` y `unregister()` ahora son de instancia en lugar de estáticos
- **🎯 Mayor Flexibilidad**: El framework puede ser usado por cualquier plugin sin modificaciones

### Migración desde v1.0

#### Cambios Principales

1. **Java 21**: Actualización de Java 17 a Java 21
2. **Gradle 8.3+**: Actualización del sistema de build
3. **Shadow Plugin**: Migración a `com.gradleup.shadow`
4. **API Thread-Safe**: Implementación mejorada del singleton
5. **Dependencias Actualizadas**: Todas las librerías a sus últimas versiones

### Pasos de Migración

1. Actualizar Java a versión 21
2. Actualizar Gradle a 8.3+
3. Cambiar el plugin Shadow en build.gradle
4. Actualizar las llamadas a la API si es necesario
5. Recompilar el proyecto

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

## 📞 Soporte

- **Issues**: [GitHub Issues](https://github.com/murcisluis/BasePlugin/issues)
- **Email**: murcisluis@gmail.com
- **Discord**: [Servidor de Discord](https://discord.gg/tu-servidor)

## 🎉 Agradecimientos

- Comunidad de Spigot/BungeeCord
- Desarrolladores de Adventure API
- Contribuidores del proyecto

---

**¡Hecho con ❤️ para la comunidad de Minecraft!**