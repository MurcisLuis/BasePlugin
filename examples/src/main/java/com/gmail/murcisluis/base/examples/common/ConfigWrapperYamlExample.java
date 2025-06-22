package com.gmail.murcisluis.base.examples.common;

/**
 * Ejemplo que demuestra las capacidades de generación de YAML del ConfigWrapper.
 * 
 * El ConfigWrapper ahora puede:
 * 1. Guardar su configuración actual a cualquier archivo YAML
 * 2. Crear backups automáticos con timestamp
 * 3. Exportar configuración con comentarios legibles
 * 
 * Esto es útil para:
 * - Crear backups antes de cambios importantes
 * - Exportar configuraciones para otros servidores
 * - Generar plantillas de configuración
 * - Debugging y análisis de configuración
 */
public class ConfigWrapperYamlExample {
    
    public static void main(String[] args) {
        System.out.println("=== EJEMPLO: GENERACIÓN DE YAML DESDE CONFIGWRAPPER ===");
        
        // ==================== CARGAR CONFIGURACIÓN ACTUAL ====================
        System.out.println("\n1. Cargando configuración actual...");
        ConfigWrapper config = new ConfigWrapper();
        config.printConfigSummary();
        
        // ==================== GUARDAR A ARCHIVO YAML SIMPLE ====================
        System.out.println("\n2. Guardando configuración a archivo YAML simple...");
        
        boolean saved = config.saveToYaml("mi-configuracion-exportada.yml");
        if (saved) {
            System.out.println("✓ Configuración guardada en 'mi-configuracion-exportada.yml'");
        } else {
            System.out.println("✗ Error al guardar configuración");
        }
        
        // ==================== CREAR BACKUP AUTOMÁTICO ====================
        System.out.println("\n3. Creando backup automático con timestamp...");
        
        String backupFile = config.createBackup();
        if (backupFile != null) {
            System.out.println("✓ Backup creado: " + backupFile);
            System.out.println("  Este archivo contiene todos los valores actuales en memoria");
        }
        
        // ==================== EXPORTAR CON COMENTARIOS ====================
        System.out.println("\n4. Exportando configuración con comentarios...");
        
        boolean exported = config.exportToYamlWithComments("config-con-comentarios.yml");
        if (exported) {
            System.out.println("✓ Configuración exportada con comentarios en 'config-con-comentarios.yml'");
            System.out.println("  Este archivo incluye:");
            System.out.println("  - Comentarios explicativos");
            System.out.println("  - Fecha de exportación");
            System.out.println("  - Estructura organizada por secciones");
        }
        
        // ==================== CASOS DE USO PRÁCTICOS ====================
        System.out.println("\n5. Casos de uso prácticos:");
        
        // Caso 1: Backup antes de cambios
        System.out.println("\n   Caso 1: Backup antes de cambios importantes");
        String preChangeBackup = config.createBackup();
        System.out.println("   - Backup pre-cambio: " + preChangeBackup);
        
        // Caso 2: Exportar configuración para otro servidor
        System.out.println("\n   Caso 2: Exportar para otro servidor");
        config.saveToYaml("config-servidor-2.yml");
        System.out.println("   - Configuración exportada para servidor 2");
        
        // Caso 3: Crear plantilla de configuración
        System.out.println("\n   Caso 3: Crear plantilla con comentarios");
        config.exportToYamlWithComments("plantilla-config.yml");
        System.out.println("   - Plantilla creada con documentación completa");
        
        // ==================== DEMOSTRACIÓN CON CONFIGURACIÓN MODIFICADA ====================
        System.out.println("\n6. Demostración con ConfigWrapperBuilder:");
        
        // Crear configuración personalizada
        ConfigWrapper customConfig = ConfigWrapperBuilder.create()
            .serverName("Servidor de Prueba YAML")
            .maxPlayers(100)
            .economyEnabled(true)
            .startingMoney(5000.0)
            .currencySymbol("€")
            .chatFormat("[%player%] %message%")
            .welcomeMessage(true, "¡Bienvenido %player% al servidor!")
            .mysqlDatabase("localhost", 3306, "minecraft", "admin", "password", true)
            .logging("DEBUG", true, true, 30)
            .build();
        
        System.out.println("\n   Configuración personalizada creada:");
        customConfig.printConfigSummary();
        
        // Exportar configuración personalizada
        System.out.println("\n   Exportando configuración personalizada...");
        customConfig.saveToYaml("config-personalizada.yml");
        customConfig.exportToYamlWithComments("config-personalizada-comentada.yml");
        
        System.out.println("   ✓ Configuración personalizada exportada en 2 formatos");
        
        // ==================== VERIFICACIÓN DE ARCHIVOS GENERADOS ====================
        System.out.println("\n7. Archivos YAML generados:");
        String[] generatedFiles = {
            "mi-configuracion-exportada.yml",
            backupFile,
            "config-con-comentarios.yml",
            "config-servidor-2.yml",
            "plantilla-config.yml",
            "config-personalizada.yml",
            "config-personalizada-comentada.yml"
        };
        
        for (String file : generatedFiles) {
            if (file != null) {
                System.out.println("   - " + file);
            }
        }
        
        // ==================== CONSEJOS DE USO ====================
        System.out.println("\n8. Consejos de uso:");
        System.out.println("   💡 Usa saveToYaml() para exportaciones rápidas");
        System.out.println("   💡 Usa createBackup() antes de cambios importantes");
        System.out.println("   💡 Usa exportToYamlWithComments() para documentación");
        System.out.println("   💡 Los archivos generados son compatibles con ConfigurationManager");
        System.out.println("   💡 Puedes cargar estos archivos con ConfigWrapperBuilder.fromConfig()");
        
        System.out.println("\n=== EJEMPLO COMPLETADO ===");
    }
    
