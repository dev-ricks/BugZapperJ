# BugZapperJ

Demonstration of microservices using Java language and Spring Framework. The goal of the application is to record bugs
for a system and be able to assign them to developers to fix. The application is a RESTful API (level1) that uses JSON
to communicate between the client and server. The application is built using the Spring Framework and uses Spring Boot
to create a group of standalone microservices. The application uses Spring Data JPA to interact with a Postgres
database.

A client application is under construction that will use the RESTful API to interact with the server. This client will
be react.js based.

## Getting Started

### Microservice Bug

Install Git<br/>
https://git-scm.com/book/en/v2/Getting-Started-Installing-Git<br/>

Install Java - install at least version 17 for compile<br/>
https://www.java.com/en/download/help/download_options.html<br/>

Install Apache Maven<br/>
https://maven.apache.org/install.html<br/>

Run: mvn -version<br/>
You will see the version of java and maven installed. Don't forget to set your MAVEN_HOME and JAVA_HOME to make things
work.<br/>

RUN: cd \<location\> (location where you want to clone the repository)<br/>
RUN: git clone https://github.com/dev-ricks/BugZapperJ.git<br/>
RUN: cd .\BugZapperJ\microservice-bug\ <br/>
RUN: mvn clean install<br/>

Test the application:<br/>
RUN: mvn test <br/>
RUN: mvn spring-boot:run<br/>

Open browser and go to:<br/>
http://localhost:8080/swagger-ui.html<br/>

H2 mem db is used by default but if have an installed version of Postgres, you can change the application.properties file by
uncommenting the lines for the installed version of Postgres, to keep data stored between server sessions. <br/>


