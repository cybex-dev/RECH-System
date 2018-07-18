package exceptions;

public class IncompleteDataException extends Exception {
    public IncompleteDataException() {
         super();
    }

    public IncompleteDataException(String message) {
        super(message);
    }
}
