package seedu.address.model.payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Immutable record of a single payment made by a member.
 * <p>
 * Contains the business date of the payment, the amount, optional remarks,
 * and a system timestamp ({@code recordedAt}) for chronology.
 */
public class Payment {
    private final Amount amount;
    private final LocalDate date;
    private final String remarks;
    private final LocalDateTime recordedAt;

    /**
     * Constructs a {@code Payment}.
     *
     * @param amount     monetary amount (must be valid per {@link Amount})
     * @param date       business date of the payment
     * @param remarks    optional remarks (may be {@code null})
     * @param recordedAt system timestamp when this record was created
     */
    public Payment(Amount amount, LocalDate date, String remarks, LocalDateTime recordedAt) {
        this.amount = amount;
        this.date = date;
        this.remarks = remarks;
        this.recordedAt = recordedAt;
    }

    /**
     * Constructs a {@code Payment} without remarks.
     *
     * @param amount     monetary amount
     * @param date       business date of the payment
     * @param recordedAt system timestamp when this record was created
     */
    public Payment(Amount amount, LocalDate date, LocalDateTime recordedAt) {
        this(amount, date, null, recordedAt);
    }

    /** Returns the payment amount. */
    public Amount getAmount() {
        return amount;
    }

    /** Returns the business date of the payment. */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the optional remarks.
     *
     * @return {@code Optional.empty()} if no remarks were provided
     */
    public Optional<String> getRemarks() {
        return Optional.ofNullable(remarks);
    }

    /** Returns the system timestamp when this record was created. */
    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    /**
     * Human-readable summary including amount, date, and remarks (if present).
     */
    @Override
    public String toString() {
        return String.format("$%s on %s%s",
                amount, date, remarks == null ? "" : " (" + remarks + ")");
    }

    /**
     * Structural equality across amount, date, remarks, and recordedAt.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Payment)) {
            return false;
        }
        Payment p = (Payment) o;
        return amount.equals(p.amount)
                && date.equals(p.date)
                && ((remarks == null && p.remarks == null) || (remarks != null && remarks.equals(p.remarks)))
                && recordedAt.equals(p.recordedAt);
    }

    @Override
    public int hashCode() {
        return amount.hashCode() * 31 + date.hashCode();
    }
}
