# TEORIA.md

## 1. ¿Cómo implementarías el patrón Repository en este proyecto?

En Spring Boot, el patrón Repository se implementa usando interfaces que extienden `JpaRepository` o `CrudRepository`. Por ejemplo, para la entidad `Product`:

```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategory(String category);
    List<Product> findByPriceBetween(Double min, Double max);
}
```
Esto permite separar la lógica de acceso a datos del resto de la aplicación, facilitando el mantenimiento y las pruebas.

## 2. Diferencias entre JPA y Hibernate y cuándo usar cada uno

- **JPA** es una especificación estándar para el mapeo objeto-relacional en Java. Define las reglas y anotaciones, pero no implementa nada.
- **Hibernate** es una implementación concreta de JPA, con funcionalidades adicionales y optimizaciones.
- Usar JPA permite cambiar de proveedor (por ejemplo, EclipseLink) sin cambiar el código. Hibernate se usa cuando se necesitan características avanzadas no cubiertas por JPA.

## 3. Ventajas de React frente a otros frameworks

- Virtual DOM para alto rendimiento.
- Componentes reutilizables y composición sencilla.
- Gran ecosistema y comunidad.
- Fácil integración con otras librerías (Redux, Context API, etc).
- Enfoque declarativo para construir interfaces.
- Excelente para aplicaciones SPA y escalables.

## 4. ¿Cómo organizarías tu tiempo en 2 días para entregar la prueba?

**Día 1:**
- Modelado de entidades y relaciones en backend.
- Implementación de endpoints CRUD y seguridad JWT.
- Configuración de base de datos y pruebas básicas.
- Inicio del frontend: estructura, rutas y pantallas principales.

**Día 2:**
- Finalizar frontend: filtros, carrito, historial, validaciones y llamadas API.
- Implementar y ajustar tests (backend y frontend).
- Documentar endpoints y decisiones en README.
- Grabar video/capturas y preparar entrega.

---

# Docker

## Backend (Spring Boot)
Crea un archivo `Dockerfile` en `backend/blossom/`:

```dockerfile
FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/blossom-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

Para construir y correr:
```bash
mvn clean package
# Luego
docker build -t blossom-backend .
docker run -p 8080:8080 blossom-backend
```

## Frontend (React)
Crea un archivo `Dockerfile` en `frontend/`:

```dockerfile
FROM node:18-alpine as build
WORKDIR /app
COPY . .
RUN npm install && npm run build

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

Para construir y correr:
```bash
docker build -t blossom-frontend .
docker run -p 80:80 blossom-frontend
```

---

# Test Frontend (React)

Instala React Testing Library y Jest:
```bash
npm install --save-dev @testing-library/react @testing-library/jest-dom jest
```

Ejemplo de test en `src/pages/Products.test.tsx`:
```tsx
import { render, screen } from '@testing-library/react';
import Products from './Products';

test('renderiza el título de productos', () => {
  render(<Products />);
  expect(screen.getByText(/Productos/i)).toBeInTheDocument();
});
```

Agrega en `package.json`:
```json
"scripts": {
  "test": "jest"
}
```

Para ejecutar los tests:
```bash
npm test
```

---

**Con esto cumples todos los requisitos y extras de la prueba.**
