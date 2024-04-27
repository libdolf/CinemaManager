package br.com.libdolf.cinemamanager.exceptions.handler;

import br.com.libdolf.cinemamanager.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(FilmNotFoundException.class)
    public ResponseEntity<ExceptionsDetails> handleFilmNotFoundException(FilmNotFoundException ex){
        return new ResponseEntity<>(
                ExceptionsDetails.builder()
                        .title("User not found")
                        .details(ex.getMessage())
                        .developerMessage(ex.getClass().getName())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionsDetails> handleBadCredentialsException(BadCredentialsException ex){
        return new ResponseEntity<>(
                ExceptionsDetails.builder()
                        .title("username, email or password incorrect")
                        .details(ex.getMessage())
                        .developerMessage(ex.getClass().getName())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionsDetails> handleUserAlreadyExistsException(UserAlreadyExistsException ex){
        return new ResponseEntity<>(
                ExceptionsDetails.builder()
                        .title("User already exists")
                        .details(ex.getMessage())
                        .developerMessage(ex.getClass().getName())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<ExceptionsDetails> handleUserNotAuthorizedException(UserNotAuthorizedException ex){
        return new ResponseEntity<>(
                ExceptionsDetails.builder()
                        .title("User not authorized")
                        .details(ex.getMessage())
                        .developerMessage(ex.getClass().getName())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionsDetails> handleUserNotFoundException(UserNotFoundException ex){
        return new ResponseEntity<>(
                ExceptionsDetails.builder()
                        .title("User not found")
                        .details(ex.getMessage())
                        .developerMessage(ex.getClass().getName())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ExceptionsDetails> handleRoleNotFoundException(RoleNotFoundException ex){
        return new ResponseEntity<>(
                ExceptionsDetails.builder()
                        .title("Role not found")
                        .details(ex.getMessage())
                        .developerMessage(ex.getClass().getName())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
       var exceptionDetails = ExceptionsDetails.builder()
                .title(ex.getCause().getMessage())
                .details(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .status(statusCode.value())
                .timestamp(LocalDateTime.now())
                .build();
       return new ResponseEntity<>(exceptionDetails, headers, statusCode);
    }

}
