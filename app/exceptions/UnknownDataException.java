package exceptions;

public class UnknownDataException extends Exception {
    public UnknownDataException() {
    }

    public UnknownDataException(String message) {
        super(message);
    }
}
