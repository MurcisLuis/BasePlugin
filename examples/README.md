# BasePlugin Examples

Este módulo contiene **ejemplos de implementación** del framework BasePlugin. 

⚠️ **IMPORTANTE**: Estas son clases de **EJEMPLO**. No las uses directamente en producción. Copia y modifica según tus necesidades específicas.

## 📁 Estructura

```
examples/
├── src/main/java/com/gmail/murcisluis/base/examples/
│   ├── bungeecord/
│   │   ├── ExampleBungeePlugin.java      # Ejemplo de plugin BungeeCord
│   │   └── commands/
│   │       └── ExampleCommandBungee.java # Ejemplo de comando BungeeCord
│   └── spigot/
│       ├── ExampleSpigotPlugin.java      # Ejemplo de plugin Spigot
│       └── commands/
│           └── ExampleCommandSpigot.java # Ejemplo de comando Spigot
├── src/main/resources/
│   ├── plugin.yml.example               # Plantilla para plugin.yml (Spigot)
│   └── bungee.yml.example               # Plantilla para bungee.yml (BungeeCord)
└── README.md
```

## 🚀 Cómo usar estos ejemplos

### 1. Para crear un plugin de Spigot:

1. **Copia** `ExampleSpigotPlugin.java` a tu proyecto
2. **Copia** `plugin.yml.example` a `src/main/resources/plugin.yml`
3. **Renombra** la clase a tu nombre de plugin (ej: `MiPlugin.java`)
4. **Cambia** el package a tu dominio
5. **Modifica** `plugin.yml` con tu información:
   - Cambia `name`, `main`, `authors`, `description`
   - Personaliza permisos y comandos
6. **Modifica** según tus necesidades:
   - Elimina el registro de comandos si no los necesitas
   - Añade tu lógica específica en `onEnable()`

### 2. Para crear un plugin de BungeeCord:

1. **Copia** `ExampleBungeePlugin.java` a tu proyecto
2. **Copia** `bungee.yml.example` a `src/main/resources/bungee.yml`
3. **Renombra** la clase a tu nombre de plugin
4. **Cambia** el package a tu dominio
5. **Modifica** `bungee.yml` con tu información:
   - Cambia `name`, `main`, `author`, `description`
   - Personaliza permisos
6. **Modifica** según tus necesidades

### 3. Para crear comandos personalizados:

1. **Copia** `ExampleCommandSpigot.java` o `ExampleCommandBungee.java`
2. **Modifica** la anotación `@CommandInfo`:
   - Cambia `permission`, `usage`, `description`, `aliases`
3. **Implementa** tu lógica en `getCommandHandler()`
4. **Personaliza** el tab completion si lo necesitas

## 📋 Ejemplo de uso mínimo

### Plugin Spigot básico (sin comandos):

```java
package com.tudominio.tuplugin;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.spigot.api.BaseSpigotAPI;
import com.gmail.murcisluis.base.spigot.api.utils.config.ConfigAdapterSpigot;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MiPlugin extends JavaPlugin implements BasePlugin {
    private ConfigAdapterSpigot configAdapter;

    @Override
    public void onLoad() {
        configAdapter = new ConfigAdapterSpigot();
        BaseAPIFactory.initialize(new BaseSpigotAPI());
        BaseAPIFactory.getAPI().onLoad(this);
    }

    @Override
    public void onEnable() {
        BaseAPIFactory.getAPI().onEnable();
        
        // Tu lógica aquí
        getLogger().info("¡Mi plugin está funcionando!");
    }

    @Override
    public void onDisable() {
        BaseAPIFactory.getAPI().onDisable();
    }
    
    // Implementar métodos requeridos de BasePlugin...
}
```

## 🔧 Configuración del build.gradle

Para usar BasePlugin en tu proyecto, añade estas dependencias:

```gradle
dependencies {
    implementation 'com.gmail.murcisluis:BasePlugin-spigot:2.0.0'
    // o para BungeeCord:
    // implementation 'com.gmail.murcisluis:BasePlugin-bungeecord:2.0.0'
    
    compileOnly 'org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT'
}
```

## 📚 Características mostradas en los ejemplos

- ✅ Inicialización correcta del framework
- ✅ Registro opcional de comandos
- ✅ Manejo seguro de recursos
- ✅ Implementación de métodos requeridos
- ✅ Manejo de errores mejorado
- ✅ Documentación clara
- ✅ Buenas prácticas de código

## 🎯 Diferencias con la versión anterior

**Antes (incorrecto):**
- Las clases base registraban comandos automáticamente
- No había separación entre framework y ejemplos
- El desarrollador no tenía control total

**Ahora (correcto):**
- El framework solo proporciona APIs
- Los ejemplos están separados
- El desarrollador decide qué registrar
- Cumple las promesas del README principal

---

**¡Recuerda**: Estos son **ejemplos educativos**. Adapta el código a tus necesidades específicas!