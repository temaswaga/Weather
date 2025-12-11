package exceptions;

public class InvalidSessionException extends NullPointerException{
    public InvalidSessionException(String message) {
        super(message);
    }
}
