import { Navigate, Route, Routes } from "react-router-dom";
import ProtectedRoute from "../components/ProtectedRoute";
import { useAuth } from "../context/AuthContext";
import Login from "../pages/Login";
import Register from "../pages/Register";
import Movies from "../pages/Movies";
import Booking from "../pages/Booking";

function AppRoutes() {
  const { isAuthenticated } = useAuth();

  return (
    <Routes>
      <Route
        path="/"
        element={<Navigate to={isAuthenticated ? "/movies" : "/login"} replace />}
      />

      <Route
        path="/login"
        element={isAuthenticated ? <Navigate to="/movies" replace /> : <Login />}
      />

      <Route
        path="/register"
        element={isAuthenticated ? <Navigate to="/movies" replace /> : <Register />}
      />

      <Route
        path="/movies"
        element={(
          <ProtectedRoute>
            <Movies />
          </ProtectedRoute>
        )}
      />

      <Route
        path="/booking"
        element={(
          <ProtectedRoute>
            <Booking />
          </ProtectedRoute>
        )}
      />

      <Route path="*" element={<Navigate to={isAuthenticated ? "/movies" : "/login"} replace />} />
    </Routes>
  );
}

export default AppRoutes;
