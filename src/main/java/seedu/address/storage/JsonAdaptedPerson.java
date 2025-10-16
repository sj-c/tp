package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.payment.Payment;
import seedu.address.model.person.Email;
import seedu.address.model.person.MatriculationNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String matriculationNumber; // ✅ lowercase field name
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedPayment> payments = new ArrayList<>();

    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("matriculationNumber") String matriculationNumber,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("payments") List<JsonAdaptedPayment> payments) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.matriculationNumber = matriculationNumber;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (payments != null) {
            this.payments.addAll(payments);
        }
    }

    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        matriculationNumber = source.getMatriculationNumber().value; // ✅ lowercase
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        source.getPayments().stream()
                .map(JsonAdaptedPayment::new)
                .forEach(this.payments::add);
    }

    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (matriculationNumber == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, MatriculationNumber.class.getSimpleName()));
        }
        if (!MatriculationNumber.isValidMatriculationNumber(matriculationNumber)) { // ✅ reference class name
            throw new IllegalValueException(MatriculationNumber.MESSAGE_CONSTRAINTS);
        }
        final MatriculationNumber modelMatriculationNumber = new MatriculationNumber(matriculationNumber);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final List<Payment> modelPayments = new ArrayList<>();
        for (JsonAdaptedPayment jap : payments) {
            modelPayments.add(jap.toModelType());
        }

        return new Person(modelName, modelPhone, modelEmail, modelMatriculationNumber, modelTags,
                modelPayments);
    }
}
