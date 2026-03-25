# Docker Lab Guide - Cau 1 + Cau 2

## 1) Muc tieu
- Cau 1:
  - Multi-stage build image (build stage + runtime stage).
  - Toi uu image va quy trinh Dockerfile -> Image -> Container.
  - Pull Postgres, insert data, commit thanh image co san data.
- Cau 2:
  - Demo SQL Server partition: Horizontal, Vertical, Functional.

## 2) File da duoc tao
- `docker-compose.lab.yml`
- `run-cau1-cau2.ps1`
- `commit-postgres-ready-image.ps1`
- `cau1-app/Dockerfile`
- `cau1-app/package.json`
- `cau1-app/src/index.js`
- `cau1-postgres/seed/01_schema.sql`
- `cau1-postgres/seed/02_data.sql`
- `cau2-sqlserver/partition_demo.sql`

## 3) Preconditions
1. Cai Docker Desktop.
2. Docker dang chay.
3. Mo PowerShell tai thu muc goc project nay.

Kiem tra:
```powershell
docker version
docker compose version
```

## 4) Chay nhanh toan bo Cau 1 + Cau 2
```powershell
cd d:\nam4hk2\KTTKPM\TH\Tuan4
.\run-cau1-cau2.ps1
```

## 5) Chay thu cong tung buoc
### 5.1 Build app image + run app
```powershell
docker build -t nam4hk2/cau1-app:1.0 .\cau1-app
docker run -d --name cau1-app -p 8080:8080 nam4hk2/cau1-app:1.0
docker logs -f cau1-app
```

Test app:
```powershell
Invoke-RestMethod http://localhost:8080/health
```

### 5.2 Postgres seed-data image
Run Postgres with seed scripts:
```powershell
docker run -d --name pg-seed -e POSTGRES_PASSWORD=123456 -e POSTGRES_DB=fooddb -p 5432:5432 -v ${PWD}/cau1-postgres/seed:/docker-entrypoint-initdb.d postgres:16-alpine
```

Check data:
```powershell
docker exec -i pg-seed psql -U postgres -d fooddb -c "SELECT * FROM users;"
```

Commit to new image:
```powershell
docker commit pg-seed nam4hk2/postgres-fooddb:seed-v1
```

Test image ready-data:
```powershell
docker run -d --name pg-ready -e POSTGRES_PASSWORD=123456 -p 5433:5432 nam4hk2/postgres-fooddb:seed-v1
docker exec -i pg-ready psql -U postgres -d fooddb -c "SELECT * FROM users;"
```

### 5.3 SQL Server partition demo
Run SQL Server:
```powershell
docker run -d --name sql2022 -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=YourStrong!Passw0rd" -p 1433:1433 mcr.microsoft.com/mssql/server:2022-latest
```

Copy and execute SQL:
```powershell
docker cp .\cau2-sqlserver\partition_demo.sql sql2022:/tmp/partition_demo.sql
docker exec -i sql2022 /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P "YourStrong!Passw0rd" -C -i /tmp/partition_demo.sql
```

## 6) Push Docker Hub (optional)
```powershell
docker login
docker push nam4hk2/cau1-app:1.0
docker push nam4hk2/postgres-fooddb:seed-v1
```

## 7) Lenh thao tac Docker hay dung
```powershell
docker ps
docker ps -a
docker images
docker logs -f <container>
docker exec -it <container> sh
docker stop <container>
docker start <container>
docker rm <container>
docker rmi <image>
docker compose -f .\docker-compose.lab.yml down
docker system prune -f
```

## 8) Checklist nop bai
1. Screenshot `docker images` co image app va image postgres seed.
2. Screenshot app `/health` tra ve `status=ok`.
3. Screenshot query Postgres co du lieu users.
4. Screenshot 3 ket qua query trong `partition_demo.sql`.
5. Giai thich ngan 3 partition va vi sao cai thien performance.
