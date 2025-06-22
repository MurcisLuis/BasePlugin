# Script para generar Javadoc usando Gradle en cada subproyecto
Write-Host "Generando Javadoc con Gradle para cada módulo..."

# Crear directorio principal de javadoc
$javadocDir = "javadoc"
if (Test-Path $javadocDir) {
    Remove-Item $javadocDir -Recurse -Force
}
New-Item -ItemType Directory -Path $javadocDir -Force | Out-Null

# Función para generar Javadoc con Gradle
function Generate-GradleJavadoc($ModuleName, $ModulePath, $OutputPath) {
    Write-Host "Generando Javadoc para módulo: $ModuleName"
    
    # Crear directorio del módulo
    New-Item -ItemType Directory -Path $OutputPath -Force | Out-Null
    
    # Verificar que el módulo existe
    if (-not (Test-Path $ModulePath)) {
        Write-Host "[ERROR] Módulo $ModuleName no encontrado en $ModulePath"
        return $false
    }
    
    # Ejecutar Gradle javadoc para el módulo específico
    Write-Host "Ejecutando: ./gradlew :$ModuleName`:javadoc"
    
    try {
        # Ejecutar Gradle javadoc
        $process = Start-Process -FilePath "./gradlew.bat" -ArgumentList ":$ModuleName`:javadoc" -Wait -PassThru -NoNewWindow -RedirectStandardOutput "gradle-$ModuleName-output.log" -RedirectStandardError "gradle-$ModuleName-error.log"
        
        if ($process.ExitCode -eq 0) {
            Write-Host "[OK] Gradle javadoc completado para $ModuleName"
            
            # Buscar el directorio de javadoc generado
            $gradleJavadocPath = "$ModulePath\build\docs\javadoc"
            
            if (Test-Path $gradleJavadocPath) {
                Write-Host "Copiando documentación desde $gradleJavadocPath a $OutputPath"
                
                # Copiar archivos generados
                Copy-Item -Path "$gradleJavadocPath\*" -Destination $OutputPath -Recurse -Force
                
                # Verificar que se copiaron archivos HTML
                $htmlFiles = Get-ChildItem -Path $OutputPath -Filter "*.html" -Recurse
                if ($htmlFiles.Count -gt 0) {
                    Write-Host "[OK] Documentación copiada exitosamente para $ModuleName ($($htmlFiles.Count) archivos HTML)"
                    return $true
                } else {
                    Write-Host "[WARN] No se encontraron archivos HTML en la documentación generada para $ModuleName"
                }
            } else {
                Write-Host "[WARN] No se encontró directorio de javadoc en $gradleJavadocPath"
            }
        } else {
            Write-Host "[ERROR] Gradle javadoc falló para $ModuleName (código de salida: $($process.ExitCode))"
            
            # Mostrar errores si existen
            if (Test-Path "gradle-$ModuleName-error.log") {
                $errorContent = Get-Content "gradle-$ModuleName-error.log" -Raw
                if ($errorContent.Trim()) {
                    Write-Host "Errores encontrados:"
                    Write-Host $errorContent
                }
            }
        }
    } catch {
        Write-Host "[ERROR] Excepción al ejecutar Gradle para $ModuleName`: $($_.Exception.Message)"
    }
    
    # Si Gradle falló, crear documentación básica
    Write-Host "Creando documentación básica para $ModuleName..."
    return Create-BasicDocumentation $ModuleName $ModulePath $OutputPath
}

