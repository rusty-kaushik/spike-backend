package Like_Service.ResponseHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler<B> {
    public static ResponseEntity<Object> response(HttpStatus status, String message, Object responseData) {
        Map<String, Object> response = new HashMap<>();
        response.put("HttpStatus", status);
        response.put("message", message);
        response.put("data", responseData);
        return new ResponseEntity<>(response, status);

    }
}

