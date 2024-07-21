package ium.pethub.exception;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> EntityNotFoundExceptionHandler(EntityNotFoundException e) {
        log.error("EntityNotFoundException : " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> JwtExceptionHandler(JwtException e){
        log.error("JwtException : " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> NullPointerExceptionHandler(NullPointerException e){
        log.error("NullPointerException : " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> IllegalStateExceptionHandler(IllegalStateException e ){
        log.error("IllegalStateException : " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> AccessDeniedExceptionHandler(AccessDeniedException e){
        log.error("AccessDeniedException : " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<?> FileUploadExceptionHandler(FileUploadException e){
        log.error("FileUploadException : " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<?> AlreadyExistExceptionHandler(AlreadyExistException e){
        log.error("AlreadyExistException : " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
    }
}
