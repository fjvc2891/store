// src/api/axios.ts
import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api", // 👈 asegúrate que coincide con tu backend
});

// Agregar token en headers automáticamente si existe
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
