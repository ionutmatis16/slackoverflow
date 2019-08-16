# Software Design - Assignment 2 ![Deadline 14.04.2019 23:59](https://img.shields.io/badge/deadline-14.04.2019%2023%3A59-blue.svg "Deadline: 14.04.2019 23:59")
This is the second assignment of the software design laboratory.

## Resources
The feature descriptions may be found in [this presentation](https://slides.com/spet/utcn-sd-assignment-topic), whilst the theoretical background may be found in [this presentation](https://slides.com/spet/utcn-sd-assignment-2).

## Design Constraints
 * MV* architecture (MVC or MVP recommended),
 * Using an Observer pattern,
 * Without any backend for persistance,
 * Holding the state purely in memory.

## Recommended Technologies
 * NodeJS,
 * VSCode,
 * React.

## Getting started
 * Install [Visual Studio Code](https://code.visualstudio.com/) and [NodeJS](https://nodejs.org/en/),
 * Run `npm i -g create-react-app` in a command line (installs [Create React App](https://github.com/facebook/create-react-app)),
 * Run `create-react-app assignment-2` in your working directory,
 * Move into the newly created `assignment-2` directory (`cd assignment-2`),
 * Run `npm start` to start your application,
 * A web browser should be launched to display the new app (a hello world).

Now you can directly change the code and the application will automatically reload your browser.

## Grading
Minimum requirements for a passing grade:
 * Browser-based U.I.,
 * Feature 1,
 * Clear MV* separation,
 * Observable model.

Additional requirements:

| Requirement                        | Grade |
|------------------------------------|-------|
| Primitive model-based routing      |   6   |
| Feature 2                          |   8   |
| Feature 3                          |  10   |

Bonus requirements:

| Requirement                                                | Points |
|------------------------------------------------------------|--------|
| Prepare and present mockups (picture / wireframe / paper)  | 1.0    |
| Use Redux for holding the state                            | 2.0    |
| Use React Router for handling the routing                  | 1.0    |
| Use a CSS library (e.g. Bulma, Bootstrap) for styling*     | 1.0    |
| Write [component tests](https://facebook.github.io/create-react-app/docs/running-tests#option-2-react-testing-library) for your views | 1.0    | 

*Do not use a React control library, just a (S)CSS library to style your HTML*