# Lume - Sistema de Gerenciamento de Clientes

## Pré-requisitos

- Java 21+
- Node.js 18+
- npm

## Como executar

### Backend

```bash
cd Backend/lume
./mvnw spring-boot:run
```

No Windows:

```bash
cd Backend\lume
mvnw.cmd spring-boot:run
```

O backend será iniciado em `http://localhost:8080`.

### Frontend

```bash
cd Frontend/lume-frontend
npm install
npm run dev
```

O frontend será iniciado em `http://localhost:5173`.

## Como acessar

| Recurso | URL |
|---|---|
| Frontend (Login) | http://localhost:5173 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| H2 Console | http://localhost:8080/h2-console |

### H2 Console

- JDBC URL: `jdbc:h2:mem:testedb`
- User: `sa`
- Password: (vazio)

## Credenciais de login

| Email | Senha |
|---|---|
| admin@lume.com | admin123 |

O usuário de teste é criado automaticamente ao iniciar o backend.

## Endpoints da API

### Autenticação (pública)

| Método | Rota | Descrição |
|---|---|---|
| POST | /auth/login | Login (retorna accessToken e refreshToken) |

### Clientes (requer autenticação)

| Método | Rota | Descrição |
|---|---|---|
| POST | /clientes/criar | Criar cliente (nome, cpf, cep) |
| GET | /clientes/listar | Listar todos os clientes |
| GET | /clientes/id/{id} | Buscar cliente por ID |
| GET | /clientes/cpf/{cpf} | Buscar cliente por CPF |
| PUT | /clientes/atualizar/{id} | Atualizar cliente |
| DELETE | /clientes/deletar/{id} | Deletar cliente |

As requisições autenticadas devem incluir o header `Authorization: Bearer <token>`.

## Tecnologias

**Backend:** Java 21, Spring Boot 4.0.2, Spring Security, JWT, H2, JPA/Hibernate

**Frontend:** React, Vite, React Router, html, CSS e JavaScript
