$ErrorActionPreference = 'Stop'

Write-Host '== Step 1: Build app image and start all containers ==' -ForegroundColor Cyan
docker compose -f .\docker-compose.lab.yml up -d --build

Write-Host '== Step 2: Verify app ==' -ForegroundColor Cyan
$maxRetries = 10
$retryDelaySeconds = 3
$appReady = $false

for ($i = 1; $i -le $maxRetries; $i++) {
  try {
    $health = Invoke-RestMethod -Uri "http://localhost:8080/health" -Method GET
    $health | ConvertTo-Json
    $appReady = $true
    break
  } catch {
    Write-Host "App not ready (attempt $i/$maxRetries), waiting $retryDelaySeconds seconds..." -ForegroundColor Yellow
    Start-Sleep -Seconds $retryDelaySeconds
  }
}

if (-not $appReady) {
  Write-Host 'App is still not ready. Last logs:' -ForegroundColor Red
  docker logs --tail 80 cau1-app
}

Write-Host '== Step 3: Verify seeded Postgres data ==' -ForegroundColor Cyan
docker exec -i pg-seed psql -U postgres -d fooddb -c "SELECT * FROM users;"

Write-Host '== Step 4: Run SQL Server partition demo ==' -ForegroundColor Cyan
docker cp .\cau2-sqlserver\partition_demo.sql sql2022:/tmp/partition_demo.sql
docker exec -i sql2022 /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P "YourStrong!Passw0rd" -C -b -i /tmp/partition_demo.sql

Write-Host '== Done ==' -ForegroundColor Green
Write-Host 'Stop all: docker compose -f .\docker-compose.lab.yml down' -ForegroundColor Green
