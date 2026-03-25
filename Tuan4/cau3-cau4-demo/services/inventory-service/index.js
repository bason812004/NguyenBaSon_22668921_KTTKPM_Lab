const express = require('express');
const { Pool } = require('pg');
const { createClient } = require('redis');
const axios = require('axios');

const app = express();
app.use(express.json());

const ORDER_SERVICE_URL = process.env.ORDER_SERVICE_URL || 'http://order-service:3003';

const pool = new Pool({
  host: process.env.DB_HOST || 'db-inventory',
  port: Number(process.env.DB_PORT || 5432),
  user: process.env.DB_USER || 'postgres',
  password: process.env.DB_PASSWORD || 'postgres',
  database: process.env.DB_NAME || 'inventorydb'
});

const pub = createClient({ url: process.env.REDIS_URL || 'redis://redis:6379' });
const sub = pub.duplicate();
pub.on('error', (err) => console.error('inventory redis pub error', err));
sub.on('error', (err) => console.error('inventory redis sub error', err));

async function init() {
  for (let i = 1; i <= 20; i++) {
    try {
      await pool.query('SELECT 1');
      break;
    } catch (err) {
      if (i === 20) throw err;
      console.log(`inventory-service waiting for db (attempt ${i}/20)`);
      await new Promise((resolve) => setTimeout(resolve, 2000));
    }
  }

  await pool.query(`
    CREATE TABLE IF NOT EXISTS inventory (
      id SERIAL PRIMARY KEY,
      item_name VARCHAR(120) UNIQUE NOT NULL,
      stock INT NOT NULL
    )
  `);

  const count = await pool.query('SELECT COUNT(*)::int AS total FROM inventory');
  if (count.rows[0].total === 0) {
    await pool.query(
      `INSERT INTO inventory(item_name, stock) VALUES
      ('Com ga', 20),
      ('Pho bo', 15),
      ('Bun cha', 10)`
    );
  }

  await pub.connect();
  await sub.connect();

  await sub.subscribe('events:OrderCreated', async (message) => {
    const order = JSON.parse(message);
    const ok = await reserveStock(order.item_name, order.quantity);

    if (ok) {
      await axios.patch(`${ORDER_SERVICE_URL}/orders/${order.id}/status`, { status: 'INVENTORY_RESERVED' });
      await pub.publish('events:InventoryReserved', JSON.stringify(order));
    } else {
      await axios.patch(`${ORDER_SERVICE_URL}/orders/${order.id}/status`, { status: 'INVENTORY_REJECTED' });
      await pub.publish('events:InventoryRejected', JSON.stringify(order));
    }
  });
}

async function reserveStock(itemName, quantity) {
  const result = await pool.query(
    `UPDATE inventory
     SET stock = stock - $2
     WHERE item_name = $1 AND stock >= $2
     RETURNING *`,
    [itemName, quantity]
  );
  return result.rowCount > 0;
}

app.get('/health', (_req, res) => res.json({ status: 'ok', service: 'inventory-service' }));

app.get('/inventory', async (_req, res) => {
  const { rows } = await pool.query('SELECT * FROM inventory ORDER BY id');
  res.json(rows);
});

app.post('/reserve', async (req, res) => {
  const { item_name, quantity } = req.body;
  const success = await reserveStock(item_name, quantity);
  if (!success) return res.status(409).json({ success: false, message: 'Not enough stock' });
  res.json({ success: true });
});

app.post('/release', async (req, res) => {
  const { item_name, quantity } = req.body;
  await pool.query('UPDATE inventory SET stock = stock + $2 WHERE item_name = $1', [item_name, quantity]);
  res.json({ success: true });
});

const port = 3002;
init()
  .then(() => app.listen(port, () => console.log(`inventory-service listening on ${port}`)))
  .catch((err) => {
    console.error('inventory-service init error', err);
    process.exit(1);
  });
