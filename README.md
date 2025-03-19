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

### Installing on AWS

Keep in mind that you will need an active AWS account to run this project on cloud.

#### MicroServices

1. First, we'll need to divide the project into three parts: one that will manage the login system, another that will manage the threads, and another that will manage the posts.

We will have 3 different directories with their respective files.

![](images\1.png)

Something like this:

![](images\2.png)

2. 

## Architecture



#### Overview


## Class Diagram


#### Overview





## Running the tests


### **Test Execution**
Each of the tests was executed using **JUnit 5** and **Mockito** to mock dependencies and isolate the `PropertyController`. The expected outcomes were met in all cases, validating the correctness of the CRUD operations.




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
