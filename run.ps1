$ErrorActionPreference = 'Stop'
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$jdk = 'C:\Program Files\Java\jdk-21.0.11\bin'
$out = Join-Path $PSScriptRoot 'out'
if (Test-Path $out) { Remove-Item $out -Recurse -Force }
New-Item -ItemType Directory -Force $out | Out-Null
$files = Get-ChildItem (Join-Path $PSScriptRoot 'src') -Recurse -Filter *.java | ForEach-Object { $_.FullName }
& "$jdk\javac.exe" -encoding UTF-8 -d $out $files
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
& "$jdk\java.exe" '-Dfile.encoding=UTF-8' '-Dstdout.encoding=UTF-8' -cp $out Main
