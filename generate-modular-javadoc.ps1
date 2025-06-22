# Script PowerShell para generar Javadoc por módulos
Write-Host "Generando Javadoc modular para BasePlugin..."

# Crear directorio principal de javadoc
$javadocDir = "javadoc"
if (Test-Path $javadocDir) {
    Remove-Item $javadocDir -Recurse -Force
}
New-Item -ItemType Directory -Path $javadocDir -Force | Out-Null

# Función para generar Javadoc de un módulo
function Generate-ModuleJavadoc($ModuleName, $SourcePath, $OutputPath, $Title) {
    Write-Host "Generando Javadoc para módulo: $ModuleName"
    
    # Crear directorio del módulo
    New-Item -ItemType Directory -Path $OutputPath -Force | Out-Null
    
    # Buscar archivos Java
    $sourceFiles = @()
    if (Test-Path $SourcePath) {
        $sourceFiles = Get-ChildItem -Path $SourcePath -Filter "*.java" -Recurse | ForEach-Object { $_.FullName }
    }
    
    Write-Host "Encontrados $($sourceFiles.Count) archivos Java en $ModuleName"
    
    if ($sourceFiles.Count -eq 0) {
        Write-Host "No se encontraron archivos Java en $ModuleName"
        return $false
    }
    
    # Construir comando javadoc básico
    $javadocCmd = "javadoc -d `"$OutputPath`" -encoding UTF-8 -charset UTF-8 -author -version -windowtitle `"$Title`" -doctitle `"$Title`" -Xdoclint:none -source 21 -link https://docs.oracle.com/en/java/javase/21/docs/api/"
    
    # Agregar archivos fuente
    foreach ($file in $sourceFiles) {
        $javadocCmd += " `"$file`""
    }
    
    Write-Host "Ejecutando javadoc para $ModuleName..."
    Invoke-Expression $javadocCmd
    
    # Verificar si se generaron archivos HTML
    $htmlFiles = Get-ChildItem -Path $OutputPath -Filter "*.html" -ErrorAction SilentlyContinue
    if ($htmlFiles.Count -gt 0) {
        Write-Host "[OK] Javadoc generado exitosamente para $ModuleName ($($htmlFiles.Count) archivos HTML)"
        return $true
    } else {
        Write-Host "[WARN] No se generaron archivos HTML para $ModuleName"
        return $false
    }
}

# Generar Javadoc para cada módulo
$results = @{}

# Módulo Common (núcleo)
$results['common'] = Generate-ModuleJavadoc "Common" "common\src\main\java" "$javadocDir\common" "BasePlugin Common API Documentation"

# Módulo Spigot
$results['spigot'] = Generate-ModuleJavadoc "Spigot" "spigot\src\main\java" "$javadocDir\spigot" "BasePlugin Spigot API Documentation"

# Módulo BungeeCord
$results['bungeecord'] = Generate-ModuleJavadoc "BungeeCord" "bungeecord\src\main\java" "$javadocDir\bungeecord" "BasePlugin BungeeCord API Documentation"

# Crear página principal de navegación
$indexContent = @"
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BasePlugin API Documentation</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 30px;
            border-bottom: 3px solid #3498db;
            padding-bottom: 10px;
        }
        .modules {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        .module {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            background: #fafafa;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .module:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .module h3 {
            color: #2980b9;
            margin-top: 0;
            margin-bottom: 15px;
        }
        .module p {
            color: #666;
            margin-bottom: 15px;
        }
        .module a {
            display: inline-block;
            background: #3498db;
            color: white;
            padding: 8px 16px;
            text-decoration: none;
            border-radius: 4px;
            transition: background 0.2s;
        }
        .module a:hover {
            background: #2980b9;
        }
        .status {
            margin-top: 20px;
            padding: 15px;
            border-radius: 5px;
            background: #e8f5e8;
            border-left: 4px solid #27ae60;
        }
        .warning {
            background: #fff3cd;
            border-left-color: #ffc107;
        }
        .error {
            background: #f8d7da;
            border-left-color: #dc3545;
        }
        .unavailable {
            opacity: 0.6;
            pointer-events: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>BasePlugin API Documentation</h1>
        <p>Documentación completa de la API de BasePlugin, organizada por módulos.</p>
        
        <div class="modules">
"@

# Agregar información de cada módulo
if ($results['common']) {
    $indexContent += @"
            <div class="module">
                <h3>📦 Common Module</h3>
                <p>Núcleo del framework con funcionalidades compartidas entre plataformas.</p>
                <a href="common/index.html">Ver Documentación</a>
            </div>
"@
} else {
    $indexContent += @"
            <div class="module unavailable">
                <h3>📦 Common Module</h3>
                <p>Núcleo del framework - Documentación no disponible</p>
                <span>Error en generación</span>
            </div>
"@
}

if ($results['spigot']) {
    $indexContent += @"
            <div class="module">
                <h3>🔧 Spigot Module</h3>
                <p>Implementación específica para servidores Spigot/Paper.</p>
                <a href="spigot/index.html">Ver Documentación</a>
            </div>
"@
} else {
    $indexContent += @"
            <div class="module unavailable">
                <h3>🔧 Spigot Module</h3>
                <p>Implementación específica para servidores Spigot/Paper - Documentación no disponible</p>
                <span>Error en generación</span>
            </div>
"@
}

if ($results['bungeecord']) {
    $indexContent += @"
            <div class="module">
                <h3>🌐 BungeeCord Module</h3>
                <p>Implementación específica para servidores BungeeCord/Waterfall.</p>
                <a href="bungeecord/index.html">Ver Documentación</a>
            </div>
"@
} else {
    $indexContent += @"
            <div class="module unavailable">
                <h3>🌐 BungeeCord Module</h3>
                <p>Implementación específica para servidores BungeeCord/Waterfall - Documentación no disponible</p>
                <span>Error en generación</span>
            </div>
"@
}

$indexContent += @"
        </div>
        
        <div class="status">
            <h4>Estado de Generación:</h4>
            <ul>
"@

foreach ($module in $results.Keys) {
    $status = if ($results[$module]) { "[OK] Generado" } else { "[ERROR] Error" }
    $indexContent += "                <li><strong>$($module.ToUpper()):</strong> $status</li>`n"
}

$indexContent += @"
            </ul>
            <p><em>Nota: Algunos módulos pueden tener errores de compilación debido a dependencias externas faltantes.</em></p>
        </div>
    </div>
</body>
</html>
"@

# Guardar página principal
$indexPath = Join-Path $javadocDir "index.html"
Set-Content -Path $indexPath -Value $indexContent -Encoding UTF8

Write-Host "`n=== Resumen de Generación ==="
Write-Host "Documentación principal: $indexPath"
foreach ($module in $results.Keys) {
    $status = if ($results[$module]) { "[OK]" } else { "[ERROR]" }
    Write-Host "$status Módulo $($module.ToUpper()): $($results[$module])"
}

Write-Host "`n¡Documentación modular generada!"