# Sistema de Localización Mejorado - BasePlugin Framework

## Descripción General

El sistema de localización mejorado del BasePlugin Framework ahora incluye soporte completo para manejo de UUIDs de jugadores y persistencia de preferencias de idioma. Este sistema permite que cada jugador tenga su propio idioma configurado que se mantiene entre sesiones.

## Características Principales

### 🌍 Gestión de Idiomas por Jugador
- **Persistencia de Preferencias**: Las preferencias de idioma se guardan automáticamente en archivos
- **Detección Automática**: Detección automática del idioma basada en la configuración regional del cliente
- **Configuración Flexible**: Los jugadores pueden cambiar su idioma en cualquier momento
- **Fallback Inteligente**: Sistema de respaldo al idioma por defecto si no se encuentra el preferido

### 📊 Estadísticas y Métricas
- **Estadísticas de Uso**: Seguimiento de qué idiomas son más utilizados
- **Métricas de Rendimiento**: Monitoreo del rendimiento del sistema de localización
- **Informes Administrativos**: Herramientas para administradores para ver estadísticas detalladas

### 🔧 Herramientas de Administración
- **Comandos de Gestión**: Comandos completos para gestionar idiomas
- **Recarga en Caliente**: Capacidad de recargar archivos de idioma sin reiniciar
- **Limpieza Automática**: Limpieza automática de datos de jugadores inactivos

## Estructura de Archivos

```
examples/
├── src/main/java/
│   └── com/gmail/murcisluis/base/examples/
│       ├── common/
│       │   ├── LocalizationExample.java          # Clase principal de ejemplos
│       │   └── PlayerLocalizationHelper.java     # Utilidades de localización
│       ├── bungeecord/
│       │   ├── ExampleBungeePlugin.java          # Plugin con localización mejorada
│       │   ├── ExampleBungeeListener.java        # Listener con soporte UUID
│       │   └── commands/
│       │       └── LanguageCommandBungee.java    # Comando de gestión de idiomas
│       └── spigot/
│           └── [archivos similares para Spigot]
└── src/main/resources/
    ├── lang_en.yml                               # Archivo de idioma inglés
    ├── lang_es.yml                               # Archivo de idioma español
    ├── lang_fr.yml                               # Archivo de idioma francés
    └── player_languages.yml                      # Configuración de preferencias
```

## Uso Básico

### 1. Inicialización del Sistema

```java
// En tu plugin principal
private void initializeEnhancedLocalization() {
    LocalizationManager locManager = LocalizationManager.getInstance();
    
    // Cargar archivos de idioma
    locManager.loadLanguage("en", "lang_en.yml");
    locManager.loadLanguage("es", "lang_es.yml");
    locManager.loadLanguage("fr", "lang_fr.yml");
    
    // Establecer idioma por defecto
    locManager.setCurrentLanguage("en");
    
    // Inicializar clases auxiliares
    this.localizationExample = new LocalizationExample();
    this.localizationHelper = new PlayerLocalizationHelper();
}
```

### 2. Manejo de Conexión de Jugadores

```java
@EventHandler
public void onPlayerJoin(PostLoginEvent event) {
    ProxiedPlayer player = event.getPlayer();
    
    // Obtener configuración regional del cliente
    String clientLocale = "en_US"; // En implementación real: player.getLocale()
    
    // Configurar idioma del jugador
    localizationExample.handlePlayerJoin(
        player.getUniqueId(),
        player.getName(),
        clientLocale
    );
}
```

### 3. Envío de Mensajes Localizados

```java
// Mensaje específico para un jugador
Component welcomeMessage = localizationManager.getPlayerComponent(
    playerUUID, 
    "welcome.message",
    Placeholder.unparsed("player", playerName)
);

// Mensaje con múltiples placeholders
Component transactionMessage = localizationManager.getPlayerComponent(
    playerUUID,
    "economy.transaction",
    Placeholder.unparsed("amount", "100.50"),
    Placeholder.unparsed("currency", "$")
);
```

### 4. Gestión de Preferencias de Idioma

```java
// Establecer idioma de un jugador
localizationManager.setPlayerLanguage(playerUUID, "es");

// Obtener idioma de un jugador
String playerLanguage = localizationManager.getPlayerLanguage(playerUUID);

// Remover preferencia (volver al por defecto)
localizationManager.removePlayerLanguage(playerUUID);
```

## Comandos Disponibles

### Para Jugadores
- `/lang` - Mostrar idioma actual
- `/lang set <idioma>` - Cambiar idioma
- `/lang list` - Mostrar idiomas disponibles
- `/lang reset` - Restablecer al idioma por defecto

### Para Administradores
- `/lang stats` - Mostrar estadísticas de idiomas
- `/lang reload` - Recargar archivos de idioma

## Configuración de Archivos de Idioma

### Estructura Básica (lang_en.yml)

