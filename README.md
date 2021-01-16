
## Spring Boot Coding Dojo

Spring Boot Coding Dojo implementation

### Introduction

This is a simple application that requests its data from [OpenWeather](https://openweathermap.org/) and stores the result in a database. The implementation had quite a few problems making it a non-production ready product.

### The task

The task was to make it production-grade. Refactoring and other changes where allowed to achieve the goal.

### The solution

The main goal of the task was to make the application production-grade. It is a very simple application with only one feature, and it should be kept like this without knowing new requirements.
I could have added swagger support for user testing and documenting of the api endpoints, but this would take extra time and it is also not required for a production-grade application. The actual purpose of the application its end users are currently unknown.

**This is what I did to make the application production-grade:**

- Restructured the code (moved to packages)
- Code cleanup (refactoring and fixes)
- Logging for application
- Exception handling
- Database configuration for Dev and Prod
- Unit tests for service and Controller


### How to use the application

The application can be started with the command:

> mvn spring-boot:run
> On production:
> mvn spring-boot:run -Dspring.profiles.active=dev

Example Api Call:

> http://{host}:{port}/weather?city=Madrid

