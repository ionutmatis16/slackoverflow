package ro.utcn.sd.mid.assign1.slackoverflow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.*;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.*;
import ro.utcn.sd.mid.assign1.slackoverflow.service.*;

import java.util.Scanner;


@Component
@RequiredArgsConstructor
@Order(0)
public class ConsoleController implements CommandLineRunner {
    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final SOUserService sOUserService;
    private final TagService tagService;
    private final VoteService voteService;
    private SOUser loggedInUser;
    private boolean loggedIn = false;
    private boolean exitApp = false;

    @Override
    @SuppressWarnings("Duplicates")
    public void run(String... args) {
        println("Welcome to the SlackOverflow application.");


        while (!exitApp) {
            while (!loggedIn) {
                println("Type \"login\" to enter the application or \"register\" to register as a new user.");
                String loginRegisterCommand = scanner.next().trim().toLowerCase();
                switch (loginRegisterCommand) {
                    case "login":
                        try {
                            print("Username: ");
                            String username = scanner.next().trim();
                            print("Password: ");
                            String password = scanner.next().trim();
                            loggedInUser = sOUserService.loginSOUser(username, password);
                            loggedIn = true;    // exit while
                        } catch (InvalidNameOrPasswordException e) {
                            print("Invalid name or password! ");
                        }
                        break;

                    case "register":
                        try {
                            print("Username: ");
                            String username = scanner.next().trim();
                            print("Password: ");
                            String password = scanner.next().trim();
                            loggedInUser = sOUserService.registerSOUser(username, password);
                            loggedIn = true;
                        } catch (NameAlreadyExistsException e) {
                            print("Name already taken, please choose a different one! ");
                        }
                        break;
                    default:
                        print("Invalid command. ");
                }
            }


            println("\nWelcome! Logged in as \"" + loggedInUser.getSOUsername() + "\".");
            println("Type \"help\" to see a list of available commands");
            boolean handleCommand = true;
            while (handleCommand && !exitApp) {
                print("\nCommand: ");
                String command = scanner.next().trim();
                switch (command) {
                    case "help":
                        handleHelp();
                        break;
                    case "list all":
                        handleListAll();
                        break;
                    case "ask":
                        handleAsk();
                        break;
                    case "filter tag":
                        handleFilterTagTitle(true);
                        break;
                    case "filter title":
                        handleFilterTagTitle(false);
                        break;
                    case "logout":
                        loggedIn = false;
                        handleCommand = false;
                        break;
                    case "exit":
                        handleCommand = false;
                        exitApp = true;
                        break;

                    // ================== Feature 2 ==================

                    case "list one":
                        handleListOne();
                        break;

                    case "answer":
                        handleAnswer();
                        break;

                    case "edit answer":
                        handleEditDelete(true); // edit the answer
                        break;

                    case "delete answer":
                        handleEditDelete(false); // delete the answer
                        break;

                    // ================== Feature 3 ==================

                    case "upvote question":
                        handleQuestionVote(true);   // upvote question
                        break;
                    case "downvote question":
                        handleQuestionVote(false);  // downvote question
                        break;

                    case "upvote answer":
                        handleAnswerVote(true);   // upvote answer
                        break;
                    case "downvote answer":
                        handleAnswerVote(false);  // downvote answer
                        break;

                    default:
                        println("Invalid command. Try again.");
                        handleHelp();
                        break;
                }
            }
        }
    }

    private void handleHelp() {
        println("Available commands:");
        println("\"list all\" - show all questions");
        println("\"ask\" - ask a new question");
        println("\"filter tag\" - filter the questions by tag");
        println("\"filter title\" - filter the questions by text title");
        println("\"logout\" - logout from the application");
        println("\"exit\" - stop the application\n");

        println("\"list one\" - show one specific question with associated answers");
        println("\"answer\" - answer one specific question");
        println("\"edit answer\" - edit one specific question, provided it is done by the author of it");
        println("\"delete answer\" - delete one specific question, provided it is done by the author of it\n");

        println("\"upvote question\" - upvote a specific question, provided it is not done by the author of it");
        println("\"downvote question\" - downvote a specific question, provided it is not done by the author of it");
        println("\"upvote answer\" - upvote a specific answer, provided it is not done by the author of it");
        println("\"downvote answer\" - downvote a specific answer, provided it is not done by the author of it");
    }

    private void handleListAll() {
        println("\nThese are the questions:");
        questionService.listAllQuestions().forEach(System.out::println);
        sOUserService.findAllSOUsers().forEach(
                soUser -> println(soUser.getSOUsername() + " score " +
                        sOUserService.calculateSOUserScore(soUser).getScore())
        );
    }

    private void handleFilterTagTitle(boolean value) {
        String tagTitleWord = value ? "tag" : "title";
        print(tagTitleWord.substring(0, 1).toUpperCase() + tagTitleWord.substring(1) + " to search for: ");
        String tagTitleName = scanner.next();
        if (value) {
            try {
                questionService.filterByTag(tagTitleName).forEach(System.out::println);
            } catch (TagNotFoundException e) {
                println("There are no tags with that name.");
            }
        } else {
            questionService.filterByTitle(tagTitleName).forEach(System.out::println);
        }

    }

