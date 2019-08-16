# Slack Overflow
Stack Overflow like application for Software Design laboratory. I was guided by [Serban Petrescu](http://serban-petrescu.github.io/) Teaching Assistant.
The application is separated in 3 versions: 
 * v1 - back-end, using Java as the main programming language together with technologies like Spring Boot, JDBC, Hibernate and MySQL
 * v2 - front-end, using React library and Bootstrap for styling
 * v3 - unifies both back-end and fontend, exposing a REST API on server and consuming it on client
 
## Functionalities
 * The users are able to ask questions and add tags to them
 * The user is able to filter the questions by tag or via a text search, which will match the title of the question
 * Each question may be answered one or more times by any user (including the original author)
 * Answers may be edited or deleted by their author
 * Users may vote questions and answers (upvote and downvote)
 * Based on upvotes and downvotes, the system computes a user score
 
## v1 - back-end
 * layered architecture (database, persistence layer, business layer, console layer)
 * uses Abstract Factory design pattern to choose between 3 repositories (in memory, JDBC, Hibernate)
 * database initialization using Flyway
 * it has no U.I. - just a console application
 
## v2 - front-end
 * Model View Presenter architecture
 * uses Observer design pattern to notify the views if the model has changed
 * it has no back-end for persistence, it holds the state in the memory
 * uses React Router for handling the routing
 
## v3 - links back-end with front-end
 * Client - Server architecture
 * exposes a REST API from the server and consuming it on the client
 * uses the command design pattern on server
 * protects the API using HTTP Basic Authentication
