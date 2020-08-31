# Vaadin Pet Store

A Vaadin proof of concept showing a pet store application.  
The application makes use of Vaadin 16+, Spring Boot 2.3+, 
JPA and Liquibase.

![Screenshot](doc/vaadin-petstore.png)

Access this application at:
[ms-vaadin-petstore @ heroku](https://ms-vaadin-petstore.herokuapp.com/)

## Running
```
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=9999"
```
Open http://localhost:9999/

## Generate liquibase schema snapshot from DB
A file from the current DB data can be generated in order to pre-populate a new DB with other than the provided default data in [changelog/](src/main/resources/db/changelog/). 
```
mvn liquibase:generateChangeLog
```

## Deployment 

### Heroku
```
heroku deploy:jar target/*.war --app ms-vaadin-petstore
heroku ps:scale web=1 -a ms-vaadin-petstore
```

## Related articles

- [Introduction to Vaadin](https://www.baeldung.com/vaadin)
- [Sample Application with Spring Boot and Vaadin](https://www.baeldung.com/spring-boot-vaadin)
