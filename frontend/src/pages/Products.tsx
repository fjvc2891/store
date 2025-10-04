import { useEffect, useState } from "react";
import api from "../api/axios";

type Product = {
  id: number;
  name: string;
  category: string;
  price: number;
};

export default function Products() {
  const [products, setProducts] = useState<Product[]>([]);
  const [selected, setSelected] = useState<number[]>([]);
  const [message, setMessage] = useState("");
  const [name, setName] = useState("");
  const [category, setCategory] = useState("");
  const [min, setMin] = useState("");
  const [max, setMax] = useState("");

  const userId = localStorage.getItem("userId");

  // 🔹 Obtener productos (con filtros opcionales)
  const fetchProducts = async () => {
    try {
      const params: any = {};
      if (name) params.name = name;
      if (category) params.category = category;
      if (min) params.min = min;
      if (max) params.max = max;

      const url = Object.keys(params).length > 0 ? "/products/search" : "/products";
      const res = await api.get(url, { params });
      setProducts(res.data);
    } catch (error) {
      console.error("Error al buscar productos:", error);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  // 🔹 Seleccionar productos
  const toggleSelect = (id: number) => {
    setSelected((prev) =>
      prev.includes(id) ? prev.filter((p) => p !== id) : [...prev, id]
    );
  };

  // 🔹 Crear orden (POST /api/orders)
  const createOrder = async () => {
    if (!userId) {
      setMessage("⚠️ Debes iniciar sesión para crear una orden.");
      return;
    }
    if (selected.length === 0) {
      setMessage("Selecciona al menos un producto.");
      return;
    }

    try {
      await api.post("/orders", {
        userId: parseInt(userId),
        productIds: selected,
      });
      setSelected([]);
      setMessage("✅ Orden creada exitosamente.");
    } catch (error) {
      console.error("Error al crear orden:", error);
      setMessage("❌ Error al crear la orden.");
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>🛍️ Productos</h1>

      {/* 🔍 Buscador */}
      <div style={{ marginBottom: "15px" }}>
        <input
          placeholder="Buscar nombre"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <input
          placeholder="Categoría"
          value={category}
          onChange={(e) => setCategory(e.target.value)}
        />
        <input
          placeholder="Min"
          type="number"
          value={min}
          onChange={(e) => setMin(e.target.value)}
        />
        <input
          placeholder="Max"
          type="number"
          value={max}
          onChange={(e) => setMax(e.target.value)}
        />
        <button onClick={fetchProducts}>Buscar</button>
      </div>

      {/* 🧾 Tabla de productos */}
      {products.length > 0 ? (
        <>
          <table border={1} cellPadding={5} style={{ borderCollapse: "collapse", width: "100%" }}>
            <thead>
              <tr>
                <th></th>
                <th>ID</th>
                <th>Nombre</th>
                <th>Categoría</th>
                <th>Precio</th>
              </tr>
            </thead>
            <tbody>
              {products.map((p) => (
                <tr key={p.id}>
                  <td>
                    <input
                      type="checkbox"
                      checked={selected.includes(p.id)}
                      onChange={() => toggleSelect(p.id)}
                    />
                  </td>
                  <td>{p.id}</td>
                  <td>{p.name}</td>
                  <td>{p.category}</td>
                  <td>${p.price}</td>
                </tr>
              ))}
            </tbody>
          </table>

          <button onClick={createOrder} style={{ marginTop: "15px" }}>
            🛒 Crear Orden
          </button>
        </>
      ) : (
        <p>No se encontraron productos.</p>
      )}

      {/* Mensaje de estado */}
      {message && <p style={{ marginTop: "10px", color: "green" }}>{message}</p>}
    </div>
  );
}
