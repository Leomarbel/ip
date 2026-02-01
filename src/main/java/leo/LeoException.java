package leo;

/** Custom exception class for Leo application-specific errors. */
public class LeoException extends Exception {

    /**
     * Creates a LeoException with the specified error message.
     *
     * @param msg The error message describing the exception.
     */
    public LeoException(String msg) {
        super(msg);
    }
}

