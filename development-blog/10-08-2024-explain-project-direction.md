[Back](../README.md)

# 10-08-2024 Explain Project Direction

Today, I wanted to take some time to explain the direction of this Bug Tracking project. I started the project with the
intention of creating a quick demonstration of microservices using the Java language and Spring Framework. The goal of
the application is to record bugs for a system and assign them to developers for fixing. The application is a RESTful
API (level 1) that uses JSON to communicate between the client and server. It is built using the Spring Framework and
utilizes Spring Boot to create a collection of standalone microservices. Additionally, it uses Spring Data JPA to
interact with a Postgres database.

However, as I delved into the React front-end, I realized that I would prefer to move away from creating a quick,
out-of-the-box solution that has been seen countless times. Instead, I want to focus on a design and development
README-style blog that will explain my thoughts and the decisions I have made during the development of the project.

One of the issues that bothered me about the quick solution was that I had integrated Spring so tightly into the
microservices that it would be difficult to remove for future changes. Therefore, I decided to take the design of this
project very seriously and started from the ground up. As you can see from the latest commits, I have added an initial
set of use cases that I will develop into a core set of code, which will be the foundation of the project. You can see
the first implementation of the "create Bug" use case with unit tests to verify functionality. I believe I will end up
with a core set of functionality that will be used by the Spring Framework as a plugin. The next step will be a React
front-end that will use the core functionality to interact with the server.

Some of the disciplines that I want the project to follow are:  
Test Driven Development  
Domain Driven Design  
Clean Code  
SOLID Principles  
Design Patterns  
Microservices  
RESTful API  
Performance Evaluation  

With the latest changes, the bug-service is the first core module, microservice-bug
is still functional as is but I will revisit this module using spring framework 
to use the core module as a replacement for the service I created previously.
After that I have a bunch of use cases to implement and complete the bug-service. Which 
after I will rework the bugzapperj-client-react module to use the newly created
microservice. The architecture-documents folder will contain design documents.



