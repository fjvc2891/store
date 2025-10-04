# Backend - Blossom E-commerce
## Requisitos
- Java 17
- PostgreSQL (DB: ecommerce_db)
- Maven

## Ejecución
1. Crear BD `ecommerce_db`
2. Configurar `application.properties`
3. Ejecutar: `mvn spring-boot:run`
4. Swagger: http://localhost:8080/swagger-ui.html

## Endpoints principales
- POST /api/users/register
- POST /api/users/login
- GET /api/products
- GET /api/products/search
- POST /api/orders
- GET /api/orders/user/{userId}
