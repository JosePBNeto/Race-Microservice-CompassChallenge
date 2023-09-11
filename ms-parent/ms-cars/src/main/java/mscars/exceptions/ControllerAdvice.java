package mscars.exceptions;

import com.mongodb.MongoWriteException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    CustomResponse customErrorDetails;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        CustomResponse errorDetails = new CustomResponse(LocalDateTime.now(),
                ex.getMostSpecificCause().getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        CustomResponse errorDetails = new CustomResponse(LocalDateTime.now(),
                ex.getFieldError().getDefaultMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<CustomResponse> handleIdNotFound(IdNotFoundException ex, WebRequest request) {
        customErrorDetails = new CustomResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(customErrorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<CustomResponse> handleDuplicateKeyException(MongoWriteException ex, WebRequest request) {
        customErrorDetails = new CustomResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        if (ex.getMessage().contains("model")) {
            customErrorDetails = new CustomResponse(LocalDateTime.now(), "Car model already registered", request.getDescription(false));
        } else if (ex.getMessage().contains("pilot")){
            customErrorDetails = new CustomResponse(LocalDateTime.now(), "Pilot name already registered", request.getDescription(false));
        }
        return new ResponseEntity<>(customErrorDetails, HttpStatus.CONFLICT);
    }

}