```yaml
# Mensajes de Bienvenida
welcome:
  message: "<green>Welcome to the server, <yellow>{player}</yellow>!</green>"
  first_time: "<green>Welcome for the first time, <yellow>{player}</yellow>!</green>"

# Mensajes de Error
error:
  permission_denied: "<red>You don't have permission to execute this command.</red>"
  player_not_found: "<red>Player not found.</red>"
  language_not_available: "<red>Language '{language}' is not available. Available: {available}</red>"

# Mensajes de Éxito
success:
  language_changed: "<green>Language successfully changed to {language}!</green>"
  economy_transaction: "<green>Transaction completed! Amount: ${amount}</green>"

# Información
info:
  language_current: "<blue>Your current language is: {language}</blue>"
  available_commands: "<blue>Available commands: /help, /lang, /stats</blue>"
```

### Configuración de Preferencias (player_languages.yml)

```yaml
# Metadatos
metadata:
  version: "2.1.0"
  last_updated: "2024-01-15T10:30:00Z"
  total_players: 0

# Preferencias de jugadores
players:
  # UUID del jugador: idioma preferido
  # "550e8400-e29b-41d4-a716-446655440000": "es"

# Configuración
config:
  auto_detect_language: true
  save_immediately: true
  cleanup_offline_days: 30
  default_language: "en"
  supported_languages:
    - "en"
    - "es"
    - "fr"

# Estadísticas
statistics:
  language_usage:
    en: 0
    es: 0
    fr: 0
  last_cleanup: "never"
```

## Características Avanzadas

### 1. Detección Automática de Idioma

```java
// Configurar idioma automáticamente basado en la configuración regional
localizationHelper.autoConfigurePlayerLanguage(playerUUID, clientLocale, playerName)
    .thenAccept(configuredLanguage -> {
        // Idioma configurado exitosamente
        sendWelcomeMessage(playerUUID, configuredLanguage);
    });
```

### 2. Estadísticas de Uso

```java
// Obtener estadísticas de uso de idiomas
Map<String, Integer> stats = localizationManager.getLanguageStatistics();
int totalPlayers = localizationManager.getPlayersWithLanguagePreferences();

// Mostrar estadísticas formateadas
String formattedStats = localizationHelper.getFormattedLanguageStatistics();
```

### 3. Mensajes de Broadcast Multiidioma

```java
// Enviar mensaje a todos los jugadores en su idioma preferido
public void sendBroadcastMessage(String messageKey, TagResolver... resolvers) {
    Map<String, Integer> languageStats = localizationManager.getLanguageStatistics();
    
    for (String language : languageStats.keySet()) {
        Component message = localizationManager.getComponent(language, messageKey, resolvers);
        // Enviar a todos los jugadores con ese idioma
        sendToPlayersWithLanguage(language, message);
    }
}
```

## Mejores Prácticas

### 1. Gestión de Memoria
- Limpia regularmente las preferencias de jugadores inactivos
- Usa caché para mensajes frecuentemente accedidos
- Evita cargar todos los idiomas si no son necesarios

### 2. Rendimiento
- Carga los archivos de idioma de forma asíncrona
- Usa CompletableFuture para operaciones de E/S
- Implementa un sistema de caché para mensajes

### 3. Mantenimiento
- Mantén consistencia en las claves de mensajes entre idiomas
- Usa placeholders descriptivos
- Documenta las claves de mensajes personalizadas

### 4. Seguridad
- Valida las entradas de idioma
- Sanitiza los placeholders de usuario
- Implementa límites de velocidad para cambios de idioma

## Solución de Problemas

### Problemas Comunes

1. **Archivo de idioma no encontrado**
   - Verifica que el archivo esté en el directorio correcto
   - Asegúrate de que el nombre del archivo coincida exactamente

2. **Preferencias no se guardan**
   - Verifica permisos de escritura en el directorio de datos
   - Comprueba que no haya errores en los logs

3. **Mensajes no se localizan**
   - Verifica que la clave del mensaje exista en el archivo de idioma
   - Asegúrate de que el idioma esté cargado correctamente

### Logs de Depuración

```java
// Habilitar logs detallados
getLogger().info("Available languages: " + localizationManager.getAvailableLanguages());
getLogger().info("Player language: " + localizationManager.getPlayerLanguage(playerUUID));
getLogger().info("Language statistics: " + localizationHelper.getFormattedLanguageStatistics());
```

## Migración desde Versión Anterior

Si estás migrando desde una versión anterior del sistema de localización:

1. **Respalda tus archivos de configuración existentes**
2. **Actualiza la estructura de archivos de idioma** según los nuevos formatos
3. **Migra las preferencias de jugadores** al nuevo formato UUID-based
4. **Actualiza tu código** para usar las nuevas APIs

## Contribución

Para contribuir al sistema de localización:

1. Agrega nuevos idiomas creando archivos `lang_XX.yml`
2. Mejora las traducciones existentes
3. Reporta bugs o sugiere mejoras
4. Contribuye con nuevas características

## Licencia

Este sistema es parte del BasePlugin Framework y está sujeto a la misma licencia del proyecto principal.