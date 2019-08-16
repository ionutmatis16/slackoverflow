package ro.utcn.sd.mid.assign1.slackoverflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.utcn.sd.mid.assign1.slackoverflow.dto.ErrorDTO;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.AlreadyVotedException;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.AnswerNotFoundException;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.InvalidNameOrPasswordException;
import ro.utcn.sd.mid.assign1.slackoverflow.exceptions.NameAlreadyExistsException;

@Component
@RestControllerAdvice //advice we give to rest controller to tell them how they should handle errors
public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidNameOrPasswordException.class)
    public ErrorDTO handlerInvalidLoginException(InvalidNameOrPasswordException e) {
        return new ErrorDTO("LoginException");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NameAlreadyExistsException.class)
    public ErrorDTO handlerAlreadyExistsException(NameAlreadyExistsException e) {
        return new ErrorDTO("AlreadyExistsException");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AnswerNotFoundException.class)
    public ErrorDTO handlerAnswerNotFoundException(AnswerNotFoundException e) {
        return new ErrorDTO("AnswerNotFoundException");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyVotedException.class)
    public ErrorDTO handlerAnswerNotFoundException(AlreadyVotedException e) {
        return new ErrorDTO("AlreadyVotedException");
    }
}
