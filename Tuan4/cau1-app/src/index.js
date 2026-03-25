const express = require('express');

const app = express();
const port = process.env.PORT || 8080;

app.get('/health', (_req, res) => {
  res.json({ status: 'ok', service: 'cau1-app' });
});

app.get('/', (_req, res) => {
  res.send('Cau 1 app is running');
});

app.listen(port, () => {
  console.log(`Server listening on port ${port}`);
});
