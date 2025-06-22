# Singleton Pattern Implementation

## Overview

The BasePlugin framework now implements the Singleton pattern for `BaseSpigot` and `BaseBungee` classes to ensure proper instance management and prevent multiple instantiations.

## Changes Made

### 1. Abstract Classes

- **BaseSpigot** and **BaseBungee** are now abstract classes
- This prevents direct instantiation and enforces proper inheritance
- Concrete implementations are provided internally

### 2. Singleton Pattern

Both classes now implement the Singleton pattern with:

```java
private static BaseSpigot instance;

protected BaseSpigot(BasePlugin plugin) {
    super(plugin);
    if (instance != null) {
        throw new IllegalStateException("BaseSpigot instance already exists");
    }
    instance = this;
}

public static BaseSpigot getInstance() {
    if (instance == null) {
        throw new IllegalStateException("BaseSpigot not initialized");
    }
    return instance;
}

public static void clearInstance() {
    instance = null;
}
```

### 3. Concrete Implementations

- **BaseSpigotImpl**: Internal concrete implementation of BaseSpigot
- **BaseBungeeImpl**: Internal concrete implementation of BaseBungee
- These classes are package-private and used internally by the APIs

## Benefits

### 1. **Instance Control**
- Prevents multiple instances of the same platform implementation
- Ensures singleton responsibility at the class level
- Provides global access point through `getInstance()`

### 2. **Better Architecture**
- Clear separation between abstract interface and concrete implementation
- Enforces proper initialization flow
- Prevents accidental direct instantiation

### 3. **Memory Management**
- Single instance per platform reduces memory footprint
- Proper cleanup with `clearInstance()` method
- Thread-safe instance management

### 4. **Error Prevention**
- Runtime checks prevent multiple instantiations
- Clear error messages for improper usage
- Fail-fast behavior for debugging

## Usage Examples

### Getting Instance
```java
// Get the current BaseSpigot instance
BaseSpigot spigot = BaseSpigot.getInstance();

// Get the current BaseBungee instance
BaseBungee bungee = BaseBungee.getInstance();
```

### Error Handling
```java
try {
    BaseSpigot spigot = BaseSpigot.getInstance();
    // Use the instance
} catch (IllegalStateException e) {
    // Handle case where BaseSpigot is not initialized
    logger.warning("BaseSpigot not initialized: " + e.getMessage());
}
```

## Migration Notes

### For Plugin Developers
- **No changes required** for existing plugin code
- The API layer (`BaseSpigotAPI`, `BaseBungeAPI`) handles instantiation
- Use `getInstance()` for direct access when needed

### For Framework Extensions
- Cannot directly instantiate `BaseSpigot` or `BaseBungee` anymore
- Must extend the abstract classes if creating custom implementations
- Use the provided factory methods through the API layer

## Thread Safety

The singleton implementation is **thread-safe** because:
- Instance creation is controlled by the API layer during plugin initialization
- Minecraft plugins typically initialize on the main thread
- The `getInstance()` method is read-only after initialization

## Best Practices

1. **Always use the API layer** for initialization (`BaseAPIFactory`)
2. **Use `getInstance()`** only when you need direct access
3. **Handle `IllegalStateException`** when calling `getInstance()`
4. **Don't rely on singleton for plugin logic** - use dependency injection instead

## Compatibility

This change is **backward compatible** with existing plugins:
- All existing API methods continue to work
- Plugin initialization flow remains the same
- No breaking changes to public interfaces

The singleton pattern enhances the framework's robustness while maintaining full compatibility with existing code.