# PowerShell script to generate Javadoc for BasePlugin
Write-Host "Generating Javadoc for BasePlugin..."

# Create javadoc directory
$javadocDir = "javadoc"
if (Test-Path $javadocDir) {
    Remove-Item $javadocDir -Recurse -Force
}
New-Item -ItemType Directory -Path $javadocDir -Force | Out-Null

# Collect Java source files from common module only
$sourceFiles = @()
$commonSrc = "common\src\main\java"
if (Test-Path $commonSrc) {
    $sourceFiles = Get-ChildItem -Path $commonSrc -Filter "*.java" -Recurse | ForEach-Object { $_.FullName }
}

Write-Host "Found $($sourceFiles.Count) Java files in common module"

if ($sourceFiles.Count -eq 0) {
    Write-Host "No Java source files found"
    exit 1
}

# Generate Javadoc with very lenient settings to ignore compilation errors
Write-Host "Executing javadoc command..."
$javadocCmd = "javadoc -d `"$javadocDir`" -encoding UTF-8 -charset UTF-8 -author -version -windowtitle `"BasePlugin Core API Documentation`" -doctitle `"BasePlugin Core API Documentation`" -Xdoclint:none -source 21 -link https://docs.oracle.com/en/java/javase/21/docs/api/ -Xmaxerrs 0 -Xmaxwarns 0"

foreach ($file in $sourceFiles) {
    $javadocCmd += " `"$file`""
}

Write-Host "Executing: javadoc with $($sourceFiles.Count) files..."
Invoke-Expression $javadocCmd

# Check if any HTML files were generated (even with errors)
$htmlFiles = Get-ChildItem -Path $javadocDir -Filter "*.html" -ErrorAction SilentlyContinue
if ($htmlFiles.Count -gt 0) {
    Write-Host "Javadoc generation completed! Generated $($htmlFiles.Count) HTML files."
    $indexPath = Join-Path $javadocDir "index.html"
    if (Test-Path $indexPath) {
        Write-Host "Documentation available at: $indexPath"
    } else {
        Write-Host "Main documentation files generated in: $javadocDir"
    }
} else {
    Write-Host "Javadoc generation failed - no HTML files generated"
}