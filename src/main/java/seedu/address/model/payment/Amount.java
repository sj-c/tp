package seedu.address.model.payment;

import java.math.BigDecimal;

public final class Amount {
    public static final String MESSAGE_CONSTRAINTS =
            "Invalid amount (must be positive, up to 2 decimal places).";
    // Accepts 12 total digits and 2 decimal places (tune as needed)
    private static final int SCALE = 2;

    public final BigDecimal value;

    public Amount(String rawString) {
        this.value = parse(rawString);
        validate(); // ensure it's not negative
    }

    public Amount(BigDecimal value) {
        this.value = value.setScale(SCALE, BigDecimal.ROUND_UNNECESSARY);
        validate();
    }

    private static BigDecimal parse(String raw) {
        BigDecimal v = new BigDecimal(raw);
        return v.setScale(SCALE); // may throw if more than 2 dp
    }

    private void validate() {
        if (value.signum() <= 0) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    @Override
    public String toString() {
        return value.toPlainString();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Amount) && value.equals(((Amount)o).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
