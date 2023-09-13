# Race Microservices Application

This project is a microservices application built with Spring Boot and Docker Compose. It consists of five microservices:

1. **ms-cars:** Manages Cars data, including Create, Read, Update, and Delete operations.
2. **ms-races:** Simulates a Race by retrieving random cars from the Cars Microservice and sends the result to a RabbitMQ queue.
3. **ms-history:** Consumes a RabbitMQ Queue containing the result of a race and saves it to a database.
4. **ms-naming-server:** Eureka Naming Server for service discovery.
5. **ms-api-gateway:** API Gateway for routing requests to the appropriate microservices.

## Prerequisites

Before you begin, ensure you have the following installed:

- Java JDK (17 or higher) <br />
- Maven <br />
- Docker
- Docker Compose<br />


## Getting Started

1. Clone the repository:

   ```shell
    git clone [repository_url](https://github.com/JosePBNeto/CHALLENGE-3-WEEK-12-Microservices.git) 

2. Docker Compose:

The application uses Docker Compose to run RabbitMQ, MongoDB database and Mongo Express UI


### How to run the Application
1. Build the project using Maven
2. Run the command to build the docker images:
   ```shell
   docker-compose build
   
2. Run the command to run the docker containers:
   ```shell
   docker-compose up
   
4. Tou need to mannually star the following microservices using maven:    
   - ms-cars
   - ms-races
   - ms-history
   - ms-naming-server
   - ms-api-gateway

     Once the build is successful, you can run your Spring Boot application using the java -jar command. Replace your-application.jar with the actual name of your JAR file:   
   ```shell
   ./mvnw clean install
   
   java -jar target/your-application.jar   

#### API Endpoints:
##### You can use Open API SWAGGER to check the API end-points and payloads

### Car Microservice
http://localhost:8081/swagger-ui/index.html

### Races Microservice
http://localhost:8082/swagger-ui/index.html

### History Microservice
http://localhost:8083/swagger-ui/index.html

### Service Discovery Eureka
http://localhost:8761/

### MongoDB Express UI
http://localhost:8089/

##### You can also use the API GATEWAY endpoits to acess the microservices:
### Car Microservice
 `POST` http://localhost:8765/ms-cars/cars <br />
 `GET`  http://localhost:8765/ms-cars/cars <br />
 `GET`  http://localhost:8765/ms-cars/cars/{id} <br />
 `PUT`  http://localhost:8765/ms-cars/cars/{id} <br />
 `DELETE` http://localhost:8765/ms-cars/cars/{id} <br />

 ### Races Microservice
 `POST` http://localhost:8765/ms-races/races/start<br />
 `POST` http://localhost:8765/ms-races/races/overtake/{PositionToOvertake}<br />
 `POST` http://localhost:8765/ms-races/races/finish<br />

 ### History Microservice
 `GET` http://localhost:8765/ms-history/races/history<br />
 `GET` http://localhost:8765/ms-history/races/history/{id}<br />
 
 





