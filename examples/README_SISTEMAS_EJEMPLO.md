# Sistemas de Ejemplo - BasePlugin Framework

Este directorio contiene implementaciones de ejemplo que demuestran cómo extender las clases abstractas `BaseBungee` y `BaseSpigot` para crear sistemas personalizados.

## Estructura de los Ejemplos

### Para BungeeCord

- **`ExampleBaseBungeeImpl.java`**: Implementación personalizada que extiende `BaseBungee`
- **`ExampleBaseBungeAPI.java`**: API personalizada que utiliza la implementación extendida
- **`ExampleBungeeListener.java`**: Listener de eventos que demuestra el uso de los sistemas
- **`ExampleBungeePlugin.java`**: Plugin principal que inicializa todo

### Para Spigot

- **`ExampleBaseSpigotImpl.java`**: Implementación personalizada que extiende `BaseSpigot`
- **`ExampleBaseSpigotAPI.java`**: API personalizada que utiliza la implementación extendida
- **`ExampleSpigotListener.java`**: Listener de eventos que demuestra el uso de los sistemas
- **`ExampleSpigotPlugin.java`**: Plugin principal que inicializa todo

## Sistemas Implementados

### 🎭 Sistema de Emotes

**Funcionalidades:**

- Gestión de emotes disponibles
- Control de acceso por jugador
- Uso de emotes en chat global
- Configuración desde archivos YAML

**Métodos principales:**

```java
// Obtener un emote
String emote = base.getEmote("happy"); // 😊

// Verificar acceso
boolean hasAccess = base.hasEmoteAccess(player, "happy");

// Otorgar emote
base.grantEmote(player, "love");

// Usar emote
base.useEmote(player, "laugh");
```

### 💰 Sistema de Economía

**Funcionalidades:**

- Gestión de balances de jugadores
- Transferencias entre jugadores
- Precios de items configurables
- Transacciones seguras

**Métodos principales:**

```java
// Obtener balance
double balance = base.getBalance(player);

// Añadir dinero
base.addBalance(player, 100.0);

// Retirar dinero
boolean success = base.removeBalance(player, 50.0);

// Transferir dinero
boolean transferred = base.transferMoney(fromPlayer, toPlayer, 25.0);

// Obtener precio de item
double price = base.getItemPrice("diamond");
```

### 📊 Sistema de Métricas

**Funcionalidades:**

- Contadores de eventos
- Registro de tiempos
- Estadísticas del servidor
- Métricas de rendimiento (TPS en Spigot)

**Métodos principales:**

```java
// Incrementar contador
base.incrementMetric("commands_executed");

// Obtener valor de métrica
int count = base.getMetricCount("player_joins");

// Registrar tiempo
base.recordMetricTime("last_backup", System.currentTimeMillis());

// Obtener todas las métricas
Map<String, Object> allMetrics = base.getAllMetrics();

// Mostrar estadísticas a un jugador
base.showServerStats(player);
```

## Cómo Usar

### 1. Inicialización en el Plugin Principal

**Para BungeeCord:**

```java
@Override
public void onLoad() {
    // Usar nuestra API personalizada
    BaseAPIFactory.initialize(new ExampleBaseBungeAPI());
    BaseAPIFactory.getAPI().onLoad(this);
}

@Override
public void onEnable() {
    BaseAPIFactory.getAPI().onEnable();
    
    // Obtener nuestra implementación
    ExampleBaseBungeeImpl base = ExampleBaseBungeAPI.getExampleImplementation();
    
    // Registrar listener
    getProxy().getPluginManager().registerListener(this, new ExampleBungeeListener());
}
```

**Para Spigot:**

```java
@Override
public void onLoad() {
    // Usar nuestra API personalizada
    BaseAPIFactory.initialize(new ExampleBaseSpigotAPI());
    BaseAPIFactory.getAPI().onLoad(this);
}

@Override
public void onEnable() {
    BaseAPIFactory.getAPI().onEnable();
    
    // Obtener nuestra implementación
    ExampleBaseSpigotImpl base = ExampleBaseSpigotAPI.getExampleImplementation();
    
    // Registrar listener
    getServer().getPluginManager().registerEvents(new ExampleSpigotListener(), this);
}
```

### 2. Uso en Comandos

```java
public class EmoteCommand extends AbstractCommandSpigot {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ExampleBaseSpigotImpl base = ExampleBaseSpigotAPI.getExampleImplementation();
            
            if (args.length > 0) {
                base.useEmote(player, args[0]);
            } else {
                // Mostrar emotes disponibles
                Map<String, String> emotes = base.getAvailableEmotes();
                // ... mostrar lista
            }
        }
    }
}
```

### 3. Uso en Listeners

```java
@EventHandler
public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    ExampleBaseSpigotImpl base = ExampleBaseSpigotAPI.getExampleImplementation();
    
    // Los sistemas se inicializan automáticamente
    base.onPlayerJoin(player);
}
```

## Configuración

Los sistemas utilizan archivos de configuración YAML:

- **`emotes.yml`**: Configuración de emotes
- **`economy.yml`**: Configuración económica
- **`metrics.yml`**: Configuración de métricas

### Ejemplo de `emotes.yml`:

```yaml
emotes:
  enabled: true
  custom-emotes:
    party: "🎉"
    sleep: "😴"
```

### Ejemplo de `economy.yml`:

```yaml
economy:
  enabled: true
  starting-balance: 100.0
  items:
    diamond: 100.0
    gold: 50.0
```

### Ejemplo de `metrics.yml`:

```yaml
metrics:
  enabled: true
  save-interval: 5
  track-tps: true
```

## Ventajas de esta Arquitectura

1. **Extensibilidad**: Fácil añadir nuevos sistemas
2. **Modularidad**: Cada sistema es independiente
3. **Reutilización**: Código compartido entre Spigot y BungeeCord
4. **Mantenibilidad**: Separación clara de responsabilidades
5. **Testabilidad**: Fácil crear tests unitarios

## Personalización

Para crear tu propia implementación:

1. Crea una clase que extienda `BaseBungee` o `BaseSpigot`
2. Implementa tus sistemas personalizados
3. Crea una API personalizada que use tu implementación
4. Inicializa tu API en el plugin principal
5. Usa `getExampleImplementation()` para acceder a métodos específicos

## Notas Importantes

- Los datos se almacenan en memoria por defecto
- Para persistencia, implementa guardado en archivos o base de datos
- Los sistemas son thread-safe usando `ConcurrentHashMap`
- Las métricas se reinician al recargar el plugin
- Los balances se inicializan automáticamente para nuevos jugadores

## Ejemplo Completo de Uso

```java
// Obtener la implementación
ExampleBaseSpigotImpl base = ExampleBaseSpigotAPI.getExampleImplementation();

// Usar sistema de economía
double balance = base.getBalance(player);
base.addBalance(player, 50.0);

// Usar sistema de emotes
base.grantEmote(player, "happy");
base.useEmote(player, "happy");

// Usar sistema de métricas
base.incrementMetric("custom_action");
base.showServerStats(player);
```

Esta arquitectura te permite tener control total sobre los sistemas internos mientras mantienes la compatibilidad con el framework BasePlugin.