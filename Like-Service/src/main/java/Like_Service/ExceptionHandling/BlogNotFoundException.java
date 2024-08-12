package Like_Service.ExceptionHandling;

public class BlogNotFoundException extends RuntimeException{

    public BlogNotFoundException(String message) {
        super(message);
    }

}
