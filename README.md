# Vaadin Pet Store

A pet store management application implemented in [Vaadin](https://vaadin.com/).  
The application uses Vaadin 16+, Spring Boot 2.3+, JPA and Liquibase to show all [CRUD](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete) operations.

![Screenshot](doc/vaadin-petstore.png)

Try out this application at:
[ms-vaadin-petstore @ heroku](https://ms-vaadin-petstore.herokuapp.com/)

## Running
```
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=9999"
```
If successful, the vaadin-petstore can be accessed locally by opening http://localhost:9999/ in a web browser . 

## Generate liquibase schema snapshot from DB
A file from the current DB data can be generated in order to pre-populate a new DB with other than the provided default data in [changelog/](src/main/resources/db/changelog/). 
```
mvn liquibase:generateChangeLog
```

## Deployment 

In order to deploy the application productively, the production WAR file can be built by running:
```
mvn clean package -Pproduction
```

The WAR file can be deployed in any servlet-capable server such as [Tomcat](http://tomcat.apache.org/), [Jetty](https://www.eclipse.org/jetty/) or it can simply be run as a JAR file:
```
java -jar target/*war
```

### Heroku
A free account can be created at [Heroku](https://heroku.com/). Once registered, the WAR file above can directly be deployed, by creating a new app with a unique name.
Provided [Heroku CLI](https://devcenter.heroku.com/articles/heroku-command-line) is installed, then the following commands should be run for deployment:
```
heroku login
heroku deploy:jar target/*.war --app unique-vaadin-petstore-application
heroku ps:scale web=1 -a unique-vaadin-petstore-application
```

## Further Reading

- [Introduction to Vaadin](https://www.baeldung.com/vaadin)
- [Vaadin Components](https://vaadin.com/components)
- [Sample Application with Spring Boot and Vaadin](https://www.baeldung.com/spring-boot-vaadin)
