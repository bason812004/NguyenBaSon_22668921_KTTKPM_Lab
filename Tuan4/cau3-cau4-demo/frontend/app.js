async function api(path, method = 'GET', body) {
  const options = { method, headers: { 'Content-Type': 'application/json' } };
  if (body) options.body = JSON.stringify(body);
  const response = await fetch(path, options);
  const data = await response.json().catch(() => ({}));
  if (!response.ok) throw new Error(JSON.stringify(data));
  return data;
}

function getOrderPayload() {
  return {
    user_id: Number(document.getElementById('user_id').value),
    item_name: document.getElementById('item_name').value,
    quantity: Number(document.getElementById('quantity').value)
  };
}

async function createUser() {
  try {
    const data = await api('/api/users', 'POST', {
      full_name: document.getElementById('name').value,
      email: document.getElementById('email').value
    });
    document.getElementById('result').textContent = JSON.stringify(data, null, 2);
    await loadAll();
  } catch (e) {
    document.getElementById('result').textContent = e.message;
  }
}

async function createChoreographyOrder() {
  try {
    const data = await api('/api/choreography/orders', 'POST', getOrderPayload());
    document.getElementById('result').textContent = JSON.stringify(data, null, 2);
    setTimeout(loadAll, 1200);
  } catch (e) {
    document.getElementById('result').textContent = e.message;
  }
}

async function createOrchestrationOrder() {
  try {
    const data = await api('/api/orchestration/orders', 'POST', getOrderPayload());
    document.getElementById('result').textContent = JSON.stringify(data, null, 2);
    await loadAll();
  } catch (e) {
    document.getElementById('result').textContent = e.message;
  }
}

async function loadAll() {
  try {
    const [users, inventory, orders, events] = await Promise.all([
      api('/api/users'),
      api('/api/inventory'),
      api('/api/orders'),
      api('/api/events')
    ]);

    document.getElementById('users').textContent = JSON.stringify(users, null, 2);
    document.getElementById('inventory').textContent = JSON.stringify(inventory, null, 2);
    document.getElementById('orders').textContent = JSON.stringify(orders, null, 2);
    document.getElementById('events').textContent = JSON.stringify(events, null, 2);
  } catch (e) {
    document.getElementById('result').textContent = e.message;
  }
}

loadAll();
