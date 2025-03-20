# LABORATORY 07 AREP - MICROSERVICES

## Oscar Santiago Lesmes Parra & Jeisson Steban Casallas Rozo
 
This project manages a microservice-based API designed to allow users to create Twitter-like posts. This project is deployed through AWS using Lambda functions and API gateways, and its JWT security service is implemented with Cognito.
Below, we'll see how to run it locally and how to deploy the service in the cloud.

![Cloud](images/demo.gif)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You need to install the following tools to run the project:
1. Java
    ```
    java -version
    ```
    It should appear something like this:
    ```
    java version "17.0.10" 2024-01-16 LTS
    Java(TM) SE Runtime Environment (build 17.0.10+11-LTS-240)
    Java HotSpot(TM) 64-Bit Server VM (build 17.0.10+11-LTS-240, mixed mode, sharing)
    ```
    ```
    javac -version
    ```
    It should appear something like this:
    ```
    javac 17.0.10
    ```
2. Maven
    ```
    mvn -version
    ```
    It should appear something like this:
    ```
    Apache Maven 3.9.6 (bc0240f3c744dd6b6ec2920b3cd08dcc295161ae)
    Maven home: C:\workspace\apache-maven-3.9.6-bin\apache-maven-3.9.6
    Java version: 17.0.10, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-17
    Default locale: es_CO, platform encoding: Cp1252
    OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
    ```
3. Git
    ```
    git --version
    ```
    It should appear something like this:
    ```
    git version 2.44.0
    ```

### Installing locally

1. Clone this repository and go to project directory:
    ```
    git clone https://github.com/oscar0617/Lab07-AREP-Twitter

    cd Lab07-AREP-Twitter
    ```
2. Build the project:
    ```
    mvn package
    ```
    Should appear something like this:
    ```     
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time:  5.093 s
    [INFO] Finished at: 2025-03-18T12:17:38-05:00
    [INFO] ------------------------------------------------------------------------
    ```

3. Run the project:
    ```
    mvn spring-boot:run
    ```
    Should appear something like this:
    ```
        .   ____          _            __ _ _
    /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
    ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
    \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
      '  |____| .__|_| |_|_| |_\__, | / / / /
    =========|_|==============|___/=/_/_/_/

    :: Spring Boot ::                (v3.4.3)

    2025-03-18T12:24:22.854-05:00  INFO 4044 --- [           main] e.e.arep.SecureapplicationApplication    : Starting SecureapplicationApplication using Java 17.0.12 with PID 4044 (C:\Users\User\Desktop\Lab07-AREP-Twitter\target\classes started by User in C:\Users\User\Desktop\Lab07-AREP-Twitter)
    ```
After this, you can access your browser with https://localhost:8080 and test the login, registration, and posting system with a local h2 database. To transform it into microservices and implement a more sophisticated login system, follow the next section to deploy it on AWS. Watch this video of the project running locally:

![Demo](images\Prueba-LocalHost-video.gif)

## Installing on AWS

Keep in mind that you will need an active AWS account to run this project on cloud.

### MicroServices & Deployment on S3

1. First, we'll need to divide the project into three parts: one that will manage the login system, another that will manage the threads, and another that will manage the posts.

We will have 3 different directories with their respective files.

![](images\1.png)

Something like this:

![](images\2.png)

2. Now we will go to the AWS interface and create a bucket in S3 where we will upload the static files of the Frontend (Index.html, Styles.css, Script.js). To deploy, we need to configure the static website hosting in the properties and in the permissions section, we will disable public access to access the Internet and add the following policy:


    You must have this activated:

    ![](images\3.png)

3. we will disable public access to access the Internet and add the following policy:
    ```
    {
        "Version": "2012-10-17",
        "Statement": [
            {
                "Effect": "Allow",
                "Principal": "*",
                "Action": "s3:GetObject",
                "Resource": "arn:aws:s3:::minitwitters3/*"
            }
        ]
    }
    ```

    It should look something similar to the imagen:

    ![](images\4.png)

4. Now we can join into our Frontend and see how it works.

    Check the index file and click in the open button to test it.

    ![](images\5.png)

    It should look something similar to the imagen:

    ![](images\6.png)


### Amazon Cognito

1. Now we will enter Amazon Cognito where we will create a new user group to implement the login system, in this case we add a verification system with email or nickname and we will redirect it to the URL of the s3 bucker where we have the index file.

