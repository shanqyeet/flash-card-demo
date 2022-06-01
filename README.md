# FLASH CARD DEMO PROJECT 

## Requirements
* JDK 11
* Maven 3
## How To Run The Project
### * Option 1: Using IDE
execute the `main` method in the FlashCardDemoApplication class from the IDE.
###  * Option 1: Using Spring Boot Maven Plugin
From your terminal, go the project root and execute the following command
```java 
mvn spring-boot:run
```
### Option 3: Using JAR file
Download the share JAR file of this project and through your terminal, go to the folder where the project is downloaded
```java
java -jar JAR_FILE_NAME_PLACEHOLDER
```

## User Interface
There two ways to consume the APIs from this application
### 1. Postman
A postman collection is created and shared along with this project to be uploaded to your local postman collection

After loading the postman collection, all available APIs provided, in following folders:
* RegisterUser: it is the first api to call to create a user to play the flash card game
* UserLogin: APIs in this folder are used to generate access token (jwt) to access all other game related APIs in this project
* MathGame: With access token given, you may call any gaming api to retrieve questions, submit results and completing the game
* ScoreLeaderBoard: APIs in this folder are to retrieve the list of game scores order by scores in descending order

### 2. React Front End
A react front end was created with graphical intefaces to consume the APIs from this project.

Simply download/clone the react project shared along with this project and run the following command in the react project root
```shell
npm install
npm run start
```
## Development Notes
1. The project utilises both SQL Database and Cache. To decouple dependency on the need to running a local database instance or a caching sever (i.e. Redis), this project used the embedded H2 Database and Embedded Redis (Common used to testing) as the source
2. Aspects of the project that can be further improved
   1. Security related features were not tested
   2. Some controller tests seems to be producing false positive, cause unknown at this moment
   3. the @SpringBootApplication failed to scan the packages in the project and an explicit scanning was implemented as short term solution
   4. Exception handling, i.e. exception types, error messages can better improved to convey the issue to API consumers.