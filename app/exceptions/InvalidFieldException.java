package exceptions;

public class InvalidFieldException extends Exception {

    public InvalidFieldException() {
        super();
    }

    public InvalidFieldException(String error) {
        super(error);
    }
}
