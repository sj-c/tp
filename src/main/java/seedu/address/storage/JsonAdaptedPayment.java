package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.payment.Amount;
import seedu.address.model.payment.Payment;

/**
 * Jackson-friendly version of {@code Payment}.
 */
public class JsonAdaptedPayment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Payment's %s field is missing";

    private final String amount; // e.g. "12.34"
    private final String date; // yyyy-MM-dd
    private final String remarks; // optional, may be null
    private final String recordedAt; // ISO-8601, e.g. 2025-10-15T14:23:05.123

    /**
     * Constructs a {@code JsonAdaptedPayment} with the given JSON properties.
     */
    @JsonCreator
    public JsonAdaptedPayment(@JsonProperty("amount") String amount,
                              @JsonProperty("date") String date,
                              @JsonProperty("remarks") String remarks,
                              @JsonProperty("recordedAt") String recordedAt) {
        this.amount = amount;
        this.date = date;
        this.remarks = remarks;
        this.recordedAt = recordedAt; // may be null for older save files
    }

    /**
     * Converts a given {@code Payment} into this class for Jackson use.
     */
    public JsonAdaptedPayment(Payment source) {
        this.amount = source.getAmount().toString();
        this.date = source.getDate().toString();
        this.remarks = source.getRemarks(); // may be null
        this.recordedAt = source.getRecordedAt().toString();
    }

    /**
     * Converts this Jackson-friendly adapted payment object into the model's {@code Payment} object.
     *
     * @throws IllegalValueException if any field data constraints are violated.
     */
    public Payment toModelType() throws IllegalValueException {
        if (amount == null || amount.isBlank()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "amount"));
        }
        if (date == null || date.isBlank()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }

        final Amount modelAmount;
        try {
            modelAmount = Amount.parse(amount.trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Invalid amount: " + amount);
        }

        final LocalDate modelDate;
        try {
            modelDate = LocalDate.parse(date.trim());
        } catch (Exception e) {
            throw new IllegalValueException("Invalid payment date: " + date + " (expected yyyy-MM-dd)");
        }

        final LocalDateTime modelRecordedAt;
        try {
            modelRecordedAt = (recordedAt == null || recordedAt.isBlank())
                ? LocalDateTime.now()
                : LocalDateTime.parse(recordedAt.trim());
        } catch (Exception e) {
            throw new IllegalValueException("Invalid recordedAt: " + recordedAt + " (expected ISO-8601)");
        }

        return new Payment(modelAmount, modelDate, remarks, modelRecordedAt);
    }
}
