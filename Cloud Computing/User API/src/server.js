require('dotenv').config();
const app = require('./express');
const appRoutes = require('./routes/appRoutes');

// Middleware untuk menangani permintaan koneksi
app.use('/', (req, res, next) => {
  if (req.method === 'GET' && req.url === '/') {
    return res.status(200).json({ message: 'Connection to API successful' });
  }
  next();
});

// Setup routing
app.use(appRoutes);

const PORT = 8080;

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
