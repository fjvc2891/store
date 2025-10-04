# 🧩 Full Stack – Java + React  
## Blossom Technical Case

---

### 🎯 Objective

Develop a full-stack e-commerce application to manage **Products**, **Users**, and **Orders**.  
The backend was developed in **Java (Spring Boot + JPA/Hibernate + PostgreSQL)** and the frontend in **React (Vite + TypeScript)**.

---

## ⚙ Technical Requirements

### Backend (Spring Boot)
- Data model with inheritance (User → Admin / Customer)  
- CRUD for Products  
- Authentication with JWT  
- Order creation (User → multiple Orders → multiple Products)  
- Unit & integration tests using JUnit and MockMvc  
- API documented with Swagger  

### Frontend (React)
- Screens: Login / Register / Product List / Product Filters / Order History  
- State managed via Context API  
- Calls with Axios  
- Form validation and error handling  
- Responsive layout  

---

## 🚀 How to Run the Project Locally

### 🖥 Backend – Spring Boot + PostgreSQL

**Requirements:**
- Java 17+
- Maven
- PostgreSQL running locally (port 5432)

**Steps:**
1. Clone the repository:
   ```bash
   git clone https://github.com/fjvc2891/store.git
   cd store/backend
