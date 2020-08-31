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
If successful, you can then open http://localhost:9999/ in your web browser to access your vaadin-petstore locally. 

## Generate liquibase schema snapshot from DB
A file from the current DB data can be generated in order to pre-populate a new DB with other than the provided default data in [changelog/](src/main/resources/db/changelog/). 
```
mvn liquibase:generateChangeLog
```

## Deployment 

In order to deploy the application productively, you can build the production WAR file by running:
```
mvn clean package -Pproduction
```

You can then deploy the WAR file in any servlet-capable server such as [Tomcat](http://tomcat.apache.org/), [Jetty](https://www.eclipse.org/jetty/) or you can simply run it as a JAR file:
```
java -jar target/*war
```

### Heroku
You can directly deploy the WAR file to [Heroku](https://heroku.com/), by creating a new app with your unique name.
If you have installed [Heroku CLI](https://devcenter.heroku.com/articles/heroku-command-line), then you can run the following commands:
```
heroku login
heroku deploy:jar target/*.war --app your-vaadin-petstore-application
heroku ps:scale web=1 -a your-vaadin-petstore-application
```

## Useful links

- [Introduction to Vaadin](https://www.baeldung.com/vaadin)
- [Vaadin Components](https://vaadin.com/components)
- [Sample Application with Spring Boot and Vaadin](https://www.baeldung.com/spring-boot-vaadin)
