# Overview

Offer is a JAVA SpringBoot backend API that enable the registration and the display of a user.
This Documentation is divided into three parts:
* HOW TO: contains all what you need to run the project.
* TECHNICAL DOC: explains technical aspects of the project
* BUSINESS DOC: explains functionalities from a business point of view


#HOW TO

##REQUIREMENTS
To run the api, we need:
* Java jdk 17
* Maven

##STEPS
Navigate to the root of the project via command line and execute the command
```
mvn spring-boot:run
```

For developers, you can run the application via your favorite IDE.

#TECHNICAL DOC
In this section, we'll run through packages:

##MODEL
Contains the DB model used to store users in the h2 database.
User table is called "USERS" as keyword "USER" is reserved for H2
A user is identified by his "userName", "birthDate" and "country". so we defined a composite key (see UserId.java)


##DTO
Contains the request and response payloads. it's different from DB model as it's a plain model.

##MAPPER
We use the mapper to go from entity to dto and from dto to entity. For that, we used a library called "mapstruct" that enable us to easily convert objects.

##REPOSITORY
The repository enables us to query entities from db.
For that, we use SPRING-DATA.

##SERVICE
Service is the business Layer used by controller to perform business use-cases. Service uses Repository lays in case it needs to query the db.

##EXCEPTION
Contains all implementation of exceptions that can be thrown during the execution of our use-cases. 

##CONTROLLER
Contains endpoints implementation. This layer also contains an Exception Handler that will transform Exceptions thrown during execution to a readable Response for API User.

##Tests
Contains UNIT TESTS for all parts of the application.
the coverage that we had after running tests:

| package    | Class(%) | Method(%) | Line(%)|
|------------|----------|-----------| --- |
| controller | 100      | 100       | 100 |
| dto        | 100      | 100       | 100 |
| exception  | 100      | 100       | 100 |
| mapper     | 100      | 100       | 76 |
| model      | 100      | 85        | 85 |
| repository | 100      | 100       | 100 |
|  service   | 100      | 100       | 100 |

### ** NOTE ** : You can find in the root directory a simple postman collection to run get and post endpoints.******


#BUSINESS DOC

The API exposes two endpoints:

##Save new user
enables creating new user.
```
POST /users
paylaod:
{
  userName: String,
  birthDate: LocalDate,
  country: String,
  phoneNumber: String,
  gender: String,
}
*date format: YYYY-MM-DD
```
checks:
* Country must be France
* User must not be under 18
* PhoneNumber must be (0033/0)*********
* gender must be M or F

##get registered user
enables getting registered user.
```
GET /users?userName={userName}&birthDate={birthDate}&userName={country}
response paylaod:
{
  userName: String,
  birthDate: LocalDate,
  country: String,
  phoneNumber: String,
  gender: String,
}
```
checks:
* All params are required
