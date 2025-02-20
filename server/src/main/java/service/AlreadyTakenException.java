package service;

/**
 * Indicates there was an error where the username was already taken
 */
public class AlreadyTakenException extends Exception{
    public AlreadyTakenException(String message) {
        super(message);
    }
}
