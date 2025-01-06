# Soccer Team Management App

### Overview
The Soccer Team Management System is a RESTful application built with Java Spring Boot.
It allows users to manage soccer teams and players, including basic CRUD operations 
and a custom transfer functionality for players between teams.

### Main functionality
* CRUD Operations:
  * Manage teams (create, read, update, delete).
  * Manage players (create, read, update, delete, player transfer).

### Technical notes
* H2 Database: I decided for simple, in-memory DB in order to simplify development. It is a good
choice for a small pat-projects.
* Liquibase: good for DB schema migration, and convenient for initial population of data in the
Database.

### Implementation details
* Database population with sample data is done using Liquibase. The problem with it, Spring doesn't
update the database auto-increment value. As a result, I had to manually to do the update for the auto-increment value
* I decided against docker or docker-compose to host application as well as real database, in order not to
increase scope of the implementation.
* Transfer player method is annotated with @Transactional in order to protect integrity of the data and
to guard against the change of the balance of the team, if one of the transfer conditions wasn't satisfied
* I used combination of the `final` key word as well as `@RequiredArgsConstructor` for the dependency injection
as a more efficient way of object initialization.
* All exceptions handling is done in the `GlobalExceptionHandler.class`.

### Improvements
* I would consider using a real database (PostgreSQL).
* Hosting the application itself as well as DB in the containers and manage through docker compose.
* Cover existing implementation with Unit and Integration tests.

### Run the Application
1. Clone the repository:
<br> `git clone https://github.com/your-repo/soccer-team-management.git`
<br> `cd soccer-team-management`
2. Run the application:
<br> `./gradlew bootRun`

### Testing
Postman collection for API requests is in `postman` folder