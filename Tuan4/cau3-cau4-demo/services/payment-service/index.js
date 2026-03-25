const express = require('express');
const { createClient } = require('redis');
const axios = require('axios');

const app = express();
app.use(express.json());

const ORDER_SERVICE_URL = process.env.ORDER_SERVICE_URL || 'http://order-service:3003';
const redis = createClient({ url: process.env.REDIS_URL || 'redis://redis:6379' });
const sub = redis.duplicate();
redis.on('error', (err) => console.error('payment redis error', err));
sub.on('error', (err) => console.error('payment redis sub error', err));

async function init() {
  await redis.connect();
  await sub.connect();

  await sub.subscribe('events:InventoryReserved', async (message) => {
    const order = JSON.parse(message);
    const success = order.quantity <= 5;

    if (success) {
      await axios.patch(`${ORDER_SERVICE_URL}/orders/${order.id}/status`, { status: 'PAYMENT_SUCCEEDED' });
      await redis.publish('events:PaymentSucceeded', JSON.stringify(order));
    } else {
      await axios.patch(`${ORDER_SERVICE_URL}/orders/${order.id}/status`, { status: 'PAYMENT_FAILED' });
      await redis.publish('events:PaymentFailed', JSON.stringify(order));
    }
  });
}

app.get('/health', (_req, res) => res.json({ status: 'ok', service: 'payment-service' }));

app.post('/charge', async (req, res) => {
  const { quantity } = req.body;
  const success = Number(quantity) <= 5;
  if (!success) return res.status(402).json({ success: false, message: 'Payment failed by policy(quantity > 5)' });
  res.json({ success: true, transaction_id: `txn_${Date.now()}` });
});

const port = 3004;
init()
  .then(() => app.listen(port, () => console.log(`payment-service listening on ${port}`)))
  .catch((err) => {
    console.error('payment-service init error', err);
    process.exit(1);
  });
