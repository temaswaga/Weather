package exceptions;

public class UnauthorizedDeleteAttemptException extends RuntimeException {
    public UnauthorizedDeleteAttemptException(String message) {
        super(message);
    }
}
