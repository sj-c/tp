package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.payment.Payment;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final List<Payment> payments;
    private final boolean archived;

    /**
     * Minimal constructor (AB3 default fields). Starts with no payments.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.archived = false;
        this.payments = Collections.unmodifiableList(new ArrayList<>()); // empty immutable list
    }

    /**
     * Full constructor including payments.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Set<Tag> tags, boolean archived, List<Payment> payments) {
        requireAllNonNull(name, phone, email, address, tags, payments);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.archived = archived;
        this.payments = Collections.unmodifiableList(new ArrayList<>(payments));
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public boolean isArchived() { return archived; }

    /** NEW: copy-with for archived flag */
    public Person withArchived(boolean newArchived) {
        return new Person(name, phone, email, address, tags, newArchived, payments);
    }

    /** Returns an immutable view of the payments list. */
    public List<Payment> getPayments() {
        return payments;
    }

    /**
     * Returns a new Person that is identical to this person but with one extra payment appended.
     * This preserves immutability.
     */
    public Person withAddedPayment(Payment payment) {
        List<Payment> updated = new ArrayList<>(this.payments);
        updated.add(payment);
        return new Person(name, phone, email, address, tags, archived, updated);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * (Note: payments are intentionally not part of equality to preserve AB3 semantics.)
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof Person)) return false;
        Person o = (Person) other;
        return name.equals(o.name)
                && phone.equals(o.phone)
                && email.equals(o.email)
                && address.equals(o.address)
                && tags.equals(o.tags)
                && archived == o.archived;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("archived", archived)
                .add("paymentsCount", payments.size())
                .toString();
    }
}
