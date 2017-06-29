package xl.application.social.whatsup.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xl.application.social.whatsup.exception.BadArgumentException;
import xl.application.social.whatsup.exception.ResourceNotFoundException;

/**
 * Exception handling for all controllers.
 */
@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler
    public ResponseEntity<String> handleBadArgumentException(BadArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
