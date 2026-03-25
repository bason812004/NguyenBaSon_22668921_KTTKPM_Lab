const express = require('express');
const axios = require('axios');

const app = express();
app.use(express.json());

const USER_SERVICE_URL = process.env.USER_SERVICE_URL || 'http://user-service:3001';
const INVENTORY_SERVICE_URL = process.env.INVENTORY_SERVICE_URL || 'http://inventory-service:3002';
const ORDER_SERVICE_URL = process.env.ORDER_SERVICE_URL || 'http://order-service:3003';
const ORCHESTRATOR_SERVICE_URL = process.env.ORCHESTRATOR_SERVICE_URL || 'http://orchestrator-service:3007';
const NOTIFICATION_SERVICE_URL = process.env.NOTIFICATION_SERVICE_URL || 'http://notification-service:3006';

const asyncHandler = (fn) => (req, res, next) => Promise.resolve(fn(req, res, next)).catch(next);

app.get('/health', (_req, res) => res.json({ status: 'ok', service: 'api-gateway' }));

app.get('/api/users', asyncHandler(async (_req, res) => {
  const response = await axios.get(`${USER_SERVICE_URL}/users`);
  res.json(response.data);
}));

app.post('/api/users', asyncHandler(async (req, res) => {
  const response = await axios.post(`${USER_SERVICE_URL}/users`, req.body);
  res.status(response.status).json(response.data);
}));

app.get('/api/inventory', asyncHandler(async (_req, res) => {
  const response = await axios.get(`${INVENTORY_SERVICE_URL}/inventory`);
  res.json(response.data);
}));

app.get('/api/orders', asyncHandler(async (_req, res) => {
  const response = await axios.get(`${ORDER_SERVICE_URL}/orders`);
  res.json(response.data);
}));

app.get('/api/events', asyncHandler(async (_req, res) => {
  const response = await axios.get(`${NOTIFICATION_SERVICE_URL}/events`);
  res.json(response.data);
}));

app.post('/api/choreography/orders', asyncHandler(async (req, res) => {
  const response = await axios.post(`${ORDER_SERVICE_URL}/orders/choreography`, req.body);
  res.status(response.status).json(response.data);
}));

app.post('/api/orchestration/orders', asyncHandler(async (req, res) => {
  const response = await axios.post(`${ORCHESTRATOR_SERVICE_URL}/orchestrate/order`, req.body);
  res.status(response.status).json(response.data);
}));

app.use((err, _req, res, _next) => {
  const details = err.response?.data || err.message;
  res.status(502).json({ error: 'Upstream service error', details });
});

const port = 3000;
app.listen(port, () => console.log(`gateway listening on ${port}`));
