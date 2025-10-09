package seedu.address.model.payment;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class Payment {
    private final Amount amount; // note: a separate class for amount to handle formatting and negative values
    private final LocalDate date;
    private String remarks;
    private final LocalDateTime recordedAt;

    public Payment(Amount amount, LocalDate date, String remarks, LocalDateTime recordedAt) {
        this.amount = amount;
        this.date = date;
        this.remarks = remarks;
        this.recordedAt = recordedAt;
    }

    public Payment(Amount amount, LocalDate date, LocalDateTime recordedAt) {
        this(amount, date, null, recordedAt);
    }

    public Amount getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Optional<String> getRemarks() {
        return Optional.ofNullable(remarks);
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    @Override
    public String toString() {
        return String.format("$%s on %s%s",
                amount, date, remarks == null ? "" : " (" + remarks + ")");
    }

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
