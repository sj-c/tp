package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateMatriculationNumberException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * <p>
 * Uniqueness rules:
 * 1. No two persons may share the same matriculation number.
 * 2. No two persons may have the same name, email, and phone combination.
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person (same name, phone, and email).
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Returns true if the list contains another person with the same matriculation number.
     */
    public boolean hasDuplicateMatriculationNumber(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(p ->
                p.getMatriculationNumber().equals(toCheck.getMatriculationNumber()));
    }

    /**
     * Adds a person to the list.
     * The person must not already exist and must have a unique matriculation number.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);

        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }

        if (hasDuplicateMatriculationNumber(toAdd)) {
            throw new DuplicateMatriculationNumberException();
        }

        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity and matriculation number of {@code editedPerson} must not
     * duplicate another person in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        boolean duplicateMatric = internalList.stream()
                .anyMatch(p -> !p.equals(target)
                        && p.getMatriculationNumber().equals(editedPerson.getMatriculationNumber()));

        if (duplicateMatric) {
            throw new DuplicateMatriculationNumberException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent {@code Person} from the list.
     *
     * <p>
     * The person to remove must exist in the list. Equality is determined by
     * {@link Person#equals(Object)}.
     * </p>
     *
     * @param toRemove the person to be removed from the list; must not be {@code null}
     * @throws NullPointerException    if {@code toRemove} is {@code null}
     * @throws PersonNotFoundException if the person to remove does not exist in the list
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        if (!matriculationNumbersAreUnique(persons)) {
            throw new DuplicateMatriculationNumberException();
        }

        internalList.setAll(persons);
    }

    public ObservableList<Person> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniquePersonList
                && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    // ---------- Helper methods ----------

    private boolean personsAreUnique(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePerson(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean matriculationNumbersAreUnique(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).getMatriculationNumber()
                        .equals(persons.get(j).getMatriculationNumber())) {
                    return false;
                }
            }
        }
        return true;
    }
}
