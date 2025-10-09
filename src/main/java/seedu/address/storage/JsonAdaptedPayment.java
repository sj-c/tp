package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.model.payment.Amount;
import seedu.address.model.payment.Payment;

import java.time.LocalDate;

/** Jackson-friendly version of {@code Payment}. */
public class JsonAdaptedPayment {

    private final String amount;   // e.g. "12.34" (keep as string to avoid float issues)
    private final String date;     // ISO-8601, e.g. "2025-10-09"
    private final String remarks;  // optional

    @JsonCreator
    public JsonAdaptedPayment(@JsonProperty("amount") String amount,
                              @JsonProperty("date") String date,
                              @JsonProperty("remarks") String remarks) {
        this.amount = amount;
        this.date = date;
        this.remarks = remarks;
    }

    public JsonAdaptedPayment(Payment source) {
        this.amount = source.getAmount().toString();
        this.date = source.getDate().toString();
        this.remarks = source.getRemarks();
    }

    public Payment toModelType() {
        return new Payment(
                Amount.parse(amount),
                LocalDate.parse(date),
                remarks
        );
    }
}
