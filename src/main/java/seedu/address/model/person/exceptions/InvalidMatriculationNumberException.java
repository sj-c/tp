package seedu.address.model.person.exceptions;

/**
 * Signals that the provided matriculation number is invalid.
 */
public class InvalidMatriculationNumberException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidMatriculationNumberException} with a detailed error message
     * that includes the invalid matriculation number provided by the user.
     *
     * @param input the invalid matriculation number that caused this exception
     */
    public InvalidMatriculationNumberException(String input) {
        super("Invalid matriculation number: " + input
            + ". Expected format: A########X (e.g. A01234567X).");
    }

}
