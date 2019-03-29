# Slack Overflow
Stack Overflow like application for Software Design laboratory.
The application is separated in 3 versions: 
 * v1 - back-end, using Java as the main programming language together with technologies like Spring Boot, JDBC, Hibernate and MySQL.
 * v2 - to be done
 * v3 - to be done
 
## Functionalities
 * The users are able to ask questions and add tags to them.
 * The user is able to filter the questions by tag or via a text search, which will match the title of the question.
 * Each question may be answered one or more times by any user (including the original author).
 * Answers may be edited or deleted by their author.
 * Users may vote questions and answers (upvote and downvote).
 * Based on upvotes and downvotes, the system computes a user score.
 
## v1 - back-end
 * layered architecture (database, persistence layer, business layer, console layer)
 * uses Abstract Factory design pattern to choose between 3 repositories (in memory, JDBC, Hibernate)
 * database initialization using Flyway
 * it has no U.I. - just a console application
 
