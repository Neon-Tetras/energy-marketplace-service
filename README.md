# Energy Marketplace Service

This is a rest service to match buyer interests with seller with the adequate quanities and energy sources, built with kotlin and spring boot using the TDD and BDD approach.


## Requirements

For running the application you need to have Java and Maven installed on your system:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](https://maven.apache.org)

Technologies:

- [Kotlin](https://kotlinlang.org/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [H2 Database](https://www.h2database.com/html/main.html)
- [Swagger](https://swagger.io/)

## Running the application unit tests

To run and pass the tests.

```bash
mvn clean
```
```bash
mvn install
```
```shell
mvn test
```

## Running the application locally

There are several ways to run a Spring Boot application on your local machine.

One way is to execute the `java -jar ` command on the `energy-marketplace-0.0.1-SNAPSHOT.jar` bundle in the target folder.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like on the project directory :

```shell
mvn spring-boot:run
```

## Test using the Swagger UI

The easiest way to check the service functionalities is to use the [Swagger UI](http://localhost:8080/swagger-ui/index.html#/):

```shell
http://localhost:8080/swagger-ui/index.html#/
```

## To test use the sample request and response data

## Some Assumptions

This service works with the assumption that:

* its in a microservice environment where each service is built to deliver single business capability
* there are other sevices to manage customers information, verification of certificate, payments etc.

## Service Functionalities

With this service:

* buyer can create new certificate interest"
* buyer can view all created certificate interests"
* seller can create new certificate"
* seller can view the list of all created certificates"
* seller can see all open buyer interest"
* seller can match interest with appropriate volume of certificates


### 1. Sample Request and Response to create buyer interest

```shell
http://localhost:8080/api/v1/certificates/interests
```

```json
{
  "buyerId": "2002",
  "energySource": "SOLAR",
  "quantity": 300
}
```

```json
{
  "id": 5,
  "energySource": "SOLAR",
  "quantity": 300,
  "buyerId": "2002",
  "sellerId": "",
  "status": "OPEN"
}
```

### 2. Sample Request and Response to view all buyer open interests with buyer Id

```shell
http://localhost:8080/api/v1/certificates/interests/buyer/2002
```

```json
[
  {
    "id": 5,
    "energySource": "SOLAR",
    "quantity": 300,
    "buyerId": "2002",
    "sellerId": "",
    "status": "OPEN"
  }
]
```

### 3. Sample Request and Response for seller to create certificate

```shell
http://localhost:8080/api/v1/certificates/
```

```json
{
  "energySource": "WATER",
  "issueDate": "2021-10-17",
  "issuer": "First Dutch Energy",
  "ownerId": "1001",
  "quantity": 300
}
```

```json
{
  "id": 4,
  "ownerId": "1001",
  "energySource": "WATER",
  "quantity": 300,
  "issuer": "First Dutch Energy",
  "issueDate": "2021-10-17"
}
```

### 4. Sample Request and Response for seller to view list of certificates with ownerId

```shell
http://localhost:8080/api/v1/certificates/owner/1001
```

```json
[
  {
    "id": 3,
    "ownerId": "1001",
    "energySource": "WIND",
    "quantity": 200,
    "issuer": "First Dutch Energy",
    "issueDate": "2021-10-17"
  },
  {
    "id": 4,
    "ownerId": "1001",
    "energySource": "WATER",
    "quantity": 300,
    "issuer": "First Dutch Energy",
    "issueDate": "2021-10-17"
  }
]
```

### 5. Sample Request and Response for seller to see list of all open interests

```shell
http://localhost:8080/api/v1/certificates/interests
```

```json
[
  {
    "id": 5,
    "energySource": "SOLAR",
    "quantity": 300,
    "buyerId": "2002",
    "sellerId": "",
    "status": "OPEN"
  }
]
```


### 6. Sample Request and Response for seller to match certifcates(IDs of certificate(s)) with open interest 

```shell
http://localhost:8080/api/v1/certificates/
```

```json
{
  "certificateIds": [
    1,2
  ],
  "interestId": 5,
  "sellerId": "1001"
}
```

```json
{
  "id": 5,
  "energySource": "SOLAR",
  "quantity": 300,
  "buyerId": "2002",
  "sellerId": "1001",
  "status": "CLOSED"
}
```


## Troubleshoot

* The service runs on port 8080"
* To change the port go to the application folder in the src/main/resource/ and change the port as desired
```shell
server.port = 8080
```
