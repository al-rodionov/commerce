# E-commerce platform

## About

An emulator of e-commerce platform that can process transaction requests 
with various payment methods and calculate sales.  
The runnable example consists of 4 docker containers.

### Server

The gRPC server, can receive transaction/report requests:

**Transaction workflow:**
- validate
- calculate sales and points
- store transaction and payment
- return sales and points

**Transaction report workflow:**
- return list of payments

### Transaction client

Generate transaction requests and send them to the server, 
process success/failure response

### Report client

Generate transaction report requests (by default, request payments for the last 30 seconds), 
send them to the server and process a successful response

### Database

Postgres instance for server needs


## Build and run

*Runtime example requires Docker to build a local image*

How to run:

1. In project directory run command to build the application:

>./gradlew build

2. Create base application image:

>./gradlew bootBuildImage

3. In ${project.dir}/docker run containers:

>docker compose up
