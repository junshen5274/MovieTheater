package com.jpmc.theater;

public class InvalidShowingException extends Exception {
    
    private static final long serialVersionUID = 7718828512143293559L;

    public InvalidShowingException() {
        super();
    }

    public InvalidShowingException(String message) {
        super(message);
    }

    public InvalidShowingException(Throwable cause) {
        super(cause);
    }

    public InvalidShowingException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidShowingException(String message, Throwable cause, boolean enabelSuppression, boolean writableStackTrace) {
        super(message, cause, enabelSuppression, writableStackTrace);
    }

}
