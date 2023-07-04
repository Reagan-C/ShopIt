**Project Title:** Shop It.

**Description:**

You shop in malls and you get tired of constantly going to the mall physically to buy items. You wish you can order for items. Here ShopIt steps in. With your internet enabled device, you just log and and select all the items you want and pay for them and your order will be delivered to you 



**Project Development Requirement**

- Programming Language: Java
- Runtime JDK 17
- Database: MySQL
- API Documentation: Postman 
- SQL Viewer or GUI: MySQL Workbench
- IDE: IntelliJ or VS Code
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
- Controller e.g. UserController or AuthenticationController or AuthController
- Service e.g. UserService or ProductService
- Service Implementation e.g. UserServiceImpl or EmailServiceImpl
- Repository e.g. UserRepository
- Repository Implementation e.g. UserRepositoryImpl
- Validator e.g. PhoneNumberValidator
- Validator Implementation .e.g PhoneNumberAddressValidatorImpl
- Utility e.g. JwtUtil
- Configuration e.g. SecurityConfig
- Exception e.g. UserNotFoundException or EmailAddressNotFoundException or EmailAddressExistsException

**Description**



- Controller class should be annotated with @RestController and @RequestMapping. The base mapping url of any controller should be the name of the entity they are creating a controller for. For instance, if you are creating an AuthenticationController, the base url should be @RequestMapping("/authentication") or @RequestMapping("/auth"). This class will contain HTTP Request handling logic.

- Service class should be annotated with @Service. This class will contain business related logic for Apply For me. Exceptions should be throwned in this classes.

- Repository class should be annotated with @Repository. This class will contain logic that relates to data persistence and management.


**Spring Dependency Injection**

- Constructor Injection
- @Autowired Injection



**Reference**
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html "Google Java Style Guide")


**Tutorials**
- [Building a RESTful service on Spring Boot](https://codegym.cc/groups/posts/295-overview-of-rest-part-3-building-a-restful-service-on-spring-boot "Building a RESTful service on Spring Boot")

**Updates**



