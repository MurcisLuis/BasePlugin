# Simple Javadoc generator - creates basic documentation
Write-Host "Creating simple Javadoc documentation..."

# Create javadoc directory
$javadocDir = "javadoc"
if (Test-Path $javadocDir) {
    Remove-Item $javadocDir -Recurse -Force
}
New-Item -ItemType Directory -Path $javadocDir -Force | Out-Null

# Find Java files and filter out problematic ones
$commonSrc = "common\src\main\java"
$allFiles = @()
if (Test-Path $commonSrc) {
    $allFiles = Get-ChildItem -Path $commonSrc -Filter "*.java" -Recurse
}

Write-Host "Found $($allFiles.Count) total Java files"

# Filter out files with known annotation issues
$goodFiles = @()
foreach ($file in $allFiles) {
    $content = Get-Content $file.FullName -Raw
    # Skip files with @NonNull, @Nullable, or other problematic annotations
    if ($content -notmatch '@NonNull|@Nullable|@NotNull') {
        $goodFiles += $file.FullName
    } else {
        Write-Host "Skipping $($file.Name) (contains problematic annotations)"
    }
}

Write-Host "Using $($goodFiles.Count) files without annotation issues"

if ($goodFiles.Count -eq 0) {
    Write-Host "No suitable files found. Creating basic documentation structure..."
    
    # Create a basic index.html
    $indexContent = @"
<!DOCTYPE html>
<html>
<head>
    <title>BasePlugin Core API Documentation</title>
    <meta charset="UTF-8">
</head>
<body>
    <h1>BasePlugin Core API Documentation</h1>
    <p>Documentation for BasePlugin Core API</p>
    <h2>Available Modules:</h2>
    <ul>
        <li>Common Module - Core functionality</li>
        <li>Spigot Module - Spigot/Paper implementation</li>
        <li>BungeeCord Module - BungeeCord implementation</li>
    </ul>
    <p><em>Note: Full Javadoc generation encountered compilation issues. This is a basic documentation structure.</em></p>
</body>
</html>
"@
    
    $indexPath = Join-Path $javadocDir "index.html"
    Set-Content -Path $indexPath -Value $indexContent -Encoding UTF8
    Write-Host "Basic documentation created at: $indexPath"
} else {
    # Try to generate Javadoc with filtered files
    $javadocCmd = "javadoc -d `"$javadocDir`" -encoding UTF-8 -charset UTF-8 -author -version -windowtitle `"BasePlugin Core API Documentation`" -doctitle `"BasePlugin Core API Documentation`" -Xdoclint:none -source 21"
    
    foreach ($file in $goodFiles) {
        $javadocCmd += " `"$file`""
    }
    
    Write-Host "Executing javadoc with $($goodFiles.Count) filtered files..."
    Invoke-Expression $javadocCmd
    
    $htmlFiles = Get-ChildItem -Path $javadocDir -Filter "*.html" -ErrorAction SilentlyContinue
    if ($htmlFiles.Count -gt 0) {
        Write-Host "Javadoc generation completed! Generated $($htmlFiles.Count) HTML files."
        $indexPath = Join-Path $javadocDir "index.html"
        if (Test-Path $indexPath) {
            Write-Host "Documentation available at: $indexPath"
        }
    } else {
        Write-Host "Javadoc failed, creating basic documentation..."
        # Fallback to basic documentation
        $indexContent = @"
<!DOCTYPE html>
<html>
<head>
    <title>BasePlugin Core API Documentation</title>
    <meta charset="UTF-8">
</head>
<body>
    <h1>BasePlugin Core API Documentation</h1>
    <p>Documentation for BasePlugin Core API</p>
    <p><em>Javadoc generation encountered issues. Please check source files for compilation errors.</em></p>
</body>
</html>
"@
        $indexPath = Join-Path $javadocDir "index.html"
        Set-Content -Path $indexPath -Value $indexContent -Encoding UTF8
        Write-Host "Basic documentation created at: $indexPath"
    }
}