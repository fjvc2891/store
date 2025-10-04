import { useContext, useState } from "react";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

export default function Register() {
  const { register } = useContext(AuthContext);
  const nav = useNavigate();
  const [form, setForm] = useState({ username: "", password: "", email: "", address: "" });
  const [ok, setOk] = useState<string | null>(null);
  const [err, setErr] = useState<string | null>(null);

  const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    setOk(null); setErr(null);
    try {
      await register(form.username, form.password, form.email, form.address);
      setOk("Usuario registrado. Ahora puedes iniciar sesión.");
      setTimeout(() => nav("/login"), 800);
    } catch (e: any) {
      setErr("No se pudo registrar");
    }
  };

  return (
    <form onSubmit={submit} className="space-y-3 max-w-sm mx-auto">
      <h1 className="text-2xl font-bold">Registro</h1>
      {ok && <div className="text-green-700">{ok}</div>}
      {err && <div className="text-red-600">{err}</div>}
      <input className="border p-2 w-full" placeholder="Username"
             onChange={(e) => setForm({ ...form, username: e.target.value })}/>
      <input className="border p-2 w-full" placeholder="Email"
             onChange={(e) => setForm({ ...form, email: e.target.value })}/>
      <input type="password" className="border p-2 w-full" placeholder="Password"
             onChange={(e) => setForm({ ...form, password: e.target.value })}/>
      <input className="border p-2 w-full" placeholder="Address"
             onChange={(e) => setForm({ ...form, address: e.target.value })}/>
      <button className="bg-blue-600 text-white px-4 py-2 rounded">Crear cuenta</button>
    </form>
  );
}
