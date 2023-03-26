# RESTful API an Array Numbers Input.

## Spring Boot API service.

The Spring Boot REST service for working with numbers from an array.

## TASK:

Create a Restful API service for working with an array of numbers.
The service should upload an array of numbers from the file to the database.
Need to create the methods:

- Find the largest number.
- Find the smallest number.
- Find the average number.
- Find the longest sequence in ascending order.
- Find the longest descending sequence.
- Export result to JSON/TXT/EXCEL (Bonus).

## Requirements:

- Use any *object-oriented language*.
- Use last Spring boot version.
- Use last PostgreSQL database version.
- Create REST API service.
- Create docker compose for run service.
- provide code and clear instructions how to run it.

**Note**: please put your code in public repository.

**Note**: Please send link to this repo when you are done.

You will need the following technologies available to try it out:

* Git
* Spring Boot 3.*
* PostgreSQL 14.*
* Gradle 7+
* JDK 17
* Docker
* IDE of your choice

## TO-DO list (ASAP):

- Create functionality for upload an array numbers from file(JSON,TXT,EXCEL). :heavy_check_mark:
- Create functionality for insert data to a database. :heavy_check_mark: 
- Create functionality for find the largest number. :heavy_check_mark:
- Create functionality for find the smallest number. :heavy_check_mark:
- Create functionality for find the median. :heavy_check_mark:
- Create functionality for find the average number. :heavy_check_mark:
- Create functionality for find the longest sequence in ascending order. :heavy_check_mark:
- Create functionality for find the longest descending sequence. :heavy_check_mark:
- Create functionality for export result (array) to JSON/TXT/EXCEL (Bonus).
- Add unit tests. :heavy_check_mark:
- Add Integration tests. :heavy_check_mark:


## Instructions how to run:

First of all: You should create a database with name input_numbers.

Execute command
to run test ```gradle build```

## Swagger-ui:
http://localhost:8080/swagger-ui/index.html
![](https://i.postimg.cc/gJZxhsZv/593c34ec-dbd6-476b-b9e5-b911d672d28c.png)

### Generate Allure report

```gradle allure:report```

### Open Allure report in browser

```gradle allure:serve```

**Note**: You should have installed Chrome browser, Gradle.
