package Like_Service.ExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(BlogNotFoundException.class)
    public ResponseEntity BlogNotFoundExceptionHandling(BlogNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation Failed");
        response.put("message", ex.getMessage());
return ResponseEntity.badRequest().body(response);
    }

  

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleIllegalArgumentException(UserNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        //response.put("timestamp", System.currentTimeMillis());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation Failed");
        response.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> RuntimeException(RuntimeException ex) {
        Map<String, Object> response = new HashMap<>();
        //response.put("timestamp", System.currentTimeMillis());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Error Occured");
        response.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

}