Now created, we can see the name of the client and the ID client, it will be used later. If we want to see how it looks we can click the "view login page" button.

![](images\7.png)

You're going to see something similar:

![](images\10.png)

2. In the session home page section we can verify that the callback to the bucket is correctly configured.

![](images\9.png)

3. In the user management section we will find all the users who have registered in the application.

![](images\8.png)

### Amazon Lambda

1. In the Amazon Lambda service we will need 3 functions, one for each microservice (auth-service, hilos-service, posts-service)

![](images\11.png)

2. For all of them we will follow the same configuration, that is, we load the .zip or .jar file and we will change the path of that for the Stream Lambda Handler  class path and the handleRequest method.

we can see the 3 configured functions:

**auth-service**

![](images\12.png)

**hilos-service**

![](images\13.png)

**posts-service**

![](images\14.png)

3. We also changed the response times of the functions to prevent them from closing the connection early. 

![](images\15.png)

### Amazon API gateway

1. We enter the Amazon API gateway service and we are going to create an API for each microservice.

![](images\16.png)

2. For each API we are going to create a resource according to those needed by each microservice, we will go to the "integration request" section and we will activate the integration with lambda and add the name of the function to use.

**AuthService**

For this one we will only create the resource without needing to define an http verb because it is the login system

![](images\17.png)
![](images\18.png)


**HilosService**

For this we will need to create a get resource to obtain the streams.

![](images\21.png)
![](images\22.png)

**PostsService**


For this we need two resources, one for creating posts and another to obtain them.

![](images\24.png)
![](images\25.png)



3. The next step is implement the API. To do this, we define a stage. If we don't have one, we must create one. In this example, we'll call it "beta." This will be the route that defines how we access the resource.

![](images\19.png)

The next routes will be:

```
https://{URL invocation authService}/beta/all
```
![](images\20.png)

```
https://{URL invocation hilosService}/beta/all
```
![](images\23.png)

```
https://{URL invocation postsService}/beta/all

https://{URL invocation postsService}/beta/create?params=...
```
![](images\26.png)


### LocalConfiguration

1. In the Script.js file we are going to put the POSTS URL and HILOS URL to connect with the frontend.

![](images\27.png)



## Architecture

This architecture represents a serverless web application hosted on AWS that interacts with a MySQL database running on an EC2 instance. Below is an explanation of the main components and their role in the system:

![](images\28.png)

### Overview

#### 1. User and Internet
    
* The user accesses the application through the Internet using a web browser.

#### 2. Amazon S3 (Frontend)

* The application frontend, developed with HTML, CSS, and JavaScript, is hosted and deployed on Amazon S3 as a static website.

* Users can access this site to interact with the application.

#### 3. Amazon Cognito (Authentication)

* Amazon Cognito is used for user authentication management, enabling secure access to protected application resources.

#### 4. Amazon API Gateway

* Amazon API Gateway acts as an intermediary between the frontend and Lambda functions.

* It handles HTTP requests and routes them to the corresponding Lambda functions.

#### 5. AWS Lambda (Serverless Backend)

* The application's business logic is managed through AWS Lambda functions, enabling a serverless backend.

* These functions handle different requests:
    * GET /beta/users: Retrieves user information.
    * GET /beta/all: Fetches all relevant records.
    * POST /beta/create: Creates new records in the database.

#### 6. Amazon EC2 with MySQL (Database)

* The MySQL database runs on an Amazon EC2 instance.
* Lambda functions connect to this database to perform CRUD operations.

#### Application Flow

1. The user accesses the frontend hosted on Amazon S3.
2. If required, they authenticate through Amazon Cognito.
3. The frontend sends requests to API Gateway.
4. API Gateway invokes Lambda functions based on the request.
5. Lambda functions process the data and connect to the MySQL database on Amazon EC2.
6. The response is sent back to the frontend for display.

This architecture is highly scalable, secure, and efficient.

## Class Diagram

This class diagram represents the architecture of a Spring Boot application that manages users, streams, and posts. The system follows a layered architecture with the Repository, Service, Controller, and Model layers, ensuring separation of concerns and maintainability.

![](images\29.png)

### Overview

#### 1. Model Layer (Data Representation)

The Model layer defines the entities used in the application:

* Hilo (Thread): Represents a discussion thread.

    * Attributes: id, nombre.
    * Relationships: A thread contains multiple posts (List<Post>).
    * Methods: Allows retrieving posts and adding a new post.

