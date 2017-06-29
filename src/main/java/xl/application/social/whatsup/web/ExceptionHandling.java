package xl.application.social.whatsup.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xl.application.social.whatsup.exception.ArgumentNotValidException;
import xl.application.social.whatsup.exception.ResourceNotFoundException;
import xl.application.social.whatsup.web.dto.ErrorReply;

import java.util.stream.Collectors;

/**
 * Exception handling for all controllers.
 */
@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler
    public ResponseEntity<ErrorReply> handleValidationFailure(BindException e) {
        String fields = e.getFieldErrors()
                .stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(", "));
        String message = "invalid argument supplied for " + fields;
        ErrorReply reply = new ErrorReply("bad request", message);
        return new ResponseEntity<>(reply, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorReply> handleArgumentNotValid(ArgumentNotValidException e) {
        ErrorReply reply = new ErrorReply("bad request", e.getMessage());
        return new ResponseEntity<>(reply, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorReply> handleResourceNotFound(ResourceNotFoundException e) {
        ErrorReply reply = new ErrorReply("resource not found", e.getMessage());
        return new ResponseEntity<>(reply, HttpStatus.NOT_FOUND);
    }
}
