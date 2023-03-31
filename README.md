HealthAPI: Health for Sickness Clinic - Spring Boot Project


**[MindSwap Bootcamp's](https://mindswap.academy/)** project
created by **[Nuno Mesquita](https://github.com/nunogmesquita/)**,
**[Susana Gandra](https://github.com/susanagandra/)**.

This is a Java Spring Boot project that uses MySQL and Redis to build a REST API.
This API is designed to make CRUD operations within an health appointment system. 
It also allows the registration and authentication of a given user or client.
Healthcare providers (aka Users) can create TimeSlots and therefore, our clients can
schedule appointments for a particular slot.

### API Tools

> - Model Relationships
> - Spring Security
> - Docker File and Docker Compose
> - In Memory Cache
> - Swagger
> - Postman Collection
> - Tests

### Technologies Used
> - Java 19
> - Spring Boot 3.0.4
> - MySQL 8.0
> - Redis 5.0.3

## Tutorial

Before running this project, make sure you have the following installed:
> - Java 11 or higher
> - MySQL 8.0 or higher
> - Redis 6.0 or higher

1. Clone this repository to your local machine.
2. Open the project in your favorite IDE.

   `git clone https://github.com/nunogmesquita/HealthAPI.git`
3. Go to the DockerCompose.yml file.

   `docker -compose -f docker-compose.yml up`
4. Start the server

   `npm start -- --port=8080`
5. Use Postman to make API calls to manage database entries and Swagger 
further information about the endpoints and schemas.