    /**
     * Ejemplo de flujo de trabajo completo con backups
     */
    public static void workflowExample() {
        System.out.println("\n=== FLUJO DE TRABAJO CON BACKUPS ===");
        
        // 1. Cargar configuración actual
        ConfigWrapper config = new ConfigWrapper();
        
        // 2. Crear backup antes de cambios
        String backup = config.createBackup();
        System.out.println("Backup creado: " + backup);
        
        // 3. Simular cambios (en un caso real, modificarías el archivo config.yml)
        System.out.println("Simulando cambios en la configuración...");
        
        // 4. Crear nueva configuración modificada
        ConfigWrapper modifiedConfig = ConfigWrapperBuilder.fromConfig("config.yml")
            .maxPlayers(150)  // Aumentar capacidad
            .debug(true)      // Activar debug
            .build();
        
        // 5. Exportar configuración modificada
        modifiedConfig.saveToYaml("config-modificada.yml");
        
        // 6. Crear backup de la configuración modificada
        String modifiedBackup = modifiedConfig.createBackup();
        
        System.out.println("Configuración modificada guardada");
        System.out.println("Backup original: " + backup);
        System.out.println("Backup modificado: " + modifiedBackup);
    }
    
    /**
     * Ejemplo de migración entre servidores
     */
    public static void migrationExample() {
        System.out.println("\n=== MIGRACIÓN ENTRE SERVIDORES ===");
        
        // Servidor origen
        ConfigWrapper sourceConfig = new ConfigWrapper();
        
        // Exportar configuración base
        sourceConfig.exportToYamlWithComments("servidor-origen.yml");
        
        // Crear configuración para servidor de desarrollo
        ConfigWrapper devConfig = ConfigWrapperBuilder.fromConfig("servidor-origen.yml")
            .serverName("Servidor de Desarrollo")
            .maxPlayers(10)
            .debug(true)
            .maintenanceMode(false)
            .sqliteDatabase("dev-data.db")
            .build();
        
        devConfig.exportToYamlWithComments("servidor-desarrollo.yml");
        
        // Crear configuración para servidor de producción
        ConfigWrapper prodConfig = ConfigWrapperBuilder.fromConfig("servidor-origen.yml")
            .serverName("Servidor de Producción")
            .maxPlayers(200)
            .debug(false)
            .mysqlDatabase("prod-db.example.com", 3306, "minecraft_prod", "admin", "prod_pass", true)
            .logging("INFO", true, true, 90)
            .build();
        
        prodConfig.exportToYamlWithComments("servidor-produccion.yml");
        
        System.out.println("Configuraciones de migración creadas:");
        System.out.println("- servidor-origen.yml (base)");
        System.out.println("- servidor-desarrollo.yml (dev)");
        System.out.println("- servidor-produccion.yml (prod)");
    }
}