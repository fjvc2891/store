import { useEffect, useState, useContext } from "react";
import api from "../api/axios";
import { AuthContext } from "../context/AuthContext";

type Product = {
  id: number;
  name: string;
  category: string;
  price: number;
};

type Order = {
  id: number;
  products: Product[];
};

export default function Orders() {
  const { user, token } = useContext(AuthContext);
  const [orders, setOrders] = useState<Order[]>([]);
  const [error, setError] = useState<string | null>(null);

  const fetchOrders = async () => {
    try {
      setError(null);
      // Obtener el ID del usuario desde el backend si lo guardas localmente
      const userId = localStorage.getItem("userId");
      if (!userId) {
        setError("No hay usuario autenticado.");
        return;
      }

      const res = await api.get(`/orders/user/${userId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setOrders(res.data);
    } catch (err) {
      console.error(err);
      setError("No se pudieron obtener las órdenes.");
    }
  };

  useEffect(() => {
    fetchOrders();
  }, []);

  return (
    <div style={{ padding: "20px" }}>
      <h1>📦 Órdenes</h1>

      {error && <p style={{ color: "red" }}>{error}</p>}

      {orders.length === 0 ? (
        <p>No hay órdenes registradas.</p>
      ) : (
        <div>
          {orders.map((o) => (
            <div
              key={o.id}
              style={{
                border: "1px solid #ccc",
                borderRadius: "8px",
                marginBottom: "15px",
                padding: "10px",
              }}
            >
              <h3>Orden #{o.id}</h3>
              <ul>
                {o.products.map((p) => (
                  <li key={p.id}>
                    {p.name} — {p.category} — ${p.price}
                  </li>
                ))}
              </ul>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
