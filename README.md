# Task Management REST API

A stateless REST API built to manage tasks assignments, project progress and user specific workflows.

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
 - Created GlobalExceptionHandler with custom exceptions to properly handle them and provide extensive and clear message about occuring exceptions
 - Each endpoint never returns an object entity, everything is wired using DTOs and mapped via MapStruct for fast and secure handling of data.
 - Created a custom JWT Authentication filter
 - Created custom TaskSecurity Bean that enables safer task management
 - @Transactional used for deleting project and reassigning of incompleted tasks to specified project
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
| DELETE | /project/{id} | deletes project and reassigns incompleted task to project specified in RequestParam |

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
- Improving endpoints exposing vulnerable data
- Enhancing some endpoints (AllTask endpoint for user)
