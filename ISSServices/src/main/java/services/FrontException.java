package services;

public class FrontException extends Exception{
    public FrontException() {
    }

    public FrontException(String message) {
        super(message);
    }

    public FrontException(String message, Throwable cause) {
        super(message, cause);
    }

}
