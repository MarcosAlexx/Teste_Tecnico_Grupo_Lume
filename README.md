# Lume - Sistema de Gerenciamento de Clientes

## Pré-requisitos

### Execução com Docker (recomendado)

- Docker Desktop

### Execução manual

- Java 21+
- Node.js 18+
- npm

## Como executar

### Opção 1: Docker (recomendado)

```bash
# Na pasta raiz do projeto
docker compose up --build -d
```

Aguarde os containers iniciarem. Para verificar se estão rodando:

```bash
docker ps
```

Para parar os containers:

```bash
docker compose down
```

Para ver os logs:

```bash
docker compose logs
```

### Opção 2: Execução manual

> Abra **dois terminais** separados, um para o backend e outro para o frontend.

#### 1. Backend (Terminal 1)

```bash
# Acesse a pasta do backend
cd Backend/lume

# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

Aguarde até aparecer a mensagem `Started GroupLumeApplication`. O backend estará rodando em `http://localhost:8080`.

#### 2. Frontend (Terminal 2)

```bash
# Acesse a pasta do frontend
cd Frontend/lume-frontend

# Instale as dependências (apenas na primeira vez)
npm install

# Inicie o servidor de desenvolvimento
npm run dev
```

O frontend estará rodando em `http://localhost:5173`.

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

**Frontend:** React, Vite, React Router, HTML, CSS e JavaScript

**DevOps:** Docker, Docker Compose, Nginx
