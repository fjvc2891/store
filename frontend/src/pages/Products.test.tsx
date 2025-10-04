import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom";
import Products from "./Products";

describe("Componente Products", () => {
  test("renderiza el título principal de productos", () => {
    render(<Products />);
    const titulo = screen.getByRole("heading", { name: /productos/i });
    expect(titulo).toBeInTheDocument();
  });

  test("muestra mensaje cuando no hay productos", () => {
    render(<Products />);
    expect(screen.getByText(/no se encontraron productos/i)).toBeInTheDocument();
  });

  test("permite escribir en el campo de búsqueda", () => {
    render(<Products />);
    const input = screen.getByPlaceholderText("Buscar nombre");
    fireEvent.change(input, { target: { value: "Laptop" } });
    expect(input).toHaveValue("Laptop");
  });
});