    private void handleAsk() {
        boolean answered = false;
        print("Question title: ");
        String title = scanner.next();
        print("Question text: ");
        String text = scanner.next();
        Question questionToBeCreated = new Question(loggedInUser.getId(), title, text);
        questionService.askQuestion(questionToBeCreated);
        print("Available tags: ");
        tagService.listTags().forEach(tag -> print(tag.getTagName() + " "));

        while (!answered) {
            println("Do you wish to add any tags to your question? [yes/no]");
            String yesNo = scanner.next().trim().toLowerCase();
            if (yesNo.indexOf('y') >= 0) {
                println("Specify the tags by separating them with a space \" \". " +
                        "The tags that are not existent will be created and assigned " +
                        "to your question.");
                print("Tags to be added: ");
                String tagLine = scanner.next();
                questionService.addTagsToQuestion(tagLine, questionToBeCreated);
                println("Question " + questionToBeCreated.getId() + " was created.");
                answered = true;
            } else {
                if (yesNo.indexOf('n') >= 0) {
                    println("Question " + questionToBeCreated.getId() + " was created.");
                    answered = true;
                } else
                    println("Invalid command.");
            }
        }
    }


    // ================== Feature 2 handlers ==================


    private void handleListOne() {
        print("Id of the question to list: ");
        String id = scanner.next();
        try {
            int qId = Integer.parseInt(id);
            try {
                Question question = questionService.findById(qId);
                println(question.toString());
                answerService.listAnswersForQuestion(question).forEach(
                        answer -> println("   " + answer.toString())
                );
            } catch (QuestionNotFoundException e) {
                println("There is no question with id = " + qId);
            }
        } catch (NumberFormatException e) {
            println("Invalid id. Please introduce a number.");
        }
    }


    private void handleAnswer() {
        print("Id of the question to answer: ");
        String id = scanner.next();
        try {
            int qId = Integer.parseInt(id);
            print("Text of the answer: ");
            String answerText = scanner.next();
            try {
                answerService.saveAnswer(qId, loggedInUser.getId(), answerText);
                println("You answer was created.");
            } catch (QuestionNotFoundException e) {
                println("There is no question with id = " + qId);
            }
        } catch (NumberFormatException e) {
            println("Invalid id. Please introduce a number.");
        }
    }

    private void handleEditDelete(boolean value) {
        String editDeleteWord = value ? "edit" : "delete";
        print("Id of the answer to " + editDeleteWord + ": ");
        String id = scanner.next();
        try {
            int aId = Integer.parseInt(id);
            try {
                if (value) {    // choose between edit and delete, true means edit
                    print("New text of the answer: ");
                    String newText = scanner.next();
                    answerService.editAnswer(aId, loggedInUser.getId(), newText);
                } else {
                    answerService.deleteAnswer(aId, loggedInUser.getId());
                }
                println("Your answer was " + editDeleteWord + "ed.");
            } catch (AnswerNotFoundException e) {
                println("There is no question with id = " + aId + ".");
            } catch (InvalidActionException e) {
                println(e.getMessage());
            }
        } catch (NumberFormatException e) {
            println("Invalid id. Please introduce a number.");
        }
    }


    // ================== Feature 3 handlers ==================


    private void handleQuestionVote(boolean value) {
        String voteType = value ? "upvote" : "downvote";
        print("Id of the question to " + voteType + ": ");
        String id = scanner.next();
        try {
            int qId = Integer.parseInt(id);
            try {
                voteService.voteQuestion(qId, loggedInUser.getId(), value);
                println("Your " + voteType + " was registered.");
            } catch (QuestionNotFoundException e) {
                println("There is no question with id = " + qId + ".");
            } catch (InvalidActionException e) {
                println(e.getMessage());
            } catch (AlreadyVotedException e) {
                println("You cannot " + voteType + " multiple times the same question.");
            }
        } catch (NumberFormatException e) {
            println("Invalid id. Please introduce a number.");
        }
    }

    private void handleAnswerVote(boolean value) {
        String voteType = value ? "upvote" : "downvote";
        print("Id of the answer to " + voteType + ": ");
        String id = scanner.next();
        try {
            int aId = Integer.parseInt(id);
            try {
                voteService.voteAnswer(aId, loggedInUser.getId(), value);
                println("Your " + voteType + " was registered.");
            } catch (AnswerNotFoundException e) {
                println("There is no answer with id = " + aId + ".");
            } catch (InvalidActionException e) {
                println(e.getMessage());
            } catch (AlreadyVotedException e) {
                println("You cannot " + voteType + " multiple times the same answer.");
            }
        } catch (NumberFormatException e) {
            println("Invalid id. Please introduce a number.");
        }
    }


    private void println(String value) {
        System.out.println(value);
    }

    private void print(String value) {
        System.out.print(value);
    }
}
