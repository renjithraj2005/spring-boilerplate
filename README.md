
# Spring Demo App



The application emulates the main features of *Twitter*:
* login, logout;
* account management (registration, editing, deleting, search by substring);


## Requirements

* [JDK 8+](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Apache Maven 3.6.0+](https://maven.apache.org/download.cgi)

## Running


1. From the command line with *Maven*:

   `mvn spring-boot:run` 

1. Access the deployed web application at:

   http://localhost:8080

1. Log in with existing accounts (`jsmith/password`) or create a new account

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
