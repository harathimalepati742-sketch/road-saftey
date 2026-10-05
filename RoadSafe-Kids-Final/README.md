# RoadSafe Kids – Interactive Road Safety Education Game

A hackathon-ready road safety game using React, Spring Boot, Java 17, REST APIs, JPA/Hibernate and MySQL.

## 1. Folder structure

RoadSafe-Kids-Final/
- frontend/
  - index.html
  - package.json
  - vite.config.js
  - src/
    - App.jsx
    - App.css
    - main.jsx
- backend/
  - pom.xml
  - src/main/java/com/roadsafe/game/...
  - src/main/resources/application.properties

## 2. Start MySQL

Make sure MySQL Server is running.

The application uses:
- database: roadsafe
- username: root
- password: root by default

If your password is different, either:
- edit `backend/src/main/resources/application.properties`, OR
- set environment variable `MYSQL_PASSWORD`.

The URL contains `createDatabaseIfNotExist=true`, so the `roadsafe` database is created automatically by MySQL if the user has permission.

## 3. Run backend

Requirements:
- Java 17+
- Maven

Open terminal in `backend`:

mvn spring-boot:run

Backend:
http://localhost:8080

Test:
http://localhost:8080/api/scenarios

## 4. Run frontend

Requirements:
- Node.js 18+

Open terminal in `frontend`:

npm install
npm run dev

Frontend:
http://localhost:5173

## 5. API endpoints

GET  /api/scenarios
GET  /api/scenarios/{id}
POST /api/players
POST /api/game/answer
GET  /api/leaderboard

## 6. Game scoring

Correct action = +10 points
Wrong action = -5 points
Starting lives = 3

The UI gives immediate feedback and safety guidance.

## 7. If you see "files not found"

Do not open individual .jsx files directly in the browser.

Open the project folder in VS Code, then:

cd frontend
npm install
npm run dev

Then open the Vite URL shown in the terminal, normally:
http://localhost:5173

For the backend, use:
cd backend
mvn spring-boot:run

## 8. GitHub

Upload the whole `RoadSafe-Kids-Final` folder, including both `frontend` and `backend`.
Do not upload `node_modules` or `target`.
