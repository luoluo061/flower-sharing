$ErrorActionPreference = 'Stop'

function Test-Command {
    param(
        [Parameter(Mandatory = $true)]
        [string]$Name
    )

    $command = Get-Command $Name -ErrorAction SilentlyContinue
    if ($null -eq $command) {
        return $null
    }

    return $command.Source
}

function Write-CheckResult {
    param(
        [Parameter(Mandatory = $true)]
        [string]$Label,
        [Parameter(Mandatory = $true)]
        [bool]$Passed,
        [string]$Detail = ""
    )

    $status = if ($Passed) { "OK" } else { "MISSING" }
    if ([string]::IsNullOrWhiteSpace($Detail)) {
        Write-Output ("[{0}] {1}" -f $status, $Label)
    } else {
        Write-Output ("[{0}] {1} - {2}" -f $status, $Label, $Detail)
    }
}

Write-Output "flower-sharing environment check"
Write-Output "Workspace: $PSScriptRoot\\..\\.."

$javaPath = Test-Command -Name "java"
Write-CheckResult -Label "Java runtime" -Passed ($null -ne $javaPath) -Detail $javaPath

$mvnPath = Test-Command -Name "mvn"
Write-CheckResult -Label "Maven CLI" -Passed ($null -ne $mvnPath) -Detail $mvnPath

$mvnwFile = Join-Path $PSScriptRoot "..\\..\\mvnw.cmd"
$mvnwExists = Test-Path $mvnwFile
Write-CheckResult -Label "Maven Wrapper" -Passed $mvnwExists -Detail $mvnwFile

$dockerPath = Test-Command -Name "docker"
Write-CheckResult -Label "Docker CLI" -Passed ($null -ne $dockerPath) -Detail $dockerPath

$mysqlPath = Test-Command -Name "mysql"
Write-CheckResult -Label "MySQL CLI" -Passed ($null -ne $mysqlPath) -Detail $mysqlPath

$redisCliPath = Test-Command -Name "redis-cli"
Write-CheckResult -Label "Redis CLI" -Passed ($null -ne $redisCliPath) -Detail $redisCliPath

Write-Output ""
Write-Output "Expected local prerequisites:"
Write-Output "- JDK 17"
Write-Output "- Maven 3.9+ or Maven Wrapper"
Write-Output "- MySQL 8"
Write-Output "- Redis 6+"
Write-Output ""
Write-Output "Recommended next checks:"
Write-Output "1. If Maven is available: mvn -q -DskipTests compile"
Write-Output "2. If target jars exist: start service by java -jar ..."
Write-Output "3. Follow docs/testing/startup-checklist.md after startup"
