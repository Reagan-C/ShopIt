**Project Title:** Shop It.

**Description:**

This project encompasses all the basic functions of an e-commerce website and more. When the user signs up, an email with a link is sent to the user to confirm the account. The user cant log in till he/she confirms the account. The link is active only for a short while(20 minutes).
JWT is used for security configuration.
When a user places an order, the cart which holds a record of all the items in the order is cleared. The order placed is added to the pending orders list.
When the order is delivered, the admin sends an email to the user to confirm order reception.
When the user clicks on this link, a gratitude message is returned to him and this order is stored in the fulfilled orders table.
There are a host of various other functions which only a user with the role Admin is capable of performing.

An API documentation with a link to POSTMAN is embedded below;



**API Documentation**
-  [Rest APIs](https://www.postman.com/shopit121/workspace/shopit/collection/24452852-1b980d67-3e39-4d33-9c14-79a148357c79)


**Project Development Requirement**

- Programming Language: Java
- Runtime JDK 17
- Database: MySQL
- API Documentation: Postman 
- SQL Viewer or GUI: MySQL Workbench
- IDE: IntelliJ 
- Lombok


**Downloads and References**
- [Enable Annotation Processing Lombok](https://www.google.com/search?q=enable+annotation+processing+lombok&oq=enab&aqs=chrome.0.69i59j69i57j69i59l2j0i433i512j46i433i512j69i65l2.1915j0j7&sourceid=chrome&ie=UTF-8)
- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html "JDK 17")
- [IntelliJ](https://www.jetbrains.com/idea/download/ "IntelliJ")
- [Visual Studio Code](https://code.visualstudio.com/download "Visual Studio Code")
- [MySQL Workbench](https://dev.mysql.com/downloads/workbench/ "Workbench")

#### VCS Branches
- Main


#### Branch Naming Style Guide

**Feature**
- commit message :- feat:summary-of-task or feature/summary-of-task
- branch name :- feat/branch-name or feature/branch-name

**Update**
- commit message :- update:summary-of-update or update/summary-of-update
- branch name :- update/branch-name

**Bug Fix**

- commit message: fix:summary-of-fix or bug-fix:summary-of-fix
- branch name: fix/branch-name or bug-fix/branch-name

**Code Cleanup**

- commit message :- chore:summary-of-cleanup 
- branch name:- chore/branch-name

**Reference**
- [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/ "Conventional Commits")
- [Commit and Branch Practice](https://gist.github.com/digitaljhelms/4287848)

#### Pull Requests Style Guide

##### Issue


##### What has changed:



#### Code Convention and Naming Standards
- Controller e.g. UserController or AuthenticationController 
- Service e.g. UserService or ProductService
- Service Implementation e.g. UserServiceImpl or EmailServiceImpl
- Repository e.g. UserRepository
- Repository Implementation e.g. UserRepositoryImpl
- Validator e.g. PhoneNumber
- Validator Implementation .e.g PhoneNumberValidator
- Utility e.g. JwtUtil
- Configuration e.g. SecurityConfig
- Exception e.g. UserNotFoundException or EmailAddressNotFoundException or EmailAddressExistsException

**Spring Dependency Injection**

- Constructor Injection
- @Autowired Injection



**Reference**
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html "Google Java Style Guide")


**Tutorials**
- [Building a RESTful service on Spring Boot](https://codegym.cc/groups/posts/295-overview-of-rest-part-3-building-a-restful-service-on-spring-boot "Building a RESTful service on Spring Boot")

**Updates**



