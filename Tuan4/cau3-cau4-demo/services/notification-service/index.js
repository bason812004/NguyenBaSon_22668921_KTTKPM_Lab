const express = require('express');
const { createClient } = require('redis');

const app = express();

const redis = createClient({ url: process.env.REDIS_URL || 'redis://redis:6379' });
const sub = redis.duplicate();
const events = [];

async function init() {
  await redis.connect();
  await sub.connect();
  await sub.pSubscribe('events:*', async (message, channel) => {
    events.unshift({ channel, message, at: new Date().toISOString() });
    if (events.length > 50) events.pop();
    console.log(`[notification] ${channel}: ${message}`);
  });
}

app.get('/health', (_req, res) => res.json({ status: 'ok', service: 'notification-service' }));
app.get('/events', (_req, res) => res.json(events));

const port = 3006;
init()
  .then(() => app.listen(port, () => console.log(`notification-service listening on ${port}`)))
  .catch((err) => {
    console.error('notification-service init error', err);
    process.exit(1);
  });
