# Реактивное CRUD приложение использующие Spring WebFlux и MongoDB 

Это простое реактивное приложение демонстирует CRUD операции с сущностями Task и User.

## Технологии

- Java 11
- Spring Boot 2.x
- Spring WebFlux
- Spring Data MongoDB
- Project Reactor (Mono, Flux)
- MapStruct for DTO mapping
- MongoDB

## Установка 

1. **Склонирейте Репозиторий**

   ```bash
   git clone https://github.com/Bunchiek/task-tracker.git
   cd docker
   docker compose up
   
2. **запустите**

Build and Run
Приложение запуститься по пути http://localhost:8080.

API Endpoints
Users
* GET api/users: Получить user 
* GET api/users/{id}: Получить user по ID
* POST api/users: создать user
* PUT api/users/{id}: Изменить user 
* DELETE /users/{id}: Удалить user 

Tasks
* GET api/tasks: Получить task (в ответе находиться вложенные сущности, которые описывают автора задачи и исполнителя, а также содержат список наблюдающих за задачей) 
* GET api/tasks/{id}: Получить task по ID(в ответе находиться вложенные сущности, которые описывают автора задачи и исполнителя, а также содержат список наблюдающих за задачей) 
* POST api/tasks: создать task
* PUT api/tasks/{id}: Изменить task 
* DELETE /tasks/{id}: Удалить task 
