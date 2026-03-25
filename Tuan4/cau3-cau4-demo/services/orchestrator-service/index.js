const express = require('express');
const axios = require('axios');

const app = express();
app.use(express.json());

const ORDER_SERVICE_URL = process.env.ORDER_SERVICE_URL || 'http://order-service:3003';
const INVENTORY_SERVICE_URL = process.env.INVENTORY_SERVICE_URL || 'http://inventory-service:3002';
const PAYMENT_SERVICE_URL = process.env.PAYMENT_SERVICE_URL || 'http://payment-service:3004';
const DELIVERY_SERVICE_URL = process.env.DELIVERY_SERVICE_URL || 'http://delivery-service:3005';

app.get('/health', (_req, res) => res.json({ status: 'ok', service: 'orchestrator-service' }));

app.post('/orchestrate/order', async (req, res) => {
  const { user_id, item_name, quantity } = req.body;
  try {
    const create = await axios.post(`${ORDER_SERVICE_URL}/orders/manual`, { user_id, item_name, quantity });
    const order = create.data;

    await axios.patch(`${ORDER_SERVICE_URL}/orders/${order.id}/status`, { status: 'ORCH_INVENTORY_PROCESSING' });
    await axios.post(`${INVENTORY_SERVICE_URL}/reserve`, { item_name, quantity });

    await axios.patch(`${ORDER_SERVICE_URL}/orders/${order.id}/status`, { status: 'ORCH_PAYMENT_PROCESSING' });
    await axios.post(`${PAYMENT_SERVICE_URL}/charge`, { order_id: order.id, quantity });

    await axios.patch(`${ORDER_SERVICE_URL}/orders/${order.id}/status`, { status: 'ORCH_DELIVERY_PROCESSING' });
    const delivery = await axios.post(`${DELIVERY_SERVICE_URL}/create`, { order_id: order.id });

    await axios.patch(`${ORDER_SERVICE_URL}/orders/${order.id}/status`, { status: 'ORCH_COMPLETED' });

    return res.status(201).json({
      message: 'Orchestration completed',
      order_id: order.id,
      delivery: delivery.data
    });
  } catch (err) {
    const details = err.response?.data || err.message;

    if (item_name && quantity) {
      try {
        await axios.post(`${INVENTORY_SERVICE_URL}/release`, { item_name, quantity });
      } catch (_e) {
      }
    }

    return res.status(500).json({
      message: 'Orchestration failed and compensation attempted',
      details
    });
  }
});

const port = 3007;
app.listen(port, () => console.log(`orchestrator-service listening on ${port}`));
