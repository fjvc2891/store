import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import "./index.css";   // ahora es CSS puro

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
