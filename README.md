# Real-Time Chat Application

A full-stack real-time group chat application. Users register, log in, and exchange messages instantly over WebSockets. Messages are stored in PostgreSQL.

**Backend:** Java 21, Spring Boot, Spring Security, Spring Data JPA, WebSocket (STOMP)
**Frontend:** React, Vite, Axios, SockJS and STOMP client
**Database:** PostgreSQL

## Features

- User registration with passwords hashed using BCrypt
- JWT token generation on login (HS512, 24-hour expiry)
- Real-time messaging using WebSocket with STOMP over SockJS
- Chat messages saved to PostgreSQL through Spring Data JPA
- Protected chat route in the React app (redirects to login when there is no token)
- CORS configured for the local React dev server

## Architecture

```
React (Vite, :5173)
   |
   |-- REST (Axios) ------------> /message-service/api/auth/register
   |                              /message-service/api/auth/login
   |
   |-- WebSocket (SockJS/STOMP) -> /message-service/ws
         publish   /app/sendMessage
         subscribe /topic/messages
                          |
                  Spring Boot (:9099)
                          |
                    PostgreSQL (message)
```

When a client publishes to `/app/sendMessage`, the server saves the message and broadcasts it to every client subscribed to `/topic/messages`.

## Tech Stack

| Layer | Technologies |
| --- | --- |
| Backend | Java 21, Spring Boot 3.5, Spring Web, Spring Security, Spring Data JPA, Spring WebSocket, JJWT, Lombok |
| Database | PostgreSQL |
| Frontend | React 18, React Router 6, Vite 5, Axios, SockJS, @stomp/stompjs |
| Build tools | Maven (wrapper included), npm |

## Project Structure

```
.
├── backend/
│   ├── pom.xml
│   └── src/main/java/com/mahesh/messageservice/
│       ├── config/        # Security, CORS, WebSocket configuration
│       ├── controller/    # AuthController (REST), ChatController (STOMP)
│       ├── model/         # User, ChatMessage entities
│       ├── repository/    # Spring Data JPA repositories
│       └── service/       # UserService, JwtService
└── frontend/
    └── chat-app/
        └── src/
            ├── api/           # Axios instance
            ├── components/    # Login, Register, ChatPage
            └── utils/         # PrivateRoute
```

## Getting Started

### Prerequisites

- Java 21
- Node.js 18 or later
- PostgreSQL

### 1. Set up the database

Create a database named `message`:

```sql
CREATE DATABASE message;
```

Tables (`users`, `chat_message`) are created automatically on first run (`spring.jpa.hibernate.ddl-auto=update`).

### 2. Run the backend

Open `backend/src/main/resources/application.properties` and set your own values:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/message
spring.datasource.username=<your_db_username>
spring.datasource.password=<your_db_password>

jwt.secret=<a_long_random_secret_of_at_least_64_characters>
```

Then start the server:

```bash
cd backend
./mvnw spring-boot:run
```

The API runs at `http://localhost:9099/message-service`.

### 3. Run the frontend

```bash
cd frontend/chat-app
npm install
npm run dev
```

Open `http://localhost:5173`.

### 4. Try it out

1. Register a user on the home page.
2. Log in.
3. Open the app in a second browser window with another user and chat in real time.

## API Reference

### REST

| Method | Endpoint | Body | Description |
| --- | --- | --- | --- |
| POST | `/message-service/api/auth/register` | `{ "userName", "email", "password" }` | Register a new user |
| POST | `/message-service/api/auth/login` | `{ "userName", "password" }` | Log in and receive a JWT |

### WebSocket (STOMP)

| Item | Value |
| --- | --- |
| Endpoint (SockJS) | `/message-service/ws` |
| Send to | `/app/sendMessage` with `{ "sender", "content" }` |
| Subscribe to | `/topic/messages` |

## Database Schema

**users**

| Column | Type | Notes |
| --- | --- | --- |
| user_id | bigint | Primary key, auto-generated |
| user_name | varchar | Unique |
| email | varchar | |
| password | varchar | BCrypt hash |
| join_date | varchar | |

**chat_message**

| Column | Type | Notes |
| --- | --- | --- |
| id | bigint | Primary key, auto-generated |
| sender | varchar | |
| content | varchar | |

## Roadmap

- Load previous messages when a user opens the chat
- Validate the JWT on protected endpoints and WebSocket connections
- Private one-to-one chats and chat rooms
- Message timestamps and online status
- Improved UI styling
- Docker Compose setup for the backend, frontend, and database

## Author

**Donga Mahesh**
Java Backend Developer
[LinkedIn](https://www.linkedin.com/in/mahesh-donga-a320b6256) | [GitHub](https://github.com/MAHESHD13)
