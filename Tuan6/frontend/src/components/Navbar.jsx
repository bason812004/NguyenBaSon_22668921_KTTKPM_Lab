import { Link, NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function Navbar() {
  const { user, isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login", { replace: true });
  };

  return (
    <header className="topbar">
      <div className="topbar-left">
        <Link to={isAuthenticated ? "/movies" : "/login"} className="brand">
          Movie Ticket Booking
        </Link>
      </div>

      <div className="topbar-right">
        {!isAuthenticated ? (
          <>
            <NavLink to="/login" className={({ isActive }) => `top-link ${isActive ? "active" : ""}`}>
              Login
            </NavLink>
            <NavLink to="/register" className={({ isActive }) => `top-link ${isActive ? "active" : ""}`}>
              Register
            </NavLink>
          </>
        ) : (
          <>
            <span className="welcome">Welcome {user?.username}</span>
            <button type="button" className="danger-btn" onClick={handleLogout}>
              Logout
            </button>
          </>
        )}
      </div>
    </header>
  );
}

export default Navbar;
