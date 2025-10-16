package seedu.address.model.person.exceptions;

/**
 * Signals that the operation would result in duplicate matriculation numbers.
 */
public class DuplicateMatriculationNumberException extends RuntimeException {
    public DuplicateMatriculationNumberException() {
        super("Operation would result in duplicate matriculation numbers");
    }
}
