import { createContext, useEffect, useState, type ReactNode } from "react";
import api from "../api/axios";

type AuthContextType = {
  user: string | null;
  token: string | null;
  login: (username: string, password: string) => Promise<void>;
  register: (u: string, p: string, email: string, address: string) => Promise<void>;
  logout: () => void;
};

export const AuthContext = createContext<AuthContextType>({
  user: null, token: null,
  login: async () => {}, register: async () => {}, logout: () => {}
});

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser]   = useState<string | null>(null);
  const [token, setToken] = useState<string | null>(null);

    useEffect(() => {
      const t = localStorage.getItem("token");
      const u = localStorage.getItem("username");
      if (t) setToken(t);
      if (u) setUser(u);
    }, []);

    const login = async (username: string, password: string) => {
      const res = await api.post("/users/login", { username, password });
      const { token, userId } = res.data;
      localStorage.setItem("token", token);
      localStorage.setItem("userId", userId.toString());
      localStorage.setItem("username", username);
      setToken(token);
      setUser(username);
    };

  const register = async (u: string, p: string, email: string, address: string) => {
    await api.post("/users/register", { username: u, password: p, email, address });
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    setToken(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, token, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
