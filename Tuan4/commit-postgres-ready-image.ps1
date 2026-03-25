$ErrorActionPreference = 'Stop'

Write-Host '== Ensure pg-seed is running ==' -ForegroundColor Cyan
docker start pg-seed | Out-Null

Write-Host '== Optional check ==' -ForegroundColor Cyan
docker exec -i pg-seed psql -U postgres -d fooddb -c "SELECT COUNT(*) AS total_users FROM users;"

Write-Host '== Commit container to ready-data image ==' -ForegroundColor Cyan
docker commit pg-seed nam4hk2/postgres-fooddb:seed-v1

docker images | Select-String "nam4hk2/postgres-fooddb"

Write-Host '== Test ready-data image on port 5433 ==' -ForegroundColor Cyan
docker rm -f pg-ready 2>$null | Out-Null
docker run -d --name pg-ready -e POSTGRES_PASSWORD=123456 -p 5433:5432 nam4hk2/postgres-fooddb:seed-v1
Start-Sleep -Seconds 3
docker exec -i pg-ready psql -U postgres -d fooddb -c "SELECT * FROM users;"

Write-Host '== Done ==' -ForegroundColor Green