# Función para crear documentación básica
function Create-BasicDocumentation($ModuleName, $ModulePath, $OutputPath) {
    $sourceDir = "$ModulePath\src\main\java"
    
    if (-not (Test-Path $sourceDir)) {
        Write-Host "[WARN] No se encontró directorio de fuentes en $sourceDir"
        return $false
    }
    
    # Buscar archivos Java
    $javaFiles = Get-ChildItem -Path $sourceDir -Filter "*.java" -Recurse
    
    # Analizar archivos para extraer clases
    $classes = @()
    foreach ($file in $javaFiles) {
        $content = Get-Content $file.FullName -Raw -ErrorAction SilentlyContinue
        if ($content) {
            # Extraer paquete
            $packageMatch = [regex]::Match($content, 'package\s+([^;]+);')
            $package = if ($packageMatch.Success) { $packageMatch.Groups[1].Value } else { "(default)" }
            
            # Extraer clases
            $classMatches = [regex]::Matches($content, 'public\s+(?:abstract\s+)?class\s+(\w+)')
            foreach ($match in $classMatches) {
                $classes += @{
                    Name = $match.Groups[1].Value
                    Package = $package
                    Type = 'Class'
                    File = $file.Name
                }
            }
            
            # Extraer interfaces
            $interfaceMatches = [regex]::Matches($content, 'public\s+interface\s+(\w+)')
            foreach ($match in $interfaceMatches) {
                $classes += @{
                    Name = $match.Groups[1].Value
                    Package = $package
                    Type = 'Interface'
                    File = $file.Name
                }
            }
        }
    }
    
    # Crear HTML básico
    $html = @"
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BasePlugin $ModuleName API Documentation</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; margin: 20px; background: #f5f5f5; }
        .container { max-width: 1000px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px; }
        .warning { background: #fff3cd; border: 1px solid #ffeaa7; padding: 15px; margin: 20px 0; border-radius: 4px; }
        .stats { background: #e8f5e8; border-left: 4px solid #28a745; padding: 15px; margin: 20px 0; }
        .package-section { margin: 20px 0; }
        .package-name { font-weight: bold; color: #2980b9; font-size: 1.2em; margin-bottom: 10px; }
        .class-item { background: #f8f9fa; border: 1px solid #dee2e6; padding: 10px; margin: 5px 0; border-radius: 4px; }
        .class-name { font-weight: bold; color: #2c3e50; }
        .class-type { background: #e9ecef; color: #495057; padding: 2px 6px; border-radius: 3px; font-size: 0.8em; margin-left: 8px; }
        .file-name { color: #6c757d; font-size: 0.9em; }
    </style>
</head>
<body>
    <div class="container">
        <h1>BasePlugin $ModuleName API Documentation</h1>
        
        <div class="warning">
            <strong>Nota:</strong> Esta documentación fue generada automáticamente. 
            La generación de Javadoc con Gradle falló, posiblemente debido a errores de compilación.
        </div>
        
        <div class="stats">
            <strong>Estadísticas:</strong><br>
            📁 Archivos Java: $($javaFiles.Count)<br>
            🏗️ Clases e interfaces: $($classes.Count)
        </div>
"@
    
    if ($classes.Count -gt 0) {
        # Agrupar por paquete
        $packageGroups = $classes | Group-Object Package
        
        foreach ($packageGroup in $packageGroups) {
            $html += @"
        <div class="package-section">
            <div class="package-name">📦 $($packageGroup.Name)</div>
"@
            foreach ($class in $packageGroup.Group) {
                $html += @"
            <div class="class-item">
                <span class="class-name">$($class.Name)</span>
                <span class="class-type">$($class.Type)</span>
                <div class="file-name">📄 $($class.File)</div>
            </div>
"@
            }
            $html += "        </div>"
        }
    } else {
        $html += "        <p>No se encontraron clases o interfaces públicas.</p>"
    }
    
    $html += @"
        
        <div style="margin-top: 40px; padding-top: 20px; border-top: 1px solid #dee2e6; color: #6c757d; text-align: center;">
            <p>BasePlugin $ModuleName - Documentación generada automáticamente</p>
        </div>
    </div>
</body>
</html>
"@
    
    # Guardar HTML
    $indexPath = Join-Path $OutputPath "index.html"
    Set-Content -Path $indexPath -Value $html -Encoding UTF8
    
    Write-Host "[OK] Documentación básica creada para $ModuleName"
    return $true
}

# Generar documentación para cada módulo
$results = @{}

Write-Host "\n=== Iniciando generación de Javadoc con Gradle ==="

# Módulo Common
$results['common'] = Generate-GradleJavadoc "common" "common" "$javadocDir\common"

# Módulo Spigot
$results['spigot'] = Generate-GradleJavadoc "spigot" "spigot" "$javadocDir\spigot"

# Módulo BungeeCord
$results['bungeecord'] = Generate-GradleJavadoc "bungeecord" "bungeecord" "$javadocDir\bungeecord"

# Crear página principal
$mainHtml = @"
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BasePlugin API Documentation</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; margin: 0; padding: 20px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }
        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 40px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
        h1 { color: #2c3e50; text-align: center; margin-bottom: 10px; font-size: 2.5em; font-weight: 300; }
        .subtitle { text-align: center; color: #7f8c8d; margin-bottom: 40px; font-size: 1.2em; }
        .modules { display: grid; grid-template-columns: repeat(auto-fit, minmax(350px, 1fr)); gap: 25px; margin: 40px 0; }
        .module { border-radius: 12px; padding: 25px; background: linear-gradient(145deg, #f8f9fa, #e9ecef); box-shadow: 0 5px 15px rgba(0,0,0,0.1); transition: all 0.3s ease; position: relative; overflow: hidden; }
        .module::before { content: ''; position: absolute; top: 0; left: 0; right: 0; height: 4px; background: linear-gradient(90deg, #3498db, #2980b9); }
        .module:hover { transform: translateY(-5px); box-shadow: 0 10px 25px rgba(0,0,0,0.15); }
        .module h3 { color: #2c3e50; margin-top: 0; margin-bottom: 15px; font-size: 1.4em; display: flex; align-items: center; }
        .module-icon { font-size: 1.5em; margin-right: 10px; }
        .module p { color: #5a6c7d; margin-bottom: 20px; line-height: 1.6; }
        .module a { display: inline-block; background: linear-gradient(45deg, #3498db, #2980b9); color: white; padding: 12px 24px; text-decoration: none; border-radius: 6px; transition: all 0.3s ease; font-weight: 500; }
        .module a:hover { background: linear-gradient(45deg, #2980b9, #1f5f8b); transform: translateY(-2px); }
        .module.unavailable { opacity: 0.7; }
        .module.unavailable::before { background: linear-gradient(90deg, #e74c3c, #c0392b); }
        .module.unavailable a { background: #95a5a6; cursor: not-allowed; pointer-events: none; }
        .status { margin-top: 40px; padding: 25px; border-radius: 10px; background: linear-gradient(145deg, #d5f4e6, #c8e6c9); border-left: 5px solid #27ae60; }
        .footer { text-align: center; margin-top: 40px; padding-top: 20px; border-top: 1px solid #ecf0f1; color: #7f8c8d; }
    </style>
</head>
<body>
    <div class="container">
        <h1>BasePlugin API</h1>
        <p class="subtitle">Documentación generada con Gradle Javadoc</p>
        
        <div class="modules">
"@

# Agregar módulos
if ($results['common']) {
    $mainHtml += @"
            <div class="module">
                <h3><span class="module-icon">📦</span>Common Module</h3>
                <p>Núcleo del framework con funcionalidades compartidas entre plataformas.</p>
                <a href="common/index.html">Ver Documentación</a>
            </div>
"@
} else {
    $mainHtml += @"
            <div class="module unavailable">
                <h3><span class="module-icon">📦</span>Common Module</h3>
                <p>Núcleo del framework - Documentación no disponible</p>
                <a href="#">No Disponible</a>
            </div>
"@
}

if ($results['spigot']) {
    $mainHtml += @"
            <div class="module">
                <h3><span class="module-icon">🔧</span>Spigot Module</h3>
                <p>Implementación específica para servidores Spigot/Paper.</p>
                <a href="spigot/index.html">Ver Documentación</a>
            </div>
"@
} else {
    $mainHtml += @"
            <div class="module unavailable">
                <h3><span class="module-icon">🔧</span>Spigot Module</h3>
                <p>Implementación específica para servidores Spigot/Paper - No disponible</p>
                <a href="#">No Disponible</a>
            </div>
"@
}

if ($results['bungeecord']) {
    $mainHtml += @"
            <div class="module">
                <h3><span class="module-icon">🌐</span>BungeeCord Module</h3>
                <p>Implementación específica para servidores BungeeCord/Waterfall.</p>
                <a href="bungeecord/index.html">Ver Documentación</a>
            </div>
"@
} else {
    $mainHtml += @"
            <div class="module unavailable">
                <h3><span class="module-icon">🌐</span>BungeeCord Module</h3>
                <p>Implementación específica para servidores BungeeCord/Waterfall - No disponible</p>
                <a href="#">No Disponible</a>
            </div>
"@
}

$mainHtml += @"
        </div>
        
        <div class="status">
            <h4>📊 Estado de Generación con Gradle</h4>
            <ul>
"@

foreach ($module in $results.Keys) {
    $status = if ($results[$module]) { "[OK] Documentación disponible" } else { "[ERROR] Error en generación" }
    $mainHtml += "                <li><strong>$($module.ToUpper()):</strong> $status</li>`n"
}

$mainHtml += @"
            </ul>
            <p><strong>Método:</strong> Gradle Javadoc con fallback a documentación básica</p>
        </div>
        
        <div class="footer">
            <p>🚀 BasePlugin Framework - Documentación generada con Gradle</p>
        </div>
    </div>
</body>
</html>
"@

# Guardar página principal
$mainIndexPath = Join-Path $javadocDir "index.html"
Set-Content -Path $mainIndexPath -Value $mainHtml -Encoding UTF8

# Limpiar archivos de log
Get-ChildItem -Filter "gradle-*-*.log" | Remove-Item -Force -ErrorAction SilentlyContinue

Write-Host "\n=== Resumen Final ==="
Write-Host "Documentación principal: $mainIndexPath"
foreach ($module in $results.Keys) {
    $status = if ($results[$module]) { "[OK]" } else { "[ERROR]" }
    Write-Host "$status Módulo $($module.ToUpper()): $(if ($results[$module]) { 'Disponible' } else { 'No disponible' })"
}

Write-Host "\n¡Documentación con Gradle completada!"
Write-Host "Abra 'javadoc/index.html' para ver la documentación."