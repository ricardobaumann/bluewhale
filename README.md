# Blue whale service

## Order management backend service

### Entities and atrributes

* Product
    * ID
    * Name
    * Unit price
* Order
    * ID
    * Status
    * Items
        * Product
        * Amount

Structures are also mentioned on the API docs.

### API

This service is designed as a REST service, exposing 3 endpoints:

* GET /v1/products: Returns the available products
* POST /v1/orders: Creates an order
* GET /v1/orders/{id}: Retrieves an order by ID
* POST /v1/login: Generate a jwt token for a given username

When running on local, API docs can be checked at http://localhost:8080/swagger-ui/index.html

### Architecture and design insights

The service is implemented using JDK23 + Spring boot. The main layers and packages are:

* config: All configuration classes, including
    * Embedded Jwt handling using [jjwt](https://github.com/jwtk/jjwt)
    * [Endpoint Security](https://spring.io/projects/spring-security)
    * [Spring Boot Cache](https://spring.io/guides/gs/caching)
    * [Swagger/API docs](https://swagger.io/specification/)
* controller: Controller/Http Layer, responsible for REST input and output, security and static validation. On the
  controller layer, we can see the following libraries in action:
    * [Spring Security](https://spring.io/projects/spring-security), for the endpoints security
    * [Bean validation](https://beanvalidation.org/) for static input validation
    * [Error Handling](https://github.com/wimdeblauwe/error-handling-spring-boot-starter) to render error response
      bodies on errors
* input: Classes used to parse input http request bodies
* output: Classes used to render http response bodies
* mappers: Classes to transform information back and forth between layers, using [MapStruct](https://mapstruct.org/) to
  reduce boilerplate
* entities: Database representation for persistence, using [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* repos: Persistence/CRUD components, using [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
  and [PostgreSQL](https://www.postgresql.org/)
  for database
* service: Business rules and dynamic validation layer
* exceptions: The possible exception explicitly thrown by the service
* db.changelog: The [liquibase](https://www.liquibase.com/) database migrations, allowing the database to evolve from
  scratch, and giving us a fast and simple
  local env build.

### Local usage

To build and run the service on local, be sure to have:

* JDK 23+ (I recommend using https://sdkman.io/ to manage it)
* Docker
* Docker-compose

To run the service, run

````sh
./run-local-sh
````

After checking if the service is up, feel free to check the
local [swagger docs](http://localhost:8080/swagger-ui/index.html).
All the endpoints are protected, except for the login endpoint. Use this one to get a token for a username, and then set
the token on swagger via ![authorize.png](authorize.png) button.

You can also watch my presentation about the
API [here](https://www.loom.com/share/7073a5ac225746c68abce7f22ec3f4cf?sid=f761451f-ea58-4660-9efe-feabf67442fe).

### Production setup

* Database: A proper and fine-tuned PostgreSQL instance need to be created, and linked to the service. I recommend [AWS
  RDS](https://aws.amazon.com/pt/rds/) for it.
* Cache: The default settings are including an in-memory cache mechanism. It suffices for local development, but for
  production I recommend https://redis.io/ or https://memcached.org/. Both solutions can be easily integrated with the
  Spring Boot Cache abstraction.
* Security: The default settings are including a simple and embedded JWT token generation and authorization. By adding a
  proper login with username+password, that can
  suffice for a while, but as soon as we have more services that require authorization, more sophisticated solutions
  will be required. I recommend [AWS Cognito](https://aws.amazon.com/pt/cognito/)
  and [Keycloak](https://www.keycloak.org/) on the longer run.

### Development insights

Stick to best practices, and keep every concern on the right layer. Be
a [boy scout](https://medium.com/@codechuckle/code-quality-unleashed-boy-scout-principle-6b845e831424, focusing on the
long term health of this asset.
Aim for 100% test coverage, including integration and unit tests as required.

### Further development

There are many possibilities we can exercise we go down the road here, for example:

* Different caching policies, for increased scalability and low latency
* CQRS + NoSQL solutions, if many different read data projections are required

And many others, depending on the challenges we will face

### Contact

Count with me to help developing your team.

E-mail: ricardo.baumann83@gmail.com

LinkedIn: https://www.linkedin.com/in/baumann-ricardo/