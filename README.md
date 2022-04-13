# Development of a Functional Blockchain

## Project Screencast
Video screencast is located under the "screencast.mp4" in the repository. The screencast is also able to be viewed through this link https://www.youtube.com/watch?v=xs41Ul2SNzY .


## Table of contents
* [About The Project](#about-the-project)
* [Main Project Topics](#main-project-topics)
* [Technologies Used](#technologies-used)
* [How to use the Project](#how-to-use-the-project)

## About The Project
The repository conatins a robust functional coin-oriented blockchain application accessible through the browser. The blockchain is developed from scratch and is designed work as digital currencey. The blockchain is accessible through a front-end. The front-end visually represents the blockchain functionality and is designed to provide users with good user-experience. The blockchain and front-end are connected by Control Container that acts as a bridge, that handles requests to and from the front-end and blockchain. The front-end is accessible through https://spring-angular-blockchain.herokuapp.com/ .

## Main Project Topics

### Blockchain
The Blockchain holds immutable blocks that contain block information and hold blockchain transactions which can be sent from user to user on the blockchain network without a third party. The Blockchains digital currency works just as a currency just like the Euro or Dollar but in this blockchain the currency is a cryptocurrency. Transactions between blockchain participants are able to be created. Transaction’s
information is stored in blocks that are then mined, signed with a digital signature, and added to the blockchain. The Blockchain operates under a strict decentralized consensus mechanism called the ”Proof of work” algorithm, this algorithm validates the blockchain transactions and broadcasts new blocks to the blockchain. The SHA-256
hash algorithm is used to generate hash numbers, it moderates the creation and management of addresses, wallet key pairs and is also used for transaction verification, this algorithm is used for the encrypting of data in the blockchain into a secure format.
### Front-End
Additionally, the Blockchain is accessible through a front-end. The Angular framework is used for the front-end functionally and building the application using HTML, CSS and Typescript. The front-end simplifies the complexity of the Blockchain into an easy-to-understand process, where each page displays different functional features of the blockchain that a user can access.
### Spring-Boot Server
The data transferred between the front-end and blockchain is through Spring-Boot a service framework that will handle requests between the user and the blockchain. When a user makes a request, the front-end this will send a request to the service and notify the blockchain to process this, which then the service gives feedback to the user with a change or message notifying them. The Spring-Boot server is hosted on Heroku and accessed by the front-end.

## Technology Used

### Java
The chosen programming language for the blockchain being developed for this project, is Java. Java is object oriented, class-based and concurrent. It is designed in such a way that it has few implementation dependencies, it can also easily run on any computer that has the Java Runtime Environment (JRE) installed on it.
### Angular 
The frontend of an application should be intuitive and easy to use especially when it comes to blockchains where your dealing with a financial system with multiple functionality. Angular framework is used to develop the front-end, along with HTML, CSS and Typescript programming language. 
### Spring-Boot
The service allows the user to be a participant of the blockchain by being able to control all blockchain functionality that is available to them. Spring Boot is an open source, microservice-based Java web framework, that allows for the bridge to be connected between the back-end and front-end.
### Maven
Maven is used for java-based projects, helping to download dependencies which refers to the libraries or JAR files and to automate the build of the Spring Boot application. Maven helps to get the right JAR files for each project. While using Maven doesn’t eliminate the need to know about the underlying mechanisms. Maven is based on the Project Object Model, this allows projects to be maintainable, reusable and display a simple model for projects. 
### MySQL & PostgreSQl
My SQL and PostgreSQL are used to store block information.
### Heroku 
Heroku is a container-based cloud platform as a service supporting several programming languages. Heroku allows for the deployment and management of large-scale modern applications. The front-end and server are both deployed on heroku.
The Spring Boot Server:  https://spring-blockchain-server.herokuapp.com/
The Front-End server: https://spring-angular-blockchain.herokuapp.com/

## How to use the Project
The project can be used through the deployed front-end https://spring-angular-blockchain.herokuapp.com/ 
or you can view the files and edit the files just by following the steps below.

1. Open a directory on your machine and in the command line of this directory Git Clone this GitHub repository.

    ```bash
    $ git clone <repo>  (e.g. https://github.com/kacpergrzenda/Java-Blockchain-Applied-Project)
    ```
2. Open your local IDE (eclipse) and open the "blockchainapp" folder as a maven project. This will allow you to edit the Spring-Boot server. Run the project to run the server locally.

3. Open the "blockchainapp" folder in an IDE. In the terminal run "ng serve" this will run the front-end locally.

4. Open the "blockchain" folder to access all the blockchain functionallity.