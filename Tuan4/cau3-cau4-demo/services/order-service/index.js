const express = require('express');
const { Pool } = require('pg');
const { createClient } = require('redis');

const app = express();
app.use(express.json());

const pool = new Pool({
  host: process.env.DB_HOST || 'db-order',
  port: Number(process.env.DB_PORT || 5432),
  user: process.env.DB_USER || 'postgres',
  password: process.env.DB_PASSWORD || 'postgres',
  database: process.env.DB_NAME || 'orderdb'
});

const redis = createClient({ url: process.env.REDIS_URL || 'redis://redis:6379' });
redis.on('error', (err) => console.error('order-service redis error', err));

async function init() {
  for (let i = 1; i <= 20; i++) {
    try {
      await pool.query('SELECT 1');
      break;
    } catch (err) {
      if (i === 20) throw err;
      console.log(`order-service waiting for db (attempt ${i}/20)`);
      await new Promise((resolve) => setTimeout(resolve, 2000));
    }
  }

  await pool.query(`
    CREATE TABLE IF NOT EXISTS orders (
      id SERIAL PRIMARY KEY,
      user_id INT NOT NULL,
      item_name VARCHAR(120) NOT NULL,
      quantity INT NOT NULL,
      status VARCHAR(40) NOT NULL,
      flow VARCHAR(30) NOT NULL,
      created_at TIMESTAMP DEFAULT NOW()
    )
  `);
  await redis.connect();
}

async function updateOrderStatus(id, status) {
  await pool.query('UPDATE orders SET status = $1 WHERE id = $2', [status, id]);
}

app.get('/health', (_req, res) => res.json({ status: 'ok', service: 'order-service' }));

app.get('/orders', async (_req, res) => {
  const { rows } = await pool.query('SELECT * FROM orders ORDER BY id DESC');
  res.json(rows);
});

app.post('/orders/manual', async (req, res) => {
  const { user_id, item_name, quantity } = req.body;
  const { rows } = await pool.query(
    'INSERT INTO orders(user_id, item_name, quantity, status, flow) VALUES($1,$2,$3,$4,$5) RETURNING *',
    [user_id, item_name, quantity, 'CREATED', 'orchestration']
  );
  res.status(201).json(rows[0]);
});

app.post('/orders/choreography', async (req, res) => {
  const { user_id, item_name, quantity } = req.body;
  const { rows } = await pool.query(
    'INSERT INTO orders(user_id, item_name, quantity, status, flow) VALUES($1,$2,$3,$4,$5) RETURNING *',
    [user_id, item_name, quantity, 'ORDER_CREATED', 'choreography']
  );

  const order = rows[0];
  await redis.publish('events:OrderCreated', JSON.stringify(order));
  res.status(201).json({ message: 'Order created and event published', order });
});

app.patch('/orders/:id/status', async (req, res) => {
  const id = Number(req.params.id);
  const { status } = req.body;
  await updateOrderStatus(id, status);
  res.json({ id, status });
});

const port = 3003;
init()
  .then(() => app.listen(port, () => console.log(`order-service listening on ${port}`)))
  .catch((err) => {
    console.error('order-service init error', err);
    process.exit(1);
  });
