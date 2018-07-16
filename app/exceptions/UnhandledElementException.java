package exceptions;

public class UnhandledElementException extends Exception {
    public UnhandledElementException() {
    }

    public UnhandledElementException(String message) {
        super(message);
    }
}
