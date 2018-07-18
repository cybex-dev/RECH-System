package exceptions;

public class UnhandledElementException extends Exception {
    public UnhandledElementException() {
        super();
    }

    public UnhandledElementException(String message) {
        super(message);
    }
}
