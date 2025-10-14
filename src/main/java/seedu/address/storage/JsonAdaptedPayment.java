package seedu.address.storage;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.payment.Amount;
import seedu.address.model.payment.Payment;

/**
 * Jackson-friendly version of {@code Payment}.
 */
public class JsonAdaptedPayment {

    private final String amount; // e.g. "12.34" (keep as string to avoid float issues)
    private final String date; // ISO-8601, e.g. "2025-10-09"
    private final String remarks; // optional

    /**
     * Constructs a {@code JsonAdaptedPayment} with the given payment details.
     *
     * @param amount  The amount as a string.
     * @param date    The date in ISO-8601 format.
     * @param remarks The optional remarks.
     */
    @JsonCreator
    public JsonAdaptedPayment(@JsonProperty("amount") String amount,
                              @JsonProperty("date") String date,
                              @JsonProperty("remarks") String remarks) {
        this.amount = amount;
        this.date = date;
        this.remarks = remarks;
    }

    /**
     * Converts a given {@code Payment} into this class for Jackson use.
     *
     * @param source The {@code Payment} to convert.
     */
    public JsonAdaptedPayment(Payment source) {
        this.amount = source.getAmount().toString();
        this.date = source.getDate().toString();
        this.remarks = source.getRemarks();
    }

    /**
     * Converts this Jackson-friendly adapted object into the model's {@code Payment} object.
     *
     * @return A {@code Payment} instance with the stored details.
     */
    public Payment toModelType() {
        return new Payment(
                Amount.parse(amount),
                LocalDate.parse(date),
                remarks
        );
    }
}
