package com.jpmc.theater;

public class InvalidReserveException extends Exception {
    
    private static final long serialVersionUID = 7718828512143293558L;

    public InvalidReserveException() {
        super();
    }

    public InvalidReserveException(String message) {
        super(message);
    }

    public InvalidReserveException(Throwable cause) {
        super(cause);
    }

    public InvalidReserveException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidReserveException(String message, Throwable cause, boolean enabelSuppression, boolean writableStackTrace) {
        super(message, cause, enabelSuppression, writableStackTrace);
    }

}
