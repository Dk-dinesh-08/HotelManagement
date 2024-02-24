package com.exceptionhandling;


/**
 * The CustomException class represents a custom exception used for handling specific errors
 * in the application.
 *
 * <p>It extends the standard Java Exception class to create custom exceptions with specific
 * error messages.</p>
 *
 * @author Dinesh Karmegam(expleo)
 * @since feb 21 2024
 */
@SuppressWarnings("serial")
public class CustomException extends Exception {
    /**
     * Constructs a new CustomException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage method)
     */
    public CustomException(String message) {
        super(message);
    }
}

