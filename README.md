# Task Management REST API

What it does, Paragraph

## Technologies
- Java 21, MapStruct, Lombok
- Spring Boot, Spring Security
- JUnit 5/Mockito, GitHub Actions
- Docker, MySQL

## Features

### Task & Project management
- Task reassignment
- CRUD opetation for task management
- Overdue tasks filtering
### Authentication & Authorization
- JWT-based authentication
- Role-based authentication with @PreAuthorize and 'hasAuthority'
- Ownership based with custom TaskSecurity bean for assignee checks
### Testing
- Full unit test suite
- GitHub Actions CI pipeline
## Architecture

## API endpoints
| Method | Endpoint | Description |
|---|---|---|
| POST | /auth/login | Authenticate and receive JWT token |
| POST | /task | Create a new task |
| GET | /task/{id} | Get task details |
| PUT | /task/{id} | Update a whole task |
| DELETE | /task/{id} | Delete a task |
| PATCH | /task/{id}/start | Starts a task |
| PATCH | /task/{id}/complete | Completes a task |
| GET | /task/overdue | Get tasks that are overdue |
| PATCH | /task/{id}/reassign | Reassigns a task to different assignee |

## How to run
### Dependencies
- Java 21, Maven, MySQL, Docker
### With Docker
\`\`\`
docker build -t task-management .
docker run -p 8080:8080 -e DB_HOST=... task-management
\`\`\`
### Without Docker
\`\`\`
./mvnw spring-boot:run
\`\`\`

## What's next
