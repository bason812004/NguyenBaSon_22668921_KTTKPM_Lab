import { createContext, useContext, useMemo, useState } from "react";
import api from "../services/api";

const AUTH_STORAGE_KEY = "movie.auth";
const AuthContext = createContext(null);

const defaultAuth = {
  user: null,
  token: ""
};

function readStoredAuth() {
  try {
    const raw = localStorage.getItem(AUTH_STORAGE_KEY);
    if (!raw) {
      return defaultAuth;
    }

    const parsed = JSON.parse(raw);
    if (!parsed?.user?.userId) {
      return defaultAuth;
    }

    return {
      user: parsed.user,
      token: parsed.token || ""
    };
  } catch {
    return defaultAuth;
  }
}

export function AuthProvider({ children }) {
  const [authState, setAuthState] = useState(readStoredAuth);

  const persistAuth = (nextState) => {
    setAuthState(nextState);

    if (nextState.user) {
      localStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(nextState));
      return;
    }

    localStorage.removeItem(AUTH_STORAGE_KEY);
  };

  const register = async ({ username, email, password }) => {
    const response = await api.post("/users/register", { username, email, password });
    return response.data;
  };

  const login = async ({ email, password }) => {
    const response = await api.post("/users/login", { email, password });
    const data = response.data;

    if (!data?.success) {
      throw new Error(data?.message || "Invalid credentials");
    }

    const user = {
      userId: data.userId,
      username: data.username,
      email: data.email
    };

    const token = data.token || data.accessToken || "";
    persistAuth({ user, token });
    return user;
  };

  const logout = () => {
    persistAuth(defaultAuth);
  };

  const value = useMemo(
    () => ({
      user: authState.user,
      token: authState.token,
      isAuthenticated: Boolean(authState.user),
      register,
      login,
      logout
    }),
    [authState]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used inside AuthProvider");
  }

  return context;
}
