IF DB_ID('PartitionDemo') IS NOT NULL
BEGIN
    ALTER DATABASE PartitionDemo SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE PartitionDemo;
END
GO

CREATE DATABASE PartitionDemo;
GO
USE PartitionDemo;
GO

/* 1) Horizontal partition (row) */
CREATE TABLE dbo.table_user_01 (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    full_name NVARCHAR(100) NOT NULL,
    gender CHAR(1) NOT NULL CHECK (gender = 'M')
);

CREATE TABLE dbo.table_user_02 (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    full_name NVARCHAR(100) NOT NULL,
    gender CHAR(1) NOT NULL CHECK (gender = 'F')
);

INSERT INTO dbo.table_user_01(full_name, gender) VALUES (N'Nam A', 'M'), (N'Nam B', 'M');
INSERT INTO dbo.table_user_02(full_name, gender) VALUES (N'Nu C', 'F'), (N'Nu D', 'F');
GO

CREATE OR ALTER VIEW dbo.vw_users_all AS
SELECT user_id, full_name, gender, 'table_user_01' AS source_table FROM dbo.table_user_01
UNION ALL
SELECT user_id, full_name, gender, 'table_user_02' AS source_table FROM dbo.table_user_02;
GO

/* 2) Vertical partition (column) */
CREATE TABLE dbo.user_core (
    user_id INT PRIMARY KEY,
    full_name NVARCHAR(100) NOT NULL,
    phone VARCHAR(20) NULL
);

CREATE TABLE dbo.user_profile (
    user_id INT PRIMARY KEY,
    address NVARCHAR(200) NULL,
    bio NVARCHAR(500) NULL,
    avatar_url VARCHAR(300) NULL,
    CONSTRAINT FK_user_profile_core FOREIGN KEY(user_id) REFERENCES dbo.user_core(user_id)
);

INSERT INTO dbo.user_core(user_id, full_name, phone)
VALUES (1, N'Nguyen Van A', '0901000001'),
       (2, N'Tran Thi B',   '0901000002');

INSERT INTO dbo.user_profile(user_id, address, bio, avatar_url)
VALUES (1, N'HN', N'Core user A', 'https://img/a.png'),
       (2, N'HCM', N'Core user B', 'https://img/b.png');
GO

/* 3) Functional partition (domain/schema) */
CREATE SCHEMA identity_svc;
GO
CREATE SCHEMA ordering_svc;
GO

CREATE TABLE identity_svc.users (
    user_id INT PRIMARY KEY,
    email VARCHAR(120) UNIQUE NOT NULL
);

CREATE TABLE ordering_svc.orders (
    order_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME()
);

INSERT INTO identity_svc.users(user_id, email)
VALUES (1, 'a@test.com'), (2, 'b@test.com');

INSERT INTO ordering_svc.orders(user_id, amount)
VALUES (1, 120000), (2, 85000);

/* Verify */
SELECT * FROM dbo.vw_users_all;

SELECT c.user_id, c.full_name, p.address, p.bio
FROM dbo.user_core c
LEFT JOIN dbo.user_profile p ON c.user_id = p.user_id;

SELECT o.order_id, u.email, o.amount, o.created_at
FROM ordering_svc.orders o
JOIN identity_svc.users u ON o.user_id = u.user_id;
GO
