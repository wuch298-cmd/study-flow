# StudyFlow

StudyFlow is a small learning project for managing study tasks. It is a server-side Java web application built with Spring Boot and Thymeleaf.

The current V1 focuses on one feature: task management.

## Features

- View all tasks.
- Filter tasks by status.
- Create a task.
- Edit a task.
- Update a task status while editing it.
- Delete a task with a browser confirmation message.
- Show task title, description, status, priority, and deadline.

Each task has:

- `id`
- `title`
- `description`
- `status`: `TODO`, `IN_PROGRESS`, or `DONE`
- `priority`: `LOW`, `MEDIUM`, or `HIGH`
- `deadline`
- `createdAt`
- `updatedAt`

## Tech Stack

- Java 21
- Spring Boot 4.1.0
- Maven Wrapper
- Spring MVC
- Thymeleaf
- Spring Data JPA and Hibernate
- MySQL Connector/J
- Jakarta Validation
- JUnit 5, Mockito, and Spring test support

## Architecture

The project uses a simple layered structure:

```text
controller/  Receives HTTP requests and returns Thymeleaf views.
service/     Contains task application logic.
repository/  Reads and writes Task entities with Spring Data JPA.
entity/      Defines the Task entity and its status and priority enums.
templates/   Contains server-rendered HTML pages.
static/      Contains static resources, including CSS files.
```

For a task request, the flow is:

```text
Browser -> TaskController -> TaskService -> TaskRepository -> MySQL
Browser <- Thymeleaf template <- TaskController
```

## Main Routes

| HTTP Method | Route | Description |
| --- | --- | --- |
| GET | `/` | Show the home page. |
| GET | `/tasks` | Show all tasks. Accepts an optional `status` query parameter. |
| GET | `/tasks/new` | Show the create-task form. |
| POST | `/tasks` | Create a task. |
| GET | `/tasks/{id}/edit` | Show the edit form for one task. |
| POST | `/tasks/{id}` | Save changes to one task. |
| POST | `/tasks/{id}/delete` | Delete one task. |

Examples:

```text
/tasks
/tasks?status=TODO
/tasks?status=DONE
```

## Run Locally

### Prerequisites

- JDK 21
- MySQL
- Internet access the first time Maven downloads dependencies

### Database Configuration

The application imports an optional local configuration file named:

```text
src/main/resources/application-local.properties
```

This file is ignored by Git. Configure your local MySQL datasource there with your own URL, username, and password. Do not commit database credentials.

The shared application configuration uses Hibernate schema update mode for local development. The application creates or updates its mapped database table when it starts.

### Start the Application

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS or Linux:

```bash
./mvnw spring-boot:run
```

Then open:

```text
http://localhost:8080/
```

Task management is available at:

```text
http://localhost:8080/tasks
```

## Validation

Task validation is implemented with Jakarta Validation:

- `title` is required and cannot be blank.
- `title` has a maximum length of 100 characters.
- `description` has a maximum length of 2,000 characters.

The create and update controller methods use `@Valid` and `BindingResult`. If validation fails, the user returns to the task form and the task is not saved.

## Exception Handling

`TaskService.findById` throws `TaskNotFoundException` when a task ID does not exist. The global exception handler returns HTTP 404, adds the exception message to the model, and renders the Thymeleaf `error/404` page.

## Testing

The project currently includes tests for:

- Task defaults, validation rules, and timestamps.
- Task repository status filtering.
- Task service behavior with Mockito.
- Task controller behavior with Mockito.
- Global handling of a missing task.
- Spring application context startup.
- Home page routing with MockMvc.

Run all tests with:

```powershell
.\mvnw.cmd test
```

On macOS or Linux:

```bash
./mvnw test
```

## V1 Status

V1 task management is implemented:

- Task entity, enums, persistence, and timestamps.
- Task list and status filter.
- Create, edit, and delete actions.
- Server-side validation for create and update.
- Field-level validation feedback in the task form.
- A custom 404 page for missing tasks.
- Thymeleaf task pages and responsive task CSS.
- A home page with direct links to the task list and create-task page.

## Future Plans

These items are not implemented in V1:

- More error pages and consistent handling for other application errors.
- Project management and task-to-project relationships.
- Note management.
- Pagination, search, and task sorting.
- User accounts and authorization.
- A dedicated test profile and more isolated integration-test configuration.