* User: Represents an application user.

    * Attributes: id, username, password.
    * Methods: Getters and setters for attributes.

* Post: Represents a message or post within a thread.

    * Attributes: id, username, content, nombreHilo (thread name).
    * Methods: Getters and setters for attributes.

#### 2. Repository Layer (Data Access)

The Repository layer provides the data access functionality for each entity:

* HiloRepository: Manages thread (Hilo) persistence.
* UserRepository: Handles user authentication and storage.
* PostRepository: Manages post storage and retrieval.

These repositories interact with a database to perform CRUD operations.

#### 3. Service Layer (Business Logic)

The Service layer contains the business logic for handling users, threads, and posts:

* HiloService:

    * Creates and retrieves threads.
    * Adds posts to a thread.

* UserService:

    * Finds users by ID or username.
    * Saves new users.
    * Authenticates users.

* PostService:

    * Creates posts under a thread.
    * Retrieves all posts.

Each service interacts with its respective repository to perform operations.

#### 4. Controller Layer (API Endpoints)

The Controller layer exposes REST endpoints for external interaction:

* HiloController:

    * Initializes data.
    * Retrieves all threads.

* UserController:

    * Handles user registration and authentication.

* PostController:

    * Allows users to create posts and retrieve all posts.

Each controller interacts with its respective Service to process requests and return responses using ResponseEntity.

#### Application Flow

1. A user registers or logs in via UserController, which interacts with UserService and UserRepository.

2. Users can retrieve existing threads or create new ones through HiloController and HiloService.

3. Users can post messages inside threads using PostController, which interacts with PostService and PostRepository.

4. All data is stored in a database via the repository layer.



## Running the tests
This project includes unit tests for the main controllers to ensure correct functionality and robustness. The tests are written using JUnit 5 and Mockito, following best practices for testing a Spring Boot application.

#### 1. HiloControllerTest (Thread Controller Tests)

This test class verifies the correct behavior of the HiloController, which manages discussion threads.

* testGetAllHilos()
    * Mocks the HiloService to return a list of threads (Hilo).
    * Calls the getAllHilos() endpoint.
    * Verifies that the HTTP response is 200 OK and contains the expected list of threads.
    * Ensures that hiloService.getAllHilos() is called exactly once.

#### 2. PostControllerTest (Post Controller Tests)

This test class ensures that PostController correctly handles creating and retrieving posts.

* testCreatePostSuccess()

    * Simulates creating a valid post.
    * Mocks PostService to return a new post.
    * Calls createPost() and checks if the response is 200 OK with the correct post object.
    * Ensures postService.createPost() is called exactly once.

* testCreatePostContentTooLong()

    * Attempts to create a post with more than 140 characters.
    * Verifies that the response is 400 Bad Request with an appropriate error message.
    * Ensures that postService.createPost() is never called due to validation.

* testGetAllPosts()

    * Mocks PostService to return a list of posts.
    * Calls getAllPosts() and verifies the response is 200 OK with the correct list of posts.
    * Ensures postService.getAllPosts() is called exactly once.

#### 3. UserControllerTest (User Authentication Tests)

This test class validates the authentication logic in UserController.

* testAuthenticateSuccess()

    * Simulates a user logging in with valid credentials.
    * Mocks UserService to return true for authentication.
    * Calls authenticate() and checks if the response is 200 OK with "Authenticated" as the response body.

* testAuthenticateFailure()

    * Simulates a user logging in with incorrect credentials.
    * Mocks UserService to return false for authentication.
    * Calls authenticate() and verifies that the response is 401 Unauthorized with a null body.

### **Test Execution**
Each of the tests was executed using **JUnit 5** and **Mockito** to mock dependencies and isolate the `PropertyController`. The expected outcomes were met in all cases, validating the correctness of the CRUD operations.

![](images\30.png)


## Conclusion


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [GIT](https://git-scm.com) - Version control
* [Spring-boot](https://spring.io/projects/spring-boot) - Backend framework

## Versioning

I use [GitHub](http://github.com) for versioning.

## Authors

* **Oscar Santiago Lesmes Parra** - [oscar0617](https://github.com/oscar0617)
* **Jeisson Steban Casallas Rozo** - [jeissoncasallas09](https://github.com/JeissonCasallas09)

Date: 19/03/2025

## License

This project is licensed under the GNU.
