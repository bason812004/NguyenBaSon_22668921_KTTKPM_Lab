const express = require('express');
const { createClient } = require('redis');
const axios = require('axios');

const app = express();
app.use(express.json());

const ORDER_SERVICE_URL = process.env.ORDER_SERVICE_URL || 'http://order-service:3003';
const redis = createClient({ url: process.env.REDIS_URL || 'redis://redis:6379' });
const sub = redis.duplicate();
redis.on('error', (err) => console.error('delivery redis error', err));
sub.on('error', (err) => console.error('delivery redis sub error', err));

async function init() {
  await redis.connect();
  await sub.connect();

  await sub.subscribe('events:PaymentSucceeded', async (message) => {
    const order = JSON.parse(message);
    await axios.patch(`${ORDER_SERVICE_URL}/orders/${order.id}/status`, { status: 'DELIVERY_CREATED' });
    await redis.publish('events:DeliveryCreated', JSON.stringify(order));
  });

  await sub.subscribe('events:PaymentFailed', async (message) => {
    const order = JSON.parse(message);
    await axios.patch(`${ORDER_SERVICE_URL}/orders/${order.id}/status`, { status: 'CANCELLED' });
  });
}

app.get('/health', (_req, res) => res.json({ status: 'ok', service: 'delivery-service' }));

app.post('/create', async (req, res) => {
  const { order_id } = req.body;
  res.json({ success: true, delivery_id: `dly_${order_id}_${Date.now()}` });
});

const port = 3005;
init()
  .then(() => app.listen(port, () => console.log(`delivery-service listening on ${port}`)))
  .catch((err) => {
    console.error('delivery-service init error', err);
    process.exit(1);
  });
