# BasePlugin Framework

[![Version](https://img.shields.io/badge/version-2.0.0-blue.svg)](https://github.com/murcisluis/BasePlugin)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://opensource.org/licenses/MIT)

Un framework base moderno y robusto para el desarrollo de plugins de Minecraft (Spigot/BungeeCord) que implementa principios SOLID y patrones de diseño como Singleton. Diseñado para ser usado como dependencia en otros proyectos.

## 🚀 Características

- **Arquitectura Modular**: Separación clara entre common, spigot y bungeecord
- **Principios SOLID**: Implementación de principios de diseño sólidos
- **Patrón Singleton Thread-Safe**: Factory pattern con sincronización adecuada
- **API Moderna**: Uso de Adventure API para mensajes modernos
- **Configuración Avanzada**: Sistema de configuración flexible y extensible
- **Comandos Inteligentes**: Sistema de comandos con autocompletado
- **Soporte Multi-Plataforma**: Compatible con Spigot y BungeeCord
- **Versiones Actualizadas**: Todas las dependencias actualizadas a las últimas versiones

## 📋 Requisitos

- **Java 21** o superior
- **Gradle 8.3+**
- **Spigot/Paper 1.21.4+** o **BungeeCord 1.21+**

## 📦 Instalación como Dependencia

### Gradle (Recomendado)

#### 1. Agregar el repositorio

```gradle
repositories {
    mavenCentral()
    maven { url 'https://hub.spigotmc.org/nexus/content/repositories/snapshots/' }
    maven { url 'https://oss.sonatype.org/content/repositories/snapshots' }
    maven { url 'https://maven.pkg.github.com/murcisluis/BasePlugin' }
}
```

#### 2. Agregar la dependencia

**Para plugins de Spigot:**
```gradle
dependencies {
    implementation 'com.gmail.murcisluis:BasePlugin-spigot:2.0.0'
}
```

**Para plugins de BungeeCord:**
```gradle
dependencies {
    implementation 'com.gmail.murcisluis:BasePlugin-bungeecord:2.0.0'
}
```

**Para proyectos que usan ambos:**
```gradle
dependencies {
    implementation 'com.gmail.murcisluis:BasePlugin-common:2.0.0'
    implementation 'com.gmail.murcisluis:BasePlugin-spigot:2.0.0'
    implementation 'com.gmail.murcisluis:BasePlugin-bungeecord:2.0.0'
}
```

### Maven

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/murcisluis/BasePlugin</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.gmail.murcisluis</groupId>
        <artifactId>BasePlugin-spigot</artifactId>
        <version>2.0.0</version>
    </dependency>
</dependencies>
```

## 🛠️ Uso Básico

### Plugin de Spigot

```java
package com.tudominio.tuplugin;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.spigot.api.BaseSpigotAPI;
import com.gmail.murcisluis.base.spigot.api.BaseSpigot;
import org.bukkit.plugin.java.JavaPlugin;

public class TuPlugin extends JavaPlugin {
    
    @Override
    public void onLoad() {
        // Inicializar la API de BasePlugin
        BaseAPIFactory.initialize(new BaseSpigotAPI());
        BaseAPIFactory.getAPI().onLoad(this);
    }
    
    @Override
    public void onEnable() {
        // Habilitar la API
        BaseAPIFactory.getAPI().onEnable();
        
        // Obtener la instancia de BaseSpigot
        BaseSpigot base = (BaseSpigot) BaseAPIFactory.get();
        
        // Tu lógica de plugin aquí
        getLogger().info("Plugin habilitado usando BasePlugin Framework!");
    }
    
    @Override
    public void onDisable() {
        // Deshabilitar la API de forma segura
        BaseAPIFactory.shutdown();
    }
}
```

### Plugin de BungeeCord

```java
package com.tudominio.tuplugin;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.bungeecord.api.BaseBungeeCordAPI;
import net.md_5.bungee.api.plugin.Plugin;

public class TuPlugin extends Plugin {
    
    @Override
    public void onLoad() {
        BaseAPIFactory.initialize(new BaseBungeeCordAPI());
        BaseAPIFactory.getAPI().onLoad(this);
    }
    
    @Override
    public void onEnable() {
        BaseAPIFactory.getAPI().onEnable();
        getLogger().info("Plugin BungeeCord habilitado!");
    }
    
    @Override
    public void onDisable() {
        BaseAPIFactory.shutdown();
    }
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

```java
public class MiComando extends AbstractCommandSpigot {
    
    public MiComando() {
        super("micomando");
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        // Lógica del comando
        sender.sendMessage("¡Comando ejecutado!");
    }
    
    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        // Autocompletado
        return Arrays.asList("opcion1", "opcion2", "opcion3");
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
    implementation 'com.gmail.murcisluis:BasePlugin-spigot:2.0.0'
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

## 🔄 Migración desde v1.0

### Cambios Principales

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