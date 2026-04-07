$ErrorActionPreference = 'Stop'

$workspaceRoot = (Resolve-Path (Join-Path $PSScriptRoot "..\..")).Path
$mavenHome = Join-Path $workspaceRoot ".tools\apache-maven-3.9.14"
$mavenCmd = Join-Path $mavenHome "bin\mvn.cmd"
$settingsFile = Join-Path $workspaceRoot ".mvn-local-settings.xml"
$repoLocal = Join-Path $workspaceRoot ".m2\repository"

if (-not (Test-Path $mavenCmd)) {
    throw "Local Maven not found: $mavenCmd"
}

if (-not (Test-Path $settingsFile)) {
    throw "Local Maven settings file not found: $settingsFile"
}

New-Item -ItemType Directory -Force -Path $repoLocal | Out-Null

$mavenArgs = @(
    "-s", $settingsFile,
    "-Duser.home=$workspaceRoot",
    "-Dmaven.repo.local=$repoLocal"
)

if ($args.Count -gt 0) {
    $mavenArgs += $args
}

& $mavenCmd @mavenArgs
