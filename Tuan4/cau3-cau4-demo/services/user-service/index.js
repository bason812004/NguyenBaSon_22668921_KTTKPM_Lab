const express = require('express');
const { Pool } = require('pg');

const app = express();
app.use(express.json());

const pool = new Pool({
  host: process.env.DB_HOST || 'db-user',
  port: Number(process.env.DB_PORT || 5432),
  user: process.env.DB_USER || 'postgres',
  password: process.env.DB_PASSWORD || 'postgres',
  database: process.env.DB_NAME || 'userdb'
});

async function init() {
  for (let i = 1; i <= 20; i++) {
    try {
      await pool.query('SELECT 1');
      break;
    } catch (err) {
      if (i === 20) throw err;
      console.log(`user-service waiting for db (attempt ${i}/20)`);
      await new Promise((resolve) => setTimeout(resolve, 2000));
    }
  }

  await pool.query(`
    CREATE TABLE IF NOT EXISTS users (
      id SERIAL PRIMARY KEY,
      full_name VARCHAR(120) NOT NULL,
      email VARCHAR(120) UNIQUE NOT NULL
    )
  `);
}

app.get('/health', (_req, res) => res.json({ status: 'ok', service: 'user-service' }));

app.get('/users', async (_req, res) => {
  const { rows } = await pool.query('SELECT * FROM users ORDER BY id');
  res.json(rows);
});

app.post('/users', async (req, res) => {
  const { full_name, email } = req.body;
  if (!full_name || !email) return res.status(400).json({ error: 'full_name and email are required' });
  const { rows } = await pool.query(
    'INSERT INTO users(full_name, email) VALUES($1, $2) RETURNING *',
    [full_name, email]
  );
  res.status(201).json(rows[0]);
});

const port = 3001;
init()
  .then(() => app.listen(port, () => console.log(`user-service listening on ${port}`)))
  .catch((err) => {
    console.error('user-service init error', err);
    process.exit(1);
  });
