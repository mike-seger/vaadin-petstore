# Vaadin Pet Store

A Vaadin proof of concept showing a pet store application.  

## Running
```
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=9999"
```
Open http://localhost:9999/

## Generate liquibase schema from running DB
```
mvn liquibase:generateChangeLog
```

## Related articles

- [Introduction to Vaadin](https://www.baeldung.com/vaadin)
- [Sample Application with Spring Boot and Vaadin](https://www.baeldung.com/spring-boot-vaadin)
