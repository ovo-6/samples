# Expenses Application

To run application in command line:
```
$ mvn spring-boot:run
```

It is started on `http:\\localhost:8080`.

## Credentials
Now you can login with these credentials:
- admin:admin  (administrator)
- manager:manager (user manager)
- user1:user1 (regular user)

## Configuration
There is a config file `application.properties` with DB config:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/expenses
spring.datasource.username=dbuser
spring.datasource.password=dbpass
```

## Tech Stack
Application is using these libraries or frameworks:
- Spring Boot
- Spring Data REST for creating REST API
- React for UI processing
- jQuery for REST queries
- React-Bootstrap for UI components
- react-datetime for date picker component  