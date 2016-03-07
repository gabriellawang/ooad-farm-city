package exception;

/**
 * The FarmCityControlException class represents the exception that will be
 * thrown when the invalid input is passed to the controller.
 *
 * @author G3-T02
 */
public class FarmCityControlException extends Exception {

    /**
     * Constructs an FarmCityControlException
     *
     * @param message The message the exception will pass
     */
    public FarmCityControlException(String message) {
        super(message);
    }
}
