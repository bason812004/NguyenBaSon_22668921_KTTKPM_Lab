import Navbar from "./components/Navbar";
import AppRoutes from "./routes/AppRoutes";

function App() {
  return (
    <div className="app-layout">
      <Navbar />
      <main className="app-main">
        <AppRoutes />
      </main>
    </div>
  );
}

export default App;
