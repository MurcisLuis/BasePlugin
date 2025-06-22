# Soporte Multi-Versión para Spigot y Paper

BasePlugin ahora incluye soporte completo para múltiples versiones de Minecraft, tanto para Spigot como Paper.

## Versiones Soportadas

### Minecraft 1.19.x

- **Spigot**: 1.19.4-R0.1-SNAPSHOT
- **Paper**: 1.19.4-R0.1-SNAPSHOT

### Minecraft 1.20.x

- **Spigot**: 1.20.6-R0.1-SNAPSHOT
- **Paper**: 1.20.6-R0.1-SNAPSHOT

### Minecraft 1.21.x

- **Spigot**: 1.21.1, 1.21.2, 1.21.3, 1.21.4, 1.21.5, 1.21.6
- **Paper**: 1.21.1, 1.21.2, 1.21.3, 1.21.4, 1.21.5, 1.21.6

## Configuración Base

Por defecto, el proyecto compila contra **Spigot 1.19.4** para máxima compatibilidad hacia atrás.

## Compilación para Versiones Específicas

### Para Spigot

```bash
# Compilar para Spigot 1.19.4
./gradlew :spigot:build -PuseConfiguration=spigot119

# Compilar para Spigot 1.20.6
./gradlew :spigot:build -PuseConfiguration=spigot120

# Compilar para Spigot 1.21.4
./gradlew :spigot:build -PuseConfiguration=spigot1214
```

### Para Paper

```bash
# Compilar para Paper 1.19.4
./gradlew :spigot:build -PuseConfiguration=paper119

# Compilar para Paper 1.20.6
./gradlew :spigot:build -PuseConfiguration=paper120

# Compilar para Paper 1.21.4
./gradlew :spigot:build -PuseConfiguration=paper1214
```

## Ventajas de Paper sobre Spigot

- **Mejor rendimiento**: Optimizaciones adicionales
- **Más APIs**: APIs extendidas para desarrolladores
- **Mejor gestión de chunks**: Carga asíncrona de chunks
- **Configuración avanzada**: Más opciones de configuración
- **Compatibilidad**: 100% compatible con plugins de Spigot

## Detección de Versión en Runtime

```java
public class VersionUtils {
    
    public static boolean isPaper() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    
    public static String getMinecraftVersion() {
        return Bukkit.getBukkitVersion().split("-")[0];
    }
    
    public static boolean isVersionAtLeast(String version) {
        String current = getMinecraftVersion();
        return compareVersions(current, version) >= 0;
    }
    
    private static int compareVersions(String v1, String v2) {
        String[] parts1 = v1.split("\\.");
        String[] parts2 = v2.split("\\.");
        
        int length = Math.max(parts1.length, parts2.length);
        for (int i = 0; i < length; i++) {
            int part1 = i < parts1.length ? Integer.parseInt(parts1[i]) : 0;
            int part2 = i < parts2.length ? Integer.parseInt(parts2[i]) : 0;
            
            if (part1 < part2) return -1;
            if (part1 > part2) return 1;
        }
        return 0;
    }
}
```

## Uso de Features Específicas

```java
public class FeatureManager {
    
    public void useModernFeatures() {
        if (VersionUtils.isPaper()) {
            // Usar APIs específicas de Paper
            usePaperFeatures();
        }
        
        if (VersionUtils.isVersionAtLeast("1.21")) {
            // Usar features de 1.21+
            useModern121Features();
        } else if (VersionUtils.isVersionAtLeast("1.20")) {
            // Usar features de 1.20+
            useModern120Features();
        } else {
            // Fallback para 1.19.x
            useLegacyFeatures();
        }
    }
    
    private void usePaperFeatures() {
        // Implementar features específicas de Paper
    }
    
    private void useModern121Features() {
        // Implementar features de 1.21+
    }
    
    private void useModern120Features() {
        // Implementar features de 1.20+
    }
    
    private void useLegacyFeatures() {
        // Implementar features compatibles con 1.19.x
    }
}
```

## Recomendaciones

1. **Para desarrollo**: Usa Paper para mejor experiencia de desarrollo
2. **Para producción**: Paper ofrece mejor rendimiento
3. **Para compatibilidad**: La compilación base con Spigot 1.19.4 asegura máxima compatibilidad
4. **Para features modernas**: Detecta la versión en runtime y usa features apropiadas

## Plugin.yml Recomendado

```yaml
name: TuPlugin
version: 1.0.0
main: com.tu.plugin.Main
api-version: '1.19'
author: TuNombre
description: Tu descripción

# Compatibilidad amplia
softdepend: [Vault, PlaceholderAPI]
```

El `api-version: '1.19'` asegura compatibilidad desde 1.19.4 hasta las versiones más recientes.