package com.adamchilds.nativejna.exception;

/**
 * {@code ProcessNotFoundException} is a {@link RuntimeException} thrown when a given process cannot be found.
 *
 * @author Adam Childs
 * @since 1.0
 */
public class ProcessNotFoundException extends RuntimeException {

    /**
     * {@inheritDoc}
     */
    public ProcessNotFoundException() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public ProcessNotFoundException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public ProcessNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}