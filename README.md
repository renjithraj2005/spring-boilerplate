
# Spring Demo App



## Requirements

* [JDK 8+](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Apache Maven 3.6.0+](https://maven.apache.org/download.cgi)

## Running


1. From the command line with *Maven*:

   `mvn spring-boot:run` 

1. Access the deployed web application at:

   http://localhost:8080

1. Log in with existing accounts (`admin@demo.com/123@Admin`) or create a new account

### Obtain Token

```curl
curl --location --request POST 'http://localhost:8080/api/oauth/token' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic b2F1dGgyLWNsaWVudDpvYXV0aDItc2VjcmV0' \
--data-raw '{
    "username": "admin@demo.com",
    "password": "123@Admin",
    "grant_type": "password"
}'
```

Response will have the Bearer Token. Use this with every request like
```aidl
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbkBkZW1vLmNvbSIsInNjb3BlIjpbIndyaXRlIiwicmVhZCJdLCJpZCI6ImZmODA4MDgxNzk4NGQ1NDIwMTc5ODRkNTUwYWMwMDAxIiwiZXhwIjoxNjIxNDQyMTgxLCJhdXRob3JpdGllcyI6WyJST0xFX0FETUlOIl0sImp0aSI6IktzbjR3RnBBVjBRbVQtQTduSERpRHhlZlZCdyIsImNsaWVudF9pZCI6Im9hdXRoMi1jbGllbnQifQ.hOfEJSH0qbppBAld0dPcWPNCU2CNvVLaG91cvcg0RKE"
```

### Swagger API Documentation 

http://localhost:8080/api/swagger-ui.html

### Run Test

```aidl
mvn clean test
```
